package com.softs.hn.ip.ipscam.ui.inicio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.softs.hn.ip.ipscam.Custom_Dialog;
import com.softs.hn.ip.ipscam.HttpGetRequest2;
import com.softs.hn.ip.ipscam.InternetChecker;
import com.softs.hn.ip.ipscam.databinding.FragmentInicioBinding;

import java.util.Objects;

public class InicioFragment extends Fragment {

    private final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int PERMISSION_REQUEST_CAMERA = 700;
    private static final int PERMISSION_REQUEST_IMAGES = 701;
    private Bitmap imageBitmap;
    Uri fileUri;
    TextRecognizer recon = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnEscanearAhora.setOnClickListener(v -> {
            if (solicitarPermisoCamara()) {
                scroppFoto(1);
            }
        });

        binding.btnGaleria.setOnClickListener(v -> {
            /*
            if(solicitarPermisoGaleria()) {
                scroppFoto(2);
            }
             */
            solicitarPermisoGaleria();
        });
        binding.txtPlaca.setFocusableInTouchMode(true);
        binding.txtPlaca.requestFocus();

        binding.btnBuscar.setOnClickListener(v -> {

            if (binding.txtPlaca.getText().toString().length() >= 7) {
/*
                Intent intent = new Intent(this.getContext(), MenuReconocimiento.class);
                intent.putExtra("placa", binding.txtPlaca.getText().toString());
                startActivity(intent);
*/
                comprobar(binding.txtPlaca.getText().toString());
            } else {
                binding.txtPlaca.setError("Formato de placa invalido, debe contener 7 caracteres");

            }
        });

        return root;
    }

    private void extraerTexto() {
        if (imageBitmap != null) {
            InputImage image = InputImage.fromBitmap(imageBitmap, 0);

            recon.process(image)
                    .addOnSuccessListener(visionText -> {
                        CleanText cleanText = new CleanText(visionText.getText(), getContext());
                        String txtResult;
                        txtResult = cleanText.getCleanText();
                        binding.txtPlaca.setText(txtResult);
                        binding.txtPlaca.setSelection(binding.txtPlaca.getText().length());
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "No se pudo extraer el texto", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private final ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(
            new CropImageContract(),
            result -> {
                if (result.isSuccessful()) {
                    fileUri = result.getUriContent();
                    imageBitmap = result.getBitmap(requireContext());
                    binding.txtPlaca.setText("");
                    extraerTexto();
                }
            }
    );

    private void scroppFoto(int option) {
        CropImageOptions cropImageOptions = new CropImageOptions();
        if (option == 1) {
            cropImageOptions.imageSourceIncludeCamera = true;
            cropImageOptions.imageSourceIncludeGallery = false;
        } else {
            cropImageOptions.imageSourceIncludeCamera = false;
            cropImageOptions.imageSourceIncludeGallery = true;

        }
        fileUri = null;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(fileUri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    private void comprobar(String placa) {
        if (InternetChecker.isInternetAvailable(Objects.requireNonNull(getContext()))) {
            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                try {
                    //new HttpGetRequest(getContext()).execute("http://34.125.242.85:8080/api/consultar/" + placa);
                    new HttpGetRequest2(getContext()).execute("https://apex.oracle.com/pls/apex/is1_project/consulta/" + placa);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            new Custom_Dialog(getContext());
        }
    }

    private boolean solicitarPermisoCamara() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            return false;
        } else {
            return true;
        }
    }

    private void solicitarPermisoGaleria() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_IMAGES);
            } else {
                // Permiso READ_MEDIA_IMAGES ya concedido, puedes iniciar la selección de imagen
                scroppFoto(2);
            }
        } else {


            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_IMAGES);
            } else {
                scroppFoto(2);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}