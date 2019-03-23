package bem.ware.yoipis;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class RateUs extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please, rate us on Google Play")
                .setCancelable(true)
                .setPositiveButton("Rate",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=bem.ware.yoipis"));
                                startActivity(intent);
                                final String APP_PREFERENCES = "prefs";
                                final String APP_PREFERENCES_COUNTER = "isRate";
                                SharedPreferences mSettings;
                                mSettings = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putInt(APP_PREFERENCES_COUNTER, 2);
                                editor.apply();
                            }
                        })
                .setNeutralButton("Later",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                final String APP_PREFERENCES = "prefs";
                                final String APP_PREFERENCES_COUNTER = "isRate";
                                SharedPreferences mSettings;
                                mSettings = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putInt(APP_PREFERENCES_COUNTER, 0);
                                editor.apply();
                            }
                        })
                .setNegativeButton("Never",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                final String APP_PREFERENCES = "prefs";
                                final String APP_PREFERENCES_COUNTER = "isRate";
                                SharedPreferences mSettings;
                                mSettings = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putInt(APP_PREFERENCES_COUNTER, 2);
                                editor.apply();
                            }
                        });

        return builder.create();
    }
}
