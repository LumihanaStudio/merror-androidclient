package kr.edcan.merror.activity;

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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Retrofit retrofit;
    Call<User> userLogin, loginValidate, userRegister;

    SharedPreferences sharedPreferecenes;
    SharedPreferences.Editor editor;
    NetworkInterface service;
    EditText userid, userpw, username, user_repw, user_birthday;
    TextView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actikvity);
        setRestAdapter();
        setDefault();
//        setLogin();
    }

    private void setDefault() {
        sharedPreferecenes = getSharedPreferences("Merror", 0);
        editor = sharedPreferecenes.edit();
        userid = (EditText) findViewById(R.id.register_id_input);
        userpw = (EditText) findViewById(R.id.register_password_input);
        user_repw = (EditText) findViewById(R.id.register_password_re_input);
        user_birthday = (EditText) findViewById(R.id.register_birthday_input);
        username = (EditText) findViewById(R.id.register_name_input);
        register = (TextView) findViewById(R.id.register_register_btn);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register_btn:
                sendRegister();
                break;
        }
    }

    private void sendRegister() {
        String id = userid.getText().toString().trim();
        String password = userpw.getText().toString().trim();
        String password_re = user_repw.getText().toString().trim();
        String name = username.getText().toString().trim();
        String birthday = user_birthday.getText().toString().trim();

        if (!id.isEmpty() && !password.isEmpty()
                && !password_re.isEmpty() && !name.isEmpty() && !birthday.isEmpty()) {
            if (password.equals(password_re)) {
                userRegister = service.userRegister(id, password, name, birthday);
                userRegister.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(RegisterActivity.this, "Success!\nPlease Login!", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                            case 409 :
                                Toast.makeText(RegisterActivity.this, "Conflict!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            } else
                Toast.makeText(RegisterActivity.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(RegisterActivity.this, "Please Input Everything without blank!", Toast.LENGTH_SHORT).show();
    }

        private void setRestAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://malang.moe:7727/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NetworkInterface.class);
    }
}
