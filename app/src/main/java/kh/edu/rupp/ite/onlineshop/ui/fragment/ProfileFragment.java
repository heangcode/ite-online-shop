package kh.edu.rupp.ite.onlineshop.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

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
    private TextView email, fullName;
    private ImageView profilePicture;
    private ProgressBar loadingSpinner;
    private LinearLayout contentLayout, loadingLayout;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email);
        profilePicture = view.findViewById(R.id.profile_picture);
        inputEmail = view.findViewById(R.id.input_email);
        inputPhone = view.findViewById(R.id.input_phone);
        inputGender = view.findViewById(R.id.input_gender);
        inputBirthday = view.findViewById(R.id.input_birthday);
        inputAddress = view.findViewById(R.id.input_address);
        loadingSpinner = view.findViewById(R.id.loading_spinner);
        contentLayout = view.findViewById(R.id.content_layout);
        loadingLayout = view.findViewById(R.id.loading_layout);

        loadProfileData();

        return view;
    }

    private void loadProfileData() {
        // Set it to visible
        loadingSpinner.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com").addConverterFactory(GsonConverterFactory.create()).build();

                ApiService apiService = retrofit.create(ApiService.class);
                Call<Profile> call = apiService.getProfileData();

                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Profile profile = response.body();
                        if (profile != null) {
                            fullName.setText(profile.getFirst_name() + " " + profile.getLast_name());
                            email.setText(profile.getEmail());
                            inputEmail.setText(profile.getEmail());
                            inputPhone.setText(profile.getPhoneNumber());
                            inputGender.setText(profile.getGender());
                            inputBirthday.setText(profile.getBirthday());
                            inputAddress.setText(profile.getAddress());
                            // Load profile image with Glide
                            Glide.with(getActivity()).load(profile.getImageUrl()).placeholder(R.drawable.profile).circleCrop()  // This line makes the image rounded
                                    .into(profilePicture);
                        }
                        // After loading data, set the ProgressBar to gone
                        loadingLayout.setVisibility(View.GONE);
                        contentLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error loading profile data", Toast.LENGTH_SHORT).show();
                        // In case of failure too, set the ProgressBar to gone
                        loadingSpinner.setVisibility(View.GONE);
                    }
                });
            }
        }, 1000);  // This 3000 here is the delay time in milliseconds. This means the code inside run() will execute after 3 seconds.
    }
}