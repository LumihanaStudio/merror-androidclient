package kr.edcan.merror.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import kr.edcan.merror.Interface.NetworkInterface;
import kr.edcan.merror.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    Call<String> countPerson;
    MaterialDialog loading;
    SharedPreferences sharedPreferecenes;
    SharedPreferences.Editor editor;
    NetworkInterface service;

    TextView personCount, startChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRestAdapter();
        loading = new MaterialDialog.Builder(MainActivity.this)
                .content("잠시만요오오오오")
                .progress(true, 0)
                .show();
        setDefault();
        countPerson.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                switch (response.code()){
                    case 200:
                        personCount.setText(response.body().toString());
                        loading.dismiss();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefault() {
        personCount = (TextView) findViewById(R.id.person_count);
        startChat = (TextView) findViewById(R.id.startChat);
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });

    }

    private void setRestAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://malang.moe:7727/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NetworkInterface.class);
        countPerson = service.countPerson();

    }

    public void onResume() {
        super.onResume();
    }
}
