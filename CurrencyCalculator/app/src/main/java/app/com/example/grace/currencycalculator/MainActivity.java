package app.com.example.grace.currencycalculator;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.com.example.grace.currencycalculator.Controller.Calculator;
import app.com.example.grace.currencycalculator.Controller.ExpressionAnalyzer;
import app.com.example.grace.currencycalculator.Controller.Validator;
import app.com.example.grace.currencycalculator.models.Expression;

public class MainActivity extends AppCompatActivity {

    NumberFormat numberFormat;

    private Calculator calculator;

    private ExpressionAnalyzer expressionAnalyzer;

    private Expression expression;

    private TextView computationArea;

    private TextView resultArea;

    private Button clearButton;

    private Button openBracketButton;

    private Button closeBracketButton;

    private ImageButton delButton;

    private Button sevenButton;

    private Button eightButton;

    private Button nineButton;

    private Button divisionButton;

    private Button fourButton;

    private Button fiveButton;

    private Button sixButton;

    private Button multiplicationButton;

    private Button oneButton;

    private Button twoButton;

    private Button threeButton;

    private Button minusButton;

    private Button zeroButton;

    private Button decimalButton;

    private Button equalsButton;

    private Button plusButton;

    private Button sourceCurrencyButton;

    private Button destinationCurrencyButton;

    private Validator expressionValidator;

    public static final String KEY_COMPUTATIONAREA = "workArea";

    public static final String KEY_RESULTAREA = "resultArea";

    private R.array currencies;


    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeComponents();

        if(savedInstanceState != null) {
            computationArea.setText(savedInstanceState.getString(KEY_COMPUTATIONAREA,"computationArea"));
            resultArea.setText(savedInstanceState.getString(KEY_RESULTAREA,"resultArea"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_COMPUTATIONAREA, computationArea.getText().toString());
        savedInstanceState.putString(KEY_RESULTAREA,resultArea.getText().toString());
    }

    private void initializeComponents() {

        numberFormat = new DecimalFormat("##.###");
        calculator = new Calculator();
        expression = new Expression();
        expressionAnalyzer = new ExpressionAnalyzer();
        expressionValidator = new Validator();
        computationArea = (TextView)findViewById(R.id.computation_area);
        resultArea = (TextView)findViewById(R.id.result_area);
        clearButton = (Button) findViewById(R.id.clear);
        openBracketButton = (Button) findViewById(R.id.open_bracket);
        closeBracketButton = (Button) findViewById(R.id.close_bracket);
        delButton = (ImageButton)findViewById(R.id.del);
        sevenButton = (Button) findViewById(R.id.seven);

        eightButton = (Button) findViewById(R.id.eight);
        nineButton = (Button) findViewById(R.id.nine);
        divisionButton = (Button) findViewById(R.id.division);
        fourButton = (Button)findViewById(R.id.four);
        fiveButton = (Button) findViewById(R.id.five);

        sixButton = (Button) findViewById(R.id.six);
        multiplicationButton = (Button) findViewById(R.id.times);
        minusButton = (Button) findViewById(R.id.minus);
        delButton = (ImageButton)findViewById(R.id.del);
        zeroButton = (Button) findViewById(R.id.zero);

        decimalButton = (Button) findViewById(R.id.decimal);
        equalsButton = (Button) findViewById(R.id.equals);
        plusButton = (Button) findViewById(R.id.plus);
        sourceCurrencyButton = (Button) findViewById(R.id.source_currency);
        destinationCurrencyButton = (Button)findViewById(R.id.destination_currency);


    }

    public void display(View view) {

        switch(view.getId()) {
            case R.id.nine:
                updateWorkArea('9');
                break;
            case R.id.eight:
                updateWorkArea('8');
                break;
            case R.id.seven:
                updateWorkArea('7');
                break;
            case R.id.six:
                updateWorkArea('6');
                break;
            case R.id.five:
                updateWorkArea('5');
                break;
            case R.id.four:
                updateWorkArea('4');
                break;
            case R.id.three:
                updateWorkArea('3');
                break;
            case R.id.two:
                updateWorkArea('2');
                break;
            case R.id.one:
                updateWorkArea('1');
                break;
            case R.id.zero:
                updateWorkArea('0');
                break;
            case R.id.open_bracket:
                updateWorkArea('(');
                break;
            case R.id.close_bracket:
                updateWorkArea(')');
                break;
            case R.id.times:
                checkOperatorValidity('*');
                break;
            case R.id.division:
                checkOperatorValidity('/');
                break;
            case R.id.plus:
                checkOperatorValidity('+');
                break;
            case R.id.minus:
                checkOperatorValidity('-');
                break;
            case R.id.decimal:
                updateWorkArea('.');
                break;
            case R.id.clear:
                clearWorkArea();
                break;
            case R.id.del:
                deleteFromWorkArea();
                break;
            case R.id.equals:
                moveResultToComputationArea();
                break;
            case R.id.source_currency:
                displayCurrencies("source");
                break;
            case R.id.destination_currency:
                displayCurrencies("destination");
                break;
        }
    }

    public void updateWorkArea(char buttonText) {
        String result = "";
        String currentExpression = computationArea.getText().toString();
        expressionValidator.setExpression(currentExpression);

        if(expressionValidator.validate(buttonText)) {
            currentExpression = currentExpression + buttonText;
            computationArea.setText(currentExpression);

            if (buttonText != '(') {
                double computed = calculator.compute(currentExpression);

                result = numberFormat.format(computed);
                resultArea.setText(result);
            }
        }
    }

    public String updateWorkArea1(String currentExpression, char buttonText) {
        NumberFormat numberFormat = new DecimalFormat("##.###");
        Calculator calculator = new Calculator();
        Validator expressionValidator = new Validator();
        String result = "";
        expressionValidator.setExpression(currentExpression);

        if(expressionValidator.validate(buttonText)) {
            currentExpression = currentExpression + buttonText;

            if (buttonText != '(') {
               // resultArea.setText(numberFormat.format(calculator.compute(currentExpression)));
                result = numberFormat.format(calculator.compute(currentExpression));
            }
        }
        return result;
    }

    public void checkOperatorValidity(char buttonText) {
        String currentExpression = computationArea.getText().toString();
        expressionValidator.setExpression(currentExpression);
        computationArea.setText(expressionValidator.validateOperator(buttonText));
    }

    public void deleteFromWorkArea() {

        String currentExpression = computationArea.getText().toString();

        if(currentExpression.length() > 1 & !expressionAfterDelete(currentExpression) .equals("(")) {

            computationArea.setText(expressionAfterDelete(currentExpression));
            resultArea.setText(numberFormat.format(calculator.compute(expressionAfterDelete(currentExpression))));
        }
       else if(expressionAfterDelete(currentExpression).equals("(")) {
            computationArea.setText("(");
            resultArea.setText("0");
        }

        else {
            clearWorkArea();
        }
    }

    private void moveResultToComputationArea() {
        computationArea.setText(resultArea.getText().toString());
        resultArea.setText("");
    }

    private String expressionAfterDelete(String currentExpression) {

        String expressionAfterDelete = "";

        if(currentExpression.length() > 0) {
            expressionAfterDelete = currentExpression.substring(0,currentExpression.length()-1);
        }
        return expressionAfterDelete;
    }

    private void clearWorkArea() {

        computationArea.setText("");
        resultArea.setText("0");
    }

    @TargetApi(11)
    public void displayCurrencies(final String type) {

        AlertDialog currency = null;
        final CharSequence[] items = getResources().getStringArray(R.array.currency_codes);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getDialogTitle(type));
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                dialogInterface.dismiss();
                switch (type) {
                    case "source":
                        sourceCurrencyButton.setText(items[item]);
                        break;
                    case "destination":
                        destinationCurrencyButton.setText(items[item]);
                        break;
                }
            }

        });
        currency = builder.create();
        currency.show();
    }

    public String getDialogTitle(String type) {
        String title = "";
        if (type .equals("source")) {
            title = "Select the currency you want to convert";
        }
        else {
            title = "Select the currency you want to convert to";
        }
        return title;
    }

}
