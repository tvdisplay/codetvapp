package com.cintaxedge.tutoring.Activities.ui.results;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cintaxedge.tutoring.Activities.BottomNavigaionAcivity;
import com.cintaxedge.tutoring.Adapters.ResultAdapter;
import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.PredictionResult;
import com.cintaxedge.tutoring.Models.ResultAll;
import com.cintaxedge.tutoring.R;


public class ResultsFragment extends Fragment {

    private ResultsViewModel resultsViewModel;
    RecyclerView resultRecycler, predrecycler;
    ProgressBar pbar;
    ProgressDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        if (getActivity() != null && isAdded()) {
            resultsViewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
            pbar = root.findViewById(R.id.progbar);
            resultsViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    BottomNavigaionAcivity.setitle(s);
                }
            });

            getResults(root);


        }

        return root;
    }


    public void getResults(View root) {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();
        WebAPI.getAllResults(getActivity(), (status, itemsarray) -> {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });

            if (status) {
                WebAPI.getPredictionResults(getActivity(), (status1, result) -> {
                    if (status1) {

                        recycler( root,itemsarray, result);

//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                resultRecycler = root.findViewById(R.id.resultrecycler);
//                                ResultAdapter adapter = new ResultAdapter(itemsarray.getResult(), result, getActivity());
//                                resultRecycler.setAdapter(adapter);
//                                resultRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                            }
//                        });


                    }

                });


            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Results does not exist", Toast.LENGTH_LONG).show();
                    }
                });
                // Toast.makeText(getActivity(),"Categories does not exist",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void recycler(View root, ResultAll itemsarray, PredictionResult result){
        if (getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    resultRecycler = root.findViewById(R.id.resultrecycler);
                    ResultAdapter adapter = new ResultAdapter(itemsarray.getResult(), result, getActivity());
                    resultRecycler.setAdapter(adapter);
                    resultRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
            });
        }

    }

}