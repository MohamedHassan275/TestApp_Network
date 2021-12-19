package com.example.testapp_network.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.testapp_network.Model.LoginModel;
import com.example.testapp_network.Model.LoginResponseModel;
import com.example.testapp_network.R;
import com.example.testapp_network.ViewBinding.LoginViewModel;
import com.example.testapp_network.Views.PreferenceHelper;
import com.example.testapp_network.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    String email,password;
    private static final String KEY_EMPTY = "";
    LoginViewModel loginViewModel;
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        preferenceHelper = new PreferenceHelper(this);

        if (!(preferenceHelper.getAccessToken().isEmpty())) {
            startActivity(new Intent(LoginActivity.this,HomeMainActivity.class));
        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        loginBinding.SkipUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });
      loginBinding.btnLoginUser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              email = loginBinding.etEmailSignIn.getText().toString();
              password = loginBinding.etPasswordSignIn.getText().toString();

              if (validateInputs()) {

                  loginBinding.progressCircular.setVisibility(View.VISIBLE);
                  loginViewModel.SetLogin(email,password);

                  loginViewModel.loginModelMutableLiveData.observe(LoginActivity.this, new Observer<LoginModel>() {
                      @Override
                      public void onChanged(LoginModel loginModel) {

                          if (loginModel.getCode().equalsIgnoreCase("200")) {

                              LoginResponseModel loginResponseModel = loginModel.getData();
                              loginBinding.progressCircular.setVisibility(View.GONE);
                              Toast.makeText(LoginActivity.this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                              Toast.makeText(LoginActivity.this, loginResponseModel.getAccesstoken(), Toast.LENGTH_SHORT).show();
                              Log.i("TAG", "onChanged: " + loginModel.getMessage());
                              startActivity(new Intent(LoginActivity.this,HomeMainActivity.class));
                              preferenceHelper.putAccessToken(loginResponseModel.getAccesstoken());
                              preferenceHelper.putUser_typeLogin(loginResponseModel.getLoginType());
                              finish();

                          }else {
                              loginBinding.progressCircular.setVisibility(View.GONE);
                              Toast.makeText(LoginActivity.this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                          }

                      }
                  });

              }

          }
      });


    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(email)) {
            loginBinding.etEmailSignIn.setError("لا يمكن أن يكون  فارغا");
            loginBinding.etEmailSignIn.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            loginBinding.etPasswordSignIn.setError("لا يمكن أن يكون  فارغا");
            loginBinding.etPasswordSignIn.requestFocus();
            return false;
        }
        if (!isVaildEmail(email)) {
            loginBinding.etEmailSignIn.setError("الايميل غير صالح. حاول مرة اخري");
            loginBinding.etEmailSignIn.requestFocus();
            return false;
        }
//        if (!(password.length() > 6)) {
//            Binding.etPasswordSignIn.setError("كلمة السر غير صالح. حاول مرة اخري");
//            Binding.etPasswordSignIn.requestFocus();
//            return false;
//        }
        return true;
    }

    private boolean isVaildEmail(String emailHolder) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(emailHolder);
        return m.matches();
    }

}