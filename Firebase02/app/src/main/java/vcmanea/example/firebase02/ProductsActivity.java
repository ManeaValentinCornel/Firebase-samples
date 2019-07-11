package vcmanea.example.firebase02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class  ProductsActivity extends AppCompatActivity {





    Button jump;

    //Firebase

    DatabaseReference mReference;


    //RecyclerView
    private RecyclerView mRecyclerView;
    private ProductsAdapter mProductsAdapter;
    private ArrayList<Product> mProductsList;
    private SearchView mSearchView;

    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        //Btn jump
        jump=findViewById(R.id.jump_to_add);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductsActivity.this,AddProducts.class);
                startActivity(intent);
            }
        });


        //RecyclerView
        mRecyclerView = findViewById(R.id.product_recyclerView);
        mProductsAdapter = new ProductsAdapter(this, mProductsList);
        LinearLayoutManager mLinearLayoutManager= new LinearLayoutManager(ProductsActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Divider item decoration
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mRecyclerView.getContext(),mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mProductsAdapter);
        mProductsAdapter.notifyDataSetChanged();








    }










    //Search View






}
