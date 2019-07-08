package com.adevinta.yapo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adevinta.yapo.R;
import com.adevinta.yapo.model.Cancion;
import com.adevinta.yapo.presenter.MainActivityImpl;
import com.adevinta.yapo.presenter.MainActivityPresenter;
import com.adevinta.yapo.ui.adapter.MainAdapter;
import com.adevinta.yapo.view.MainActivityView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private MainActivityPresenter mainActivityPresenter;
    private View include;
    public static TextView nombreCancionActual;
    public static ImageButton botonCancionActual;
    private RecyclerView recyclerView;
    private GridLayoutManager glm;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityPresenter = new MainActivityImpl();
        mainActivityPresenter.onViewAttached(this);

        include = (View) findViewById(R.id.id_include);
        nombreCancionActual = (TextView) include.findViewById(R.id.id_text_nombre_cancion_actual);
        botonCancionActual = (ImageButton) include.findViewById(R.id.id_buttom_musica_actual);

        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        glm = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(glm);

        botonCancionActual.setBackground(getResources().getDrawable(R.drawable.ic_play));
        botonCancionActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAndPause();
            }
        });
    }

    @Override
    public void onCanciones(ArrayList<Cancion> canciones) {
        adapter = new MainAdapter(canciones);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void playAndPause() {
        if (MainAdapter.player != null) {
            if (MainAdapter.player.isPlaying()) {
                MainAdapter.player.pause();
                botonCancionActual.setBackground(getResources().getDrawable(R.drawable.ic_play));
            } else {
                MainAdapter.player.start();
                botonCancionActual.setBackground(getResources().getDrawable(R.drawable.ic_pause));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.id_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainActivityPresenter.onObtenerDatos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}
