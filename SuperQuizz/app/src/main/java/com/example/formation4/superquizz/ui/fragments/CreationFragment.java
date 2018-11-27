package com.example.formation4.superquizz.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.formation4.superquizz.NetworkChangeReceiver;
import com.example.formation4.superquizz.R;
import com.example.formation4.superquizz.api.APIClient;
import com.example.formation4.superquizz.model.Question;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public OnCreationFragmentListener listener;

    private Boolean radioSelect = false;
    private RadioButton lastRadio;
    FloatingActionButton buttonValidate;

    public CreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreationFragment newInstance(String param1, String param2) {
        CreationFragment fragment = new CreationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        registerReceiver();
    }

    private void registerReceiver(){
        try{
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkChangeReceiver.NETWORK_CHANGE_ACTION);
            getActivity().registerReceiver(internalNetworkChangeReceiver, intentFilter);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onPause(){
        try {
            getActivity().unregisterReceiver(internalNetworkChangeReceiver);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_creation, container, false);

        final EditText inputQuestion = rootView.findViewById(R.id.input_question);
        final EditText inputAnswer1 = rootView.findViewById(R.id.input_answer1);
        final EditText inputAnswer2 = rootView.findViewById(R.id.input_answer2);
        final EditText inputAnswer3 = rootView.findViewById(R.id.input_answer3);
        final EditText inputAnswer4 = rootView.findViewById(R.id.input_answer4);


        rootView.findViewById(R.id.radio_answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioSelection(v);
            }
        });

        rootView.findViewById(R.id.radio_answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelection(v);

            }
        });

        rootView.findViewById(R.id.radio_answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelection(v);

            }
        });

        rootView.findViewById(R.id.radio_answer4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelection(v);

            }
        });

        buttonValidate = rootView.findViewById(R.id.button_validate);
        buttonValidate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(!radioSelect){
                   Snackbar.make(v, "Sélectionnez une réponse", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               }else{
                   Log.d("DEBUG", "Valider");
                   Log.d("DEBUG", "Question : "+inputQuestion.getText());
                   String correctAnswer;

                   switch(lastRadio.getId()){
                       case R.id.radio_answer1:
                           correctAnswer = inputAnswer1.getText().toString();
                           break;

                       case R.id.radio_answer2:
                           correctAnswer = inputAnswer2.getText().toString();
                            break;
                       case R.id.radio_answer3:
                           correctAnswer = inputAnswer3.getText().toString();
                           break;
                       case R.id.radio_answer4:
                           correctAnswer = inputAnswer4.getText().toString();
                           break;
                       default:
                           correctAnswer = inputAnswer1.getText().toString();
                           break;

                    }
                   Log.d("DEBUG",lastRadio.getResources().getResourceName(lastRadio.getId()));
                   final Question question = new Question(inputQuestion.getText().toString(), correctAnswer);

                   question.addProposition(inputAnswer1.getText().toString());
                   question.addProposition(inputAnswer2.getText().toString());
                   question.addProposition(inputAnswer3.getText().toString());
                   question.addProposition(inputAnswer4.getText().toString());

                    APIClient.getInstance().createQuestion(question, new APIClient.APIResult<Question>() {
                        @Override
                        public void onFailure(IOException e) {
                            Log.e("DEBUG", "erreur ajout question");
                        }

                        @Override
                        public void OnSuccess(Question object) throws IOException {
                            listener.createQuestion(question);
                            Log.d("DEBUG", "question créée");

                        }
                    });
               }
            }
        });
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Question q);
    }

    public interface OnCreationFragmentListener {
        void createQuestion(Question q);
    }

    public void radioSelection(View v){
        if(!radioSelect){
            radioSelect = true;
            lastRadio = (RadioButton)v;
        }else{
            lastRadio.setChecked(false);
            lastRadio = (RadioButton)v;
        }
    }

    InternalNetworkChangeReceiver internalNetworkChangeReceiver = new InternalNetworkChangeReceiver();
    String actualStatus="";

    class  InternalNetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

                String status = intent.getStringExtra("status");
                if(!status.equals(actualStatus)){
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    actualStatus = status;
                }

        }
    }
}
