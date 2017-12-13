package osoble.bloodhero.Utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.Models.User;


public class Utils {
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference childref;
    private Query ref;
    private User user;
    private BloodBank bloodBank;

    public Utils() {
    }

    public User getLoggedInUser() {
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");

        ref = childref.orderByKey().equalTo(firebaseUser.getUid());

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("---------------------", "---------------------");
                Log.i("Util OnDataChange", "Was called");

                user = dataSnapshot.child(firebaseUser.getUid()).getValue(User.class);
                Log.i("---------------------", "---------------------");
                Log.i("User name", user.getName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user;

    }


    public BloodBank getBloodBank(final String id){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("Blood Bank");

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("---------------------", "---------------------");
                Log.i("Util BB OnDataChange", "Was called");

                bloodBank = dataSnapshot.child(id).getValue(BloodBank.class);

                Log.i(bloodBank.getName(), bloodBank.getAddress());
                Log.i(bloodBank.getID(), id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return bloodBank;
    }

    public User getUser(final String id){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("---------------------", "---------------------");
                Log.i("Util User OnDataChange", "Was called");

                user = dataSnapshot.child(id).getValue(User.class);

                Log.i(user.getName(), user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return user;
    }
}
