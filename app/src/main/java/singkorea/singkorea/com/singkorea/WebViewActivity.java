package singkorea.singkorea.com.singkorea;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import singkorea.singkorea.com.singkorea.util.RealPathUtil;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView mWebView; //웹뷰
    //private WebViewClient webViewClient;
    private static final int  FILECHOOSER_LOLLIPOP_REQ_CODE = 1;
    private static final String TYPE_IMAGE = "image/*";
    private static final int INPUT_FILE_REQUEST_CODE = 1;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private boolean wasPaused;
    private static final String LOAD_URL = "http://www.singkorea.com";
    private static boolean RESUME_FROM_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();

        String url = intent.getStringExtra("url"); //"jizard"문자 받아옴

        Uri uri = intent.getData();
        String type = "";
        String idx = "";
        if (uri != null) {
             type = uri.getQueryParameter("type");
             idx = uri.getQueryParameter("idx").replace("/","");
        }

        // 웹뷰 세팅
        mWebView = (WebView)findViewById(R.id.testWebView); //레이어와 연결
        setmWebSettings();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        mWebView.setWebViewClient(getWebViewClient()); // 클릭시 새창 안뜨게
        mWebView.setWebChromeClient(getCustomChreomeClient()); // alert 경고창 사용
        if(Build.VERSION.SDK_INT >= 21){
            webSettings.setMixedContentMode(0);
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else if(Build.VERSION.SDK_INT >= 19){
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else if(Build.VERSION.SDK_INT < 19){
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //mWebView.setWebViewClient(new Callback());
        if (type != "") {
             if (type.equals("shop")) {
                 mWebView.loadUrl(LOAD_URL + "/shopdetail.php?shopid=" + idx);
             } else if (type.equals("job")) {
                 mWebView.loadUrl(LOAD_URL + "/jobdetail.php?jobid=" + idx);
             } else if (type.equals("market_en")) {
                 mWebView.loadUrl(LOAD_URL + "/selldetail.php?proid=" + idx);
             } else if (type.equals("club")) {
                 mWebView.loadUrl(LOAD_URL + "/clubdetail.php?idx=" + idx);
             } else if (type.equals("estate")) {
                 mWebView.loadUrl(LOAD_URL + "/roomdetail.php?roomid=" + idx);
             }
        } else {
            if(url != null) {
                mWebView.loadUrl(url);
            } else {
                mWebView.loadUrl(LOAD_URL);
            }
        }
    }

    private Boolean isNetWork(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void startActivity(Intent intent){
        RESUME_FROM_ACTIVITY = true;
        super.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();

            if(uri == null) return;
        }
    }

    public WebViewClient getWebViewClient() {
        return new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url == null) {
                    Log.e(TAG, "shouldOverrideUrlLoading url null");
                    return false;
                }


                if (url != null && url.startsWith("intent:")) {
                    try {
                        RESUME_FROM_ACTIVITY = true;
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                        if (existPackage != null) {
                            startActivity(intent);
                        } else {

                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);

                            marketIntent.setData(Uri.parse("market_en://details?id="+intent.getPackage()));
                            if (marketIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(marketIntent);
                            }
                           // startActivity(marketIntent);

                        }
                        return true;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (url != null && url.startsWith("market_en://")) {
                    try {
                        RESUME_FROM_ACTIVITY = true;
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        if (intent != null) {
                            startActivity(intent);
                        }
                        return true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }  else if (url != null && url.startsWith("whatsapp://")) {
                    try {
                        RESUME_FROM_ACTIVITY = true;
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        Intent existPackage = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                        if (existPackage != null) {
                            startActivity(intent);
                            return true;
                        } else {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri.parse("market_en://details?id=com.whatsapp"));
                            if (marketIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(marketIntent);
                            }
                            return true;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();;
                    }
                    return false;
                } else if (url.contains("loginok.html")) {
                    setResult(RESULT_OK);
                    finish();
                    return true;
                }
                else if (!url.contains("www.singkorea.com")) {
                    RESUME_FROM_ACTIVITY = true;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                    return true;
                }

                final Uri uri = Uri.parse(url);
                return handleUri(uri);
            }



            private boolean handleUri(Uri uri) {
                //Log.e("test","handleUri : " + uri.toString());

                final String host = uri.getHost();
                final String scheme = uri.getScheme();
                final String fullUrl = uri.toString();
                if (scheme.equals("tel") || scheme.equals("sms") || scheme.equals("smsto") || scheme.equals("mms") || scheme.equals("mmsto"))
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(fullUrl));
                    startActivity(intent);
                    return true;
                }
                mWebView.loadUrl(fullUrl);
                return  false;
            }
        };
    }

    public void runUriScheme(Uri uri) {
        //String은 변환해서 Uri.parse(url)
        try {
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP); // The following flags launch the app outside the current app
            startActivity(intent);
        } catch (ActivityNotFoundException e1) {
            //catches error if play store isn't found
            Toast.makeText(WebViewActivity.this, "Play Store not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void setmWebSettings() {
        WebSettings wvs = mWebView.getSettings();

        wvs.setJavaScriptEnabled(true); //자바스크립트 사용
        wvs.setSupportMultipleWindows(true);
        wvs.setPluginState(WebSettings.PluginState.ON);
        //화면 비율 관련
        wvs.setUseWideViewPort(true); //wide viewport를 사용하도록 설정
        wvs.setLoadWithOverviewMode(true); // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        //wvs.setInitialScale(35); //비율 조절
//        wvs.setDefaultFontSize(8); // 기본 폰트 사이즈 지정
//        wvs.setMinimumFontSize(8); //폰트 사이즈 지정

        //웹뷰 멀티 터치 가능하게 (줌기능)
        wvs.setBuiltInZoomControls(true); //줌 아이콘 사용 설정
        wvs.setSupportZoom(false);

        wvs.setPluginState(WebSettings.PluginState.ON_DEMAND); // 플러그인을 사용 설정
        wvs.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정

        wvs.setDomStorageEnabled(true);

        wvs.setDefaultZoom(WebSettings.ZoomDensity.FAR); // 페이지 크기 자동 조절?


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        } else {
            wvs.setMixedContentMode(wvs.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if (isNetWork() == false) {
//                        return false;
//                    }
//                    String webUrl = mWebView.getUrl();
//                    Log.e("back","handleUri : " + webUrl);
//                    if (webUrl.equals("http://www.singkorea.com/#/")) {
//                        new AlertDialog.Builder(this)
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .setTitle(R.string.quit)
//                                .setMessage(R.string.really_quit)
//                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        //Stop the activity
//                                        finish();
//                                    }
//
//                                })
//                                .setNegativeButton(R.string.no, null)
//                                .show();
//
//                        return true;
//                    } else
//                    {
//                        if (webUrl.contains("http://www.singkorea.com")) {
//                            if (mWebView.canGoBack()) {
//                                mWebView.loadUrl("javascript:goback()");
//                                return true;
//                            } else {
//                                mWebView.loadUrl("http://www.singkorea.com");
//                                return true;
//                                                            }
//                        } else {
//                            mWebView.goBack();
//                        }
//
//                    }
//
//
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public void onBackPressed() {

        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public WebChromeClient getCustomChreomeClient() {
        return new WebChromeClient(){
            @Override
            public void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                finish();
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                final WebSettings settings = view.getSettings();
                settings.setDomStorageEnabled(true);
                settings.setJavaScriptEnabled(true);
                settings.setAllowFileAccess(true);
                settings.setAllowContentAccess(true);
                view.setWebChromeClient(this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();
                return false;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                new AlertDialog.Builder(view.getContext())
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
                //return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

                //return super.onJsConfirm(view, url, message, result);

                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm")
                        .setMessage(message)
                        .setPositiveButton("Yes",
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setNegativeButton("No",
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.cancel();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            // For Android Version < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                //System.out.println("WebViewActivity OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU), n=1");
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(TYPE_IMAGE);
                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
            }

            // For 3.0 <= Android Version < 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                //System.out.println("WebViewActivity 3<A<4.1, OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU,aT), n=2");
                openFileChooser(uploadMsg, acceptType, "");
            }

            // For 4.1 <= Android Version < 5.0
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                //Log.d(getClass().getName(), "openFileChooser : "+acceptType+"/"+capture);
                mUploadMessage = uploadFile;
                imageChooser();
            }

            // For Android Version 5.0+
            // Ref: https://github.com/GoogleChrome/chromium-webview-samples/blob/master/input-file-example/app/src/main/java/inputfilesample/android/chrome/google/com/inputfilesample/MainFragment.java
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                System.out.println("WebViewActivity A>5, OS Version : " + Build.VERSION.SDK_INT + "\t onSFC(WV,VCUB,FCP), n=3");
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                imageChooser();
                return true;
            }

            private void imageChooser() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(getClass().getName(), "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:"+photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType(TYPE_IMAGE);

                Intent[] intentArray;
                //if(takePictureIntent != null) {
                 //   intentArray = new Intent[]{takePictureIntent};
                //} else {
                    intentArray = new Intent[0];
               // }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
            }
        };
    }


    /**
     * More info this method can be found at
     * http://developer.android.com/training/camera/photobasics.html
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INPUT_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            RESUME_FROM_ACTIVITY = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mFilePathCallback == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
                Uri[] results = new Uri[]{getResultUri(data)};

                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            } else {
                if (mUploadMessage == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
                Uri result = getResultUri(data);

                Log.d(getClass().getName(), "openFileChooser : "+result);
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else {
            if (mFilePathCallback != null) mFilePathCallback.onReceiveValue(null);
            if (mUploadMessage != null) mUploadMessage.onReceiveValue(null);
            mFilePathCallback = null;
            mUploadMessage = null;
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private Uri getResultUri(Intent data) {
        Uri result = null;
        if(data == null || TextUtils.isEmpty(data.getDataString())) {
            // If there is not data, then we may have taken a photo
            if(mCameraPhotoPath != null) {
                result = Uri.parse(mCameraPhotoPath);
            }
        } else {
            String filePath = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                filePath = data.getDataString();
            } else {
                filePath = "file:" + RealPathUtil.getRealPath(this, data.getData());
            }
            result = Uri.parse(filePath);
        }

        return result;
    }

    public class Callback extends WebViewClient{
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().startSync();
        }
        if (!RESUME_FROM_ACTIVITY) {

            Intent intent = getIntent();
            Uri uri = intent.getData();
            String type = "";
            String idx = "";
            if (uri != null) {
                type = uri.getQueryParameter("type");
                idx = uri.getQueryParameter("idx").replace("/", "");
            }

            //mWebView.setWebViewClient(new Callback());
            if (type != "") {
                if (type.equals("shop")) {
                    mWebView.loadUrl(LOAD_URL + "/shopdetail.php?shopid=" + idx);
                } else if (type.equals("job")) {
                    mWebView.loadUrl(LOAD_URL + "/jobdetail.php?jobid=" + idx);
                } else if (type.equals("market_en")) {
                    mWebView.loadUrl(LOAD_URL + "/selldetail.php?proid=" + idx);
                } else if (type.equals("club")) {
                    mWebView.loadUrl(LOAD_URL + "/clubdetail.php?idx=" + idx);
                } else if (type.equals("estate")) {
                    mWebView.loadUrl(LOAD_URL + "/roomdetail.php?roomid=" + idx);
                }
            }
        }
        RESUME_FROM_ACTIVITY = false;
    }

    public void onClickEvent(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().stopSync();
        }

    }

}
