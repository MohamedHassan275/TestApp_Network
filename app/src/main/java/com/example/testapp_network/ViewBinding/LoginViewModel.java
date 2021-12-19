package com.example.testapp_network.ViewBinding;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp_network.Model.LoginModel;
import com.example.testapp_network.Retrofit_Api.Retrofit_Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

   public MutableLiveData<LoginModel> loginModelMutableLiveData = new MutableLiveData<>();

    public void SetLogin(String email, String password) {

        Call<LoginModel> call = Retrofit_Api.RETROFIT_API_INSTANCE().SetLogin(email, password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {


                loginModelMutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }
}
