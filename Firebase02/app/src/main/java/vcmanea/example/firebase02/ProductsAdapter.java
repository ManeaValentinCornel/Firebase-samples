package vcmanea.example.firebase02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>{
    private static final String TAG = "ProductsAdapter";
    public Context mContext;
    public ArrayList<Product> mProductsList;


    public ProductsAdapter(Context context,ArrayList<Product> productList){
        mContext=context;
        mProductsList=productList;

    }






    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View holder= LayoutInflater.from(mContext).inflate(R.layout.products_row,viewGroup,false);

        return new ProductsViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {

        productsViewHolder.productName.setText(mProductsList.get(productsViewHolder.getAdapterPosition()).getName());
        productsViewHolder.productCategory.setText(mProductsList.get(productsViewHolder.getAdapterPosition()).getCategory());
        productsViewHolder.productDescription.setText(mProductsList.get(productsViewHolder.getAdapterPosition()).getDescription());




    }

    @Override
    public int getItemCount() {
        return (mProductsList==null)? 0:mProductsList.size();
    }




    static class ProductsViewHolder extends RecyclerView.ViewHolder{


        CardView mCardView;
        TextView productName;
        TextView productCategory;
        TextView productDescription;
        ImageView productImage;





        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardView=itemView.findViewById(R.id.cardView_product);

            productName=itemView.findViewById(R.id.product_name);
            productCategory=itemView.findViewById(R.id.product_category);
            productDescription=itemView.findViewById(R.id.product_description);

            productImage=itemView.findViewById(R.id.product_image);





        }
    }

    public void updateList(List<Product> newList){

        mProductsList=new ArrayList<>();
        mProductsList.addAll(newList);
        notifyDataSetChanged();

    }







}
