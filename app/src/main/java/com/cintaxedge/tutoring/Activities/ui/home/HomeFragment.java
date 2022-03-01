package com.cintaxedge.tutoring.Activities.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cintaxedge.tutoring.Activities.BottomNavigaionAcivity;
import com.cintaxedge.tutoring.Adapters.HomeAdapter;
import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.CategoryItem;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView HomeRecycler;
    ProgressBar pbar;
    ProgressDialog dialog;
    private static final String TAG = "HomeFragment";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        if (getActivity()!=null && isAdded()){
            homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            pbar = root.findViewById(R.id.progbar);
            getcat(root);
            return root;
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        homeViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                BottomNavigaionAcivity.setitle(s);
            }
        });
    }

    public void getcat(View root) {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();
        WebAPI.getCategories(getActivity(), (itemsarray, status) -> {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
            if (status){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HomeRecycler = root.findViewById(R.id.homerecycler);
                        HomeRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        HomeRecycler.setAdapter(new HomeAdapter(itemsarray,getActivity()));

                    }
                });



            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Categories does not exist",Toast.LENGTH_LONG).show();
                    }
                });
               // Toast.makeText(getActivity(),"Categories does not exist",Toast.LENGTH_SHORT).show();
            }
        });
    }


}