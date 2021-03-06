package com.example.stu.awsdb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.stu.awsdb.DynamoDBManager.UserPreference;

public class UserActivity extends Activity {

    private int userNo = 0;
    private UserPreference userInfo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preference);

        userNo = Integer.valueOf(getIntent().getExtras().getString("USER_NO"));
        new GetUserInfoTask().execute();
    }

    private void setupActivity() {

        String userName = userInfo.getFirstName() + " " + userInfo.getLastName();

        final TextView textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewUserName.setText(userName);

        final CheckBox checkBoxAutoLogin = (CheckBox) findViewById(R.id.checkBoxAutoLogin);
        checkBoxAutoLogin
                .setChecked(userInfo.isAutoLogin() == null ? true : userInfo.isAutoLogin());
        checkBoxAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userInfo.setAutoLogin(isChecked);
                new UpdateAttributeTask().execute();
            }
        });

        final CheckBox checkBoxVibrate = (CheckBox) findViewById(R.id.checkBoxVibrate);
        checkBoxVibrate.setChecked(userInfo.isVibrate() == null ? false : userInfo.isVibrate());
        checkBoxVibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userInfo.setVibrate(isChecked);
                new UpdateAttributeTask().execute();
            }
        });

        final CheckBox checkBoxSilent = (CheckBox) findViewById(R.id.checkBoxSilent);
        checkBoxSilent.setChecked(userInfo.isSilent() == null ? false : userInfo.isSilent());
        checkBoxSilent.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userInfo.setSilent(isChecked);
                new UpdateAttributeTask().execute();
            }
        });

        final Spinner spinnerColorTheme = (Spinner) findViewById(R.id.spinnerColorTheme);
        if (userInfo.getColorTheme() != null) {
            Resources res = getResources();
            String[] colors = res.getStringArray(R.array.color_theme);
            for (int i = 0; i < colors.length; i++) {
                if (colors[i].equalsIgnoreCase(userInfo.getColorTheme())) {
                    spinnerColorTheme.setSelection(i, true);
                }
            }
        }
        spinnerColorTheme.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                Resources res = getResources();
                String[] colors = res.getStringArray(R.array.color_theme);

                userInfo.setColorTheme(colors[pos]);

                new UpdateAttributeTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

                // Do nothing
            }
        });
    }

    private class GetUserInfoTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... voids) {

            userInfo = DynamoDBManager.getUserPreference(userNo);
            return null;
        }

        protected void onPostExecute(Void result) {

            setupActivity();
        }
    }

    private class UpdateAttributeTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... voids) {

            DynamoDBManager.updateUserPreference(userInfo);

            return null;
        }
    }
}
