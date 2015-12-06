package app.com.example.grace.currencycalculator.Controller;

import android.test.ActivityInstrumentationTestCase2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.example.grace.currencycalculator.Controller.Calculator;
import app.com.example.grace.currencycalculator.MainActivity;
import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class CalculatorTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public CalculatorTest() {
        super(MainActivity.class);
    }

    Calculator calculator;
    Expression expression;

    public void setUp() throws Exception {
        super.setUp();

        calculator = new Calculator();

        expression = new Expression();
    }

    public void testComputeExpressionWhenExpressionNeedsNoPrecedenceRule() throws Exception {
        String expression = "12+6-3";
        assertEquals(15.0, calculator.compute(expression));
    }

    public void testComputeExpressionWhenExpressionNeedsPrecedenceRule() throws Exception {
        String expression = "12+6*4-9/3";
        assertEquals(33.0, calculator.compute(expression));

    }

    public void testComputeExpressionWhenExpressionHasBracketWithAPrecedingOperator() throws Exception {
        String expression = "12+(6)*4";
        assertEquals(36.0, calculator.compute(expression));
    }

    public void testComputeExpression1() throws Exception {
        String expression = "2*3+5";

        assertEquals(11.0, calculator.compute(expression));
    }

    public void testComputeExpressionWhenExpressionHasBracketWithNoPrecedingOperator() throws Exception {
        String expression = "12(3)";

        assertEquals(36.0, calculator.compute(expression));
    }

    public void testComputeExpressionWhenExpressionHasSubExpression() throws Exception {
        String expression = "2*4+(3+5*2-1)/2";

        assertEquals(14.0, calculator.compute(expression));
    }

    public void testCompute1() throws Exception {
        String expression = "(5+3)*6";
        assertEquals(48.0,calculator.compute(expression));
    }

    public void testComputeWhenExpressionHasOperatorsWithSamePrecedence() throws Exception {
        String expression = "1+4*2/4-1";
        assertEquals(2.0,calculator.compute(expression));
    }

    public void testComputeWhenExpressionHasOperatorsWithSamePrecedenceThroughOut() throws Exception {
        String expression = "2*3/2";
        assertEquals(3.0,calculator.compute(expression));
    }
    public void testComputeWhenExpressionStartsWithAUnaryOperator() throws Exception {
        String expression = "-2*3";
        assertEquals(-6.0,calculator.compute(expression));
    }

}