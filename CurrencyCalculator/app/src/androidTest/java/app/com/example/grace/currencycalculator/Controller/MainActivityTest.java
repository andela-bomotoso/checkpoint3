package app.com.example.grace.currencycalculator.controller;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import app.com.example.grace.currencycalculator.MainActivity;
import app.com.example.grace.currencycalculator.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    Button one_button;
    Button two_button;
    Button three_button;
    Button four_button;
    Button five_button;
    Button six_button;
    Button seven_button;
    Button eight_button;
    Button nine_button;
    Button times_button;
    Button division_button;
    Button subtraction_button;
    Button addition_button;
    ImageButton del_button;
    Button opening_bracket;
    Button closing_bracket;
    TextView computation_area;
    TextView result_area;
    Button plus_button;
    Button equals_button;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        activity = getActivity();
        one_button = (Button) activity.findViewById(R.id.one);
        two_button = (Button) activity.findViewById(R.id.two);
        three_button = (Button) activity.findViewById(R.id.three);
        four_button = (Button) activity.findViewById(R.id.four);
        five_button = (Button) activity.findViewById(R.id.five);
        six_button = (Button) activity.findViewById(R.id.six);
        seven_button = (Button) activity.findViewById(R.id.seven);
        eight_button = (Button) activity.findViewById(R.id.eight);
        nine_button = (Button) activity.findViewById(R.id.nine);
        times_button = (Button) activity.findViewById(R.id.times);
        addition_button = (Button) activity.findViewById(R.id.plus);
        division_button = (Button) activity.findViewById(R.id.division);
        subtraction_button = (Button) activity.findViewById(R.id.minus);
        opening_bracket = (Button) activity.findViewById(R.id.open_bracket);
        closing_bracket = (Button) activity.findViewById(R.id.close_bracket);
        plus_button = (Button)activity.findViewById(R.id.plus);
        equals_button = (Button)activity.findViewById(R.id.equals);
        del_button = (ImageButton)activity.findViewById(R.id.del);

        computation_area = (TextView) activity.findViewById(R.id.computation_area);
        result_area = (TextView)activity.findViewById(R.id.result_area);
    }


    public void testDeleteFromWorkAreaWhenLastCharacterIsAnOperand() throws Exception {
        //2*3+5(del)

        TouchUtils.clickView(this, two_button);
        TouchUtils.clickView(this, times_button);
        TouchUtils.clickView(this, three_button);
        TouchUtils.clickView(this, plus_button);
        TouchUtils.clickView(this, five_button);

        String ans = result_area.getText().toString();
        String expected = "11";
        assertEquals(expected, ans);

        TouchUtils.clickView(this,del_button);

        ans = result_area.getText().toString();
        expected = "6";
        assertEquals(expected, ans);

    }

    public void testDeleteFromWorkAreaWhenLastCharacterIsAnOperator() throws Exception {
        //2*3+5-del
        Button two_button = (Button) activity.findViewById(R.id.two);
        Button times_button = (Button) activity.findViewById(R.id.times);
        Button three_button = (Button) activity.findViewById(R.id.three);
        Button plus_button = (Button) activity.findViewById(R.id.plus);
        Button five_button = (Button) activity.findViewById(R.id.five);
        ImageButton del_button = (ImageButton) activity.findViewById(R.id.del);

        TouchUtils.clickView(this, two_button);
        TouchUtils.clickView(this, times_button);
        TouchUtils.clickView(this, three_button);
        TouchUtils.clickView(this, plus_button);
        TouchUtils.clickView(this, five_button);
        TouchUtils.clickView(this,subtraction_button);
        TouchUtils.clickView(this,del_button);

        String ans = result_area.getText().toString();
        String expected = "11";
        assertEquals(expected, ans);

        TouchUtils.clickView(this, del_button);

        ans = result_area.getText().toString();
        expected = "6";
        assertEquals(expected, ans);

    }

    public void testOperatorPrecedenceInAnExpressionWithSubExpression() throws Exception{
        //(2*3)+4
        TouchUtils.clickView(this,opening_bracket);
        TouchUtils.clickView(this,two_button);
        TouchUtils.clickView(this,times_button);
        TouchUtils.clickView(this,three_button);
        TouchUtils.clickView(this,closing_bracket);

        String ans = result_area.getText().toString();
        String expected = "6";
        assertEquals(expected, ans);

        TouchUtils.clickView(this, addition_button);
        TouchUtils.clickView(this,four_button);

        ans = result_area.getText().toString();
        expected = "10";

        assertEquals(expected, ans);
    }

    public void testWhenExpressionHasOperatorsWithSamePrecedence() throws Exception {
        //1+4*2/4-1=
        TouchUtils.clickView(this,one_button);
        TouchUtils.clickView(this,plus_button);
        TouchUtils.clickView(this,four_button);

        String ans = result_area.getText().toString();
        String expected = "5";
        assertEquals(expected, ans);

        TouchUtils.clickView(this, times_button);
        TouchUtils.clickView(this, two_button);

        ans = result_area.getText().toString();
        expected = "9";
        assertEquals(expected, ans);

        TouchUtils.clickView(this, division_button);
        TouchUtils.clickView(this, four_button);

        ans = result_area.getText().toString();
        expected = "3";
        assertEquals(expected, ans);

        TouchUtils.clickView(this, subtraction_button);
        TouchUtils.clickView(this, one_button);

        ans = result_area.getText().toString();
        expected = "2";
        assertEquals(expected, ans);

        TouchUtils.clickView(this,equals_button );

        ans = computation_area.getText().toString();
        expected = "2";
        assertEquals(expected, ans);
    }

    public void testWhenExpressionHasOperatorsWithSamePrecedenceWithManySubExpressions() throws Exception {
        //(8-6)3+(17-12)4-2
        TouchUtils.clickView(this,opening_bracket);
        TouchUtils.clickView(this,eight_button);
        TouchUtils.clickView(this,subtraction_button);
        TouchUtils.clickView(this,six_button);
        TouchUtils.clickView(this,closing_bracket);
        TouchUtils.clickView(this,three_button);

        String ans = result_area.getText().toString();
        String expected = "6";
        assertEquals(expected, ans);

        TouchUtils.clickView(this, plus_button);
        TouchUtils.clickView(this,opening_bracket);
        TouchUtils.clickView(this,one_button);
        TouchUtils.clickView(this,seven_button);
        TouchUtils.clickView(this, subtraction_button);
        TouchUtils.clickView(this, one_button);
        TouchUtils.clickView(this, two_button);

        ans = result_area.getText().toString();
        expected = "11";
        assertEquals(expected, ans);

        TouchUtils.clickView(this,closing_bracket);
        TouchUtils.clickView(this, four_button);

        ans = result_area.getText().toString();
        expected = "26";
        assertEquals(expected, ans);

        TouchUtils.clickView(this,subtraction_button);
        TouchUtils.clickView(this, two_button);

        ans = result_area.getText().toString();
        expected = "24";
        assertEquals(expected, ans);
    }

}