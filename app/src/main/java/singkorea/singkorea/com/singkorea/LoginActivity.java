package singkorea.singkorea.com.singkorea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.LoginListModel;
import singkorea.singkorea.com.singkorea.util.TinyDB;

public class LoginActivity extends AppCompatActivity {

    private TinyDB tinyDB;

    private EditText editId;
    private EditText editPassword;
    private ImageButton checkId;
    private ImageButton checkAutoLogin;

    public static String KEY_USER_ID = "UserId";
    public static String KEY_PASSWORD = "Password";
    public static String KEY_AUTO_LOGIN= "AutoLogin";
    public static String KEY_CHECK_USER_ID = "CheckUserId";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tinyDB = new TinyDB(this);

        String checkUserId = tinyDB.getString(KEY_CHECK_USER_ID);
        String userPassword = tinyDB.getString(KEY_PASSWORD);

        editId = findViewById(R.id.edit_id);
        editPassword = findViewById(R.id.edit_password);
        checkId = findViewById(R.id.check_id);
        checkAutoLogin = findViewById(R.id.check_auto_login);

        if(checkUserId != null && !"".equals(checkUserId)) {
            editId.setText(checkUserId);
            checkId.setSelected(true);
        }

        editId.setOnEditorActionListener((textView, id, keyEvent) -> {
            if(id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL){
                return true;
            }
            return false;
        });
        editPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                login();
                return true;
            }
            return false;
        });

    }

    public void onClickEventTop(View view) {
        finish();
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterMemberActivity.class);
                startActivity(intent);
                break;
            case R.id.check_auto_login:
                if(checkAutoLogin.isSelected()) {
                    checkAutoLogin.setSelected(false);
                } else {
                    checkAutoLogin.setSelected(true);
                }
                break;
            case R.id.check_id:
                if(checkId.isSelected()) {
                    checkId.setSelected(false);
                } else {
                    checkId.setSelected(true);
                }
                break;
            default:
                break;
        }
    }

    public void login() {
        final String userId = editId.getText().toString();
        if("".equals(userId)) {
            Toasty.warning(getApplicationContext(), "Input Id", Toast.LENGTH_SHORT).show();
            return;
        }

        final String password = editPassword.getText().toString();
        if("".equals(password)) {
            Toasty.warning(getApplicationContext(), "Input Passsword", Toast.LENGTH_SHORT).show();
            return;
        }

        PushSendUtil.getSingApi().login(editId.getText().toString(), editPassword.getText().toString())
                .enqueue(new retrofit2.Callback<LoginListModel>() {
                    @Override
                    public void onResponse(Call<LoginListModel> call, Response<LoginListModel> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getLIST() == null) {
                                Toasty.error(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                            } else {
                                if(checkId.isSelected()) {
                                    tinyDB.putString(KEY_CHECK_USER_ID,""+response.body().getLIST().get(0).getUserID());
                                } else {
                                    tinyDB.remove(KEY_CHECK_USER_ID);
                                }

                                tinyDB.putString(KEY_USER_ID, userId);
                                tinyDB.putString(KEY_PASSWORD, password);

                                if(checkAutoLogin.isSelected()) {
                                    tinyDB.putBoolean(KEY_AUTO_LOGIN, true);
                                } else {
                                    tinyDB.putBoolean(KEY_AUTO_LOGIN, false);
                                }

                                Toasty.success(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        } else {
                            Toasty.error(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginListModel> call, Throwable t) {
                        Toasty.error(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
