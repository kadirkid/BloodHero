package osoble.bloodhero.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import osoble.bloodhero.Models.Appointment;
import osoble.bloodhero.R;


public class AppointmentAdapter extends FirebaseRecyclerAdapter<Appointment, AppointmentAdapter
        .AppointmentViewHolder> {
    private Context mContext;

    public AppointmentAdapter(Class<Appointment> modelClass, int modelLayout,
                              Class<AppointmentViewHolder> viewHolderClass, Query ref,
                              Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        Log.i("Appointment Contructor", "Was Called");
    }

    @Override
    protected void populateViewHolder(AppointmentViewHolder viewHolder, Appointment model, int position) {
        viewHolder.bind(model);
        Log.i("D populateViewHolder", "Was Called");
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView time;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(Appointment appointment) {
            time.setText("On " + appointment.getDate() + "On " +appointment.getTime() + ", "
                    + appointment.getBloodbank_name());
            Log.i("On " + appointment.getDate(), "On " +appointment.getTime() + ", "
                    + appointment.getBloodbank_name());
        }
    }
}
