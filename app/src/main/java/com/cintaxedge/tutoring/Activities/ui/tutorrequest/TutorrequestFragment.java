package com.cintaxedge.tutoring.Activities.ui.tutorrequest;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cintaxedge.tutoring.Activities.BottomNavigaionAcivity;
import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.R;

public class TutorrequestFragment extends Fragment {

    private TutorrequestViewModel tutorrequestViewModel;
    EditText etfname, etlname, etemail, etphone, etmessage;
    ProgressBar pbar;
    Spinner spinner;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BottomNavigaionAcivity.setitle("Tutor Request");
        tutorrequestViewModel = new ViewModelProvider(this).get(TutorrequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tutorrequest, container, false);

        String statesarr[] = {"Select","Alabama",
                "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
                "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
                "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
                "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
                "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin",
                "Wyoming", "District of Columbia", "Puerto Rico", "Guam", "American Samoa", "U.S. Virgin Islands", "Northern Mariana Islands"};

          spinner = (Spinner) root.findViewById(R.id.spinnerstate);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, statesarr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

 
       
 
        etfname = root.findViewById(R.id.etfname);
        etlname = root.findViewById(R.id.etlname);
        etemail = root.findViewById(R.id.etemail);
        etphone = root.findViewById(R.id.etphone);
        etmessage = root.findViewById(R.id.etmessage);
        pbar = root.findViewById(R.id.pbar);
 

        Button submit = root.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.VISIBLE);
                validate();
               
            }
        });

        tutorrequestViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                BottomNavigaionAcivity.setitle(s);
            }
        });


        return root;
    }

    public void validate() {
        String firstname = etfname.getText().toString();
        String lastname = etlname.getText().toString();
        String useremail = etemail.getText().toString();
        String mobile = etphone.getText().toString();
        String message = etmessage.getText().toString();
        String state = spinner.getSelectedItem().toString();

        if (firstname.isEmpty()) {
            etfname.setError(getString(R.string.input_error_fname));
            return;
        }
        if (lastname.isEmpty()) {
            etlname.setError(getString(R.string.input_error_lname));
            etlname.requestFocus();
            return;

        }

        if (useremail.isEmpty()) {
            etemail.setError(getString(R.string.input_error_email));
            etemail.requestFocus();
            return;
        }

        if (mobile.isEmpty()) {
            etphone.setError(getString(R.string.input_error_el));
            etphone.requestFocus();
            return;

        }

        if (message.isEmpty()) {
            etmessage.setError(getString(R.string.input_error_el));
            etmessage.requestFocus();
            return;
        }

        if (state.equals("Select")) {
            Toast.makeText(getActivity(), "Select a state", Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        WebAPI.sendtutorRequest(getContext(),firstname,lastname,useremail,mobile,state,message,(status)->{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pbar.setVisibility(View.GONE);
                }
            });
            if (status){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        etfname.setText("");
                        etlname.setText("");
                        etemail.setText("");
                        etphone.setText("");
                        etmessage.setText("");
                        Toast.makeText(getActivity(),"Request submitted successfully",Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbar.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"Request Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }
}
