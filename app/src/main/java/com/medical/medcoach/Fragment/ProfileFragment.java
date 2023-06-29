package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.R;

public class ProfileFragment extends Fragment {

    DatabaseReference databaseReference;
    TextView UserName;
    Button logoutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UserName=view.findViewById(R.id.profile_name);
        logoutBtn=view.findViewById(R.id.logout);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child("+919999996262").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String Name = String.valueOf(dataSnapshot.child("Full Name").getValue());
                    UserName.setText(Name);

                } else {
                    Toast.makeText(getActivity(), "User Not Exist", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }

        });

        logoutBtn.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
            if(getActivity()!=null)
                getActivity().finish();
        });

        return view;
    }



}