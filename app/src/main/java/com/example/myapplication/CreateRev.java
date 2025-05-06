package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Entites.Revision;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateRev extends AppCompatActivity {

    private ApiService apiService;

    public boolean onCreateOptionsMenu(Menu menu) {
//        if (isLoggedIn) {//pas necessaire si changement d'activité
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ci, menu);
        return true;
//        }
//        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if (item.getItemId() == R.id.itm_ajoutRev) {
            Toast.makeText(this, "Ajout d'une révision", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, CreateRev.class);
            startActivity(intent);

            return true;
        }
        if (item.getItemId() == R.id.itm_connecter) {
            Toast.makeText(this, "Vous avez cliqué sur \n se connecter", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createrevision);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnRevision= findViewById(R.id.validerRevision);

        btnRevision.setOnClickListener(v -> {
            Retrofit retrofit = RetrofitClient.getInstance();
            ApiService apiService = retrofit.create(ApiService.class);

            EditText editDateR = findViewById(R.id.dateRInput);
            String editDateRStr = editDateR.getText().toString();
            EditText editLibelle = findViewById(R.id.libelleInput);
            String libelle = editLibelle.getText().toString();
            EditText editIdentifiant = findViewById(R.id.identifiantInput);
            String identifiant = editIdentifiant.getText().toString();
            EditText editAvion = findViewById(R.id.avionInput);
            String avion = editAvion.getText().toString();


            Date dateR = null;
            try {
                dateR = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(editDateRStr);
            } catch (ParseException e) {
                Toast.makeText(this, "Date invalide (format attendu : jj/MM/yyyy)", Toast.LENGTH_SHORT).show();
                return;
            }

            Revision revision = new Revision(dateR, libelle, Id_PERSONNEL, id_MODELE, id_AVION);
            apiService.createRevision(revision).enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreateRev.this, "Enregistrement réussie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateRev.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateRev.this, "Erreur lors de l'enregistrement': " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CreateRev.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
