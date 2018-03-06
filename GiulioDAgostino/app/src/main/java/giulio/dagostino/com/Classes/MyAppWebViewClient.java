package giulio.dagostino.com.Classes;

import android.app.Activity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import giulio.dagostino.com.GiulioDAgostino;


/**
 * Created by BabarMustafa on 4/27/2017.
 */

public class MyAppWebViewClient extends WebViewClient {
    private Activity activity;

    public MyAppWebViewClient(GiulioDAgostino mainActivity) {
        this.activity = mainActivity;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);

        return false;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
        view.loadUrl("file:///android_asset/abc.html");
    }

    private void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
    public interface Showbar {
        public void onstartloading();

        public void onstoploading();

    }
}