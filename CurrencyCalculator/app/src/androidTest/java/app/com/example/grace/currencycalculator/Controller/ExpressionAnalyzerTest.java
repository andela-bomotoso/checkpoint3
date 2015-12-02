package app.com.example.grace.currencycalculator.Controller;

import junit.framework.TestCase;

import java.util.Arrays;

import javax.xml.xpath.XPathExpressionException;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

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

    public void testGenerateExpressionString() throws Exception {
        Expression expression = new Expression();
        expression.setExpressionParts(Arrays.asList(
                new Operand("2"),
                new Operator("*"),
                new Operand("3"),
                new Operator("+"),
                new Operand("5")));

        assertEquals("2*3+5",expressionAnalyzer.generateExpressionString(expression));
    }

}