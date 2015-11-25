package app.com.example.grace.currencycalculator;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;


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

        Operand operand1= new Operand();
        operand1.setValue("20");

        Operand operand2 = new Operand();
        operand2.setValue("30");

        expression.setExpressionParts(Arrays.asList(operand1,plusOperator,operand2));

        double expressionResult =  calculator.computeExpression(expression);

        assertEquals(50,expressionResult);

    }

    public void testIsExpressionValid() throws Exception {

    }
}