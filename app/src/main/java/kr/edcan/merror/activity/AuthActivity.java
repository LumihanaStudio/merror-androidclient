package kr.edcan.merror.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.edcan.merror.Interface.NetworkInterface;
import kr.edcan.merror.R;
import kr.edcan.merror.data.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    Retrofit retrofit;
    Call<User> userLogin, loginValidate;

    SharedPreferences sharedPreferecenes;
    SharedPreferences.Editor editor;
    NetworkInterface service;
    EditText userid, userpw, username, user_repw;
    TextView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setRestAdapter();
        setDefault();
//        setLogin();
    }

    private void setDefault() {
        sharedPreferecenes = getSharedPreferences("Merror", 0);
        editor = sharedPreferecenes.edit();
        userid = (EditText) findViewById(R.id.auth_id_input);
        userpw = (EditText) findViewById(R.id.auth_password_input);
        login = (TextView) findViewById(R.id.auth_login_btn);
        register = (TextView) findViewById(R.id.auth_register_btn);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth_login_btn:
                requestLogin();
                break;
            case R.id.auth_register_btn:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
        }
    }

    private void requestLogin() {
        String id = userid.getText().toString().trim();
        String password = userpw.getText().toString().trim();
        if (!id.isEmpty() && !password.isEmpty()) {
            userLogin = service.userLogin(id, password);
            userLogin.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    switch (response.code()) {
                        case 200:
                            editor.putString("id", response.body().id);
                            editor.putString("password", response.body().password);
                            editor.putString("name", response.body().name);
                            editor.putString("birthday", response.body().birthday);
                            editor.putString("apikey", response.body().apikey);
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            break;
                        case 400:
                            Toast.makeText(AuthActivity.this, "아이디 혹은 비밀번호가 잘못되었습니다!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(AuthActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AuthActivity.this, "빈칸 없이 입력해주세요!", Toast.LENGTH_SHORT).show();
        }

    }

    private void setRestAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://malang.moe:7727/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NetworkInterface.class);
    }

}
