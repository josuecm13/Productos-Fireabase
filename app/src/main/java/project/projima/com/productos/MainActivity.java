package project.projima.com.productos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.projima.com.productos.adapter.ProductsAdapter;
import project.projima.com.productos.model.Product;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Product> productList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();
        final ProductsAdapter adapter = new ProductsAdapter(productList,getApplicationContext());


        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("productos").orderByChild("name");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Product product = new Product();

                        String name = dataSnapshot1.child("name").getValue(String.class);
                        String description = dataSnapshot1.child("description").getValue(String.class);
                        String image = dataSnapshot1.child("image").getValue(String.class);
                        int price = dataSnapshot1.child("price").getValue(Integer.class);
                        String uniqueid = dataSnapshot1.getKey();

                        product.setName(name);
                        product.setDescription(description);
                        product.setIamgeUrl(image);
                        product.setPrice(price);
                        product.setUniqueid(uniqueid);



                        productList.add(product);
                    }
                    ProductsAdapter productsAdapter = new ProductsAdapter(productList,getApplicationContext());
                    recyclerView.setAdapter(productsAdapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }


    public void addProductActivity(View view) {
        startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
    }
}
