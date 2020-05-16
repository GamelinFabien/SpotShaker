package com.example.spotshaker.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.spotshaker.Models.User;
import com.example.spotshaker.R;
import com.example.spotshaker.Requests.CreateUserRequest;
import com.example.spotshaker.Requests.LoginRequest;
import com.example.spotshaker.Utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUserActivity extends AppCompatActivity {


    Button bCreate;
    EditText etName;
    EditText etMail;
    EditText etPwd;
    EditText etPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        initContent();
    }

    public void initContent()
    {
        etName = (EditText) findViewById(R.id.etNameAdd);
        etMail = (EditText) findViewById(R.id.etMailAdd);
        etPwd = (EditText) findViewById(R.id.etPwdAdd);
        etPwd2 = (EditText) findViewById(R.id.etPwdAdd2);
        bCreate = (Button) findViewById(R.id.bCreateUser);
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Create();
            }
        });
    }

    public void Create()
    {
        final ProgressDialog dialog = ProgressDialog.show(CreateUserActivity.this, "", "Chargement en cours...", true);
        final String name = etName.getText().toString();
        final String mail = etMail.getText().toString();
        final String password = etPwd.getText().toString();
        final String confirmpassword = etPwd2.getText().toString();
        Tools tool = new Tools();

        if(name.matches(""))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Please inform your name baby", Toast.LENGTH_LONG).show();
        }
        if(mail.matches(""))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Please inform your email", Toast.LENGTH_LONG).show();
        }
        else if(password.matches(""))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Please inform your password", Toast.LENGTH_LONG).show();
        }
        else if(confirmpassword.matches(""))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Please inform your password", Toast.LENGTH_LONG).show();
        }
        else if(!confirmpassword.matches(password))
        {
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
        }
        else if(!tool.isEmailValid(mail)){
            if (dialog != null)
                dialog.cancel();
            Toast.makeText(this, "Non valid email", Toast.LENGTH_LONG).show();
        }
        else
        {
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if(response.getString("login").equals("exist"))
                        {
                            if (dialog != null)
                                dialog.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("Username or login exists already")
                                    .setNeutralButton("Retry", null)
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
                          //  Intent intent = new Intent(CreateUserActivity.this, HomeActivity.class);
                         //   CreateUserActivity.this.startActivity(intent);
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);
                            builder.setMessage("YES")
                                    .setNegativeButton("cool", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        if (dialog != null)
                            dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);
                    builder.setMessage("Connection error "+error.toString())
                            .setNegativeButton("retry", null)
                            .create()
                            .show();
                }
            };

            CreateUserRequest createRequest = null;
            try {
                createRequest = new CreateUserRequest(mail, password, name, responseListener, errorListener);
            } catch (JSONException e) {
                if (dialog != null)
                    dialog.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);
                builder.setMessage("Connection error"+e.toString())
                        .setNegativeButton("retry", null)
                        .create()
                        .show();
            }
            RequestQueue queue = Volley.newRequestQueue(CreateUserActivity.this);
            queue.add(createRequest);
        }

    }

}
