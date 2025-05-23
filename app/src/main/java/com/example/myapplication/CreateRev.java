package com.example.myapplication;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entites.Avion;
import Entites.Garagiste;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateRev extends AppCompatActivity {

    private Spinner spinnerAvions;
    private EditText editDate, editLibelle;
    private Button btnSubmit;

    private List<Avion> avionList = new ArrayList<>();
    private ArrayAdapter<String> avionAdapter;
    private Map<String, Avion> avionMap = new HashMap<>(); // label to object

    private int garagisteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        garagisteId = getIntent().getIntExtra("user", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrevision);

        editDate = findViewById(R.id.edit_date);
        editLibelle = findViewById(R.id.edit_libelle);
        spinnerAvions = findViewById(R.id.spinner_avions);
        btnSubmit = findViewById(R.id.btn_submit);

        loadAvions(); // API call

        btnSubmit.setOnClickListener(v -> submitRevision());
    }

    private void loadAvions() {
        // appel Retrofit pour lister tous les avions
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getAvions().enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<JsonObject> data = response.body();
                    List<String> labels = new ArrayList<>();

                    for (JsonObject obj : data) {
                        Avion avion = new Avion(
                                obj.get("Id_AVION").getAsInt(),
                                obj.get("code").getAsString(),
                                obj.get("numSerie").getAsInt(),
                                obj.get("Id_MODELE").getAsInt(),
                                obj.get("libelle").getAsString(),
                                obj.get("nbSiege").getAsInt()
                        );
                        String label = avion.getCode() + " - " + avion.getLibelle();
                        avionList.add(avion);
                        avionMap.put(label, avion);
                        labels.add(label);
                    }

                    avionAdapter = new ArrayAdapter<>(CreateRev.this,
                            android.R.layout.simple_spinner_item, labels);
                    avionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAvions.setAdapter(avionAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CreateRev.this, "Erreur chargement avions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitRevision() {
        String date = editDate.getText().toString();
        String libelle = editLibelle.getText().toString();
        String selectedLabel = (String) spinnerAvions.getSelectedItem();

        if (!avionMap.containsKey(selectedLabel)) {
            Toast.makeText(this, "Sélection invalide", Toast.LENGTH_SHORT).show();
            return;
        }
        Avion selectedAvion = avionMap.get(selectedLabel);

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Log.w("API", date + ", " + libelle + ", " + selectedAvion.getId_MODELE() + ", " + selectedAvion.getId_AVION() + ", " + garagisteId);
        JsonObject json = new JsonObject();
        json.addProperty("dateRevision", date);
        json.addProperty("libelle", libelle);
        json.addProperty("Id_MODELE", selectedAvion.getId_MODELE());
        json.addProperty("Id_AVION", selectedAvion.getId_AVION());
        json.addProperty("Id_PERSONNEL", garagisteId);
        RequestBody body = RequestBody.create(
                json.toString(), okhttp3.MediaType.parse("application/json")
        );
        apiService.addRevision(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject data = response.body();
                } else {
                    Toast.makeText(CreateRev.this, "erreur lors de la reponse", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w("API", t.toString());
                Toast.makeText(CreateRev.this, "erreur lors de la requete" + t.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

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

}
