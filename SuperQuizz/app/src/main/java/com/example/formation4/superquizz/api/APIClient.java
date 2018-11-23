package com.example.formation4.superquizz.api;

import android.util.Log;

import com.example.formation4.superquizz.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    private final OkHttpClient client = new OkHttpClient();

    private static APIClient sInstance = new APIClient();

    private final String URL = "http://192.168.10.38:3000/questions";
    private final String AUTHOR_IMG_URL = "https://en.shinden.pl/res/images/225x350/197768.jpg";
    private final String AUTHOR = "Etienne";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static APIClient getInstance(){
        return sInstance;
    }

    public void getQuestions(final APIResult<List<Question>> result) {

        Request request = new Request.Builder().url(URL).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Question> questions = new ArrayList<>();

                try{
                    String responseData = response.body().string();
                    JSONArray json = new JSONArray(responseData);

                    for(int i=0; i<json.length();i++){
                        ArrayList<String> propositions = new ArrayList<>();
                        JSONObject jsonQuestion = json.getJSONObject(i);
                        String title = jsonQuestion.getString("title");
                        String indexCorrectAnswer = jsonQuestion.getString("correct_answer");
                        String correctAnswer = jsonQuestion.getString("answer_"+indexCorrectAnswer);

                        Question newQuestion = new Question(title,correctAnswer);
                        /*
                        for(int j=1; j<5; j++){
                            propositions.add(jsonQuestion.getString("answer_"+j));
                        }*/

                        propositions.add(jsonQuestion.getString("answer_1"));
                        propositions.add(jsonQuestion.getString("answer_2"));
                        propositions.add(jsonQuestion.getString("answer_3"));
                        propositions.add(jsonQuestion.getString("answer_4"));


                        newQuestion.setPropositions(propositions);
                        questions.add(newQuestion);
                    }
                    //Log.d("DEBUG", responseData);
               } catch (JSONException e){
                    Log.e("DEBUG", e.toString());
                }

                // TODO : Lire les questions depuis la reponse et les ajouter à la liste

                result.OnSuccess(questions);
            }
        });

        //TODO : Faire un update
        //TODO : Faire un delete


    }

    public void updateQuestion(Question question, APIResult<Question> result){
        

    }

    public void createQuestion(Question question, APIResult<Question> result){

        JSONObject jsonQuestion = new JSONObject();
        ArrayList<String> propositions = question.getPropositions();
        int indexCorrectAnswer = 1;
        try{
            jsonQuestion.put("title", question.getIntitule());
           for(int i=0; i<propositions.size();i++){
               jsonQuestion.put("answer_"+(i+1), propositions.get(i));

               if(propositions.get(i).equals(question.getBonneReponse())){
                   indexCorrectAnswer = i+1;
               }
           }

           jsonQuestion.put("correct_answer", indexCorrectAnswer);
           jsonQuestion.put("author_img_url", AUTHOR_IMG_URL);
           jsonQuestion.put("author", AUTHOR);



        }catch (JSONException e){

        }

        RequestBody body = RequestBody.create(JSON, jsonQuestion.toString());
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

    }

    public interface APIResult<T> {
        void onFailure(IOException e);
        void OnSuccess(T object) throws IOException;
    }
}
