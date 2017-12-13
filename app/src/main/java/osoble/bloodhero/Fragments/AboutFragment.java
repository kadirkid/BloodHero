package osoble.bloodhero.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import osoble.bloodhero.R;


public class AboutFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.about, container, false);

        return root;
    }


}
