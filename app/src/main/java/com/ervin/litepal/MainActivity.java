package com.ervin.litepal;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ervin.litepal.Ui.BaseActivity;
import com.ervin.litepal.Ui.HomeActivity;
import com.ervin.litepal.Ui.fragment.WelcomeFragments;
import com.ervin.litepal.event.BusProvider;
import com.ervin.litepal.event.EventBase;
import com.ervin.litepal.event.SyncEvent;
import com.squareup.otto.Subscribe;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    Button btn;
    Button decline;
    Fragment fragment;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        decline = (Button) findViewById(R.id.button_decline);
        btn.setOnClickListener(this);
        decline.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.toolbar_center_title);
        tvTitle.setText(R.string.welcome);
        fragment = new WelcomeFragments();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.welcome_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                enterHome();
                break;
            case R.id.button_decline:
                finish();
                break;
        }
    }


    private void enterHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe public void onEvent(EventBase event){
        if(event instanceof SyncEvent){
            if(((SyncEvent) event).code == SyncEvent.SUCCESS){

            }
        }
    }
}
