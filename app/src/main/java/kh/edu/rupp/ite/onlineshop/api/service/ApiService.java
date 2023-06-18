package kh.edu.rupp.ite.onlineshop.api.service;

import java.util.List;

import kh.edu.rupp.ite.onlineshop.api.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/Midterm/Products/products.json")
     Call<List<Product>> loadProductList();
}
