package com.example.testapp_network.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.testapp_network.Model.LoginModel;
import com.example.testapp_network.Model.LoginResponseModel;
import com.example.testapp_network.R;
import com.example.testapp_network.Retrofit_Api.Retrofit_Api;
import com.example.testapp_network.ViewBinding.RegisterViewModel;
import com.example.testapp_network.Views.PreferenceHelper;
import com.example.testapp_network.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_EMPTY = "";
    ActivityRegisterBinding registerBinding;
    RegisterViewModel registerViewModel;
    PreferenceHelper preferenceHelper;
    String name,email,password,ConfirmPassword,phone,login_type,firebaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());


        preferenceHelper = PreferenceHelper.getInstans(this);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        registerBinding.btnRegisterUserSignUp.setOnClickListener(this);

        
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(name)) {
            registerBinding.etFirstNameSignUp.setError("لا يمكن أن يكون  فارغا");
            registerBinding.etFirstNameSignUp.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(email)) {
            registerBinding.etEmailSignUp.setError("لا يمكن أن يكون  فارغا");
            registerBinding.etEmailSignUp.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            registerBinding.etPasswordSignUp.setError("لا يمكن أن يكون  فارغا");
            registerBinding.etPasswordSignUp.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(ConfirmPassword)) {
            registerBinding.etConfirmPasswordSignIn.setError("لا يمكن أن يكون  فارغا");
            registerBinding.etConfirmPasswordSignIn.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(phone)) {
            registerBinding.etMobileSignUp.setError("لا يمكن أن يكون  فارغا");
            registerBinding.etMobileSignUp.requestFocus();
            return false;
        }
        if (!isVaildEmail(email)) {
            registerBinding.etEmailSignUp.setError("الايميل غير صالح. حاول مرة اخري");
            registerBinding.etEmailSignUp.requestFocus();
            return false;
        }

        if (!(password.equals(ConfirmPassword))) {
            registerBinding.etConfirmPasswordSignIn.setError("كلمة السر وتأكيد كلمة السر غير متطابقة");
            registerBinding.etConfirmPasswordSignIn.requestFocus();
            return false;
        }
        if (!(password.length() > 6)) {
            registerBinding.etPasswordSignUp.setError("كلمة السر غير صالح. حاول مرة اخري");
            registerBinding.etPasswordSignUp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isVaildEmail(String emailHolder) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(emailHolder);
        return m.matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_RegisterUserSignUp:
                name = registerBinding.etFirstNameSignUp.getText().toString().trim();
                email = registerBinding.etEmailSignUp.getText().toString().trim();
                password = registerBinding.etPasswordSignUp.getText().toString().trim();
                ConfirmPassword = registerBinding.etConfirmPasswordSignIn.getText().toString().trim();
                phone = registerBinding.etMobileSignUp.getText().toString().trim();

                if (validateInputs()) {

                    registerViewModel.SetRegisterUser("user",name,email,phone,password,"123654654");

                    registerBinding.progressRegisterCircular.setVisibility(View.VISIBLE);

                    registerViewModel.loginModelMutableLiveData.observe(RegisterActivity.this, new Observer<LoginModel>() {
                        @Override
                        public void onChanged(LoginModel loginModel) {

                            if (loginModel.getCode().equalsIgnoreCase("200")) {

                                registerBinding.progressRegisterCircular.setVisibility(View.GONE);
                                LoginResponseModel loginResponseModel = loginModel.getData();
                                startActivity(new Intent(RegisterActivity.this,HomeMainActivity.class));
                                preferenceHelper.putAccessToken(loginResponseModel.getAccesstoken());
                                preferenceHelper.putUser_typeLogin(loginResponseModel.getLoginType());
                                finish();

                            }else {

                                Toast.makeText(RegisterActivity.this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                                registerBinding.progressRegisterCircular.setVisibility(View.GONE);

                            }
                        }
                    });

                }
                break;

        }
    }
}