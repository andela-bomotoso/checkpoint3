package app.com.example.grace.currencycalculator;

import android.test.ActivityInstrumentationTestCase2;


import java.util.ArrayList;
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

    Calculator calculator;
    Expression expression;

    public void setUp() throws Exception {
        super.setUp();

        calculator = new Calculator();

        expression = new Expression();
    }

    public void testComputeExpressionWhenExpressionNeedsNoPrecedenceRule() throws Exception {

        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("+"),
                new Operand("6"),
                new Operator("-"),
                new Operand("3")
        ));
        assertEquals(15.0, calculator.compute(expression));
    }

    public void testComputeExpressionWhenExpressionNeedsPrecedenceRule() throws Exception {

        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("+"),
                new Operand("6"),
                new Operator("*"),
                new Operand("4"),
                new Operator("-"),
                new Operand("9"),
                new Operator("/"),
                new Operand("3")
        ));
        assertEquals(33.0, calculator.compute(expression));

    }

    public void testComputeExpressionWhenExpressionHasBracketWithAPrecedingOperator() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("+"),
                new Operand("(6)"),
                new Operator("*"),
                new Operand("4")));

        double expressionResult = calculator.compute(expression);

        assertEquals(36.0, expressionResult);
    }

    public void testComputeExpressionWhenExpressionHasBracketWithNoPreceedingOperator() throws Exception {
        List<ExpressionPart> expressionParts = new ArrayList<>();
        expressionParts.add(new Operand("12"));
        expressionParts.add(new Operand("(3)"));
        expression.setExpressionParts(expressionParts);

        assertEquals(36.0, calculator.compute(expression));
    }

    public void testComputeExpressionWhenExpressionHasSubExpression() throws Exception {

    }

    public void testIsExpressionValidWhenExpressionIsValid() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("+"),
                new Operand("(6)"),
                new Operator("-"),
                new Operand("4")));
        assertTrue(calculator.isValid(expression));
    }

    public void testIsExpressionValidWhenExpressionStartsWithOperator() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operator("+"),
                new Operator("12"),
                new Operand("-"),
                new Operand("4")));

        assertFalse(calculator.isValid(expression));
    }

    public void testIsExpressionValidWhenExpressionHasDoubleOperator() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operator("12"),
                new Operand("-"),
                new Operator("+"),
                new Operand("2")));

        assertFalse(calculator.isValid(expression));
    }

    public void testIsExpressionValidWhenExpressionHasBracketMismatch() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("-"),
                new Operand("2"),
                new Operator("+"),
                new Operand("(12*6+6")));
        assertFalse(calculator.isValid(expression));
    }

    public void testIsExpressionValidWhenExpressionHasDivisionByZeroError() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("/"),
                new Operand("0"),
                new Operator("+"),
                new Operand("1")));
        assertFalse(calculator.isValid(expression));
    }

    public void testIsExpressionValidWhenExpressionHasOperandWithDoubleDots() throws Exception {
        expression.setExpressionParts(Arrays.asList(
                new Operand("12"),
                new Operator("/"),
                new Operand("5.2."),
                new Operator("+"),
                new Operand("1")));
        assertFalse(calculator.isValid(expression));
    }
}