package komal.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab3 extends Fragment {
    private static final int REQUEST_CODE = 1234;
    Dialog match_text_dialog;
    ListView textlist;
    ArrayList<String> matches_text;
    String dialog;
    TextView aexplain;
    TextView apropose;
    EditText editSearch;
    boolean flag=false;
    private static final String TAG = "MyActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_3,container,false);

        Button btnSubmit;
        FloatingActionButton btnSearchspeech;
        btnSubmit = (Button) v.findViewById(R.id.iSearch_3);
        editSearch = (EditText) v.findViewById(R.id.iText_3);
        aexplain = (TextView) v.findViewById(R.id.iExplain_3);
        apropose = (TextView) v.findViewById(R.id.iPropose_3);

        btnSearchspeech = (FloatingActionButton) v.findViewById(R.id.iSearchspeech_3);

        final DataBaseHelper myDbHelper = new DataBaseHelper(getActivity().getApplicationContext());
//		myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
            Log.d("SQL","Database Created");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
            Log.d("SQL", "Database Opened");
        } catch (SQLException sqle) {
            // TODO Auto-generated catch block
            Log.d("SQL","Unable to open database");
            sqle.printStackTrace();
        }
        final SQLiteDatabase db = myDbHelper.getReadableDatabase();

        btnSearchspeech.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

                if (isConnected()) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        editSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String errorCodecob = editSearch.getText().toString();
                    String errorCode1=errorCodecob.toLowerCase();
                    String errorCode2=errorCodecob.toUpperCase();
                    InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);



                    if (editSearch.getText().toString().length() == 0) {

                        Toast.makeText(getActivity().getApplicationContext(), "Enter error code",
                                Toast.LENGTH_LONG).show();
                        aexplain.setText(" ");
                        apropose.setText(" ");
                    } else {

                        Cursor cur = db.rawQuery(
                                "SELECT * from jcl where field1=? or field1=?;",
                                new String[]{errorCode1,errorCode2});

                        if (cur.getCount() > 0) {
                            cur.moveToFirst();
                            while (cur.isAfterLast() == false) {
                                aexplain.setText("\n" + cur.getString(1));
                                apropose.setText("\n" + cur.getString(2));
                                cur.moveToNext();
                            }
                            cur.moveToPosition(0);
                        } else {

                            Toast.makeText(getActivity().getApplicationContext(), "Code not Found",
                                    Toast.LENGTH_LONG).show();
                            aexplain.setText(" ");
                            apropose.setText("  ");

                        }
                    }
                    return true;
                }
                return false;
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

// TODO Auto-generated method stub
                String errorCodecob = editSearch.getText().toString().trim();

                String errorCode1=errorCodecob.toLowerCase();
                String errorCode2=errorCodecob.toUpperCase();

                if(editSearch.getText().toString().length()==0){

                    Toast.makeText(getActivity().getApplicationContext(), "Enter error code",
                            Toast.LENGTH_LONG).show();
                    aexplain.setText(" ");
                    apropose.setText(" ");
                }
                else {


                    Cursor cur2 = db.rawQuery(
                            "SELECT * from jcl where field1=? or field1=?;",
                            new String[] { errorCode1,errorCode2 });
                    if (cur2.getCount() > 0) {
                        cur2.moveToFirst();
                        while (cur2.isAfterLast() == false) {
                            aexplain.setText("\n"+cur2.getString(1));
                            apropose.setText("\n"+cur2.getString(2));
                            cur2.moveToNext();
                        }
                        cur2.moveToPosition(0);
                    }
                    else {

                        Toast.makeText(getActivity().getApplicationContext(), "Code not Found",
                                Toast.LENGTH_LONG).show();
                        aexplain.setText(" ");
                        apropose.setText("  ");

                    }}
//				myDbHelper.close();

            }
        });
        return v;
    }
    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            match_text_dialog = new Dialog(this.getActivity().getWindow().getContext());
            match_text_dialog.setContentView(R.layout.speech_recog);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView) match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);
            final DataBaseHelper myDbHelper = new DataBaseHelper(
                    getActivity().getApplicationContext());
//		myDbHelper = new DataBaseHelper(this);

            try {
                myDbHelper.createDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new Error("Unable to create database");
            }
            try {
                myDbHelper.openDataBase();
            } catch (SQLException sqle) {
                // TODO Auto-generated catch block
                sqle.printStackTrace();
            }

            final SQLiteDatabase db = myDbHelper.getReadableDatabase();
            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
//                    Speech.setText("You have said " +matches_text.get(position));
                    dialog = matches_text.get(position).trim().replaceAll(" ","");

                    int i = 0;

                    flag=false;
                    String errorCode1 = dialog.toLowerCase();
                    String errorCode2 = dialog.toUpperCase();
                    Log.d(TAG, dialog);

                    String dialognoSpace = null;
                    int  length=dialog.length();
                    int l=0;
                    i=0;
                    boolean flag1=false;
                    //Integer intgr=Integer.parseInt(dialog);
                    //if(intgr!=0){
                    while (l<length) {
                        if ((dialog.charAt(l) == ' ')) {

                            String first = dialog.substring(0, i - 1);
                            String last = dialog.substring(i + 1);
                            dialognoSpace="-".concat(first);
                            dialognoSpace = dialognoSpace+last;
                            flag1=true;
                            break;
                        }
                        i++;
                        l++;
                    }

                    if(!flag1){
                        dialognoSpace=dialog;
                    }

                    editSearch.setText(dialognoSpace);

                    match_text_dialog.hide();
                    if (dialog.toString().length() == 0) {

                        Toast.makeText(getActivity().getApplicationContext(), "Enter error code",
                                Toast.LENGTH_LONG).show();
                        aexplain.setText(" ");
                        apropose.setText(" ");
                    } else {

                        Cursor cur = db.rawQuery(
                                "SELECT * from jcl where field1=? or field1=?;",
                                new String[]{errorCode1, errorCode2});

                        if (cur.getCount() > 0) {
                            cur.moveToFirst();
                            while (cur.isAfterLast() == false) {
                                aexplain.setText("\n" + cur.getString(1));
                                apropose.setText("\n" + cur.getString(2));
                                cur.moveToNext();
                            }
                            cur.moveToPosition(0);
                        } else {

                            Toast.makeText(getActivity().getApplicationContext(), "Code not Found",
                                    Toast.LENGTH_LONG).show();
                            aexplain.setText(" ");
                            apropose.setText("  ");

                        }
                    }
                }
            });
            match_text_dialog.show();

        }
        else{

        }
    }
}
