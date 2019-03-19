package com.rumba.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumba.objects.Contact;
import com.rumba.prowling.R;

import java.util.ArrayList;

public class ListContactAdapter extends ArrayAdapter<Contact> {
    public ListContactAdapter(Context context, ArrayList<Contact> contactos){
        super(context, 0, contactos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listaPersonalizada = convertView;

        if(listaPersonalizada == null){
            listaPersonalizada = LayoutInflater.from(getContext()).inflate(R.layout.itemlist_contacts, parent, false);
        }

        // Localizamos la posición de los partidos dentro de la list para saber dónde debe ir
        Contact contactChat = getItem(position);

        // Recuperamos los textView para después poder sacar datos pantalla
        TextView txtName = (TextView) listaPersonalizada.findViewById(R.id.txtName);
        TextView txtMsg = (TextView) listaPersonalizada.findViewById(R.id.txtMsg);
        ImageView imgThumb = (ImageView) listaPersonalizada.findViewById(R.id.imgContact);
        /* Al imageView le asignamos la imagen correspondiente siempre y cuando haya imagen
         * si no hay imagen, escondemos el imageView con visibility GONE
         * */


        String uid = contactChat.getUid();
        txtName.setText(uid);

        txtMsg.setText(contactChat.getMsg() + contactChat.getDate());

        if(!contactChat.getTipo())
            txtMsg.setText("NoTeu: " + txtMsg.getText());

        String thumb =  "https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + uid + "_1.jpg?alt=media";
        Glide.with(getContext())
                .load(thumb)
                .into(imgThumb);

        return listaPersonalizada;
    }
}
