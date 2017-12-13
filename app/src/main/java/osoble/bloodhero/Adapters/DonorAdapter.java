package osoble.bloodhero.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import osoble.bloodhero.Models.User;
import osoble.bloodhero.R;

/*
 * Created by abdulahiosoble on 11/18/17.
 */

public class DonorAdapter extends FirebaseRecyclerAdapter<User, DonorAdapter.DonorViewHolder> {

    private static final String TAG = DonorAdapter.class.getSimpleName();
    private Context mContext;
    private StorageReference mStorageReference;
    private StorageReference degreeRef;

    public DonorAdapter(Class<User> modelClass, int modelLayout, Class<DonorViewHolder>
            viewHolderClass, Query ref, Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        mStorageReference = FirebaseStorage.getInstance().getReference();
        Log.i("Donor Contructor", "Was Called");
    }

    public DonorAdapter(Class<User> modelClass, int modelLayout, Class<DonorViewHolder>
            viewHolderClass, DatabaseReference ref, Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        Log.i("Donor Contructor", "Was Called");
    }

    @Override
    protected void populateViewHolder(DonorViewHolder viewHolder, final User model, int position) {
        viewHolder.bind(model);
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, blood_type;

        public DonorViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.r_donor_name);
            email = itemView.findViewById(R.id.r_donor_email);
            blood_type = itemView.findViewById(R.id.r_donor_blood_type);
        }

        //Bind the Donor to the recyclerview
        public void bind(User user){
            name.setText(user.getName());
            email.setText(user.getEmail());
            blood_type.setText(user.getBloodType());
        }
    }
}
