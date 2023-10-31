/****** namespace ******/
package android.example.simplemathgame;


/****** includes ******/
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.ConsoleHandler;


/****** main ******/
public class MainActivity extends AppCompatActivity {

    //variables
    ArrayList<Integer> answerMathProblem = new ArrayList<>();
    int locationForCorrectAnswer;
    int score;
    int totalQuestions;
    int guessesCounter;

    //widgets
    Button buttonStart;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonSubtraction;
    Button buttonMultiplication;
    Button buttonDivision;
    Button buttonPercentages;
    Button buttonSquareRoot;
    TextView textViewMathProblem;
    TextView textViewScore;
    TextView textViewSecondsCounter;
    CountDownTimer timer;
    Toast toastTimeIsUp;
    Toast toastWinMessage;
    Toast toastOutOfGuessesMessage;
    Toast toastGuessesLeft;

    //on startup
    @Override protected void onCreate(Bundle savedInstanceState) {
        //on create
        super.onCreate(savedInstanceState);

        //set startup activity
        setContentView(R.layout.activity_main);

        //widgets
        buttonStart = findViewById(R.id.buttonStart);
        buttonSubtraction = findViewById(R.id.buttonSubtraction);
        buttonMultiplication = findViewById(R.id.buttonMultiplication);
        buttonDivision = findViewById(R.id.buttonDivision);
        buttonPercentages = findViewById(R.id.buttonPercentages);
        buttonSquareRoot = findViewById(R.id.buttonSquareRoot);

        buttonNumber1 = findViewById(R.id.buttonNumber1);
        buttonNumber2 = findViewById(R.id.buttonNumber2);
        buttonNumber3 = findViewById(R.id.buttonNumber3);
        textViewMathProblem = findViewById(R.id.textViewMathProblem);
        textViewScore = findViewById(R.id.textViewScore);
        textViewSecondsCounter = findViewById(R.id.textViewSecondsCounter);

        //set toast messages
        toastTimeIsUp = Toast.makeText(getApplicationContext(), "Time is up", Toast.LENGTH_SHORT);
        toastWinMessage = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
        toastOutOfGuessesMessage = Toast.makeText(getApplicationContext(), "All Guesses Used", Toast.LENGTH_SHORT);
        toastGuessesLeft = Toast.makeText(getApplicationContext(), String.valueOf(guessesCounter), Toast.LENGTH_SHORT);

        addQuestion();

        timer = new CountDownTimer(60100, 100)
        {
            @Override public void onTick(long millisUntilFinished)
            {
                String ticker = String.valueOf(millisUntilFinished / 1000);
                textViewSecondsCounter.setText(ticker);
            }

            @Override public void onFinish()
            {
                toastTimeIsUp.setGravity(1,0, 160);
                toastTimeIsUp.show();
                textViewSecondsCounter.setText("");
                buttonStart.setVisibility(View.VISIBLE);
                buttonNumber1.setVisibility(View.INVISIBLE);
                buttonNumber2.setVisibility(View.INVISIBLE);
                buttonNumber3.setVisibility(View.INVISIBLE);
                textViewMathProblem.setVisibility(View.INVISIBLE);
                textViewSecondsCounter.setVisibility(View.INVISIBLE);
            }
        };

    }

    //functions
    public void resetGame()
    {
        //variables
        totalQuestions = 0;
        score = 0;
        guessesCounter = 25;

        //reset widgets
        toastTimeIsUp.cancel();
        toastOutOfGuessesMessage.cancel();
        toastWinMessage.cancel();
        toastGuessesLeft.cancel();

        buttonStart.setVisibility(View.INVISIBLE);
        buttonSubtraction.setVisibility(View.INVISIBLE);
        buttonMultiplication.setVisibility(View.INVISIBLE);
        buttonDivision.setVisibility(View.INVISIBLE);
        buttonPercentages.setVisibility(View.INVISIBLE);
        buttonSquareRoot.setVisibility(View.INVISIBLE);

        buttonNumber1.setVisibility(View.VISIBLE);
        buttonNumber2.setVisibility(View.VISIBLE);
        buttonNumber3.setVisibility(View.VISIBLE);
        textViewMathProblem.setVisibility(View.VISIBLE);
        textViewSecondsCounter.setVisibility(View.VISIBLE);
        textViewScore.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));

        //start timer
        timer.start();
    }


    public void addQuestion()
    {
        //variables
        Random randomNumber = new Random();
        int numberA = randomNumber.nextInt(99);
        int numberB = randomNumber.nextInt(99);
        int correctAnswerNumber = numberA + numberB;
        int wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
        String A = Integer.toString(numberA);
        String B = Integer.toString(numberB);

        //update UI
        textViewMathProblem.setText(A + " + " + B);
        locationForCorrectAnswer = randomNumber.nextInt(3);
        answerMathProblem.clear();
        answerMathProblem.add(wrongAnswerOne); //0
        answerMathProblem.add(wrongAnswerTwo); //1
        answerMathProblem.add(wrongAnswerThree); //2
        answerMathProblem.set(locationForCorrectAnswer, correctAnswerNumber);
        buttonNumber1.setText(Integer.toString(answerMathProblem.get(0)));
        buttonNumber2.setText(Integer.toString(answerMathProblem.get(1)));
        buttonNumber3.setText(Integer.toString(answerMathProblem.get(2)));
    }


    public void onClickAnswer(View v)
    {
        //variables
        String correctAnswer = Integer.toString(locationForCorrectAnswer);
        String tag = v.getTag().toString();

        //debugging
        Log.i("Tag:", tag + " " + correctAnswer + " " + guessesCounter + " " + totalQuestions);

        if (guessesCounter == 1)
        {
            toastOutOfGuessesMessage.setGravity(1, 0, 120);
            toastOutOfGuessesMessage.show();
            textViewSecondsCounter.setText("");
            buttonStart.setVisibility(View.VISIBLE);
            buttonNumber1.setVisibility(View.INVISIBLE);
            buttonNumber2.setVisibility(View.INVISIBLE);
            buttonNumber3.setVisibility(View.INVISIBLE);
            textViewMathProblem.setVisibility(View.INVISIBLE);
            textViewSecondsCounter.setVisibility(View.INVISIBLE);
        }

        if (tag.equals(correctAnswer))
        {
            score++;
            //toastWinMessage.setGravity(1,0, 140);
            //toastWinMessage.show();
        }

        if (tag.equals(correctAnswer) == false)
        {
            guessesCounter--;

            if (guessesCounter > 1)
            {
                toastGuessesLeft.setText(Integer.toString(guessesCounter) + " Guesses Left");
                toastGuessesLeft.show();
            }
            if (guessesCounter == 1)
            {
                toastGuessesLeft.setText(Integer.toString(guessesCounter) + " Guess Left");
                toastGuessesLeft.show();
            }
        }

        /*
        if (questionCounter != 20)
        {
            questionCounter++;
            addQuestion();
        }
        */

        totalQuestions++;
        textViewScore.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));
        addQuestion();
    }


    public void onClickStart(View v)
    {
        resetGame();
    }
}