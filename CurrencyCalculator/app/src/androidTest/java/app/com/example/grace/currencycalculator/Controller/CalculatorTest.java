package app.com.example.grace.currencycalculator.Controller;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;


import app.com.example.grace.currencycalculator.MainActivity;
import app.com.example.grace.currencycalculator.models.Expression;

public class CalculatorTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public CalculatorTest() {
        super(MainActivity.class);
    }

    Calculator calculator;
    ExpressionAnalyzer expressionAnalyzer = new ExpressionAnalyzer();


    public void setUp() throws Exception {

        super.setUp();
        calculator = new Calculator();
    }

    public void testComputeExpressionWhenExpressionNeedsNoPrecedenceRule() throws Exception {
        String expression = "12+6-3";
        assertEquals(15.0, calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeExpressionWhenExpressionNeedsPrecedenceRule() throws Exception {
        String expression = "12+6*4-9/3";
        assertEquals(33.0, calculator.compute(expressionAnalyzer.breakDownExpression(expression)));

    }

    public void testComputeExpressionWhenExpressionHasBracketWithAPrecedingOperator() throws Exception {
        String expression = "12+(6)*4";
        Expression expression1 = expressionAnalyzer.breakDownExpression(expression);
        assertEquals(36.0, calculator.compute(expression1));
    }

    public void testComputeExpressionWhenExpressionStartsWithAHigherPrecedenceOperator() throws Exception {
        String expression = "2*3+5";

        assertEquals(11.0, calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeExpressionWhenExpressionHasBracketWithNoPrecedingOperator() throws Exception {
        String expression = "12(3)";

        assertEquals(36.0, calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeExpressionWhenExpressionHasSubExpression() throws Exception {
        String expression = "2*4+(3+5*2-1)/2";

       assertEquals(14.0, calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeWhenExpressionStartsWithSubExpression() throws Exception {
        String expression = "(5+3)*6";
        assertEquals(48.0,calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeWhenExpressionHasOperatorsWithSamePrecedence() throws Exception {
        String expression = "1+4*2/4-1";
        assertEquals(2.0,calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeWhenExpressionHasOperatorsWithSamePrecedenceThroughOut() throws Exception {
        String expression = "2*3/2";
        assertEquals(3.0,calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }
    public void testComputeWhenExpressionStartsWithAUnaryOperator() throws Exception {
        String expression = "-2*3";
        assertEquals(-6.0,calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeWhenExpressionStartsWithSubExpressionFollwedByALowerPrecedence() {
        String expression = "(2+4)/2";
        assertEquals(3.0,calculator.compute(expressionAnalyzer.breakDownExpression(expression)));
    }

    public void testComputeWhenExpressionStartsWithACurrencyOperand() {
        String expression = "2NGN+5";

        Calculator calculator = new Calculator();
        ExpressionAnalyzer expressionAnalyzer = new ExpressionAnalyzer(getActivity(),"NGN");
        Expression expression1 = expressionAnalyzer.breakDownExpression(expression);
        assertEquals(7.0,calculator.compute(expression1));
    }

    public void testComputeWhenExpressionHasMixedCurrencies() {
        String expression = "4USD+5GBP-2";
        ExpressionAnalyzer expressionAnalyzer = new ExpressionAnalyzer(getActivity(),"NGN");
        Expression expression1 = expressionAnalyzer.breakDownExpression(expression);
        assertEquals(2287.665296, calculator.compute(expression1));
    }

    public void testComputeWhenExpressionIsIncomplete() {
        String expression = "2(-";
        Expression expression1 = expressionAnalyzer.breakDownExpression(expression);
        assertEquals(2, calculator.compute(expression1));
    }

//    public void testComputeWhenExprssionHasLowerPrecedenceAndHigherPrecedence() {
//        String expression = "6+6*3*3";
//        assertEquals(72,calculator.compute(expression));
//    }


}