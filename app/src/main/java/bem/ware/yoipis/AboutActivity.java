package bem.ware.yoipis;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView gh = findViewById(R.id.gh);
        TextView source = findViewById(R.id.source);
        ImageView logo = findViewById(R.id.imageView2);

        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gih = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://github.com/BM835"));
                startActivity(Intent.createChooser(gih, "GitHub open in"));
            }
        });

        source.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent source_ = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://github.com/BM835/ip_checker"));
                startActivity(Intent.createChooser(source_, "GitHub open in"));
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ghp = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://bm835.github.io/ip_checker"));
                startActivity(Intent.createChooser(ghp, "IP checker's site"));
            }
        });
    }
}
