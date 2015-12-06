package app.com.example.grace.currencycalculator.Controller;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import junit.framework.TestCase;

import app.com.example.grace.currencycalculator.MainActivity;
import app.com.example.grace.currencycalculator.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    MainActivity mainActivity = new MainActivity();

    public void testOnCreate() throws Exception {

    }

    public void testOnCreateOptionsMenu() throws Exception {

    }

    public void testOnOptionsItemSelected() throws Exception {

    }

    public void testOnSaveInstanceState() throws Exception {

    }

    public void testDisplay() throws Exception {

    }

    public void testUpdateWorkArea1() throws Exception {
        String expression  = "2*3+";
        char keyPress = '5';
        //assertEquals("11", mainActivity.updateWorkArea1(expression,keyPress));

    }

    public void testCheckOperatorValidity() throws Exception {

    }

    public void testDeleteFromWorkArea() throws Exception {

    }

    public void testOperatorPrecedence() {
        MainActivity activity = getActivity();
        Button two_button = (Button) activity.findViewById(R.id.two);
        Button times_button = (Button) activity.findViewById(R.id.times);
        Button three_button = (Button) activity.findViewById(R.id.three);
        Button plus_button = (Button) activity.findViewById(R.id.plus);
        Button five_button = (Button) activity.findViewById(R.id.five);

        TextView computation_area = (TextView) activity.findViewById(R.id.computation_area);
        TextView result_area = (TextView)activity.findViewById(R.id.result_area);

        //activateInput(computation_area,"40");
        //activateInput(number2,"19");

       // TextView answer = (TextView)activity.findViewById(R.id.answer);

        TouchUtils.clickView(this, two_button);
        TouchUtils.clickView(this, times_button);
        TouchUtils.clickView(this, three_button);
        TouchUtils.clickView(this, plus_button);
        TouchUtils.clickView(this, five_button);

        String computation = computation_area.getText().toString();
        String ans = result_area.getText().toString();
        String expected = 11+"";

        //assertEquals("2*3+5",computation);
        assertEquals(expected, ans);

    }
}