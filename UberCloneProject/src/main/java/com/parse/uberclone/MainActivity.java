/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.uberclone;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {



    public void RedirectActivity()
    {
        if (ParseUser.getCurrentUser().getString("riderOrDriver").equals("Rider"))
        {
            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), ViewRequestActivity.class);
            startActivity(intent);
        }
    }

    public void GetStarted(View view)
    {
        Switch userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);

        String userType = "Rider";

        if (userTypeSwitch.isChecked())
        {
            userType = "Driver";
        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        final String finalUserType = userType;

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null)
                {
                    Toast.makeText(MainActivity.this, "Welcome " + finalUserType, Toast.LENGTH_SHORT).show();
                    RedirectActivity();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if (ParseUser.getCurrentUser() == null)
        {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null)
                    {

                    }
                    else
                    {

                    }
                }
            });
        }
        else
        {
            if (ParseUser.getCurrentUser().get("riderOrDriver") != null)
            {
                RedirectActivity();
            }
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
      }

}