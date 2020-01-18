package com.avinashdavid.trivialtrivia.UI;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avinashdavid.trivialtrivia.R;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;
import com.avinashdavid.trivialtrivia.questions.QuestionsHandling;
import com.avinashdavid.trivialtrivia.scoring.QuestionScorer;
import com.avinashdavid.trivialtrivia.scoring.QuizScorer;
import com.avinashdavid.trivialtrivia.services.InsertRecordsService;

import java.util.ArrayList;

public class ActivityQuiz extends AppCompatActivity {
    private int QUIZ_NUMBER;
    private static final String KEY_QUIZ_NUMBER = "quizNumber";
    private static final String KEY_QUIZ_SIZE = "quizSize";
    private static final String KEY_QUESTION_NUMBER = "questionNumber";
    private static final String KEY_CURRENT_SECONDS = "currentSeconds";

    private ListView mListView;
    private CardView mCardView;
    private TextView mNumberTextView;
    private TextView mCategoryTextView;
    private static ArrayList<IndividualQuestion> sIndividualQuestions;
    private ArrayList<String> mCurrentDisplayQuestion;
    private int mQuestionNumber;
    private Button mNextQuestionButton;
    private Button mPreviousQuestionButton;
    private int mQuizSize;
    private int currentVersionCode;

    private FrameLayout mFrameLayout;

    private ProgressBar mProgressBar;
    private TextView mSecondsTextview;

    private int maxTime;

    private CountDownTimer mCountDownTimer;
    public int mCurrentSeconds;

    private static QuizScorer sQuizScorer;

    Button choice1, choice2, choice3, choice4;
    private boolean hasVibrator;
    private Vibrator mVibrator;
    private static final int vibrationMillis = 50;
    Button next;
    private int chosenAnswer;
    private int selectedId = 0;

    private ArrayList<IndividualQuestion> kaifList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        currentVersionCode = android.os.Build.VERSION.SDK_INT;
        setContentView(R.layout.activity_quiz);
        setupWindowAnimations();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        hasVibrator = ((Vibrator) getSystemService(VIBRATOR_SERVICE)).hasVibrator();
        if (hasVibrator) {
            mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }

        maxTime = 120*399939;

        if (savedInstanceState != null) {
            mQuizSize = savedInstanceState.getInt(KEY_QUIZ_SIZE);
            mQuestionNumber = savedInstanceState.getInt(KEY_QUESTION_NUMBER);
            QUIZ_NUMBER = savedInstanceState.getInt(KEY_QUIZ_NUMBER);
            mCurrentSeconds = savedInstanceState.getInt(KEY_CURRENT_SECONDS);
        } else {
            //mQuizSize = 3;
            mQuizSize =ActivityInstructions.total_question;
            Cursor c = getContentResolver().query(QuizDBContract.QuizEntry.CONTENT_URI, new String[]{QuizDBContract.QuizEntry._ID}, null, null, null);
            if (c.moveToFirst()) {
                QUIZ_NUMBER = c.getCount() + 1;
            } else {
                QUIZ_NUMBER = QuizScorer.sQuizNumber + 1;
            }
            c.close();
            mQuestionNumber = 0;
            mCurrentSeconds = maxTime;
        }


        sQuizScorer = QuizScorer.getInstance(this, mQuizSize, QUIZ_NUMBER);
        sIndividualQuestions = QuestionsHandling.getInstance(kaifList,this.getApplicationContext(), QUIZ_NUMBER).getRandomQuestionSet(mQuizSize, QUIZ_NUMBER);
        mCurrentDisplayQuestion = QuestionsHandling.makeDisplayQuestionObject(sIndividualQuestions.get(mQuestionNumber));
        // //mCurrentDisplayQuestion= ActivityWelcomePage.displayList;




//        mCardView = (CardView) findViewById(R.id.card_view);
//        mListView = (ListView)rootview.findViewById(R.id.choices_listview);


        mNumberTextView = (TextView) findViewById(R.id.questionNumber_textview);
        //mCategoryTextView = (TextView) findViewById(R.id.category_textview);
        mSecondsTextview = (TextView) findViewById(R.id.seconds_display);
        mSecondsTextview.setText(Integer.toString(mCurrentSeconds));
        mFrameLayout = (FrameLayout) findViewById(R.id.card_framelayout);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(maxTime);
        mProgressBar.setProgress(mCurrentSeconds);
        mCountDownTimer = new CountDownTimer((mCurrentSeconds + 2) * 1000, 1000) {
            int mTicknumber = 0;

            @Override
            public void onTick(long l) {
                //Toast.makeText(ActivityQuiz.this, "ONTICK", Toast.LENGTH_SHORT).show();
                mSecondsTextview.setText(Integer.toString(mCurrentSeconds));
                mProgressBar.setProgress(mCurrentSeconds);
//                Log.d("timer", "ontick" + Integer.toString(mTicknumber++) + ": " + Integer.toString(mCurrentSeconds));
                if (mCurrentSeconds <= 0) {
                    mSecondsTextview.setTextColor(getResources().getColor(R.color.wrongAnswerRed));
                    if (mCurrentSeconds < 0) {
                        mFrameLayout.setClickable(false);
                    }
                }
                mCurrentSeconds -= 1;

                if(mCurrentSeconds==-1)
                    endQuiz();
            }

            @Override
            public void onFinish() {
                mSecondsTextview.setTextColor(getResources().getColor(R.color.darker_gray));
                mProgressBar.setProgress(0);
                IndividualQuestion currentQuestion = sIndividualQuestions.get(mQuestionNumber);
                sQuizScorer.addQuestionScorer(currentQuestion.questionNumber, currentQuestion.category, currentQuestion.correctAnswer, QuestionScorer.NO_ANSWER);
                goToNextQuestion();
                mTicknumber = 0;
            }
        };

//        mNextQuestionButton = (Button)findViewById(R.id.buttonNextQuestion);
//        mPreviousQuestionButton = (Button)findViewById(R.id.buttonPreviousQuestion);

        setAndUpdateChoiceTextViews(mQuestionNumber);


//        mNextQuestionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToNextQuestion();
//            }
//        });
//        mPreviousQuestionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToPreviousQuestion();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_QUESTION_NUMBER, mQuestionNumber);
        outState.putInt(KEY_QUIZ_NUMBER, QUIZ_NUMBER);
        outState.putInt(KEY_QUIZ_SIZE, mQuizSize);
        outState.putInt(KEY_CURRENT_SECONDS, mCurrentSeconds);
        super.onSaveInstanceState(outState);
    }

    @TargetApi(21)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setEnterTransition(slide);
    }

    //updates the mCurrentDisplayQuestion object and text of the respective textviews
    private void setAndUpdateChoiceTextViews(int questionNumber) {
        mCurrentDisplayQuestion = QuestionsHandling.makeDisplayQuestionObject(sIndividualQuestions.get(questionNumber));
        // mCurrentDisplayQuestion= ActivityWelcomePage.displayList;

        for(int i=0;i<mCurrentDisplayQuestion.size();i++)
        {
            System.out.println("sayed---"+mCurrentDisplayQuestion.get(i));
        }



//        mQuestionView.setText(mCurrentDisplayQuestion.get(QuestionsHandling.INDEX_QUESTION));
//        mChoice1TextView.setText(mCurrentDisplayQuestion.get(QuestionsHandling.INDEX_CHOICE_1));
//        mChoice2TextView.setText(mCurrentDisplayQuestion.get(QuestionsHandling.INDEX_CHOICE_2));
//        mChoice3TextView.setText(mCurrentDisplayQuestion.get(QuestionsHandling.INDEX_CHOICE_3));
//        mChoice4TextView.setText(mCurrentDisplayQuestion.get(QuestionsHandling.INDEX_CHOICE_4));
        if (currentVersionCode >= 13) {
            updateFragmentAnimated();
        } else {
            updateFragmentTraditional();
        }
        mNumberTextView.setText("Ques: "+Integer.toString(mQuestionNumber + 1));
     //   mCategoryTextView.setText(mCurrentDisplayQuestion.get(QuestionsHandling.INDEX_QUESTION));

        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer((mCurrentSeconds + 2) * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    mProgressBar.setProgress(mCurrentSeconds);
                    mSecondsTextview.setText(Integer.toString(mCurrentSeconds));
                    mCurrentSeconds -= 1;
                    if(mCurrentSeconds<1)
                        endQuiz();
                }

                @Override
                public void onFinish() {
                    mProgressBar.setProgress(0);
                    IndividualQuestion currentQuestion = sIndividualQuestions.get(mQuestionNumber);
                    sQuizScorer.addQuestionScorer(currentQuestion.questionNumber, currentQuestion.category, currentQuestion.correctAnswer, QuestionScorer.NO_ANSWER);
                    goToNextQuestion();
//                    if (mQuestionNumber < mQuizSize) {
//                        this.start();
//                    }
                }
            };
        } else {
            mCountDownTimer.cancel();
        }

        mCountDownTimer.start();
    }

    //updates question number by +=1
    public void goToNextQuestion() {
        //doVibration(hasVibrator);
        if (mQuestionNumber < mQuizSize - 1) {
            mQuestionNumber += 1;
            setAndUpdateChoiceTextViews(mQuestionNumber);
            mSecondsTextview.setTextColor(getResources().getColor(R.color.darker_gray));
//            mCurrentSeconds = maxTime;
            mCurrentSeconds=mCurrentSeconds-1;
            // endQuiz();

            mCountDownTimer.cancel();
            mCountDownTimer.start();
        } else {
            endQuiz();
        }
    }

    //updates question number by -=1
    /*private void goToPreviousQuestion() {
        if (mQuestionNumber > 0) {
            mQuestionNumber -= 1;
            setAndUpdateChoiceTextViews(mQuestionNumber);
        }
    }*/

    private void updateFragmentTraditional() {
        android.support.v4.app.Fragment fragmentQuestion = FragmentQuestion.getInstance(mCurrentDisplayQuestion);
        getSupportFragmentManager().beginTransaction().replace(R.id.card_framelayout, fragmentQuestion).commit();
    }

    @TargetApi(13)
    private void updateFragmentAnimated() {
        android.app.Fragment fragmentQuestion = FragmentQuestionHoneycomb.getInstance(mCurrentDisplayQuestion);
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (mQuestionNumber > 0) {
            fragmentTransaction

                    // Replace the default fragment animations with animator resources
                    // representing rotations when switching to the back of the card, as
                    // well as animator resources representing rotations when flipping
                    // back to the front (e.g. when the system Back button is pressed).
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out);
        }
        fragmentTransaction
                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.card_framelayout, fragmentQuestion)

//                // Add this transaction to the back stack, allowing users to press
//                // Back to get to the front of the card.
//                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }


    public void getRadioButtonValue(View v) {
        RadioGroup radioGroup = null;
        View inflatedView = getLayoutInflater().inflate(R.layout.fragment_question, null);

        radioGroup = (RadioGroup) inflatedView.findViewById(R.id.radioGroup);

        selectedId = radioGroup.getCheckedRadioButtonId();


        chosenAnswer = -1;
        switch (v.getId()) {
            case R.id.choice1:
                //   v.setBackgroundColor(Color.BLUE);
                chosenAnswer = 1;
                break;
            case R.id.choice2:
                // v.setBackgroundColor(Color.RED);
                chosenAnswer = 2;
                break;
            case R.id.choice3:
                //  v.setBackgroundColor(Color.GREEN);
                chosenAnswer = 3;
                break;
            case R.id.choice4:
                //  v.setBackgroundColor(Color.MAGENTA);
                chosenAnswer = 4;
                break;
            default:
                chosenAnswer = -1;


        }

    }

    public void skip(View v)
    {
        skipQuestion();

    }


    public void next(View v) {


        //System.out.println("kaif_radio_id"+selectedId);
        //System.out.println("2--"+sQuizScorer.getQuestionScorers().size());
        //System.out.println("3--"+mQuizSize);


       /* if (selectedId == 0 && sQuizScorer.getQuestionScorers().size()<= mQuizSize && sQuizScorer.getQuestionScorers().size() != mQuizSize )
            Toast.makeText(this, "Please Select an Option", Toast.LENGTH_SHORT).show();
        else {*/


       if(chosenAnswer<=0) {
           System.out.println("skip_called");
           skipQuestion();
       }
       else {
           System.out.println("add_called");
           addQuestionScorer();
       }

       /* if(chosenAnswer>0) {
            System.out.println("add_called");
            addQuestionScorer();
        }
        else {
            System.out.println("skip_called");
            skipQuestion();
        }*/

        chosenAnswer=-1;




          //  selectedId = 0;
      //  }
    }


    public void addQuestionScorer() {
       // System.out.print("yeah");

        IndividualQuestion currentQuestion = sIndividualQuestions.get(mQuestionNumber);
        int timeTaken = maxTime - mCurrentSeconds;
        try {
           // System.out.println("correctAnswer_answer"+currentQuestion.correctAnswer);
            //System.out.println("chosenAnswer_answer"+chosenAnswer);

            sQuizScorer.addQuestionScorer(false, currentQuestion.questionNumber, currentQuestion.category, timeTaken, currentQuestion.correctAnswer, chosenAnswer);


           /* if(chosenAnswer>0)//answer attempted
            {
                System.out.println("question_is_not_skipped");
                sQuizScorer.addQuestionScorer(false, currentQuestion.questionNumber, currentQuestion.category, timeTaken, currentQuestion.correctAnswer, chosenAnswer);

            }else {
                System.out.println("question_is_skipped");

                sQuizScorer.addQuestionScorer(true, currentQuestion.questionNumber, currentQuestion.category, timeTaken, currentQuestion.correctAnswer, chosenAnswer);

            }


            chosenAnswer=-1;*/
           // Log.d("quizTracker", Integer.toString(mQuestionNumber) + ": chosen answer is " + Integer.toString(chosenAnswer) + " correct answer is " +Integer.toString(currentQuestion.correctAnswer));
        } finally {
            goToNextQuestion();


        }
    }

    public void skipQuestion() {
       // System.out.print("skip");

        IndividualQuestion currentQuestion = sIndividualQuestions.get(mQuestionNumber);
        int timeTaken = maxTime - mCurrentSeconds;
        try {
            //System.out.println("correctAnswer_answer"+currentQuestion.correctAnswer);
            //System.out.println("chosenAnswer_answer"+chosenAnswer);

            sQuizScorer.addQuestionScorer(true,currentQuestion.questionNumber, currentQuestion.category, timeTaken, currentQuestion.correctAnswer, chosenAnswer);
//            Log.d("quizTracker", Integer.toString(mQuestionNumber) + ": chosen answer is " + Integer.toString(chosenAnswer) + " correct answer is " +Integer.toString(currentQuestion.correctAnswer));
        } finally {
            goToNextQuestion();


        }
    }


    public void endQuiz() {
        if (sQuizScorer.getQuestionScorers().size() == mQuizSize  || mCurrentSeconds<1 ) {
            ArrayList<QuestionScorer> questionScorers = sQuizScorer.getQuestionScorers();
            if (questionScorers != null) {
                mCountDownTimer.cancel();
                mCountDownTimer = null;
                try {
                    Intent intent = new Intent(this, InsertRecordsService.class);
                    intent.putExtra(InsertRecordsService.EXTRA_SERICE_QUIZ_SIZE, mQuizSize);
                    intent.putExtra(InsertRecordsService.EXTRA_SERVICE_QUIZ_NUMBER, QUIZ_NUMBER);
                    startService(intent);
                } finally {
                    Intent intent = new Intent(this, ActivityPostQuiz.class);
                    intent.putExtra(ActivityPostQuiz.KEY_QUIZ_SIZE, mQuizSize);
                    intent.putExtra(ActivityPostQuiz.KEY_QUIZ_NUMBER, QUIZ_NUMBER);
                    startActivity(intent);
                }
            } else {
//                Log.d("ActivityQuiz", "null questionScorers object");
            }
        } else {
//            Log.d("ActivityQuiz","quiz size is too small");
        }
    }

    public void doVibration(boolean hasVibrator) {
        if (hasVibrator) {
            if (mVibrator == null) {
                mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            }
            mVibrator.vibrate(vibrationMillis);
        }
    }

    @Override
    protected void onPause() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onPause();
    }

    public void goToPreviousQuestion(View view) {
        if (mQuestionNumber > 0) {
            mQuestionNumber -= 1;
            setAndUpdateChoiceTextViews(mQuestionNumber);
        }
    }

    public  void kaif(ArrayList<IndividualQuestion> mALLIndividualQuestions) {

        this.kaifList=mALLIndividualQuestions;

        for(int i=0;i<kaifList.size();i++)
        {
           //System.out.println("questin-"+kaifList.get(i));
        }

    }
}

