package osoble.bloodhero.LearnFragmentFiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import osoble.bloodhero.R;


public class FactsFragment extends Fragment {

    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.facts, container, false);

        return root;

    }

}
