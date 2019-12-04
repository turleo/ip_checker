package bem.ware.yoipis;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.lang.Void;

public class MainActivity extends AppCompatActivity {
        TextView outs;
        View view;
        getIP getip;


    @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        outs = findViewById(R.id.textView);
        view = findViewById(R.id.fab);
        getip = new getIP();

        /* ip will gets in start */
        getip.execute();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* you can use every AnyncTask only once, but this code
                 can help you use AnyncTask again */
                getip = new getIP();

                getip.execute();
                /**
                 * if you don't understand
                 * it will will ask ip on line
                 *  67 via AsyncTask
                 */
            }
        });
    }

    /**
     * now ip will gets
     *
     * if you will check your ip or http request ot
     * via AsyncTask your app will fall
     */

    class getIP extends AsyncTask<Void, Void, Void> {

        String ipis = null;
        ProgressBar vvait;

        /** shows status bar */
        protected void onPreExecute() {
            super.onPreExecute();

            vvait = findViewById(R.id.progressBar);
            vvait.setVisibility(ProgressBar.VISIBLE);
        }

        /** check and output your ip */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL ip_address = new URL("https://us-central1-ip-checker-7ecce.cloudfunctions.net/getIP");
                URLConnection yc = ip_address.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                yc.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    ipis = inputLine;
                in.close();
            } catch (Exception e) {
                final String TAG = "NoAnyIP";
                Log.wtf(TAG, e.toString());
                Snackbar.make(view, e.toString(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            outs.setText(ipis);

            vvait.setVisibility(ProgressBar.INVISIBLE);
        }
    }

}
