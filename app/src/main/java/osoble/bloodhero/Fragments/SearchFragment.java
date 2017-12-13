package osoble.bloodhero.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import osoble.bloodhero.R;


public class SearchFragment extends Fragment implements View.OnClickListener {
    public static String TAG = SearchFragment.class.getSimpleName();

    private Button mSearch;
    private Intent intent;
    private String[] bloodTypes, region;
    private ArrayAdapter<String> adapterB, adapterC;
    private Spinner bloodTypeSpinner, regionSpinner;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar searchToolBar;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search, container, false);
        configureToolBar(view);

        bloodTypeSpinner = view.findViewById(R.id.spinner1);
        regionSpinner = view.findViewById(R.id.spinner2);

        mSearch = view.findViewById(R.id.search_now);

        setUpSpinner();

        mSearch.setOnClickListener(this);
        return view;
    }

    public void setUpSpinner(){
        bloodTypes = new String[] {"Select Blood Type", "O-", "O+", "A+",
                "A-", "B+", "B-", "AB+" , "AB-"};
        adapterB = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                bloodTypes);
        bloodTypeSpinner.setAdapter(adapterB);

        region = new String[] {"Select Region", "Sharjah", "Dubai", "Abu Dhabi",
                "Ras Al Khaimah", "Umm Al Quwain", "Fujairah", "Ajman"};
        adapterC = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                region);
        regionSpinner.setAdapter(adapterC);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_now:
                changeFragment(new DonorSearchFragment());
                break;
            default:
                getActivity().onBackPressed();
                break;
        }
    }

    public void configureToolBar(View view){
        searchToolBar = view.findViewById(R.id.toolbar);
        searchToolBar.setTitle("Search Donors");
        searchToolBar.setTitleTextColor(Color.WHITE);
        searchToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        searchToolBar.setNavigationOnClickListener(this);
    }

    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.right_out, R.anim.right_enter, R.anim.right_out)
                .replace(R.id.home_activity_layout, f)
                .addToBackStack("Search");

        fragmentTransaction.commit();
    }
}
