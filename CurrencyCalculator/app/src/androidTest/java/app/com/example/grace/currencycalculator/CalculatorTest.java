package app.com.example.grace.currencycalculator;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class CalculatorTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public CalculatorTest() {
        super(MainActivity.class);
    }

    public void testComputeExpression() throws Exception {

        Calculator calculator = new Calculator();

        Expression expression =  new Expression();

        Operator plusOperator = new Operator();
        plusOperator.setValue("+");

        Operator timesOperator = new Operator();
        timesOperator.setValue("*");

        Operator minusOperator = new Operator();
        minusOperator.setValue("-");

        Operand operand1= new Operand();
        operand1.setValue("20");

        Operand operand2 = new Operand();
        operand2.setValue("3");

        Operand operand3 = new Operand();
        operand3.setValue("6");

        Operand operand4 = new Operand();
        operand4.setValue("9");

        Operand operand5 = new Operand();
        operand5.setValue("5");

        expression.setExpressionParts(Arrays.asList(operand1,timesOperator,operand2,plusOperator,operand3,minusOperator,operand4,plusOperator,operand5));



        double expressionResult =  calculator.computeExpression(expression);

        assertEquals(62.0,expressionResult);

    }

    public void testIsExpressionValid() throws Exception {

    }
}