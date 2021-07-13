package com.example.socialmediaintegrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class loginFacebook extends AppCompatActivity {

    private TextView name;
    private TextView emailf;
    private ImageView pic;
    private Button signoutF;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);
        name = findViewById(R.id.namef);
        emailf = findViewById(R.id.emailf);
        pic = findViewById(R.id.picf);
        signoutF = findViewById(R.id.signoutF);
        accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.d("TAG", object.toString());
                            try {
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String email = object.getString("email");
                                String id = object.getString("id");
                                String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                                name.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                                emailf.setText(email);
                                Picasso.with(loginFacebook.this).load(image_url).into(pic);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "first_name,last_name,email,id");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
