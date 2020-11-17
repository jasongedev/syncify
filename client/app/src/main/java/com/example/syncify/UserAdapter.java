package com.example.syncify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, User[] users) {
        super(context, 0, users);
    }

    @Override
    @NotNull
    public View getView(int position, View convertView, @NotNull final ViewGroup parent) {
        final User user = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_user, parent, false);
        }

        // TODO: uncomment and fix once layout files are ready
//        TextView username = convertView.findViewById(R.id.);
//        ImageView circle = convertView.findViewById(R.id.);
//
//        username.setText(user.name);
//
//        if (user.isPlaying) {
//            // TODO: change circle drawable or color to green (whichever works)
//        } else {
//            // TODO: change circle drawable or color to white/red (whichever works)
//        }


        convertView.setOnClickListener(view -> {
            Intent listenerIntent = new Intent(getContext(), ListenerActivity.class);
            listenerIntent.putExtra("HostKey", user.key);
            getContext().startActivity(listenerIntent);
        });

        return convertView;
    }
}