package com.acidblob.corbtest;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private WebViewClient webViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        this.webView = (WebView)findViewById(R.id.webView);
        this.webViewClient = new WebViewClient();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);

        final Button button = (Button)findViewById(R.id.button);
        final EditText editText = (EditText)findViewById(R.id.editText);

        final SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", 0);
        String lastText = prefs.getString("ngrok", null);
        if (lastText != null) {
            editText.setText(lastText);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ngrok = editText.getText().toString();

                if (ngrok != null) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("ngrok", ngrok);
                    editor.commit();
                }

                final String host = "https://" + ngrok + ".ngrok.io";
                final String url = host + "/welcome/index";

                if (false) {
                    webView.loadUrl(url);
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    webView.loadDataWithBaseURL(host, response, "text/html", "utf-8", null);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    queue.add(stringRequest);
                }




            }
        });
    }
}
