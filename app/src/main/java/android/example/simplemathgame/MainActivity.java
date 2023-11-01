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
    int locationForCorrectAnswer;
    int score = 0;
    int totalQuestions = 0;
    int guessesCounter = 3;
    boolean gameIsActive = false;
    String problemType = "";

    //arrays
    ArrayList<Integer> answerMathProblem = new ArrayList<>();

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

        //set toasts
        toastTimeIsUp = Toast.makeText(getApplicationContext(), "Time is up", Toast.LENGTH_SHORT);
        toastTimeIsUp.setGravity(1,0, 160);
        toastOutOfGuessesMessage = Toast.makeText(getApplicationContext(), "All Guesses Used", Toast.LENGTH_SHORT);
        toastOutOfGuessesMessage.setGravity(1, 0, 120);
        toastWinMessage = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
        toastWinMessage.setGravity(1,0, 140);
        toastGuessesLeft = Toast.makeText(getApplicationContext(), String.valueOf(guessesCounter), Toast.LENGTH_SHORT);

        //set timer
        timer = new CountDownTimer(60100, 100)
        {
            @Override public void onTick(long millisUntilFinished)
            {
                String ticker = String.valueOf(millisUntilFinished / 1000);
                textViewSecondsCounter.setText(ticker);
            }

            @Override public void onFinish()
            {
                toastTimeIsUp.show();
                resetGame();
            }
        };
    }

    //functions
    public void resetGame()
    {
        //reset variables
        totalQuestions = 0;
        score = 0;
        guessesCounter = 3;
        gameIsActive = false;

        //reset UI
        buttonStart.setVisibility(View.VISIBLE);
        buttonSubtraction.setVisibility(View.VISIBLE);
        buttonMultiplication.setVisibility(View.VISIBLE);
        buttonDivision.setVisibility(View.VISIBLE);
        buttonPercentages.setVisibility(View.VISIBLE);
        buttonSquareRoot.setVisibility(View.VISIBLE);
        buttonNumber1.setVisibility(View.INVISIBLE);
        buttonNumber2.setVisibility(View.INVISIBLE);
        buttonNumber3.setVisibility(View.INVISIBLE);
        textViewMathProblem.setVisibility(View.INVISIBLE);
        textViewSecondsCounter.setVisibility(View.INVISIBLE);
        textViewSecondsCounter.setText("");
        textViewScore.setVisibility(View.INVISIBLE);
        textViewScore.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));
        //toastTimeIsUp.cancel();
        //toastOutOfGuessesMessage.cancel();
        //toastWinMessage.cancel();
        //toastGuessesLeft.cancel();
    }


    public void startGame(View view)
    {
        //reset variables
        totalQuestions = 0;
        score = 0;
        guessesCounter = 3;
        gameIsActive = true;
        String type = view.getTag().toString();

        //reset UI
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
        textViewScore.setVisibility(View.VISIBLE);
        textViewScore.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));

        //check type
        if(type.equals("Addition")) { problemType = "Addition"; setMathProblemAddition(); }
        else if(type.equals("Subtraction")) { problemType = "Subtraction"; setMathProblemSubtraction(); }
        else if(type.equals("Multiplication")) { problemType = "Multiplication"; }
        else if(type.equals("Division")) { problemType = "Division"; }
        else if(type.equals("Percentages")) { problemType = "Percentages"; }
        else if(type.equals("SquareRoot")) { problemType = "SquareRoot"; }

        //start timer
        timer.start();
    }


    public void setMathProblemAddition()
    {
        //variables
        Random randomNumber = new Random();
        int numberA = randomNumber.nextInt(99);
        int numberB = randomNumber.nextInt(99);
        int wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
        int correctAnswerNumber = numberA + numberB;
        String A = Integer.toString(numberA);
        String B = Integer.toString(numberB);
        locationForCorrectAnswer = randomNumber.nextInt(3);

        //set array
        answerMathProblem.clear();
        answerMathProblem.add(wrongAnswerOne); //0
        answerMathProblem.add(wrongAnswerTwo); //1
        answerMathProblem.add(wrongAnswerThree); //2
        answerMathProblem.set(locationForCorrectAnswer, correctAnswerNumber);

        //update UI
        textViewMathProblem.setText(A + " + " + B);
        buttonNumber1.setText(Integer.toString(answerMathProblem.get(0)));
        buttonNumber2.setText(Integer.toString(answerMathProblem.get(1)));
        buttonNumber3.setText(Integer.toString(answerMathProblem.get(2)));
    }


    public void setMathProblemSubtraction()
    {
        //variables
        Random randomNumber = new Random();
        int numberA = randomNumber.nextInt(99);
        int numberB = randomNumber.nextInt(99);
        int wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
        int correctAnswerNumber = numberA - numberB;
        String A = Integer.toString(numberA);
        String B = Integer.toString(numberB);
        locationForCorrectAnswer = randomNumber.nextInt(3);

        //set array
        answerMathProblem.clear();
        answerMathProblem.add(wrongAnswerOne); //0
        answerMathProblem.add(wrongAnswerTwo); //1
        answerMathProblem.add(wrongAnswerThree); //2
        answerMathProblem.set(locationForCorrectAnswer, correctAnswerNumber);

        //update UI
        textViewMathProblem.setText(A + " - " + B);
        buttonNumber1.setText(Integer.toString(answerMathProblem.get(0)));
        buttonNumber2.setText(Integer.toString(answerMathProblem.get(1)));
        buttonNumber3.setText(Integer.toString(answerMathProblem.get(2)));
    }

    public void selectAnswer(View v)
    {
        //variables
        String correctAnswer = Integer.toString(locationForCorrectAnswer);
        String tag = v.getTag().toString();

        //debugging
        Log.i("Tag:", tag + " " + correctAnswer + " " + guessesCounter + " " + totalQuestions);

        //answer is correct
        if (tag.equals(correctAnswer) == true) { score++; toastWinMessage.show(); }

        //answer is wrong
        else { guessesCounter--; toastGuessesLeft.setText("Guesses Left: " + guessesCounter); toastGuessesLeft.show(); }

        //out of guesses
        if (guessesCounter == 0) { toastGuessesLeft.show(); resetGame(); }

        //increment total questions
        totalQuestions++;

        //update UI
        textViewScore.setText(Integer.toString(score) + "/" + Integer.toString(totalQuestions));

        //add new problem
        if(problemType == "Addition") { setMathProblemAddition(); }
        else if(problemType == "Subtraction") { setMathProblemSubtraction(); }
        if(problemType == "Multiplication") { }
        else if(problemType == "Division") { }
        else if(problemType == "Percentages") { }
        else if(problemType == "SquareRoot") { }
    }
}