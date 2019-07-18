package vcmanea.example.firebase03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {
    private static final String TAG = "DeleteActivity";
    DeleteAdapter mDeleteAdapter;
    RecyclerView mRecyclerView;
    ArrayList<Product> mProductsList;


    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        //ProductList
        mProductsList=new ArrayList<>();

        //RecyclerView
        mRecyclerView=findViewById(R.id.product_recyclerView_delete);
        mDeleteAdapter=new DeleteAdapter(DeleteActivity.this,mProductsList);
        LinearLayoutManager mLinearLayoutManager= new LinearLayoutManager(DeleteActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mRecyclerView.getContext(),mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mDeleteAdapter);

        //Firebase firestore get Elemets
        db=FirebaseFirestore.getInstance();
        db.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                    for(DocumentSnapshot d:list){
                       Product product=d.toObject(Product.class);
                       mProductsList.add(product);
                    }
                    mDeleteAdapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });



    }




}
