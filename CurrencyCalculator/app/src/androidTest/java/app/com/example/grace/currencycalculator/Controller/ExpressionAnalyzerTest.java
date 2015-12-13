package app.com.example.grace.currencycalculator.controller;

import junit.framework.TestCase;

import app.com.example.grace.currencycalculator.models.Expression;

public class ExpressionAnalyzerTest extends TestCase {

    Validator validator;
    ExpressionAnalyzer expressionAnalyzer;

    public void setUp() throws Exception {
        super.setUp();
        validator = new Validator();
        expressionAnalyzer = new ExpressionAnalyzer();

    }

    public void testBreakDownExpression() throws Exception {
        String expressionString = "2*3+5";
        Expression expression = expressionAnalyzer.breakDownExpression(expressionString);
        assertEquals("2",expression.getExpressionParts().get(0).getValue());
        assertEquals("*",expression.getExpressionParts().get(1).getValue());
        assertEquals("3", expression.getExpressionParts().get(2).getValue());
        assertEquals("+",expression.getExpressionParts().get(3).getValue());
        assertEquals("5",expression.getExpressionParts().get(4).getValue());

    }

    public void testBreakDownExpressionWhenExpressionHasSubExpression() throws Exception {
        String expressionString = "2*3+(6+4*5)+26";
        Expression expression = expressionAnalyzer.breakDownExpression(expressionString);
        assertEquals("2",expression.getExpressionParts().get(0).getValue());
        assertEquals("*",expression.getExpressionParts().get(1).getValue());
        assertEquals("3", expression.getExpressionParts().get(2).getValue());
        assertEquals("+",expression.getExpressionParts().get(3).getValue());
        assertEquals("(6+4*5)",expression.getExpressionParts().get(4).getValue());
        assertEquals("+",expression.getExpressionParts().get(5).getValue());
        assertEquals("26",expression.getExpressionParts().get(6).getValue());
    }

    public void testBreakDownWhenExpressionStartsWithSubExpression() throws Exception {
        String expressionString = "(3+5)3";
        Expression expression = expressionAnalyzer.breakDownExpression(expressionString);
        assertEquals("(3+5)",expression.getExpressionParts().get(0).getValue());
        assertEquals("*",expression.getExpressionParts().get(1).getValue());
        assertEquals("3",expression.getExpressionParts().get(2).getValue());
    }

}