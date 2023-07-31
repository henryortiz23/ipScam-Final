package com.softs.hn.ip.ipscam;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Dialog2 {
    public Custom_Dialog2(Context context, String titulo, String msg, int icono) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Button bOk = dialog.findViewById(R.id.btnAceptar);
        TextView txtTitulo= dialog.findViewById(R.id.titulo);
        TextView txtMsg= dialog.findViewById(R.id.msg);
        ImageView imgIcono=dialog.findViewById(R.id.icono);
        if(icono==2) {
            imgIcono.setImageResource(R.drawable.not_connection);
        }else if(icono==3){
            imgIcono.setImageResource(R.drawable.no_found);
        }
        txtTitulo.setText(titulo);
        txtMsg.setText(msg);

        bOk.setOnClickListener(v -> dialog.dismiss());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }
}
