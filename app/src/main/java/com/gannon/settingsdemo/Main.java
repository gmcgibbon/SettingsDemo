package com.gannon.settingsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main Activity
 */
public class Main extends Base
{
    private TextView helloText;
    private Button   resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloText   = (TextView)findViewById(R.id.hello_text);
    }

    @Override
    protected void onResume() // reload preference sensitive data on resume
    {
        super.onResume();

        String name = getPrefs().getString("example_text", "");

        helloText.setText("Hello " + name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent in = new Intent(this, Settings.class);
                // special intent extras for showing a frag on startup
                //in.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, Settings.BasePreferenceFragment.class.getName());
                //in.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
                startActivity(in);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
