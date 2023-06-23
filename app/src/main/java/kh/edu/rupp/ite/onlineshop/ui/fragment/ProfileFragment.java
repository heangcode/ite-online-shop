package kh.edu.rupp.ite.onlineshop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import kh.edu.rupp.ite.onlineshop.R;
import kh.edu.rupp.ite.onlineshop.api.model.Profile;
import kh.edu.rupp.ite.onlineshop.api.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private EditText inputEmail, inputPhone, inputGender, inputBirthday, inputAddress;
    private TextView email, first_name, last_name;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        first_name = view.findViewById(R.id.firstName);
        last_name = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        inputEmail = view.findViewById(R.id.input_email);
        inputPhone = view.findViewById(R.id.input_phone);
        inputGender = view.findViewById(R.id.input_gender);
        inputBirthday = view.findViewById(R.id.input_birthday);
        inputAddress = view.findViewById(R.id.input_address);

        loadProfileData();

        return view;
    }

    private void loadProfileData() {
//        email.setText(profile.getEmail());

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com").addConverterFactory(GsonConverterFactory.create()).build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Profile> call = apiService.getProfileData();

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile profile = response.body();
                if (profile != null) {
                    first_name.setText(profile.getFirst_name());
                    last_name.setText(profile.getLast_name());
                    email.setText(profile.getEmail());
                    inputEmail.setText(profile.getEmail());
                    inputPhone.setText(profile.getPhoneNumber());
                    inputGender.setText(profile.getGender());
                    inputBirthday.setText(profile.getBirthday());
                    inputAddress.setText(profile.getAddress());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getActivity(), "Error loading profile data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
