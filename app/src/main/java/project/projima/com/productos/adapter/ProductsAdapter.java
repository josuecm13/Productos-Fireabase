package project.projima.com.productos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import project.projima.com.productos.ProductActivity;
import project.projima.com.productos.R;
import project.projima.com.productos.model.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private View view;


    public ProductsAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_layout,null,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        final Product currentProduct = productList.get(i);
        Picasso.with(context).load(currentProduct.getIamgeUrl()).into(productViewHolder.productImage);
        productViewHolder.productName.setText(currentProduct.getName());
        productViewHolder.productPrice.setText(String.format("¢ %d",currentProduct.getPrice()));
        productViewHolder.deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
                firebase.child(currentProduct.getUniqueid()).removeValue();
                return true;
            }
        });
        final int temp = i;
        productViewHolder.instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("product",productList.get(temp));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        View instance;
        TextView productName, productPrice;
        ImageView productImage;
        ImageView deleteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            instance = itemView;
            productName = itemView.findViewById(R.id.product_item_name);
            productPrice = itemView.findViewById(R.id.product_item_price);
            productImage = itemView.findViewById(R.id.product_item_image);
            deleteButton = itemView.findViewById(R.id.product_item_button);

        }
    }
}
