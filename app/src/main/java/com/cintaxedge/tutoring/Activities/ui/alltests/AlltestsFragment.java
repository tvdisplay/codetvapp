package com.cintaxedge.tutoring.Activities.ui.alltests;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cintaxedge.tutoring.Activities.BottomNavigaionAcivity;

import com.cintaxedge.tutoring.Adapters.HomeAdapter;
import com.cintaxedge.tutoring.Adapters.MathSkillsAdapter;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.AllTest;
import com.cintaxedge.tutoring.Models.SectionAlltests;
import com.cintaxedge.tutoring.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class AlltestsFragment extends Fragment {

    private AlltestsViewModel alltestsViewModel;
    RecyclerView testRecycler;
    ProgressBar pbar;
    ProgressDialog dialog;
    private static final String TAG = "AlltestsFragment";

    ArrayList<SectionAlltests> sectioList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        if (getActivity()!=null && isAdded()){
            alltestsViewModel = new ViewModelProvider(this).get(AlltestsViewModel.class);

            pbar = root.findViewById(R.id.progbar);
            loadAllteste(root);
            alltestsViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    BottomNavigaionAcivity.setitle(s);
                }
            });
        }

        return root;
    }

    public void loadAllteste(View root){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();
        WebAPI.allTest(getContext(),(Jobject,status)->{

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });

            try {

                JSONArray mathskilljson = Jobject.getJSONArray("Math Skills");
                ArrayList<AllTest> mathSkillarr = new Gson().fromJson(mathskilljson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray rcjson = Jobject.getJSONArray("Reading Comprehension");
                ArrayList<AllTest> rcarr = new Gson().fromJson(rcjson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray mcjson = Jobject.getJSONArray("Mechanical Comprehension");
                ArrayList<AllTest> mcarr = new Gson().fromJson(mcjson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray sdjson = Jobject.getJSONArray("Simple Drawings");
                ArrayList<AllTest> sdarr = new Gson().fromJson(sdjson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray hfjson = Jobject.getJSONArray("Hidden Figures");
                ArrayList<AllTest> hfarr = new Gson().fromJson(hfjson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray aaijson = Jobject.getJSONArray("Army Aviation Information");
                ArrayList<AllTest> aaiarr = new Gson().fromJson(aaijson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray sajson = Jobject.getJSONArray("Spatial Apperception");
                ArrayList<AllTest> saarr = new Gson().fromJson(sajson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                JSONArray fptjson = Jobject.getJSONArray("Final Practice Test");
                ArrayList<AllTest> fptarr = new Gson().fromJson(fptjson.toString(), new TypeToken<List<AllTest>>(){}.getType());


                String sectionOne = "Math Skills";
                String sectiontwo = "Reading Comprehension";
                String sectionthree ="Mechanical Comprehension";
                String sectionfour = "Simple Drawings";
                String sectionfive = "Hidden Figures";
                String sectionsix = "Army Aviation Information";
                String sectionseven = "Spatial Apperception";
                String sectioneight = "Final Practice Test";


                sectioList.add(new SectionAlltests(sectionOne,mathSkillarr));
                sectioList.add(new SectionAlltests(sectiontwo,rcarr));
                sectioList.add(new SectionAlltests(sectionthree,mcarr));
                sectioList.add(new SectionAlltests(sectionfour,sdarr));
                sectioList.add(new SectionAlltests(sectionfive,hfarr));
                sectioList.add(new SectionAlltests(sectionsix,aaiarr));
                sectioList.add(new SectionAlltests(sectionseven,saarr));
                sectioList.add(new SectionAlltests(sectioneight,fptarr));



                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testRecycler = root.findViewById(R.id.mathRecycler);
                        MathSkillsAdapter mathSkillsAdapter = new MathSkillsAdapter(sectioList,getContext());
                        testRecycler.setAdapter(mathSkillsAdapter);
                     //   testRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

//                        testRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//                        testRecycler.setAdapter(new MathSkillsAdapter(merged,getActivity()));


                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }
}