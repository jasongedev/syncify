package com.example.syncify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    Bitmap[] mBitmaps;

    public PlaylistAdapter(Context context, Playlist[] playlists) {
        super(context, 0, playlists);
        mBitmaps = new Bitmap[playlists.length];
        for (int i = 0; i < playlists.length; i++) {
            final int idx = i;
            Thread thread = new Thread(() -> getAndStoreBitmap(playlists[idx].imageUrl, mBitmaps, idx));
            thread.start();
        }
    }

    @Override
    @NotNull
    public View getView(int position, View convertView, @NotNull final ViewGroup parent) {
        final Playlist playlist = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_playlist, parent, false);
        }

        // TODO: uncomment and fix once layout files are ready
//        TextView name = convertView.findViewById(R.id.);
//        ImageView image = convertView.findViewById(R.id.);
//        name.setText(playlist.name);
//        image.setImageBitmap(mBitmaps[position]);

        convertView.setOnClickListener(view -> {
            Intent hostIntent = new Intent(getContext(), HostActivity.class);
            hostIntent.putExtra("PlaylistUri", playlist.uri);
            getContext().startActivity(hostIntent);
        });

        return convertView;
    }

    private void getAndStoreBitmap(String src, Bitmap[] bitmaps, int index) {
        try {
            URL url = new URL(src);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            bitmaps[index] = image;

           /*HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}