package android.example.simplemathgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.ConsoleHandler;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    TextView textViewMathProblem;
    ArrayList<Integer> answerMathProblem = new ArrayList<>();
    int locationForCorrectAnswer;

    public void onClickAnswer(View v)
    {

        String correctAnswer = Integer.toString(locationForCorrectAnswer);
        String tag = v.getTag().toString();

        Toast toastWinMessage = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);

        Log.i("Tag:", tag + " " + correctAnswer);

        if (tag.equals(correctAnswer))
        {
            toastWinMessage.show();
        }
    }

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

        locationForCorrectAnswer = randomNumber.nextInt(3);

        answerMathProblem.add(wrongAnswerOne); //0
        answerMathProblem.add(wrongAnswerTwo); //1
        answerMathProblem.add(wrongAnswerThree); //2
        answerMathProblem.set(locationForCorrectAnswer, correctAnswerNumber);

        buttonNumber1.setText(Integer.toString(answerMathProblem.get(0)));
        buttonNumber2.setText(Integer.toString(answerMathProblem.get(1)));
        buttonNumber3.setText(Integer.toString(answerMathProblem.get(2)));

    }
}
