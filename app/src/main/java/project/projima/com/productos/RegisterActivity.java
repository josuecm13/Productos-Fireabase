package project.projima.com.productos;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }



    public void toSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void registerUser(View view) {
        String email = ((EditText) findViewById(R.id.registration_email_edit_text)).getText().toString();
        String password = ((EditText)findViewById(R.id.registration_password_edit_text)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.registration_password_copy_edit_text)).getText().toString();
        if (password.equals(confirmPassword)){

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                Log.d("FbAuth", user.getEmail());
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.w("FbAuth", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            EditText confirmPassEditText = findViewById(R.id.registration_password_copy_edit_text);
            confirmPassEditText.setError("Las contraseñas no coinciden");
        }
    }
}
