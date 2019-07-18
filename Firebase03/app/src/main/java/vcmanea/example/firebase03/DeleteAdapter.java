package vcmanea.example.firebase03;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DeleteViewHolder> {
    public Context mContext;
    public ArrayList<Product> mProductsList;

    public DeleteAdapter(Context context, ArrayList<Product> productsList) {
        mContext = context;
        mProductsList = productsList;
    }

    @NonNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewHolder= LayoutInflater.from(mContext).inflate(R.layout.delete_product_row,parent,false);

        return new DeleteViewHolder(viewHolder);


    }

    @Override
    public void onBindViewHolder(@NonNull DeleteViewHolder holder, int position) {
    holder.deleteTitle.setText(mProductsList.get(holder.getAdapterPosition()).getName());

    }

    @Override
    public int getItemCount() {
        return (mProductsList==null)? 0:mProductsList.size();
    }

    public static class DeleteViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mLinearLayout;
        TextView deleteTitle;

        public DeleteViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout=itemView.findViewById(R.id.linear_delete_row);
            deleteTitle=itemView.findViewById(R.id.delete_title);
        }
    }



}
