/**
 * Simple ip checker by BM835
 *
 * https://github.com/BM835
 * https://bm835.github.io/ip_checker
 */
package bem.ware.yoipis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        TextView outs;
        TextView locOut;
        View view;
        Snackbar waitforip;
        getIP getip;
        String locIP;


    @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        outs = findViewById(R.id.textView);
        locOut = findViewById(R.id.textView2);
        view = findViewById(R.id.fab);
        getip = new getIP();

        /**ip will gets in start */
        getip.execute();
        getLocalIP();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**also when you click fab it will say you what it goes for ip and check ip */
                waitforip.make(view, "Going for ip", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                /** you can use every AnyncTask only once, but this code
                 * can help you use AnyncTask again */
                getip = new getIP();

                final String TAG = "checking..";
                Log.wtf(TAG, "Checking for IP...");

                getip.execute();
                /**if you don't understand it will will ask ip on line
                126 via AsyncTask */
                getLocalIP();
            }
        });
        locOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", locIP);
                clipboard.setPrimaryClip(clip);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Copied!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        TextView infoTextView = (TextView) findViewById(R.id.textView);

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_settings:
                Intent about_ = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about_);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            String err = "Success";
            try {
                URL yahoo = new URL("https://api.ipify.org");
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
    } catch (IOException ee) { err = getResources().getString(R.string.noinetcon); }
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
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", outs.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Copied!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }



    public void getLocalIP(){
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        locIP = addr.getHostAddress();
                        locOut.setText("Local ip is: " + locIP);
                    }
                }
            }
        } catch (Exception e) {e.printStackTrace();} // for now eat exceptions
    }
}


