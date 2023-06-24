package kh.edu.rupp.ite.onlineshop.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.api.service.ApiService;
import kh.edu.rupp.ite.onlineshop.databinding.FragmentProductBinding;
import kh.edu.rupp.ite.onlineshop.ui.adapter.ProductsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;
    private boolean isListView = true; // Default view is List View

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isListView = true;
                showProductList();
            }
        });

        binding.buttonGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isListView = false;
                showProductList();
            }
        });

        showProductList();
    }


    private void showProductList() {
        Retrofit httpClient = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com").addConverterFactory(GsonConverterFactory.create()).build();

        // Create service Object
        ApiService apiService = httpClient.create(ApiService.class);

        // Get the list of products from the server
        Call<List<Product>> task = apiService.loadProductList();
        task.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    showProductList(response.body());
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                Log.e("[ProductFragment]", "Failed to get product list", t);
                t.printStackTrace();
            }
        });

    }

    private void showProductList(List<Product> productList) {
        // Create LayoutManager
        RecyclerView.LayoutManager layoutManager;
        if (isListView) {
            layoutManager = new LinearLayoutManager(getContext());
        } else {
            layoutManager = new GridLayoutManager(getContext(), 2); // 2 is number of columns
        }
        binding.recyclerView.setLayoutManager(layoutManager);

        // Create Adapter
        ProductsAdapter adapter = new ProductsAdapter();
        adapter.submitList(productList);
        binding.recyclerView.setAdapter(adapter);
    }
}
