package com.example.formation4.superquizz;

import java.util.ArrayList;

public class Question {

    private String intitule;
    private ArrayList<String> propositions;
    private String bonneReponse;
    private TypeQuestion type;

    public Question(String intitule, String bonneReponse) {
        this.intitule = intitule;
        this.bonneReponse = bonneReponse;
        this.propositions = new ArrayList<String>();
        this.type = TypeQuestion.SIMPLE;
    }

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

}