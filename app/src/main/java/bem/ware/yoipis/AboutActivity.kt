package bem.ware.yoipis

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.google_play_link -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=bem.ware.yoipis")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=bem.ware.yoipis")))
                }
            }
            R.id.github -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/turleo/ip_checker")))
            }
            R.id.author_github -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/turleo")))
            }
        }
    }
}
