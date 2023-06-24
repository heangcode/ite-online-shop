package kh.edu.rupp.ite.onlineshop.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import kh.edu.rupp.ite.onlineshop.R;
import kh.edu.rupp.ite.onlineshop.api.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        TextView productName = findViewById(R.id.product_name);
        TextView productRating = findViewById(R.id.product_rating);
        TextView productPrice = findViewById(R.id.product_price);
        TextView productDescription = findViewById(R.id.product_description);
        ImageView productImage = findViewById(R.id.product_image);
        Picasso.get().load(product.getImageUrl()).into(productImage);
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText("$" + product.getPrice());
        productRating.setText(String.valueOf(product.getRating()));
    }
}
