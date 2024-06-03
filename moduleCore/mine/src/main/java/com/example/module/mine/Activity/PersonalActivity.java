package com.example.module.mine.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.login.Util.OkHttpUtil;
import com.example.module.mine.R;
import com.example.tool.Util.SERVER_IP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/mine/PersonalActivity")
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private String Server_IP = SERVER_IP.Server_IP;
    private String Server_ModifyInfo = SERVER_IP.Server_ModifyInfo;
    EditText edit_name;
    EditText edit_phone;
    EditText edit_email;
    Button modify;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String name;
    String phone;
    String email;
    String m_name;
    String m_phone;
    String m_email;
    boolean is_m_name;
    boolean is_m_phone;
    boolean is_m_email;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initWidget();
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ChangeInformation();
        }
    };

    public void initWidget(){
        context = PersonalActivity.this;
        edit_name = findViewById(R.id.name);
        edit_phone = findViewById(R.id.phone);
        edit_email = findViewById(R.id.email);
        modify = findViewById(R.id.modify);
        modify.setOnClickListener(this);

        sp = getSharedPreferences("Information",MODE_PRIVATE);
        editor = sp.edit();
        name = sp.getString("name",null);
        phone = sp.getString("phone",null);
        email = sp.getString("email",null);

        m_name = name;
        m_phone = phone;
        m_email = email;

        edit_name.setText(name);
        edit_phone.setText(phone);
        edit_email.setText(email);

        setEditTextFocus();
    }
    public void setEditTextFocus(){
        edit_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                m_name = edit_name.getText().toString();
                if (name.equals(m_name)){
                    is_m_name = false;
                }else {
                    is_m_name = true;
                }
            }
        });

        edit_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                m_phone = edit_phone.getText().toString();
                if (phone.equals(m_phone)){
                    is_m_phone = false;
                }else {
                    is_m_phone = true;
                }
            }
        });

        edit_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                m_email = edit_email.getText().toString();
                if (email.equals(m_email)){
                    is_m_email = false;
                }else {
                    is_m_email = true;
                }
            }
        });
    }

    public void ChangeInformation(){
        Log.d("ModifyInformation", "ChangeInformation: successful!");
        edit_name.setText(m_name);
        edit_phone.setText(m_phone);
        edit_email.setText(m_email);

        name = m_name;
        phone = m_phone;
        email = m_email;

        editor.putString("name",name);
        editor.putString("phone",phone);
        editor.putString("email",email);
        editor.apply();
        Toast.makeText(context,"修改信息成功！",Toast.LENGTH_SHORT).show();
    }
    public void sendModifyInformation(){
        OkHttpUtil.sendModifyInformation(Server_IP + Server_ModifyInfo, email,m_name, m_phone, m_email, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String respondData =response.body().string();
                try {
                    JSONObject object = new JSONObject(respondData);
                    Log.d("ModifyInformation", "onResponse: " + respondData);
                    if (object.getInt("code") == 200){
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify){
            edit_email.clearFocus();
            edit_name.clearFocus();
            edit_phone.clearFocus();
            Log.d("ModifyInformation", "onClick: "+m_name);
            Log.d("ModifyInformation", "onClick: "+m_phone);
            Log.d("ModifyInformation", "onClick: "+m_email);
            if (is_m_name || is_m_phone || is_m_email){
                Log.d("ModifyInformation", "onClick: ");
                sendModifyInformation();
            }
        }
    }
}