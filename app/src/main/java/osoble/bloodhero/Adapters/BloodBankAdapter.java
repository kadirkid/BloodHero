package osoble.bloodhero.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.R;

/**
 * Created by abdulahiosoble on 11/5/17.
 */

public class BloodBankAdapter extends FirebaseRecyclerAdapter<BloodBank, BloodBankAdapter
        .BloodBankViewHolder>{
    private ArrayList<BloodBank> list;
    private Context mContext;

    private Callback callback;

    public interface Callback{
        void onItemClick(String id, String name);
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public BloodBankAdapter(Class<BloodBank> modelClass, int modelLayout, Class<BloodBankViewHolder>
            viewHolderClass, DatabaseReference ref, Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        list = new ArrayList<>();
        Log.i("Contructor", "Was Called");
    }

    @Override
    protected void populateViewHolder(BloodBankViewHolder viewHolder, final BloodBank model,
                                      final int position) {
        list.add(model);
        Log.i("ID is -> ", model.getID());
        callback.onItemClick(list.get(position).getID(), list.get(position).getName());
        viewHolder.bind(model);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null){
                    Log.i("-----------------", "------------------");
                    Log.i("ID IS ->", list.get(position).getID());
                    callback.onItemClick(list.get(position).getID(), list.get(position).getName());
                }
            }
        });

    }

    public static class BloodBankViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        public BloodBankViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.blood_bank_name);
            address = itemView.findViewById(R.id.blood_bank_address);

        }

        //Bind the bloodbank to the recyclerview
        public void bind(BloodBank bloodBank){
            name.setText(bloodBank.getName());
            address.setText(bloodBank.getAddress());
        }
    }
}