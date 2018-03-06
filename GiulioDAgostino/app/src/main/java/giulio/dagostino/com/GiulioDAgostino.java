package giulio.dagostino.com;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import giulio.dagostino.com.Classes.ConnectivityStatus;
import giulio.dagostino.com.Classes.MyAppWebViewClient;


public class GiulioDAgostino extends AppCompatActivity {
    private String URL = "http://giuliodagostino.com/";
    private WebView webView;
    LinearLayout linearLayout;
    private ProgressBar progress_bar_web;
    private Bundle webViewBundle;
    SwipeRefreshLayout mySwipeRefreshLayout;
    public static int counter = 0;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!ConnectivityStatus.isConnected(GiulioDAgostino.this)) {
                linearLayout.setVisibility(View.VISIBLE);

            } else {
                // connected
                if (webViewBundle == null) {
                    loadWebContent(URL);

                } else {
                    webView.restoreState(webViewBundle);
                }
                linearLayout.setVisibility(View.GONE);

            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giulio_dagostino);
        getSupportActionBar().hide();
        this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        linearLayout = (LinearLayout) findViewById(R.id.netstate);
        webView = (WebView) findViewById(R.id.webView);
        progress_bar_web = (ProgressBar) findViewById(R.id.progress_bar_web);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.clearView();
        webView.measure(100, 100);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.canGoBack();
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new ChromeClient());
        if (webViewBundle == null) {
            loadWebContent(URL);

        } else {
            webView.restoreState(webViewBundle);
        }

        //Stop local links and redirects from opening in browser instead of webview
        webView.setWebViewClient(new MyAppWebViewClient(GiulioDAgostino.this));
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
//                findViewById(R.id.progress).setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
//                findViewById(R.id.progress).setVisibility(View.GONE);
            }

            public void onReceivedError(WebView webview, int i, String s, String s1)
            {
                webview.loadUrl("file:///android_asset/abc.html");
            }
        });

//        mySwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        loadWebContent(URL);
////                        webView.loadUrl(URL);
////                        mySwipeRefreshLayout.setRefreshing(false);
//                    }
//
//                }
//        );


    }

    public class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progress_bar_web.setProgress(newProgress);
            if (newProgress == 100) {
                progress_bar_web.setVisibility(View.GONE);

            } else {
                progress_bar_web.setVisibility(View.VISIBLE);

            }
        }


    }


    private void loadWebContent(String url) {
        Toast.makeText(this, "Loading Content Please Wait", Toast.LENGTH_LONG).show();
        if (webViewBundle == null) {
            webView.loadUrl(url);

        } else {
            webView.restoreState(webViewBundle);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ba","onresume");
        webView.restoreState(webViewBundle);

        this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }


    @Override
    protected void onDestroy() {
        Log.v("ba","ondestro");

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("ba","onpause");
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);

        this.unregisterReceiver(receiver);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {

        if (webView.copyBackForwardList().getCurrentIndex() >= 1) {
            Toast.makeText(GiulioDAgostino.this, "Loading...", Toast.LENGTH_SHORT).show();
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }

}