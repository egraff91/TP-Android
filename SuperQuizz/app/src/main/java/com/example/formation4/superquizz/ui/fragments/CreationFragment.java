package com.example.formation4.superquizz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.formation4.superquizz.R;
import com.example.formation4.superquizz.model.Question;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_creation, container, false);

        final EditText textQuestion = rootView.findViewById(R.id.input_question);
        final EditText reponse1 = rootView.findViewById(R.id.input_response1);
        final EditText reponse2 = rootView.findViewById(R.id.input_response2);
        final EditText reponse3 = rootView.findViewById(R.id.input_response3);
        final EditText reponse4 = rootView.findViewById(R.id.input_response4);



        rootView.findViewById(R.id.radio_response1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioSelection(v);
            }
        });

        rootView.findViewById(R.id.radio_response2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelection(v);

            }
        });

        rootView.findViewById(R.id.radio_response3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelection(v);

            }
        });

        rootView.findViewById(R.id.radio_response4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelection(v);

            }
        });

        rootView.findViewById(R.id.validate_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: Créer la question en BDD
                if((reponse1.getText()!= null)){
                    if(reponse2.getText() != null) {

                    } if(reponse3.getText() != null ){

                    } if(reponse4.getText()!= null){

                    }
                }
                if(!radioSelect){
                   Snackbar.make(v, "Sélectionnez une réponse", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               }else{
                   Log.d("DEBUG", "Valider");
                   Log.d("DEBUG", "Question : "+textQuestion.getText());
                   //String bonneReponse = lastRadio;
                    /*switch(lastRadio.getResources().getResourceName(lastRadio.getId())){


                    }*/
                   //Log.d("DEBUG",lastRadio.getResources().getResourceName(lastRadio.getId()) );
                   Question question = new Question(textQuestion.getText().toString(), reponse1.getText().toString());

                   question.addProposition(reponse1.getText().toString());
                   question.addProposition(reponse2.getText().toString());
                   question.addProposition(reponse3.getText().toString());
                   question.addProposition(reponse4.getText().toString());
                    listener.createQuestion(question);
               }
            }
        });
        return rootView;
        //return inflater.inflate(R.layout.fragment_creation, container, false);
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
}
