package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListeRev extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private int garagisteId;


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_accueil, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId()== R.id.itm_connecter) {
            Toast.makeText(this, "Vous avez cliqué sur \n se connecter", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;
        }
        if (item.getItemId()== R.id.itm_ajoutRev) {
            Toast.makeText(this, "Vous avez cliqué sur \n voir les révisions", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ListeRev.class);
            intent.putExtra("garagisteId", garagisteId);
            startActivity(intent);

            return true;
        }
        if (item.getItemId()== R.id.itm_listeRev) {
            Toast.makeText(this, "Vous avez cliqué sur \n la liste des révisions", Toast.LENGTH_LONG).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listerevision);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        garagisteId = getIntent().getIntExtra("garagisteId", -1);

        recyclerView = findViewById(R.id.rv_liste);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);

    }

}