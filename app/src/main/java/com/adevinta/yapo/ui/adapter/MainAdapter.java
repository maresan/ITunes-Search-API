package com.adevinta.yapo.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adevinta.yapo.R;
import com.adevinta.yapo.model.Cancion;
import com.adevinta.yapo.ui.activity.MainActivity;

import java.io.InputStream;
import java.util.ArrayList;

import static com.adevinta.yapo.ui.activity.MainActivity.botonCancionActual;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {

    private ArrayList<Cancion> canciones;
    public static MediaPlayer player = new MediaPlayer();

    public MainAdapter(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    class MainAdapterViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ImageView img_caratula;
        public TextView nombre_cancion;
        public TextView nombre_disco;

        public MainAdapterViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.id_cardview);
            img_caratula = (ImageView) itemView.findViewById(R.id.id_img_caratula);
            nombre_cancion = (TextView) itemView.findViewById(R.id.id_text_nombre_cancion);
            nombre_disco = (TextView) itemView.findViewById(R.id.id_text_nombre_disco);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reproducirCancion(view, getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MainAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cancion, parent, false));
    }

    @Override
    public void onBindViewHolder(final MainAdapterViewHolder holder, final int position) {
        if (canciones.get(position).getArtworkUrl30() != null) {
            new DownloadImageTask(holder.img_caratula).execute(canciones.get(position).getArtworkUrl30());
        }
        holder.nombre_cancion.setText(canciones.get(position).getTrackName());
        holder.nombre_disco.setText(canciones.get(position).getCollectionName());
    }

    @Override
    public int getItemCount() {
        return canciones.size();
    }

    public void reproducirCancion(View view, Integer posicion){
        MainActivity.nombreCancionActual.setText(canciones.get(posicion).getTrackName() + " - " + canciones.get(posicion).getArtistName());
        botonCancionActual.setBackground(view.getResources().getDrawable(R.drawable.ic_pause));
        try {
            player.stop();
            player.release();
            player = new MediaPlayer();
            player.setDataSource(canciones.get(posicion).getPreviewUrl());
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
