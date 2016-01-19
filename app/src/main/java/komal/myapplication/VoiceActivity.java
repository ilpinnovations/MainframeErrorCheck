package komal.myapplication;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class VoiceActivity extends ActionBarActivity implements View.OnClickListener{

    static final int check=1111;
    Button bsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_voice);
        bsearch=(Button)findViewById(R.id.iSearch);
        bsearch.setOnClickListener(this);

    }

    protected void OnActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode==check && resultCode==RESULT_OK){
            String result=data.getStringExtra(RecognizerIntent.EXTRA_RESULTS);
        }

        super.onActivityResult(requestCode,resultCode,data);
    }
    public  void  onClick(View view){

        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
        startActivityForResult(i,check);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voice, menu);
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
}
