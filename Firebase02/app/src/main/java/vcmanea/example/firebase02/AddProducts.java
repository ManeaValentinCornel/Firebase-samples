package vcmanea.example.firebase02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProducts extends AppCompatActivity {

    private static final String TAG = "AddProducts";
    
    DatabaseReference mReference;
    EditText titleText;
    EditText catagoryText;
    EditText descriptionText;
    EditText extraText;
    String mTitle;
    String mCategory;
    String mDescription;
    String mExtra;

    Button btnJump;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        titleText=findViewById(R.id.add_title);
        catagoryText=findViewById(R.id.add_category);
        descriptionText=findViewById(R.id.add_description);
        extraText=findViewById(R.id.add_extra);


        btnJump =findViewById(R.id.jump_to_see);
        btnJump.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddProducts.this,ProductsActivity.class);
                startActivity(intent);
            }
        });



        mReference= FirebaseDatabase.getInstance().getReference();

        btnAdd=findViewById(R.id.add_products);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(validate()){
                    Log.d(TAG, "onClick: ");
                    writeNewProduct();
                }
            }
        });




    }

    //Field verificaton
    public boolean validate(){
        mTitle=titleText.getText().toString().trim();
      if(mTitle.isEmpty()){
          titleText.setError("Introduceti titlu");
          titleText.requestFocus();
          return false;
      }
        mCategory=catagoryText.getText().toString().trim();
        if(mCategory.isEmpty()){
            catagoryText.setError("Introduceti categoria produsului");
            catagoryText.requestFocus();
            return false;
        }
        mDescription=descriptionText.getText().toString().trim();
        if(mDescription.isEmpty()){
            descriptionText.setError("Introduceti descriere");
            descriptionText.requestFocus();
            return false;
        }
        mExtra=extraText.getText().toString().trim();
        if(mExtra.isEmpty()){
            extraText.setError("Introduceti descriere extra");
            extraText.requestFocus();
            return false;
        }

        
        return true;

    }
    //Write Products
    public void writeNewProduct(){
        Product product=new Product(mTitle,mCategory,mDescription,mExtra);
        mReference.child("products").push().setValue(product);



    }


}
