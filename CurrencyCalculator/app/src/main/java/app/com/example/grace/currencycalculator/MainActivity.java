package app.com.example.grace.currencycalculator;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.controller.Calculator;
import app.com.example.grace.currencycalculator.controller.ExchangeRatesFetcher;
import app.com.example.grace.currencycalculator.controller.ExpressionAnalyzer;
import app.com.example.grace.currencycalculator.controller.Validator;
import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;

public class MainActivity extends AppCompatActivity {

    private List<ExpressionPart>expressionParts;

    private NumberFormat numberFormat;

    private Calculator calculator;

    private Expression expression;

    private TextView computationArea;

    private TextView resultArea;

    private TextView currencyText;

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

    private static String destinationButtonText;

    private static final String KEY_COMPUTATION_AREA = "workArea";

    private static final String KEY_RESULT_AREA = "resultArea";

    private static final String KEY_SOURCE_CURRENCY = "sourceCurrency";

    private static final String KEY_DESTINATION_CURRENCY = "destinationCurrency";

    private String currentExpression;

    private HorizontalScrollView computationAreaScroll;

    ExchangeRatesFetcher exchangeRatesFetcher;
    ExpressionAnalyzer expressionAnalyzer;


    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        initializeComponents();

        exchangeRatesFetcher = new ExchangeRatesFetcher(this);

        if (savedInstanceState != null) {

            computationArea.setText(savedInstanceState.getString(KEY_COMPUTATION_AREA, "computationArea"));
            resultArea.setText(savedInstanceState.getString(KEY_RESULT_AREA, "resultArea"));
            sourceCurrencyButton.setText(savedInstanceState.getString(KEY_SOURCE_CURRENCY, "sourceCurrency"));
            destinationCurrencyButton.setText(savedInstanceState.getString(KEY_DESTINATION_CURRENCY,"destinationCurrency"));
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        saveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_COMPUTATION_AREA, computationArea.getText().toString());
        savedInstanceState.putString(KEY_RESULT_AREA, resultArea.getText().toString());
        savedInstanceState.putString(KEY_SOURCE_CURRENCY,sourceCurrencyButton.getText().toString());
        savedInstanceState.putString(KEY_DESTINATION_CURRENCY,destinationCurrencyButton.getText().toString());
    }

    private void initializeComponents() {
        expressionParts = new ArrayList<>();
        numberFormat = new DecimalFormat("##.###");
        expression = new Expression();
        expressionValidator = new Validator();
        computationAreaScroll = (HorizontalScrollView) findViewById(R.id.horizontal_view);
        computationArea = (TextView) findViewById(R.id.computation_area);
        resultArea = (TextView) findViewById(R.id.result_area);
        clearButton = (Button) findViewById(R.id.clear);
        openBracketButton = (Button) findViewById(R.id.open_bracket);
        closeBracketButton = (Button) findViewById(R.id.close_bracket);
        delButton = (ImageButton) findViewById(R.id.del);
        sevenButton = (Button) findViewById(R.id.seven);

        eightButton = (Button) findViewById(R.id.eight);
        nineButton = (Button) findViewById(R.id.nine);
        divisionButton = (Button) findViewById(R.id.division);
        fourButton = (Button) findViewById(R.id.four);
        fiveButton = (Button) findViewById(R.id.five);

        sixButton = (Button) findViewById(R.id.six);
        multiplicationButton = (Button) findViewById(R.id.times);
        minusButton = (Button) findViewById(R.id.minus);
        delButton = (ImageButton) findViewById(R.id.del);
        zeroButton = (Button) findViewById(R.id.zero);

        decimalButton = (Button) findViewById(R.id.decimal);
        equalsButton = (Button) findViewById(R.id.equals);
        plusButton = (Button) findViewById(R.id.plus);
        sourceCurrencyButton = (Button) findViewById(R.id.source_currency);
        destinationCurrencyButton = (Button) findViewById(R.id.destination_currency);
        currencyText = (TextView) findViewById(R.id.currency_area);
        destinationButtonText = destinationCurrencyButton.getText().toString();
        calculator = new Calculator();
        expressionAnalyzer = new ExpressionAnalyzer(this,destinationButtonText);

    }

    public void display(View view) {

        switch (view.getId()) {
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

        String currentExpression = computationArea.getText().toString();
        expressionValidator.setExpression(currentExpression);

        if (expressionValidator.validate(buttonText)) {
            currentExpression = currentExpression + buttonText;
            computationArea.setText(currentExpression);

            if (buttonText != '(') {
                String result;
                expressionAnalyzer = new ExpressionAnalyzer(this, destinationButtonText);
                Expression expression = expressionAnalyzer.breakDownExpression(currentExpression);
                double computed = calculator.compute(expression);
                result = numberFormat.format(computed);
                resultArea.setText(result);

            }
        }
    }
    public void updateWorkArea(String sourceCurrency) {

        setValidatorExpression();

        if(currentIsCurrencyOperand()){
            currentExpression = currentExpression.substring(0,currentExpression.length()-3) + sourceCurrency;
            computationArea.setText(currentExpression);
        }
       else if (expressionValidator.validateCurrency()) {
            currentExpression = currentExpression + sourceCurrency;
            computationArea.setText(currentExpression);
        }
        updateWorkArea();
    }

    public void updateWorkArea() {

        currentExpression = computationArea.getText().toString();

        if(!currentExpression.isEmpty()) {
            String result;
            expressionAnalyzer = new ExpressionAnalyzer(this, destinationButtonText);
            Expression expression = expressionAnalyzer.breakDownExpression(currentExpression);
            double computed = calculator.compute(expression);
            result = numberFormat.format(computed);
            resultArea.setText(result);
        }
    }

    public void checkOperatorValidity(char buttonText) {
        setValidatorExpression();
        computationArea.setText(expressionValidator.validateOperator(buttonText));
    }

    public void deleteFromWorkArea() {

        currentExpression = computationArea.getText().toString();

        if (currentExpression.length() > 1 & !isNonComputable()) {

            computationArea.setText(expressionAfterDelete(currentExpression));
            String expressionAfterDelete = expressionAfterDelete(currentExpression);
            Expression expression = expressionAnalyzer.breakDownExpression(expressionAfterDelete);
            resultArea.setText(numberFormat.format(calculator.compute(expression)));

        } else if (isNonComputable()) {
            updateWorkAreaAfterDelete();
        }

        else {
            clearWorkArea();
        }
    }

    private boolean isNonComputable(){
        String expressionAfterDel = expressionAfterDelete(currentExpression);

        return expressionAfterDel.equals("(") || expressionAfterDel.equals("-") || expressionAfterDel.equals("(-") ||
                (currentExpression.length() > 1 && expressionAfterDel.charAt(expressionAfterDel.length()-1)=='-');
    }

    private void updateWorkAreaAfterDelete() {
        computationArea.setText(expressionAfterDelete(currentExpression));
        initializeResultArea();
    }

    private void moveResultToComputationArea() {
        computationArea.setText(resultArea.getText().toString());
        resultArea.setText("");
    }

    private boolean currentIsCurrencyOperand() {
        return currentExpression.length()>3 && Character.isLetter(currentExpression.charAt(currentExpression.length() - 1));
    }

    private String expressionAfterDelete(String currentExpression) {

        String expressionAfterDelete = "";

        if (currentExpression.length() > 0) {

            if(Character.isLetter(currentExpression.charAt(currentExpression.length()-1))) {
                expressionAfterDelete = currentExpression.substring(0,currentExpression.length()-3);
            }
            else {
                expressionAfterDelete = currentExpression.substring(0, currentExpression.length() - 1);
            }
        }
        return expressionAfterDelete;
    }

    private void setValidatorExpression() {
        currentExpression = computationArea.getText().toString();
        expressionValidator.setExpression(currentExpression);
    }


    private void clearWorkArea() {

        computationArea.setText("");
        initializeResultArea();
    }

    private void initializeResultArea() {
        resultArea.setText("0");
    }

    @TargetApi(11)
    public void displayCurrencies(final String type) {

        AlertDialog currency = null;
        final CharSequence[] items = currenciesToDisplay();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getDialogTitle(type));
        builder.setSingleChoiceItems(items, 3, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (type) {
                    case "source":
                        sourceCurrencyButton.setText(items[item]);
                        updateWorkArea(sourceCurrencyButton.getText().toString());
                        updateWorkArea();
                        break;
                    case "destination":
                        destinationCurrencyButton.setText(items[item]);
                        destinationButtonText = items[item].toString();
                        currencyText.setText(items[item]);
                        updateWorkArea();
                        break;
                }
                dialogInterface.dismiss();
            }

        });
        currency = builder.create();
        currency.show();
    }

    public String getDialogTitle(String type) {
        String title = "";
        if (type.equals("source")) {
            title = "Select the currency you want to convert";
        } else {
            title = "Select the currency you want to convert to";
        }
        return title;
    }

    public CharSequence[] currenciesToDisplay() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String currencies_no = preferences.getString("no_of_currency", "10");
        int noOfCurrencies = Integer.parseInt(currencies_no);
        CharSequence[] items = getResources().getStringArray(R.array.currency_codes);
        CharSequence[] fetchedItems;
        List<CharSequence> currencies = new ArrayList<>();
        for (int i = 0; i <= noOfCurrencies - 1; i++) {
            currencies.add(items[i]);
        }
        fetchedItems = currencies.toArray(new CharSequence[currencies.size()]);
        return fetchedItems;
    }

    public void saveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(KEY_COMPUTATION_AREA, computationArea.getText().toString());
        savedInstanceState.putString(KEY_RESULT_AREA, resultArea.getText().toString());
        savedInstanceState.putString(KEY_SOURCE_CURRENCY,sourceCurrencyButton.getText().toString());
        savedInstanceState.putString(KEY_DESTINATION_CURRENCY,destinationCurrencyButton.getText().toString());
    }



}
