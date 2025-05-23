package com.example.myapplication;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonObject;

import Entites.Garagiste;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    String identifiantConnexion;
    String mdpConnexion;
//    private boolean isLoggedIn = true;//false;//pas necessaire si changement d'activité

    /*public boolean onCreateOptionsMenu(Menu menu) {
//        if (isLoggedIn) {//pas necessaire si changement d'activité
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ci, menu);
        return true;
//        }
//        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.itm_connecter) {
            Toast.makeText(this, "Vous avez cliqué sur \n se connecter", Toast.LENGTH_LONG).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickValider(View view) {
        // Ajoute ici l’action à exécuter lors du clic
        EditText identifiantInput = findViewById(R.id.identifiantInputConnexion);
        EditText mdpInput = findViewById(R.id.mdpInputConnexion);

        identifiantConnexion = identifiantInput.getText().toString();
        mdpConnexion = mdpInput.getText().toString();

        if (!identifiantConnexion.isEmpty() && !mdpConnexion.isEmpty()) {
            fetchConnexion(identifiantConnexion, mdpConnexion);
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchConnexion(String identifiant, String mdp) {

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        //.d("API", "identifiant: " + identifiant + " / MDP: " + mdp);
        JsonObject json = new JsonObject();
        json.addProperty("identifiant", identifiant);
        json.addProperty("mdp", mdp);
        RequestBody body = RequestBody.create(
                json.toString(), okhttp3.MediaType.parse("application/json")
        );
        apiService.connexion(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject data = response.body();
                    Garagiste user = new Garagiste(parseInt(String.valueOf(data.get("res"))));
                    Toast.makeText(MainActivity.this, "Connexion réussie : " + user.getId_PERSONNEL(), Toast.LENGTH_SHORT).show();

                    // Redirection
                    Intent intent = new Intent(MainActivity.this, ListeRev.class);
                    intent.putExtra("user", user.getId_PERSONNEL());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}