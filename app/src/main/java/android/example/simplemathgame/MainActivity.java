package android.example.simplemathgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    TextView textViewMathProblem;
    ArrayList<Integer> answerMathProblem = new ArrayList<>();

    public void onClickStart(View v)
    {
        buttonStart.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonNumber1 = findViewById(R.id.buttonNumber1);
        buttonNumber2 = findViewById(R.id.buttonNumber2);
        buttonNumber3 = findViewById(R.id.buttonNumber3);

        textViewMathProblem = findViewById(R.id.textViewMathProblem);

        Random randomNumber = new Random();

        int numberA = randomNumber.nextInt(99);
        int numberB = randomNumber.nextInt(99);
        int correctAnswerNumber = numberA + numberB;
        int wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
        int wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
        String A = Integer.toString(numberA);
        String B = Integer.toString(numberB);

        textViewMathProblem.setText(A + " + " + B);

        int locationForCorrectAnswer = randomNumber.nextInt(2) + 1;

        answerMathProblem.add(wrongAnswerOne);
        answerMathProblem.add(wrongAnswerTwo);
        answerMathProblem.add(wrongAnswerThree);
        answerMathProblem.add(locationForCorrectAnswer, correctAnswerNumber);

        buttonNumber1.setText(Integer.toString(answerMathProblem.get(1)));
        buttonNumber2.setText(Integer.toString(answerMathProblem.get(2)));
        buttonNumber3.setText(Integer.toString(answerMathProblem.get(3)));

    }
}
