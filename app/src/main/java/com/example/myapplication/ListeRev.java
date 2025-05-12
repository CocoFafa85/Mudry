package com.example.myapplication;

import static java.lang.Integer.parseInt;

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

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Entites.Garagiste;
import Entites.Revision;
import okhttp3.RequestBody;
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

        garagisteId = getIntent().getIntExtra("user", -1);
        Toast.makeText(this, "ID Garagiste : " + garagisteId, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.rv_liste);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getRevisions().enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<JsonObject> data = response.body();
                    List<Revision> revisions = new ArrayList<>();
                    Toast.makeText(ListeRev.this, "test1", Toast.LENGTH_SHORT).show();
                    for (JsonObject obj : data) {
                        revisions.add(new Revision(obj.get("id_revision").getAsInt(), new Date(obj.get("dateRevision").getAsString()), obj.get("libelle").getAsString(), obj.get("id_PERSONNEL").getAsInt(), obj.get("id_MODELE").getAsInt(), obj.get("id_AVION").getAsInt()));
                        Toast.makeText(ListeRev.this, "trouvée : " + revisions.get(revisions.size()-1).getLibelle(), Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(ListeRev.this, "Connexion réussie : " + user.getId_PERSONNEL(), Toast.LENGTH_SHORT).show();
                    Garagiste user = new Garagiste(garagisteId);
                    // Redirection
                    Intent intent = new Intent(ListeRev.this, MainActivity.class);
                    intent.putExtra("user", user.getId_PERSONNEL());
                    startActivity(intent);
                } else {
                    Toast.makeText(ListeRev.this, "Aucune révision trouvé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                Toast.makeText(ListeRev.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}