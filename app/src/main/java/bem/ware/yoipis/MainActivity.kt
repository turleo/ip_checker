package bem.ware.yoipis

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class MainActivity : AppCompatActivity() {
    var outs: TextView? = null
    var view: View? = null
    private var getIp: getIP? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        outs = findViewById(R.id.textView)
        view = findViewById(R.id.fab)

        /* ip will gets in start */
        getIp = getIP()
        getIp!!.execute()
        view!!.setOnClickListener { /* you can use every AnyncTask only once, but this code
                 can help you use AnyncTask again */
            getIp = getIP()
            getIp!!.execute()
            /*
                  if you don't understand
                  it will will ask ip on line
                   67 via AsyncTask
                 */
        }
    }

    /**
     * now ip will gets
     *
     * if you will check your ip or http request ot
     * via AsyncTask your app will fall
     */
    @SuppressLint("StaticFieldLeak")
    inner class getIP : AsyncTask<Void?, Void?, Void?>() {
        var ipis: String? = null
        var vvait: ProgressBar? = null

        /** shows status bar  */
        override fun onPreExecute() {
            super.onPreExecute()
            vvait = findViewById(R.id.progressBar)
            vvait!!.visibility = ProgressBar.VISIBLE
        }


        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            outs!!.text = ipis
            vvait!!.visibility = ProgressBar.INVISIBLE
        }

        /** check and output your ip  */
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                val ipAddress = URL("https://us-central1-ip-checker-7ecce.cloudfunctions.net/getIP")
                val yc = ipAddress.openConnection()
                val `in` = BufferedReader(
                        InputStreamReader(
                                yc.getInputStream()))
                var inputLine: String?
                while (`in`.readLine().also { inputLine = it } != null) ipis = inputLine
                `in`.close()
            } catch (e: Exception) {
                val TAG = "NoAnyIP"
                Log.wtf(TAG, e.toString())
                Snackbar.make(view!!, e.toString(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
            }
            return null
        }
    }

    fun copy(v: View?) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", outs!!.text.toString())
        clipboard.setPrimaryClip(clip)
        Snackbar.make(view!!, "Copied", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
    }
}