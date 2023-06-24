package kh.edu.rupp.ite.onlineshop.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import kh.edu.rupp.ite.onlineshop.R;
import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.databinding.ViewHolderGridProductBinding;
import kh.edu.rupp.ite.onlineshop.databinding.ViewHolderProductBinding;

public class ProductsAdapter extends ListAdapter<Product, RecyclerView.ViewHolder> {

    private boolean isListView;

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

    public void setIsListView(boolean isListView) {
        this.isListView = isListView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.view_holder_product) {
            ViewHolderProductBinding binding = ViewHolderProductBinding.inflate(layoutInflater, parent, false);
            return new ProductViewHolder(binding);
        } else {
            ViewHolderGridProductBinding binding = ViewHolderGridProductBinding.inflate(layoutInflater, parent, false);
            return new GridProductViewHolder(binding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isListView ? R.layout.view_holder_product : R.layout.view_holder_grid_product;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product item = getItem(position);
        if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).bind(item);
        } else if (holder instanceof GridProductViewHolder) {
            ((GridProductViewHolder) holder).bind(item);
        }
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
            loadImage(itemBinding.imgProduct, product.getImageUrl());

        }

        private void loadImage(ImageView imgProduct, String imageUrl) {
            Glide.with(imgProduct.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.ic_error)
                    .transform(new RoundedCornersTransformation(50, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(imgProduct);
        }
    }

    public static class GridProductViewHolder extends RecyclerView.ViewHolder {
        private final ViewHolderGridProductBinding itemBinding;

        public GridProductViewHolder(ViewHolderGridProductBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Product product) {
            itemBinding.txtProductRating.setText(String.valueOf(product.getRating()));
            itemBinding.txtProductName.setText(product.getName());
            itemBinding.txtProductPrice.setText("$" + product.getPrice());
            loadImage(itemBinding.imgGridProduct, product.getImageUrl());
        }
    }

    private static void loadImage(ImageView imgProduct, String imageUrl) {
        Glide.with(imgProduct.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.profile)
                .error(R.drawable.ic_error)
                .transform(new RoundedCornersTransformation(50, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imgProduct);
    }

}
