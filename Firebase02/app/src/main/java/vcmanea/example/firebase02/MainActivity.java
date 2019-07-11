package vcmanea.example.firebase02;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        writeNewProduct("1","gfica","gicaa","gicdsa","gifdca");




    }


//    private void writeNewProduct(String userID, String title, String category, String description, String extraDetails) {
//
//        Product product = new Product(title, category, description, extraDetails);
//
//        mDatabaseReference.child("products").child(userID).setValue(product);
//
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Product product = dataSnapshot.getValue(Product.class);
//                if(product!=null) {
//                    Log.d(TAG, "onDataChange: "+product.getName() );
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "onCancelled: ", databaseError.toException());
//            }
//        });
//
//
//    }

}
