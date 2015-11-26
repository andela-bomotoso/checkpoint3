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
import app.com.example.grace.currencycalculator.models.SubExpression;

public class CalculatorTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public CalculatorTest() {
        super(MainActivity.class);
    }

    Calculator1 calculator1;
    Expression expression;
    Operand operand1,operand2,operand3,operand4,operand5,operand6;
    Operator plusOperator,minusOperator,timesOperator,divisionOperator;

    public void setUp() throws Exception {
        super.setUp();

        calculator1 = new Calculator1();

        expression =  new Expression();

        plusOperator = new Operator();
        plusOperator.setValue("+");

        minusOperator = new Operator();
        minusOperator.setValue("-");

        timesOperator = new Operator();
        timesOperator.setValue("*");

        divisionOperator = new Operator();
        divisionOperator.setValue("/");

        operand1= new Operand();
        operand1.setValue("12");

        operand2 = new Operand();
        operand2.setValue("6");

        operand3 = new Operand();
        operand3.setValue("4");

        operand4 = new Operand();
        operand4.setValue("9");

        operand5 = new Operand();
        operand5.setValue("3");

        operand6 = new Operand();
        operand6.setValue("(3)");
    }

    public void testFaicalBothering() {
        Expression expression = new Expression();
        expression.setExpressionParts(Arrays.asList(
                new Operand("7"),
                new Operator("-"),
                new Operand("4"),
                new Operator("+"),
                new Operand("2")
        ));

        assertEquals(5.0, calculator1.compute(expression));

    }
    public void testComputeExpressionWhenExpressionNeedsNoPrecedenceRule() throws Exception {

        expression.setExpressionParts(Arrays.asList(operand1, plusOperator, operand2, minusOperator, operand3));

        double expressionResult =  calculator1.compute(expression);

        assertEquals(14.0,expressionResult);

    }

    public void testComputeExpressionWhenExpressionNeedsPrecedenceRule() throws Exception {

        expression.setExpressionParts(Arrays.asList(operand1, timesOperator, operand2, plusOperator, operand3, minusOperator, operand4, divisionOperator, operand5));

        double expressionResult =  calculator1.compute(expression);

        assertEquals(73.0,expressionResult);
    }

    public void testComputeExpressionWhenExpressionHasBracketOperator() throws Exception {

        expression.setExpressionParts(Arrays.asList(operand1, timesOperator, operand6));

        double expressionResult =  calculator1.compute(expression);

        assertEquals(36.0,expressionResult);
    }

    public void testComputeExpressionWhenExpressionHasSubExpression() throws Exception {

        SubExpression subExpression = new SubExpression();

        Calculator calculator = new Calculator();

        Expression expression =  new Expression();

        Operator plusOperator = new Operator();
        plusOperator.setValue("+");

        Operator timesOperator = new Operator();
        timesOperator.setValue("*");

        Operator minusOperator = new Operator();
        minusOperator.setValue("-");

        Operator divisionOperator = new Operator();
        divisionOperator.setValue("/");

        Operand operand1 = new Operand();
        operand1.setValue("2");

        Operand subOperand1 = new Operand();
        subOperand1.setValue("3");

        Operator subOperator1 = new Operator();
        subOperator1.setValue("+");

        Operand subOperand2 = new Operand();
        subOperand2.setValue("9");

        subExpression.setExpressionParts(Arrays.asList(subOperand1,subOperator1,subOperand2));

        Operand operand2 = new Operand();
        operand2.setValue("(3+9)");

        expression.setExpressionParts(Arrays.asList(operand1, timesOperator, subExpression));

        double expressionResult =  calculator.compute(expression);

        assertEquals(24.0,expressionResult);

    }

    public void testIsExpressionValid() throws Exception {

    }
}