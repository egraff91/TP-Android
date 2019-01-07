package com.example.formation4.superquizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.formation4.superquizz.model.Question;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class QuestionDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "qcm";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_QUESTIONS = "questions";

    private static final String KEY_QUESTION_ID = "id";
    private static final String KEY_QUESTION_SERVER_ID = "server_id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_RESPONSE_1 = "response_1";
    private static final String KEY_RESPONSE_2 = "response_2";
    private static final String KEY_RESPONSE_3 = "response_3";
    private static final String KEY_RESPONSE_4 = "response_4";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_USER_RESPONSE = "user_response";
    private static final String KEY_AUTHOR_IMAGE_URL = "author_image_url";
    private static final String KEY_AUTHOR = "author";

    private static QuestionDatabaseHelper sInstance;

    public static synchronized QuestionDatabaseHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new QuestionDatabaseHelper(context.getApplicationContext());
       }
        return sInstance;
    }

    private QuestionDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS +
                " (" +
                    KEY_QUESTION_ID + " INTEGER PRIMARY KEY, " +
                    KEY_QUESTION_SERVER_ID + " INTEGER, " +
                    KEY_QUESTION + " VARCHAR(200), " +
                    KEY_RESPONSE_1 + " VARCHAR(100), "+
                    KEY_RESPONSE_2 + " VARCHAR(100), "+
                    KEY_RESPONSE_3 + " VARCHAR(100), "+
                    KEY_RESPONSE_4 + " VARCHAR(100), "+
                    KEY_RESPONSE + " VARCHAR(100), "+
                    KEY_USER_RESPONSE + " VARCHAR(100), "+
                    KEY_AUTHOR_IMAGE_URL + " VARCHAR(100), "+
                    KEY_AUTHOR+ " VARCHAR(100)"+
                ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
            onCreate(db);
        }

    }

    public void addQuestion(Question q){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<String> propositions = q.getPropositions();

        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION, q.getIntitule());

            values.put(KEY_RESPONSE_1, propositions.get(0));
            values.put(KEY_RESPONSE_2, propositions.get(1));
            values.put(KEY_RESPONSE_3, propositions.get(2));
            values.put(KEY_RESPONSE_4, propositions.get(3));

            values.put(KEY_RESPONSE, q.getBonneReponse());
            values.put(KEY_QUESTION_SERVER_ID, q.getId());
            values.put(KEY_USER_RESPONSE, (String) null);

            db.insertOrThrow(TABLE_QUESTIONS, null, values);
            db.setTransactionSuccessful();
        } catch(Exception e){
            Log.d("DEBUG", e.toString());
        } finally {
            db.endTransaction();
        }
    }

    public int updateUserAnswer(Question q, String userResponse){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_USER_RESPONSE, userResponse);

        return db.update(TABLE_QUESTIONS, values, KEY_QUESTION + "= ?", new String[]{q.getIntitule()});
    }

    public int updateQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> propositions = question.getPropositions();

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, question.getIntitule());

        values.put(KEY_RESPONSE_1, propositions.get(0));
        values.put(KEY_RESPONSE_2, propositions.get(1));
        values.put(KEY_RESPONSE_3, propositions.get(2));
        values.put(KEY_RESPONSE_4, propositions.get(3));

        values.put(KEY_RESPONSE, question.getBonneReponse());
        values.put(KEY_QUESTION_SERVER_ID, question.getId());

        return db.update(TABLE_QUESTIONS, values, KEY_QUESTION_SERVER_ID + "= ?", new String[]{""+question.getId()});

    }

    public String getUserAnswer(Question question){
        SQLiteDatabase db = this.getReadableDatabase();

       String USER_ANSWER_QUERY = String.format("SELECT %s FROM %s WHERE %s = %s", KEY_USER_RESPONSE, TABLE_QUESTIONS, KEY_QUESTION_SERVER_ID, question.getId());

        Cursor cursor = db.rawQuery(USER_ANSWER_QUERY, null);
        String result = "";
        try {
            if(cursor.moveToFirst()){
                result = cursor.getString(cursor.getColumnIndex(KEY_USER_RESPONSE));
            }

        }catch (Exception e){

        }
        return result;
    }

    public List<Question> getAllQuestions(){

        List<Question> questions = new ArrayList<>();
        ArrayList<String> propositions;


        String QUESTIONS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_QUESTIONS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUESTIONS_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    Question question = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTION)), cursor.getString(cursor.getColumnIndex(KEY_RESPONSE)));
                    propositions = new ArrayList<>();
                    propositions.add(cursor.getString(cursor.getColumnIndex(KEY_RESPONSE_1)));
                    propositions.add(cursor.getString(cursor.getColumnIndex(KEY_RESPONSE_2)));
                    propositions.add(cursor.getString(cursor.getColumnIndex(KEY_RESPONSE_3)));
                    propositions.add(cursor.getString(cursor.getColumnIndex(KEY_RESPONSE_4)));
                    question.setPropositions(propositions);

                    question.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_SERVER_ID))));
                    question.setImageAuthorURL(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR_IMAGE_URL)));
                    question.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
                    questions.add(question);
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            Log.d("DEBUG", "Error while trying to get questions from database");
            Sentry.capture(e);
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return questions;
    }

    public void deleteAllQuestions(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(TABLE_QUESTIONS, null, null);
        } catch (Exception e){
            Log.d("DEBUG", "Error while trying to delete all questions");
        }
    }

    public void deleteQuestion(Question question){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(TABLE_QUESTIONS, KEY_QUESTION_SERVER_ID+"= ?",new String[]{""+question.getId()});

        } catch (Exception e){

        }

    }

    public void synchroniseDatabaseQuestions(List<Question> serverQuestions) {

        List<Question> databaseQuestions = getAllQuestions();


        // Here we will choose if we need to add or to update the question return by the server
        for (Question serverQuestion: serverQuestions) {
            boolean found = false;
            for (Question dataBaseQuestion: databaseQuestions) {
                if (serverQuestion.getId() == dataBaseQuestion.getId()){
                    found =true;
                    break;
                }




            }
            if (found) {
                updateQuestion(serverQuestion);
            } else {
                addQuestion(serverQuestion);
            }

        }

        // Now we want to delete the question if thy are not on the server anymore
        for (Question dataBaseQuestion: databaseQuestions) {
            boolean found = false;
            for (Question serverQuestion: serverQuestions) {
                if (serverQuestion.getId() == dataBaseQuestion.getId()){
                    found =true;
                    break;
                }
            }

            if (!found) {
                Log.d("DEBUG", "Question id "+dataBaseQuestion.getId());
                deleteQuestion(dataBaseQuestion);
            }
        }
    }
}


