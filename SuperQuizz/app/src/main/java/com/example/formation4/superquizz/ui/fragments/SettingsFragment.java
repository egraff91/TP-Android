package com.example.formation4.superquizz.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.formation4.superquizz.R;

public class SettingsFragment extends Fragment {

    private CheckBox saveAnswerCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        final SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = mSettings.edit();
       CheckBox cb =  getActivity().findViewById(R.id.save_answer_check);
       cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswerCheckBox = (CheckBox)v;

                if(saveAnswerCheckBox.isChecked()){
                    editor.putBoolean("saveAnswer", true);
                }else{
                    editor.putBoolean("saveAnswer", false);
                }
                editor.apply();

            }
        });
       cb.setChecked(mSettings.getBoolean("saveAnswer", true));

    }

}
