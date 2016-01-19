package komal.myapplication;
    import android.app.Activity;
    import android.app.Dialog;
    import android.app.FragmentManager;
    import android.app.FragmentTransaction;
    import android.content.Context;
    import android.content.Intent;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.speech.RecognizerIntent;
    import android.support.design.widget.FloatingActionButton;
    import android.support.design.widget.NavigationView;
    import android.support.design.widget.Snackbar;
    import android.support.v4.view.GravityCompat;
    import android.support.v4.view.ViewPager;
    import android.support.v4.widget.DrawerLayout;
    import android.support.v7.app.ActionBarActivity;
    import android.os.Bundle;
    import android.support.v7.app.ActionBarDrawerToggle;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.inputmethod.InputMethodManager;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.widget.Toast;

    import java.io.IOException;


public class MainActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }
// Declaring Your View and Variables

        Toolbar toolbar;
        ViewPager pager;
        ViewPagerAdapter adapter;
        SlidingTabLayout tabs;
        CharSequence Titles[]={"DB2","COBOL","JCL","CICS"};
        int Numboftabs =4;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

            // Assigning ViewPager View and setting the adapter
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);

            // Assiging the Sliding Tab Layout View
            tabs = (SlidingTabLayout) findViewById(R.id.tabs);

            tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

            // Setting Custom Color for the Scroll bar indicator of the Tab View
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });

            // Setting the ViewPager For the SlidingTabsLayout
            tabs.setViewPager(pager);


        }
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();


            return super.onOptionsItemSelected(item);
        }


}