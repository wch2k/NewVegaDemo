package com.polycom.vega.myapplicationdemo_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.polycom.vega.fundamental.Constants;
import com.polycom.vega.fundamental.IActivity;
import com.polycom.vega.rest.System;

public class PairActivity extends AppCompatActivity implements IActivity {
    private EditText urlTextEdit = null;
    private Button pairButton = null;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair);

        this.initComponent();
        this.initComponentState();
    }

    private View.OnClickListener pairButtonClickListerner = new View.OnClickListener() {
        public void onClick(View view) {
            errorMessageTextView.setText("");

            if (urlTextEdit.getText().toString() == "") {
                return;
            }

            pair();
        }
    };

    private void pair() {
        String url = urlTextEdit.getText().toString();

        if (url.equals("")) {
            return;
        }

        if (!url.startsWith("https://")) {
            url = "https://" + url;
        }

        HttpsTrustHelper.allowAllSSL();

        final String finalUrl = url;
        StringRequest request = new StringRequest(url + "/rest/system", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                parseData(response);
                Constants.setServerUrl(finalUrl);

                Intent intent = new Intent(PairActivity.this, HomeScreen.class);
                intent.putExtra("response", response);

                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageTextView.setText(error.getMessage());
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void parseData(String response) {
        System systemInfo = JSON.parseObject(response, System.class);

//        resultTextView.setText(response);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initComponent() {
        this.urlTextEdit = (EditText) findViewById(R.id.urlEditText);
        this.pairButton = (Button) findViewById(R.id.pairButton);
        this.errorMessageTextView = (TextView) findViewById(R.id.errorMessageTextView);
    }

    @Override
    public void initComponentState() {
        pairButton.setFocusable(true);
        pairButton.setFocusableInTouchMode(true);
        pairButton.requestFocus();
        pairButton.requestFocusFromTouch();
        pairButton.setOnClickListener(pairButtonClickListerner);
    }

    @Override
    public void registerNotification() {

    }

//    private class DownloadHelper extends AsyncTask<String, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            return;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String result = "No data.";
//
//            try {
//                result = new EasyHttpClient().get(params[0]);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            JSONObject jsonObject = null;
//
//            try {
//                jsonObject = new JSONObject(result);
//
//                String build = jsonObject.getString("build");
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//
//            ((TextView) findViewById(R.id.textView)).setText(result.toString());
//
//            return;
//        }
//    }
}
