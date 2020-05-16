package com.example.spotshaker.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.spotshaker.R;
import com.example.spotshaker.Requests.LoginRequest;
import com.example.spotshaker.Utils.Tools;
import com.example.spotshaker.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button bLogin;
    TextView TextCreateUser;
    EditText etMail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initContent();
    }

    public void initContent()
    {
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Login();
            }
        });


        TextCreateUser = (TextView) findViewById(R.id.textCreateUser);
        TextCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CreateUserActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });
    }

    private void Login()
    {
        final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "", "Chargement en cours...", true);
        final String mail = etMail.getText().toString();
        final String password = etPassword.getText().toString();
        Tools tool = new Tools();

        if(mail.matches(""))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Renseignez votre email", Toast.LENGTH_LONG).show();
        }
        else if(password.matches(""))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Renseignez votre mot de passe", Toast.LENGTH_LONG).show();
        }
        else if(!tool.isEmailValid(mail)){
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Email non valide", Toast.LENGTH_LONG).show();
        }

        else {

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if(!response.has("login"))
                        {
                            if (dialog != null)
                                dialog.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("Incorrect Id")
                                    .setNeutralButton("Réessayer", null)
                                    .create()
                                    .show();
                        }
                        else {
                            User u = User.getCurrentUser();
                            u.setLogin(response.getString("login"));
                           // u.setSessionId(response.getString("sessionId"));
                            User.setCurrentUser(u);

                            if (dialog != null)
                                dialog.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("yes")
                                    .setNeutralButton("cool", null)
                                    .create()
                                    .show();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        if (dialog != null)
                            dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Error occurs during connection "+e.toString())
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                }

            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (dialog != null)
                        dialog.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("An error occurs :"+error.toString())
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            };

            LoginRequest loginRequest = null;
            try {
                loginRequest = new LoginRequest(mail, password, responseListener, errorListener);
            } catch (JSONException e) {
                if (dialog != null)
                    dialog.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Erreur de connexion au serveur"+e.toString())
                        .setNegativeButton("Réessayer", null)
                        .create()
                        .show();
            }
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
    }
}
