package com.example.formation4.superquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Question implements Parcelable {

    private String intitule;
    private ArrayList<String> propositions;
    private String bonneReponse;
    private TypeQuestion type;
    private String imageAuthorURL;
    private String author;
    private int id;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getImageAuthorURL() {
        return imageAuthorURL;
    }

    public void setImageAuthorURL(String imageAuthorURL) {
        this.imageAuthorURL = imageAuthorURL;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Question(String intitule, String bonneReponse) {
        this.intitule = intitule;
        this.bonneReponse = bonneReponse;
        this.propositions = new ArrayList<String>();
        this.type = TypeQuestion.SIMPLE;
    }

    protected Question(Parcel in) {
        intitule = in.readString();
        propositions = in.createStringArrayList();
        bonneReponse = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public ArrayList<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public void setType(String type) {
        this.type = TypeQuestion.valueOf(type);
    }

    public TypeQuestion getType() {
        return this.type;
    }

    public boolean verifierReponse(String reponse) {

        return this.bonneReponse.equalsIgnoreCase(reponse);
    }

    public void addProposition(String proposition) {
        this.propositions.add(proposition);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(intitule);
        dest.writeStringList(propositions);
        dest.writeString(bonneReponse);
    }
}