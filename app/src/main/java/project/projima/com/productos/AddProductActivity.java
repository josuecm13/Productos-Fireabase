package project.projima.com.productos;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import project.projima.com.productos.model.Product;

public class AddProductActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private StorageReference storageReference;
    Uri imageUri;
    ImageView galleryImageView;

    private  static final int GALLERY_PICK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("images");
        //storageReference.child("images");
        galleryImageView = findViewById(R.id.add_product_image);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==GALLERY_PICK && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            Picasso.with(getApplicationContext()).load(imageUri).into(galleryImageView);
        }

    }


    public void uploadImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_PICK);
    }


    public void addProduct(View view) {
        EditText nameEditText = findViewById(R.id.add_product_name);
        String name = nameEditText.getText().toString();
        EditText descriptionEditText = findViewById(R.id.add_product_description);
        EditText priceEditText = findViewById(R.id.add_product_price);
        String description = descriptionEditText.getText().toString();
        int price = Integer.parseInt(priceEditText.getText().toString());
        if(name.equals("")){
            nameEditText.setError("Debe ingresar un nombre");
            return;
        }
        if(description.equals("")){
            descriptionEditText.setError("Debe ingresar una descripción");
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Product product = new Product(name,description,"",price);
        final DatabaseReference userReference = reference.child("productos").push();
        userReference.setValue(product);
        String uid = userReference.getKey();
        if(imageUri != null){
            final StorageReference imgref = storageReference.child(uid+".jpg");
            UploadTask uploadTask = imgref.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imgref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String url = downloadUri.toString();
                        userReference.child("image").setValue(url);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }else{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
