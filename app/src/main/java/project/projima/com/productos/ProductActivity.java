package project.projima.com.productos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import project.projima.com.productos.model.Product;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        TextView name = findViewById(R.id.product_profile_name);
        TextView description = findViewById(R.id.product_profile_description);
        TextView price = findViewById(R.id.product_profile_price);
        ImageView image = findViewById(R.id.product_profile_image);


        Product product = (Product) getIntent().getParcelableExtra("product");
        name.setText(product.getName());
        description.setText(product.getDescription());
        if(product.getIamgeUrl() != null)
            Picasso.with(getApplicationContext()).load(product.getIamgeUrl()).into(image);
        price.setText(String.format("¢ %d",product.getPrice()));



    }
}
