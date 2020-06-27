package com.example.myapplication1.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.R;
import com.example.myapplication1.ui.login.LoginViewModel;
import com.example.myapplication1.ui.login.LoginViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton;
    TextView registerTextView;
    private RequestQueue queue;
    JsonObjectRequest objectRequest;
    JSONObject data;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        registerTextView = (TextView) findViewById(R.id.register);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, registerActivity.class);
                startActivity(registerIntent);
            }
        });

        String URL ="http://cbit-qp-api.herokuapp.com/user-login";
        data = new JSONObject();

        try {
            data.put("uname",usernameEditText.getText().toString());
            data.put("password",passwordEditText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        queue = Volley.newRequestQueue(this);
        objectRequest = new JsonObjectRequest(Request.Method.POST,
                URL,
                data,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String accessTkn = null;
                        try {
                            accessTkn = response.getString("access_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MainActivity2.text.setText(accessTkn);


                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG);
                        toast.show();


                    }
                });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                queue.add(objectRequest);
                openActivity2();



            }
        });




    }
    private void openActivity2() {
        Intent loginIntent = new Intent(this,MainActivity2.class);
        startActivity(loginIntent);

    }
}