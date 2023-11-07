/****** namespace ******/
package android.example.simplemathgame;


/****** includes ******/
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.logging.ConsoleHandler;


/****** main ******/
public class MainActivity extends AppCompatActivity {
    //variables
    int locationForCorrectAnswer;
    int totalQuestions = 0;
    int guessesCounter = 3;
    int visible = 0;
    int invisible = 4;
    int gone = 8;
    int totalCorrectAnswers = 0;
    int totalWrongAnswers = 0;
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
    TextView textViewSecondsCounter;
    TextView textViewTotalQuestions;
    TextView textViewTotalCorrectAnswers;
    TextView textViewTotalWrongAnswers;
    TextView textViewTotalGuessesLeft;
    TextView textViewHistoryText;
    TextView textViewStatsText;
    CountDownTimer timer;
    Toast toastTimeIsUp;
    Toast toastWinMessage;
    Toast toastOutOfGuessesMessage;
    Toast toastGuessesLeft;
    ConstraintLayout Tab1;
    ConstraintLayout Tab2;
    ConstraintLayout Tab3;
    LinearLayout BottomMenu;

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
        textViewSecondsCounter = findViewById(R.id.textViewSecondsCounter);
        textViewTotalQuestions = findViewById(R.id.textViewTotalQuestions);
        textViewTotalCorrectAnswers = findViewById(R.id.textViewTotalCorrectAnswers);
        textViewTotalWrongAnswers = findViewById(R.id.textViewTotalWrongAnswers);
        textViewTotalGuessesLeft = findViewById(R.id.textViewTotalGuessesLeft);
        textViewHistoryText = findViewById(R.id.textViewHistoryText);
        textViewStatsText = findViewById(R.id.textViewStatsText);
        Tab1 = findViewById(R.id.Tab1);
        Tab2 = findViewById(R.id.Tab2);
        Tab3 = findViewById(R.id.Tab3);
        BottomMenu = findViewById(R.id.BottomMenu);

        //set toasts
        toastTimeIsUp = Toast.makeText(getApplicationContext(), "Time is up", Toast.LENGTH_SHORT);
        toastTimeIsUp.setGravity(1,0, 160);
        toastOutOfGuessesMessage = Toast.makeText(getApplicationContext(), "All Guesses Used", Toast.LENGTH_SHORT);
        toastOutOfGuessesMessage.setGravity(1, 0, 120);
        toastWinMessage = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
        toastWinMessage.setGravity(1,0, 140);
        toastGuessesLeft = Toast.makeText(getApplicationContext(), String.valueOf(guessesCounter), Toast.LENGTH_SHORT);

        //set game history
        setGameHistory();

        //set game stats
        if(!getGameHistory().equals("")) { setGameStats(); }

        //set timer
        timer = new CountDownTimer(60100, 100) {
            @Override public void onTick(long millisUntilFinished)
            { String ticker = String.valueOf(millisUntilFinished / 1000); textViewSecondsCounter.setText(ticker); }

            @Override public void onFinish()
            { saveGame(totalQuestions, totalCorrectAnswers); resetGame(); }
        };
    }

    //functions
    public void resetGame()
    {
        //reset variables
        totalQuestions = 0;
        totalCorrectAnswers = 0;
        totalWrongAnswers = 0;
        guessesCounter = 3;
        gameIsActive = false;

        //reset UI
        buttonStart.setVisibility(visible);
        buttonSubtraction.setVisibility(visible);
        buttonMultiplication.setVisibility(visible);
        buttonDivision.setVisibility(visible);
        buttonPercentages.setVisibility(visible);
        buttonSquareRoot.setVisibility(visible);
        buttonNumber1.setVisibility(invisible);
        buttonNumber2.setVisibility(invisible);
        buttonNumber3.setVisibility(invisible);
        textViewMathProblem.setVisibility(invisible);
        textViewSecondsCounter.setVisibility(invisible);
        textViewSecondsCounter.setText("");
        textViewTotalQuestions.setVisibility(invisible);
        textViewTotalCorrectAnswers.setVisibility(invisible);
        textViewTotalWrongAnswers.setVisibility(invisible);
        textViewTotalGuessesLeft.setVisibility(invisible);
        BottomMenu.setVisibility(visible);
        textViewTotalQuestions.setText("0");
        textViewTotalCorrectAnswers.setText("0");
        textViewTotalWrongAnswers.setText("0");
        textViewTotalGuessesLeft.setText(Integer.toString(guessesCounter));
        //toastTimeIsUp.cancel();
        //toastOutOfGuessesMessage.cancel();
        //toastWinMessage.cancel();
        //toastGuessesLeft.cancel();

        //stop timer
        timer.cancel();
    }


    public void startGame(View view)
    {
        //reset variables
        totalQuestions = 0;
        totalCorrectAnswers = 0;
        totalWrongAnswers = 0;
        guessesCounter = 3;
        gameIsActive = true;

        //set type
        String type = view.getTag().toString();

        //reset UI
        toastTimeIsUp.cancel();
        toastOutOfGuessesMessage.cancel();
        toastWinMessage.cancel();
        toastGuessesLeft.cancel();
        buttonStart.setVisibility(invisible);
        buttonSubtraction.setVisibility(invisible);
        buttonMultiplication.setVisibility(invisible);
        buttonDivision.setVisibility(invisible);
        buttonPercentages.setVisibility(invisible);
        buttonSquareRoot.setVisibility(invisible);
        buttonNumber1.setVisibility(visible);
        buttonNumber2.setVisibility(visible);
        buttonNumber3.setVisibility(visible);
        textViewMathProblem.setVisibility(visible);
        textViewSecondsCounter.setVisibility(visible);
        //textViewTotalQuestions.setVisibility(visible);
        textViewTotalCorrectAnswers.setVisibility(visible);
        textViewTotalWrongAnswers.setVisibility(visible);
        //textViewTotalGuessesLeft.setVisibility(visible);
        BottomMenu.setVisibility(invisible);
        textViewTotalQuestions.setText("0");
        textViewTotalCorrectAnswers.setText("0");
        textViewTotalWrongAnswers.setText("0");
        textViewTotalGuessesLeft.setText(Integer.toString(guessesCounter));

        //set math problem
        problemType = type;
        setMathProblem(type);

        //start timer
        timer.start();
    }


    public void setMathProblem(String type)
    {
        //variables
        int numberA = 0;
        int numberB = 0;
        int wrongAnswerOne = 0;
        int wrongAnswerTwo = 0;
        int wrongAnswerThree = 0;
        int correctAnswerNumber = 0;
        String A = "";
        String B = "";
        String mp = "";
        Random randomNumber = new Random();
        locationForCorrectAnswer = randomNumber.nextInt(3);

        if(type.equals("Addition"))
        {
            numberA = randomNumber.nextInt(99);
            numberB = randomNumber.nextInt(99);
            wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
            wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
            wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
            correctAnswerNumber = numberA + numberB;
            A = Integer.toString(numberA);
            B = Integer.toString(numberB);
            mp = A + " + " + B;
        }
        else if(type.equals("Subtraction"))
        {
            numberA = randomNumber.nextInt(99);
            numberB = randomNumber.nextInt(99);
            wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
            wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
            wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
            correctAnswerNumber = numberA - numberB;
            A = Integer.toString(numberA);
            B = Integer.toString(numberB);
            mp = A + " - " + B;
        }
        else if(type.equals("Multiplication"))
        {
            numberA = randomNumber.nextInt(99);
            numberB = randomNumber.nextInt(99);
            wrongAnswerOne = 1 + randomNumber.nextInt(99) * 2;
            wrongAnswerTwo = 1 + randomNumber.nextInt(99) * 2;
            wrongAnswerThree = 1 + randomNumber.nextInt(99) * 2;
            correctAnswerNumber = numberA * numberB;
            A = Integer.toString(numberA);
            B = Integer.toString(numberB);
            mp = A + " x " + B;
        }
        else if(type.equals("Division"))
        {

        }
        else if(type.equals("Percentages"))
        {

        }
        else if(type.equals("SquareRoot"))
        {

        }

        //set array
        answerMathProblem.clear();
        answerMathProblem.add(wrongAnswerOne); //0
        answerMathProblem.add(wrongAnswerTwo); //1
        answerMathProblem.add(wrongAnswerThree); //2
        answerMathProblem.set(locationForCorrectAnswer, correctAnswerNumber);

        //update UI
        textViewMathProblem.setText(mp);
        buttonNumber1.setText(Integer.toString(answerMathProblem.get(0)));
        buttonNumber2.setText(Integer.toString(answerMathProblem.get(1)));
        buttonNumber3.setText(Integer.toString(answerMathProblem.get(2)));
    }


    public void selectAnswer(View v)
    {
        //variables
        String correctAnswer = Integer.toString(locationForCorrectAnswer);
        String type = v.getTag().toString();

        //debugging
        Log.i("Tag:", type + " " + correctAnswer + " " + guessesCounter + " " + totalQuestions);

        //check answer
        if (type.equals(correctAnswer) == true) //answer is correct
        {
            //increment correct answers
            totalCorrectAnswers++;

            //update UI
            textViewTotalCorrectAnswers.setText(Integer.toString(totalCorrectAnswers));
        }
        else //answer is wrong
        {
            //increment wrong answers
            totalWrongAnswers++;

            //update UI
            textViewTotalWrongAnswers.setText(Integer.toString(totalWrongAnswers));
        }

        //out of guesses
        //if (guessesCounter == 0) { saveGame(totalQuestions, totalCorrectAnswers); resetGame(); }

        //max total questions
        if(totalQuestions == 9) { saveGame(totalQuestions, totalCorrectAnswers); resetGame(); }

        //increment total questions
        totalQuestions++;
        textViewTotalQuestions.setText(Integer.toString(totalQuestions));

        //add new problem
        setMathProblem(problemType);
    }


    public void changeTab(View view)
    {
        //set type
        String type = view.getTag().toString();

        //reset widgets
        Tab1.setVisibility(invisible);
        Tab2.setVisibility(invisible);
        Tab3.setVisibility(invisible);

        //update UI
        if(type.equals("Tab1")) { Tab1.setVisibility(visible); }
        else if(type.equals("Tab2")) { Tab2.setVisibility(visible); }
        else if(type.equals("Tab3")) { Tab3.setVisibility(visible); }
    }


    public void saveGame(int totalQuestions, int correctAnswers)
    {
        //variables
        SharedPreferences storage = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String date = getDateFormatted();
        String scoreString = "";
        String gameId = "";
        String gameData = "";
        int totalGamesSaved = 0;
        double score = 0;

        //set total games saved
        totalGamesSaved = Integer.parseInt(storage.getString("totalGamesSaved", "0"));

        //set total questions
        totalQuestions = (totalQuestions + 1);

        //set score
        score = ((double)correctAnswers / (double)totalQuestions) * 100;

        //set score string
        scoreString = Double.toString(score);
        scoreString = scoreString.split("\\.")[0];
        //scoreString = scoreString.replace(".0", "");

        //increment total games saved
        totalGamesSaved++;

        //set gameId
        gameId = "game#" + totalGamesSaved;

        //set game data
        gameData = gameId.replace("game", "")
        + " · " + date + " · " + problemType.substring(0, 3).toUpperCase() + " · " + scoreString + "%";

        //save game
        storage.edit()
               .putString(gameId, gameData)
               .apply();

        //save total games count
        storage.edit()
               .putString("totalGamesSaved", String.valueOf(totalGamesSaved))
               .apply();


        //update UI
        setGameHistory();
        setGameStats();

        //debugging
        Log.i("totalGamesSaved", storage.getString("totalGamesSaved", "0"));
    }


    public String getGameHistory()
    {
        //variables
        SharedPreferences storage = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String totalGamesSaved = storage.getString("totalGamesSaved", "0");
        String gameHistory = "";

        //game history does not exist
        if(totalGamesSaved.equals("0"))
        {
            //save total games count
            storage.edit()
                   .putString("totalGamesSaved", "0")
                   .apply();

            totalGamesSaved = "0";
        }

        //game history exists
        else
        {
            //get game history
            for(int c = Integer.parseInt(totalGamesSaved); c > 0; c--)
            {
                String gameData = storage.getString("game#" + String.valueOf(c), "");
                gameHistory += gameData + "\n";
            }

            //debugging
            //Log.i("getSelectedTheme", st);
            Log.i("totalGamesSaved", totalGamesSaved);

            //return value
            return gameHistory;
        }

        //return value
        return "";
    }


    public void setGameHistory()
    {
        //variables
        String gameHistory = getGameHistory();

        //update UI
        textViewHistoryText.setText(gameHistory);
    }


    public int getTotalGamesSaved()
    {
        //variables
        SharedPreferences storage = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        int totalGamesSaved = Integer.parseInt(storage.getString("totalGamesSaved", "0"));

        //return value
        return totalGamesSaved;
    }


    public void setGameStats()
    {
        //variables
        SharedPreferences storage = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String totalGamesSaved = "0";
        int totalGamesADD = 0;
        int totalGamesSUB = 0;
        int totalGamesMUL = 0;
        int totalGamesDIV = 0;
        int totalGamesPER = 0;
        int totalGamesSQU = 0;
        int totalScoreADD = 0;
        int totalScoreSUB = 0;
        int totalScoreMUL = 0;
        int totalScoreDIV = 0;
        int totalScorePER = 0;
        int totalScoreSQU = 0;
        int averageScoreADD = 0;
        int averageScoreSUB = 0;
        int averageScoreMUL = 0;
        int averageScoreDIV = 0;
        int averageScorePER = 0;
        int averageScoreSQU = 0;

        //set total games saved
        totalGamesSaved = storage.getString("totalGamesSaved", "0");

        //handle game data
        for(int c = Integer.parseInt(totalGamesSaved); c > 0; c--)
        {
            String gameData = storage.getString("game#" + String.valueOf(c), "");
            int gameDataScore = Integer.parseInt(gameData.split(" · ")[3].replace("%", ""));

            if(gameData.contains("ADD")) { totalGamesADD++; totalScoreADD += gameDataScore; }
            else if(gameData.contains("SUB")) { totalGamesSUB++; totalScoreSUB += gameDataScore; }
            else if(gameData.contains("MUL")) { totalGamesMUL++; totalScoreMUL += gameDataScore; }
            //else if(gameData.contains("DIV")) { totalGamesDIV++; totalScoreDIV += gameDataScore; }
            //else if(gameData.contains("PER")) { totalGamesPER++; totalScorePER += gameDataScore; }
            //else if(gameData.contains("SQU")) { totalGamesSQU++; totalScoreSQU += gameDataScore; }
        }

        //set average scores
        if(totalGamesADD != 0) { averageScoreADD = totalScoreADD / totalGamesADD; }
        if(totalGamesSUB != 0) { averageScoreSUB = totalScoreSUB / totalGamesSUB; }
        if(totalGamesMUL != 0) { averageScoreMUL = totalScoreMUL / totalGamesMUL; }
        //averageScoreDIV = totalScoreDIV / totalGamesDIV;
        //averageScorePER = totalScorePER / totalGamesPER;
        //averageScoreSQU = totalScoreSQU / totalGamesSQU;

        //set stats text
        String statsText = "(" + totalGamesADD + ") · ADD Avg: " + averageScoreADD + "%" +
        "\n" + "(" + totalGamesSUB + ") · SUB Avg: " + averageScoreSUB + "%" +
        "\n" + "(" + totalGamesMUL + ") · MUL Avg: " + averageScoreMUL + "%" +
        "\n" + "(" + totalScoreDIV + ") · DIV Avg: " + averageScoreDIV + "%" +
        "\n" + "(" + totalScorePER + ") · PER Avg: " + averageScorePER + "%" +
        "\n" + "(" + totalScoreSQU + ") · SQU Avg: " + averageScoreSQU + "%";

        //update UI
        textViewStatsText.setText(statsText);
    }

    public void clearGameHistory(View view)
    {
        //variables
        SharedPreferences storage = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

        //clear shared preferences data
        storage.edit()
               .clear()
               .apply();

        //update UI
        textViewHistoryText.setText("");
        textViewStatsText.setText("ADD Avg: \nSUB Avg: \nMUL Avg: \nDIV Avg: \nPER Avg: \nSQU Avg: ");
    }



    public String setCalendarDate(Calendar value, String type)
    {
        //variables
        Date date = null;
        String shortDateString;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        //check type
        if(type == "today")
        { date = value.getTime(); }
        else if(type == "increment")
        { value.add(Calendar.DAY_OF_YEAR, 1); date = value.getTime(); }

        //set formatted string
        shortDateString = dateFormat.format(date).substring(0, 10).replace("/", "-");

        //return value
        return shortDateString;
    }


    public String getDateFormatted()
    {
        //variables
        Calendar calendar = Calendar.getInstance();
        String date = setCalendarDate(calendar, "today");
        String value = date;
        String formattedString = value;
        String[] split = formattedString.split("-");
        String part0 = split[0];
        String part1 = split[1];
        String part2 = split[2];

        //set month
        if(Objects.equals(part1, "1")) { part1 = "Jan"; }
        else if(Objects.equals(part1, "2")) { part1 = "Feb"; }
        else if(Objects.equals(part1, "3")) { part1 = "Mar"; }
        else if(Objects.equals(part1, "4")) { part1 = "Apr"; }
        else if(Objects.equals(part1, "5")) { part1 = "May"; }
        else if(Objects.equals(part1, "6")) { part1 = "Jun"; }
        else if(Objects.equals(part1, "7")) { part1 = "Jul"; }
        else if(Objects.equals(part1, "8")) { part1 = "Aug"; }
        else if(Objects.equals(part1, "9")) { part1 = "Sep"; }
        else if(Objects.equals(part1, "10")) { part1 = "Oct"; }
        else if(Objects.equals(part1, "11")) { part1 = "Nov"; }
        else if(Objects.equals(part1, "12")) { part1 = "Dec"; }

        //set day
        if(Objects.equals(part2, "01")) { part2 = "1"; }
        else if(Objects.equals(part2, "02")) { part2 = "2"; }
        else if(Objects.equals(part2, "03")) { part2 = "3"; }
        else if(Objects.equals(part2, "04")) { part2 = "4"; }
        else if(Objects.equals(part2, "05")) { part2 = "5"; }
        else if(Objects.equals(part2, "06")) { part2 = "6"; }
        else if(Objects.equals(part2, "07")) { part2 = "7"; }
        else if(Objects.equals(part2, "08")) { part2 = "8"; }
        else if(Objects.equals(part2, "09")) { part2 = "9"; }

        //set date string
        formattedString =  part1 + " " + part2;
        //formattedString =  part1 + "-" + part2 + "-" + part0;
        //formattedString =  part1 + " " + part2 + " " + part0;

        //return value
        return formattedString;
    }
}