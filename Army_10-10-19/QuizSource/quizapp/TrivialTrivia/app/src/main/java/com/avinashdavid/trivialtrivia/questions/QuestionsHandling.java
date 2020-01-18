package com.avinashdavid.trivialtrivia.questions;

import android.content.Context;
import android.util.Log;

import com.avinashdavid.trivialtrivia.UI.ActivityInstructions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by avinashdavid on 10/31/16.
 */

public class QuestionsHandling {
    private static final String LOG_TAG = QuestionsHandling.class.getSimpleName();
    private static QuestionsHandling sQuestionsHandling;
    private Context mContext;
    private String mMasterJSONString;
    private ArrayList<IndividualQuestion> mALLIndividualQuestions;
    private ArrayList<IndividualQuestion> mALLIndividualQuestions2;

    private ArrayList<IndividualQuestion> mCurrentSetOfQuestions;
    public static int QUIZ_NUMBER = -1;

    //keys in JSON file to be used during conversion to IndividualQuestion objects
    public static final String KEY_ALL_QUESTIONS = "questions";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_CHOICES = "choices";
    public static final String KEY_CORRECTANSWER = "correctAnswer";

    //indices of the elements in the ArrayList created by makeDisplayQuestionObject method
    public static final int INDEX_QUESTION = 0;
    public static final int INDEX_CHOICE_1 = 1;
    public static final int INDEX_CHOICE_2 = 2;
    public static final int INDEX_CHOICE_3 = 3;
    public static final int INDEX_CHOICE_4 = 4;

    //private constructor for singleton property. This will be a large object that only needs to be constructed once while it can be helped
   /* public QuestionsHandling(Context context) {
        this.mContext = context;
       // this.mMasterJSONString = loadJSONFromAsset();

       // this.mALLIndividualQuestions = makeOrReturnMasterQuestionList(this.mMasterJSONString);
       // All_Quiz_List_test.mALLIndividualQuestions();
       // this.mALLIndividualQuestions = All_Quiz_List_test.mALLIndividualQuestions;
    }*/

    public QuestionsHandling(Context context, ArrayList<IndividualQuestion> mALLIndividualQuestions) {
        this.mALLIndividualQuestions = mALLIndividualQuestions;
        //this.mALLIndividualQuestions2=mALLIndividualQuestions;

        this.mContext = context;

        //  System.out.println("kaifff2"+mALLIndividualQuestions2);


    }


    //public factory method for provided context's returning QuestionsHandling object
    public static QuestionsHandling getInstance(ArrayList<IndividualQuestion> kaif, Context context, int quizNumber) {
        if (sQuestionsHandling == null || QUIZ_NUMBER != quizNumber) {
            sQuestionsHandling = new QuestionsHandling(context, kaif);
        }
        return sQuestionsHandling;
    }

    //convert JSON file into String for use with JSONObject methods.
    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("questionsJSON.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //convert JSON string into ArrayList of IndividualQuestion objects or return previously constructed list
    private ArrayList<IndividualQuestion> makeOrReturnMasterQuestionList(String json) {
        if (mALLIndividualQuestions != null) {
            //CHANGE THIS WHEN TOTAL NUMBER OF AVAILABLE QUESTIONS CHANGES
            if (mALLIndividualQuestions.size() >= 195)
                return mALLIndividualQuestions;
        }
        try {
            JSONObject wholeObject = new JSONObject(json);
            JSONArray questionsArray = wholeObject.getJSONArray(KEY_ALL_QUESTIONS);
            mALLIndividualQuestions = new ArrayList<IndividualQuestion>(questionsArray.length());
            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject thisQuestion = questionsArray.getJSONObject(i);
                String category = thisQuestion.getString(KEY_CATEGORY);
                String question = thisQuestion.getString(KEY_QUESTION);
                JSONArray choiceArray = thisQuestion.getJSONArray(KEY_CHOICES);
                String[] choicesList = new String[choiceArray.length()];
                for (int l = 0; l < choiceArray.length(); l++) {
                    choicesList[l] = choiceArray.getString(l);
                }
                int correctAnswer = thisQuestion.getInt(KEY_CORRECTANSWER);
                IndividualQuestion individualQuestion = new IndividualQuestion(i, category, question, choicesList, correctAnswer);
                mALLIndividualQuestions.add(i, individualQuestion);
            }
        } catch (JSONException e) {
            Log.d(LOG_TAG, "JSONException at makeMasterQuestionList: " + e.toString());
        }
        return mALLIndividualQuestions;
    }

    //make a random set of questions from a provided list of all questions and the desired number of questions
    private ArrayList<IndividualQuestion> makeRandomQuestionSet(ArrayList<IndividualQuestion> allQuestions, int numberOfQuestions) {
        ArrayList<IndividualQuestion> returnList = new ArrayList<IndividualQuestion>(numberOfQuestions);
//        Collections.shuffle(allQuestions);
        for (int i = 0; i < numberOfQuestions; i++) {
            returnList.add(i, allQuestions.get(i));
        }
        return returnList;
    }

    //make and return a random set of questions, with a different set being returned for different quiz numbers
    public ArrayList<IndividualQuestion> getRandomQuestionSet(int size, int quizNumber) {
        if (quizNumber != QUIZ_NUMBER || mCurrentSetOfQuestions == null) {
            if (mALLIndividualQuestions == null) {

                //mALLIndividualQuestions = makeOrReturnMasterQuestionList(mMasterJSONString);
                //mALLIndividualQuestions=mALLIndividualQuestions2;

                mALLIndividualQuestions = ActivityInstructions.mALLIndividualQuestions;


                System.out.println("ifkaif" + mALLIndividualQuestions);

                mCurrentSetOfQuestions = makeRandomQuestionSet(mALLIndividualQuestions, size);
            } else {
                System.out.println("elsekaif" + mALLIndividualQuestions);
                mCurrentSetOfQuestions = makeRandomQuestionSet(mALLIndividualQuestions, size);
            }
        }
        return mCurrentSetOfQuestions;
    }

    //return the full set of questions (initialized at object creation)
    public ArrayList<IndividualQuestion> getFullQuestionSet() {
        return mALLIndividualQuestions;
    }

    //make an ArrayList of strings for an IndividualQuestion object that can be used to display in the quiz
    public static ArrayList<String> makeDisplayQuestionObject(IndividualQuestion thisQuestion) {
        ArrayList<String> displayList = new ArrayList<>(6);
        //  displayList.add(INDEX_CATEGORY, IndividualQuestion.categoryList.get(thisQuestion.category));
        displayList.add(INDEX_QUESTION, thisQuestion.question);
        displayList.add(INDEX_CHOICE_1, thisQuestion.choicesList[0]);
        displayList.add(INDEX_CHOICE_2, thisQuestion.choicesList[1]);
        displayList.add(INDEX_CHOICE_3, thisQuestion.choicesList[2]);
        displayList.add(INDEX_CHOICE_4, thisQuestion.choicesList[3]);

        //   System.out.println("Categorykaif  "+IndividualQuestion.categoryList.get(individualQuestion.category));
        //System.out.println("questionkaif  "+thisQuestion.question);
        System.out.println("Akaif  " + thisQuestion.choicesList[0]);
        System.out.println("A  " + thisQuestion.choicesList[1]);
        System.out.println("A  " + thisQuestion.choicesList[2]);
        System.out.println("A  " + thisQuestion.choicesList[3]);


        return displayList;
    }


}