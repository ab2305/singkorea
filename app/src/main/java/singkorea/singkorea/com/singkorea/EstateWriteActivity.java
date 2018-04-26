package singkorea.singkorea.com.singkorea;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.map.RegionAddressActivity;
import singkorea.singkorea.com.singkorea.util.TinyDB;

public class EstateWriteActivity extends AppCompatActivity {

    public RequestManager mGlideRequestManager;

    private ProgressBar progressBar;

    EditText edit_title;
    TextView txtArea;
    TextView txtAddress;

    EditText edit_tel;
    EditText edit_email;
    EditText edit_price;
    EditText edit_product_desc;

    ImageView img01;
    ImageView img02;
    ImageView img03;
    ImageView img04;
    ImageView img05;

    Map<Integer, Uri> imageSelectedUriMap;

    TinyDB tinyDB;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate_write);

        progressBar = findViewById(R.id.progressBar);
        imageSelectedUriMap = new HashMap<>();
        mGlideRequestManager = Glide.with(this);
        tinyDB = new TinyDB(this);

        imageInt();
    }

    /**
     * todo 추후 버터나이프
     */
    private void imageInt() {
        txtArea = findViewById(R.id.txt_area);
        txtAddress = findViewById(R.id.txt_address);

        edit_title = findViewById(R.id.edit_title);
        edit_tel = findViewById(R.id.edit_tel);
        edit_email = findViewById(R.id.edit_price);
        edit_price = findViewById(R.id.edit_product_desc);
        edit_product_desc = findViewById(R.id.edit_product_desc);

        img01 = findViewById(R.id.img01);
        img02 = findViewById(R.id.img02);
        img03 = findViewById(R.id.img03);
        img04 = findViewById(R.id.img04);
        img05 = findViewById(R.id.img05);
    }

    public void uploadData() {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (Map.Entry<Integer, Uri> mapUri : imageSelectedUriMap.entrySet()) {
            parts.add(prepareFilePart("file[]", mapUri.getValue())); // Note: attachment[]. Not attachment
        }

//        RequestBody requestBodyFolderName = createPartFromString(folderName);

        HashMap<String, RequestBody> requestMap = new HashMap<>();

        requestMap.put("Title", createPartFromString(edit_title.getText().toString())); // 제목
        requestMap.put("areacode", createPartFromString(txtAddress.getText().toString())); //지역코드
        requestMap.put("Address", createPartFromString(txtArea.getText().toString())); //주소
        requestMap.put("Tel", createPartFromString(edit_tel.getText().toString())); //연락처
        requestMap.put("Email", createPartFromString(edit_email.getText().toString())); //이메일
        requestMap.put("Pay", createPartFromString(edit_price.getText().toString()));   //가격
        requestMap.put("Content", createPartFromString(edit_product_desc.getText().toString())); //내용
        requestMap.put("userid", createPartFromString(tinyDB.getString(LoginActivity.KEY_USER_ID))); //아이디

//        requestMap.put("Title", createPartFromString("test2"));     // 제목
//        requestMap.put("areacode", createPartFromString("D1"));   // 지역코드
//        requestMap.put("Address", createPartFromString("dfsdfds"));    // 주소
//        requestMap.put("Tel", createPartFromString("0313424234"));        // 연락처
//        requestMap.put("Email", createPartFromString("kjk0619@gmail.com"));      // 이메일
//        requestMap.put("Pay", createPartFromString("1200"));        // 금액
//        requestMap.put("Content", createPartFromString("test contents"));    // 내용
//        requestMap.put("userid", createPartFromString("kjk0619"));     // 아이디

        progressBar.setVisibility(View.VISIBLE);
        PushSendUtil.getSingApi().registerEstate(requestMap, parts)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        progressBar.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            Toasty.success(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            finish();
                            Log.e("test", "success");
                            return;
                        }
                        Toasty.error(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toasty.error(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        Log.e("test", "error : " + t.getMessage());
                    }
                });
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public void onClickEventTop(View view) {
        finish();
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_area:
            case R.id.btn_area:
                Intent intent = new Intent(EstateWriteActivity.this, PopupAreaActivity.class);
                startActivityForResult(intent, AREA_SELECT);
                break;
            case R.id.txt_address:
            case R.id.btn_address:
                Intent intentMap = new Intent(EstateWriteActivity.this, RegionAddressActivity.class);
                startActivityForResult(intentMap, MAP_SELECT);
                break;
            case R.id.btn_ok:
                uploadData();
                break;
            case R.id.btn_cancel:
                //사실 확인 창 필요
                finish();
                break;
        }
    }

    private final static int AREA_SELECT = 0x0100;
    private final static int MAP_SELECT = 0x0200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case AREA_SELECT:
                txtArea.setText("" + data.getStringExtra("areaName"));
                break;
            case MAP_SELECT:
                txtAddress.setText("" + data.getStringExtra("address"));
                latitude = data.getDoubleExtra("latitude", 0.0d);
                longitude = data.getDoubleExtra("longitude", 0.0d);

                break;
        }
    }

    public void onClickSigleImageSelect(final View view) {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(EstateWriteActivity.this)
                        .setOnImageSelectedListener(uri -> {
                            Log.d("ted", "uri: " + uri);
                            Log.d("ted", "uri.getPath(): " + uri.getPath());

                            //아래 이미지는 performclick처리?
                            switch (view.getId()) {
                                case R.id.img01:
                                    imageSelectedUriMap.put(R.id.img01, uri);
                                    break;
                                case R.id.img02:
                                    imageSelectedUriMap.put(R.id.img02, uri);
                                    break;
                                case R.id.img03:
                                    imageSelectedUriMap.put(R.id.img03, uri);
                                    break;
                                case R.id.img04:
                                    imageSelectedUriMap.put(R.id.img04, uri);
                                    break;
                                case R.id.img05:
                                    imageSelectedUriMap.put(R.id.img05, uri);
                                    break;
                            }

                            view.post(() -> mGlideRequestManager
                                    .load(uri)  //.load(uri.toString())
                                    .into((ImageView) view));
                        })
//                        .setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
//                        .setSelectedUri(selectedUri01)
//                        .showVideoMedia()
                        .setPeekHeight(1200)
                        .create();


                bottomSheetDialogFragment.show(getSupportFragmentManager());
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toasty.error(EstateWriteActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.with(EstateWriteActivity.this)
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message)
                .setDeniedTitle("Permission denied")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("bla bla")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

}

