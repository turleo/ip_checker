package space.bm835.ip_checker.insantapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    Snackbar waitforip;
    View view;
    TextView outs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        outs = findViewById(bem.ware.yoipis.R.id.textView);
        view = findViewById(bem.ware.yoipis.R.id.fab);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIP gip = new getIP();
                gip.execute();
            }
        });
    }

    class getIP extends AsyncTask<Void, Void, Void> {

        String ipis = null;
        ProgressBar vvait;

        /** shows status bar */
        protected void onPreExecute() {
            super.onPreExecute();

            vvait = findViewById(bem.ware.yoipis.R.id.progressBar);
            vvait.setVisibility(ProgressBar.VISIBLE);
        }

        /** check and output your ip */
        @Override
        protected Void doInBackground(Void... params) {
            String err = "Success";
            try {
                URL yahoo = new URL("https://us-central1-ip-checker-7ecce.cloudfunctions.net/getip");
                URLConnection yc = yahoo.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                yc.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    ipis = inputLine;
                in.close();
                //waitforip.dismiss();
            } catch (Exception e) {
                final String TAG = "NoAnyIP";
                Log.wtf(TAG, e.toString());
                try {
                    int timeoutMs = 1500;
                    Socket sock = new Socket();
                    SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                    sock.connect(sockaddr, timeoutMs);
                    sock.close();

                    err = e.toString();
                } catch (IOException ee) { err = getResources().getString(bem.ware.yoipis.R.string.noinetcon); }
            }
            //waitforip.dismiss();
            waitforip.make(view, err, Snackbar.LENGTH_SHORT)
                    .setAction("Retry", null).show();

            return null;
        }

        /** delites statusbar */
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            outs.setText(ipis);

            vvait.setVisibility(ProgressBar.INVISIBLE);
        }

        /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String getIP() {
            return ipis;
        }*/
    }

    public void copy(View v){
        new Toast(this);
        Toast toast = Toast.makeText(this, "Copy only in downloaded version", Toast.LENGTH_LONG);
        toast.show();
    }

}
