package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Call;

public class RevisionDetailActivity extends AppCompatActivity {
    private int garagisteId;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_accueil, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId()== R.id.itm_connecter) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()== R.id.itm_ajoutRev) {
            Intent intent = new Intent(this, CreateRev.class);
            intent.putExtra("garagisteId", garagisteId);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()== R.id.itm_listeRev) {
            Intent intent = new Intent(this, ListeRev.class);
            intent.putExtra("garagisteId", garagisteId);
            startActivity(intent);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_detail);

        // Liaison des champs
        TextView idText = findViewById(R.id.detail_id);
        TextView dateText = findViewById(R.id.detail_date);
        TextView libelleText = findViewById(R.id.detail_libelle);
        TextView personnelText = findViewById(R.id.detail_personnel);
        TextView modeleText = findViewById(R.id.detail_modele);
        TextView avionText = findViewById(R.id.detail_avion);

        // Récupération des extras
        int id = getIntent().getIntExtra("id_revision", -1);
        String date = getIntent().getStringExtra("dateRevision");
        String libelle = getIntent().getStringExtra("libelle");
        int personnel = getIntent().getIntExtra("idPersonnel", -1);
        int avion = getIntent().getIntExtra("idAvion", -1);

        // Affichage dans les champs
        idText.setText(String.valueOf(id));
        dateText.setText(date);
        libelleText.setText(libelle);
        personnelText.setText(String.valueOf(personnel));
        fetchAvionAndModeleName(avion);
    }

    private void fetchAvionAndModeleName(int avionId) {
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject json = new JsonObject();
        json.addProperty("Id_AVION", avionId);
        RequestBody body = RequestBody.create(
                json.toString(), okhttp3.MediaType.parse("application/json")
        );
        apiService.getAvion(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject obj = response.body();

                    String avionCode = obj.has("code") ? obj.get("code").getAsString() : "Inconnu";
                    String modeleLibelle = obj.has("libelle") ? obj.get("libelle").getAsString() : "Inconnu";

                    TextView avionText = findViewById(R.id.detail_avion);
                    TextView modeleText = findViewById(R.id.detail_modele);

                    avionText.setText("Code avion : " + avionCode);
                    modeleText.setText("Modèle : " + modeleLibelle);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RevisionDetailActivity.this, "Erreur de chargement avion/modèle", Toast.LENGTH_SHORT).show();
            }
        });
    }

}