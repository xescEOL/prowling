package com.rumba.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumba.objects.Contact;
import com.rumba.objects.LinePChat;
import com.rumba.prowling.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PChatAdapter extends ArrayAdapter<LinePChat> {
    String txtMsg = "";
    public PChatAdapter(Context context, ArrayList<LinePChat> contactos){
        super(context, 0, contactos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listaPersonalizada = convertView;

        if(listaPersonalizada == null){
            listaPersonalizada = LayoutInflater.from(getContext()).inflate(R.layout.itemlist_linepchat, parent, false);
        }

        // Localizamos la posición de los partidos dentro de la list para saber dónde debe ir
        LinePChat linePChat = getItem(position);

        // Recuperamos los textView para después poder sacar datos pantalla
        TextView name = (TextView) listaPersonalizada.findViewById(R.id.textList);
        TextView km = (TextView) listaPersonalizada.findViewById(R.id.txtKm);
        TextView msg = (TextView) listaPersonalizada.findViewById(R.id.txtMsgPChat);
        ImageView imgThumb = (ImageView) listaPersonalizada.findViewById(R.id.imgContact);
        /* Al imageView le asignamos la imagen correspondiente siempre y cuando haya imagen
         * si no hay imagen, escondemos el imageView con visibility GONE
         * */
        name.setText(linePChat.getName());
        msg.setText(linePChat.getMsg());
        km.setText(String.valueOf(linePChat.getKm().intValue()) + "km");

        switch (linePChat.getColor()){
            case 0:
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNameGrey));
                break;
            case 1:
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNameBlue));
                break;
            case 2:
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNameGreen));
                break;
            case 3:
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNamePurple));
                break;
            case 4:
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNameRed));
                break;
        }


        /*if(linePChat.getKm() < 1){
            DecimalFormat df = new DecimalFormat("#.0");
            km.setText(String.format( "%.1f", linePChat.getKm())+"km");
        }else {
            km.setText(String.valueOf(linePChat.getKm().intValue()) + "km");
        }*/

        /*LinearLayout layoutPline = (LinearLayout)listaPersonalizada.findViewById(R.id.layoutLineChat);

        layoutPline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("apretaaaa " + txtMsg);
            }
        });*/

        String thumb = "https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + linePChat.getUid() + "_1.jpg?alt=media";
        Glide.with(getContext())
                .load(thumb)
                .into(imgThumb);

        return listaPersonalizada;
    }
}
