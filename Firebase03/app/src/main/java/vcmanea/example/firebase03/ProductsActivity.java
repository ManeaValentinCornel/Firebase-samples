package vcmanea.example.firebase03;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.ListDocumentsRequest;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private static final String TAG = "ProductsActivity";
    //Query
    Query queryNormal;
    Query queryNext;
    //SearchView
    private SearchView mSearchView;
    Query query;
    //Firebase
   private FirebaseFirestore db;
   //RecyclerView
    private RecyclerView mRecyclerView;
    private ProductsAdapter mProductsAdapter;
    private ArrayList<Product> mProductsList;
    //Btn's
    private Button jump;
    private Button addBtn;
    //Recycler view scroll listener
    private boolean isScrolling = true;
    int currentItem, pastVisiblesItems, visibleItemCount, totalItemCount;
    //ProgressBar
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //init lsit

        mProductsList = new ArrayList<>();
        //Btn jump
        jump = findViewById(R.id.jump_to_add);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivity.this, AddProducts.class);
                startActivity(intent);
            }
        });

        //ProgressBar
        mProgressBar=findViewById(R.id.progressBar);
        //RecyclerView
        mRecyclerView = findViewById(R.id.product_recyclerView);
        mProductsAdapter = new ProductsAdapter(this, mProductsList);
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ProductsActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mProductsAdapter);

        //Firebase
        db = FirebaseFirestore.getInstance();
        normalSearch();

        //Recycler view scrollListener
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                    visibleItemCount=mLinearLayoutManager.getChildCount();
                    Log.d(TAG, "onScrolled: visibleItemCount" + visibleItemCount);
                    totalItemCount=mLinearLayoutManager.getItemCount();
                    Log.d(TAG, "onScrolled: totalItemCount" +totalItemCount);
                    pastVisiblesItems=mLinearLayoutManager.findFirstVisibleItemPosition();
                    Log.d(TAG, "onScrolled pastVisiblesItems" + pastVisiblesItems);

                    if(isScrolling && (visibleItemCount + pastVisiblesItems>=totalItemCount)){
                        Log.d(TAG, "onScrolled: " + "Last item wow");
                        isScrolling=false;
                        nextSearch();



                }
            }
        });




        //Search View
        mSearchView=findViewById(R.id.searchView_products);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                //Clear the list
                mProductsList.clear();

                //FirebaseSearch

                if(newText.trim().equals("")){
                    normalSearch();
                }


                else {

                    // Create a reference to the cities collection
                    CollectionReference titleReference = db.collection("products");

                    // Create a query against the collection

                    query = titleReference.whereEqualTo("name", newText.toUpperCase());

                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if (!queryDocumentSnapshots.isEmpty()) {

                                List<DocumentSnapshot> newList = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : newList) {
                                    Product product = d.toObject(Product.class);
                                    mProductsList.add(product);
                                    mProductsAdapter.notifyDataSetChanged();
                                }
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }
                mProductsAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    //Regular search with pagination
    public void normalSearch() {
        if (queryNormal == null) {
            queryNormal = db.collection("products").limit(5);
        }
        queryNormal.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot d : list) {
                            Product product = d.toObject(Product.class);
                            mProductsList.add(product);
                        }
                        mProductsAdapter.notifyDataSetChanged();


                        // Get the last visible document
                        DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                        //Constru a new query starting at this document, get the next 5
                        queryNext= db.collection("products")
                                .startAfter(lastVisible)
                                .limit(10);


                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }


        public void nextSearch() {
        mProgressBar.setVisibility(View.VISIBLE);
        //Added it on  another thread



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //In case of any bugs...
                    if (queryNext != null) {

                        queryNext.get().addOnSuccessListener(ProductsActivity.this,new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {

                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                    for (DocumentSnapshot d : list) {
                                        Product product = d.toObject(Product.class);
                                        mProductsList.add(product);

                                    }
                                    mProductsAdapter.notifyDataSetChanged();


                                    // Get the last visible document
                                    DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size()-1);
                                    //Constru a new query starting at this document, get the next 5
                                    queryNext = db.collection("products")
                                            .startAfter(lastVisible)
                                            .limit(10);
                                    mProgressBar.setVisibility(View.GONE);



                                }
                            }
                        }).addOnFailureListener(ProductsActivity.this,new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProductsActivity.this, "Error occured " + e.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }).addOnCanceledListener(ProductsActivity.this,new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }).addOnCompleteListener(ProductsActivity.this,new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });

                    }
                    else{
                        normalSearch();
                    }


                }
            },2000);


        }





}
