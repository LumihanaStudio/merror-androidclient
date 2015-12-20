package kr.edcan.merror.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.edcan.merror.R;

public class ChatActivity extends AppCompatActivity {

    LinearLayout parentView;
    View me, you;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setDefault();
        setActionBar(getSupportActionBar());
    }

    private void setActionBar(ActionBar supportActionBar) {
        supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#42A4F4")));
        supportActionBar.setTitle("");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setElevation(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDefault() {
        parentView = (LinearLayout) findViewById(R.id.chat_view);
        me = getLayoutInflater().inflate(R.layout.me_chat, null);
        you = getLayoutInflater().inflate(R.layout.noteme_chat, null);
        TextView person = (TextView) you.findViewById(R.id.me_chat_person);
        person.setText("구창림");
        parentView.addView(me);
        me = getLayoutInflater().inflate(R.layout.me_chat, null);
        TextView title = (TextView) me.findViewById(R.id.me_chat_content);
        title.setText("이씨 딱히 졸리진 않은데에에에");
        you = getLayoutInflater().inflate(R.layout.noteme_chat, null);
        TextView people2= (TextView)you.findViewById(R.id.me_chat_person);
        people2.setText("구창림");
        TextView title2= (TextView) you.findViewById(R.id.me_chat_content);
        title2.setText("자던가 바보야");
        parentView.addView(me);
        parentView.addView(you);
    }
}
