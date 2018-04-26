package singkorea.singkorea.com.singkorea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.BasicModel;

public class RegisterMemberActivity extends AppCompatActivity {

    private ImageButton radioSing;
    private ImageButton radioEtc;

    private EditText editId;
    private EditText editPass;
    private EditText editRePass;
    private EditText editName;
    private EditText editEMail;

    String idCheckString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

        radioSing = findViewById(R.id.radio_sing);
        radioEtc = findViewById(R.id.radio_etc);

        editId = findViewById(R.id.edit_id);
        editPass = findViewById(R.id.edit_password);
        editRePass = findViewById(R.id.edit_check_password);
        editName = findViewById(R.id.edit_name);
        editEMail = findViewById(R.id.edit_email);
    }

    public void onClickEventTop(View view) {
        finish();
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_check_id:
                PushSendUtil.getSingApi().checkId(editId.getText().toString())
                        .enqueue(new Callback<BasicModel>() {
                            @Override
                            public void onResponse(Call<BasicModel> call, Response<BasicModel> response) {
                                if(response.isSuccessful()) {

                                    if("SUCCESS".equals(response.body().getRESULT())) {
                                        Toasty.success(getApplicationContext(), "사용가능 ID 입니다", Toast.LENGTH_SHORT).show();
                                        idCheckString = editId.getText().toString();
                                    } else {
                                        Toasty.error(getApplicationContext(), response.body().getMSG(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BasicModel> call, Throwable t) {
                                Toasty.error(getApplicationContext(), "서버통신에 실패하였습니다", Toast.LENGTH_SHORT).show();
                            }
                        });

                break;
            case R.id.btn_ok:


                String curId = editId.getText().toString();
                if("".equals(curId)) {
                    Toasty.info(getApplicationContext(), "ID는 필수 입력값입니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!idCheckString.equals(curId)) {
                    Toasty.info(getApplicationContext(), "인증되지 않은 ID입니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                String curName = editName.getText().toString();
                if("".equals(curName)) {
                    Toasty.info(getApplicationContext(), "이름은 필수 입력값입니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                String curPass = editPass.getText().toString();
                String curRePass = editRePass.getText().toString();

                if( "".equals(curPass) || !curPass.equals(curRePass)) {
                    Toasty.info(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                String location = "";
                if(radioSing.isSelected()) {
                    location = "singapor";
                } else if(radioEtc.isSelected()) {
                    location = "etc";
                }

                PushSendUtil.getSingApi().registerMember(curId, curPass, curName,
                        editEMail.getText().toString(), location, "APP")
                        .enqueue(new Callback<BasicModel>() {
                            @Override
                            public void onResponse(Call<BasicModel> call, Response<BasicModel> response) {
                                if (response.isSuccessful()) {
                                    if("SUCCESS".equals(response.body().getRESULT())) {
                                        Toasty.success(getApplicationContext(), "회원 가입되었습니다", Toast.LENGTH_SHORT).show();
                                        finish();
                                        return;
                                    }
                                }
                                Toasty.error(getApplicationContext(), "회원 가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<BasicModel> call, Throwable t) {
                                Toasty.error(getApplicationContext(), "회원 가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                            }
                        });

                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.radio_sing:
                radioSing.setSelected(true);
                radioEtc.setSelected(false);
                break;
            case R.id.radio_etc:
                radioSing.setSelected(false);
                radioEtc.setSelected(true);
                break;
        }

    }
}
