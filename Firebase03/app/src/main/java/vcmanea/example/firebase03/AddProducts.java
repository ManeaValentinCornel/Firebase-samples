package vcmanea.example.firebase03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddProducts extends AppCompatActivity {

    private static final String TAG = "AddProducts";

    private EditText titleText;
    private EditText catagoryText;
    EditText descriptionText;
    EditText extraText;

    String mTitle;
    String mCategory;
    String mDescription;
    String mExtra;

    Button btnJumpDelete;
    Button btnJump;
    Button btnAdd;

    //Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        db = FirebaseFirestore.getInstance();

        titleText = findViewById(R.id.add_title);
        catagoryText = findViewById(R.id.add_category);
        descriptionText = findViewById(R.id.add_description);
        extraText = findViewById(R.id.add_extra);


        btnJumpDelete=findViewById(R.id.jump_to_delete_btn);
        btnJumpDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddProducts.this,DeleteActivity.class);
                startActivity(intent);
            }
        });
        btnJump = findViewById(R.id.jump_to_see);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProducts.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        btnAdd = findViewById(R.id.add_products);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Log.d(TAG, "onClick: ");
                    writeNewProduct();
                }
            }
        });


    }

    //Field verificaton
    public boolean validate() {
        mTitle = titleText.getText().toString().trim().toUpperCase();
        if (mTitle.isEmpty()) {
            titleText.setError("Introduceti titlu");
            titleText.requestFocus();
            return false;
        }
        mCategory = catagoryText.getText().toString().trim();
        if (mCategory.isEmpty()) {
            catagoryText.setError("Introduceti categoria produsului");
            catagoryText.requestFocus();
            return false;
        }
        mDescription = descriptionText.getText().toString().trim();
        if (mDescription.isEmpty()) {
            descriptionText.setError("Introduceti descriere");
            descriptionText.requestFocus();
            return false;
        }
        mExtra = extraText.getText().toString().trim();
        if (mExtra.isEmpty()) {
            extraText.setError("Introduceti descriere extra");
            extraText.requestFocus();
            return false;
        }


        return true;

    }

    //Write Products
    public void writeNewProduct() {
        Product product = new Product(mTitle, mCategory, mDescription, mExtra);

        DocumentReference dbProducts = db.collection("products").document(product.getName());


        dbProducts.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddProducts.this, "Product is added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProducts.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}