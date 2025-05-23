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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        garagisteId = getIntent().getIntExtra("user", -1);
        if(item.getItemId()== R.id.itm_connecter) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()== R.id.itm_ajoutRev) {
            Intent intent = new Intent(this, CreateRev.class);
            intent.putExtra("user", garagisteId);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()== R.id.itm_listeRev) {
            Intent intent = new Intent(this, ListeRev.class);
            intent.putExtra("user", garagisteId);
            startActivity(intent);
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
        RecyclerView recyclerView = findViewById(R.id.rv_liste);
        List<Revision> revisions = new ArrayList<>();
        RevisionAdapter adapter = new RevisionAdapter(this, revisions);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getRevisions().enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<JsonObject> data = response.body();
                    for (JsonObject obj : data) {
                        revisions.add(new Revision(obj.get("id_revision").getAsInt(), obj.get("dateRevision").getAsString(), obj.get("libelle").getAsString(), obj.get("id_MODELE").getAsInt(), obj.get("id_AVION").getAsInt(), obj.get("Id_PERSONNEL").getAsInt()));
                    }
                    adapter.notifyDataSetChanged();
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