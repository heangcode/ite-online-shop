package kh.edu.rupp.ite.onlineshop.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import kh.edu.rupp.ite.onlineshop.R;
import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.databinding.ViewHolderProductBinding;

public class ProductsAdapter extends ListAdapter<Product, ProductsAdapter.ProductViewHolder> {


    public ProductsAdapter() {

        super(new DiffUtil.ItemCallback<Product>() {
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderProductBinding binding = ViewHolderProductBinding.inflate(layoutInflater, parent, false);
        return new ProductViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product item = getItem(position);
        holder.bind(item);

    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ViewHolderProductBinding itemBinding;

        public ProductViewHolder(ViewHolderProductBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Product product) {
            itemBinding.txtProductName.setText(product.getName());
            itemBinding.txtProductPrice.setText("$" + product.getPrice());

            Glide.with(itemBinding.imgProduct.getContext())
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.ic_error)
                    .transform(new RoundedCornersTransformation(50, 0, RoundedCornersTransformation.CornerType.ALL)) // adjust the radius as you need
                    .into(itemBinding.imgProduct);
        }

    }
}
