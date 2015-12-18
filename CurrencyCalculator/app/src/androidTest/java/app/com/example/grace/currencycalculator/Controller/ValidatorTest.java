package app.com.example.grace.currencycalculator.controller;

import junit.framework.TestCase;

public class ValidatorTest extends TestCase {

    Validator validator;

    public void setUp() throws Exception {
        super.setUp();
        validator = new Validator();
    }

    public void testValidateOperatorWhenThePreviousExpressionPartIsAnOperator () throws Exception {
        String expression = "3+2+";
        validator.setExpression(expression);
        assertEquals("3+2*",validator.validateOperator('*'));
    }

    public void testValidateOperatorWhenThePreviousExpressionPartIsNotAnOperator() throws Exception {
        String expression = "13";
        validator.setExpression(expression);
        assertEquals("13*", validator.validateOperator('*'));
    }

    public void testDivisionByZeroWhenKeyPressGeneratesDivisionByZeroException() throws Exception {
        String expression = "5/";
        validator.setExpression(expression);
        assertTrue(validator.isDivisionByZero('0'));
    }

    public void testDivisionByZeroWhenKeyPressDoesNotGeneratesDivisionByZeroException() throws Exception {
        String expression = "5+3";
        validator.setExpression(expression);
        assertFalse(validator.isDivisionByZero('0'));
    }

    public void testRepeatedZerosWhenZerosAreRepeated() throws Exception {
        String expression = "5+0";
        validator.setExpression(expression);
        assertTrue(validator.isRepeatedZeros('0'));
    }
    public void testRepeatedZerosWhenZerosAreRepeatedButIsValid() throws Exception {
        String expression = "5+30";
        validator.setExpression(expression);
        assertFalse(validator.isRepeatedZeros('0'));
    }
    public void testRepeatedZerosWhenZerosAreNotRepeated() throws Exception {
        String expression = "32+3";
        validator.setExpression(expression);
        assertFalse(validator.isRepeatedZeros('0'));
    }
    public void testRepeatedDecimalsWhenThereAreRepeatedDecimalsInAnOperand() throws Exception {
        String expression = "34+56+78.90";
        validator.setExpression(expression);
        assertTrue(validator.isRepeatedDecimal('.'));
   }
    public void testRepeatedDecimalsWhenThereAreNoRepeatedDecimalsInAnOperand() throws Exception {
        String expression = "34+56+78";
        validator.setExpression(expression);
        assertFalse(validator.isRepeatedDecimal('.'));
    }

    public void testMismatchedBracketsWhenBracketsAreMismatched() throws Exception {
        String expression = "4+5)";
        validator.setExpression(expression);
        assertTrue(validator.isMismatchedBrackets(')'));
    }

    public void testMismatchedBracketsWhenBracketsAreNotMismatched() throws Exception {
        String expression = "4+5*(3*2)";
        validator.setExpression(expression);
        assertFalse(validator.isMismatchedBrackets(')'));
    }

    public void testOpeningBracketMatchesClosingBracketWhenNumberOfBracketsDoNotMatch() {
        String expression = "(8(6)";
        validator.setExpression(expression);
        assertTrue(validator.openingBracketsDoesNotMatchClosingBrackets('('));
    }

    public void testOpeningBracketMatchesClosingBracketWhenNumberOfBracketsMatch() {
        String expression = "(8(6)";
        validator.setExpression(expression);
        assertFalse(validator.openingBracketsDoesNotMatchClosingBrackets(')'));
    }

    public void testCurrencyFollowingAnOperandWhenACurrencyFollowsAtBeginningAnExpressionOperand(){
        String expression = "2";
        validator.setExpression(expression);
        assertTrue(validator.isLastCharacterADigit());
    }

    public void testCurrencyFollowingAnOperandWhenACurrencyFollowsAnOperand(){
        String expression = "2USD+3";
        validator.setExpression(expression);
        assertTrue(validator.isLastCharacterADigit());
    }

    public void testCurrencyFollowingAnOperandWhenACurrencyFollowsDoesNotFollowAnOperand(){
        String expression = "2USD";
        validator.setExpression(expression);
        assertFalse(validator.isLastCharacterADigit());
    }

    public void testOperatorAfterOpeningBracketWhenOperatorFollowsOpeningBracket() {
        String expression = "(2+3)*(";
        validator.setExpression(expression);
        assertTrue(validator.operatorAfterOpeningBracket('/'));
    }

    public void testOperatorAfterOpeningBracketWhenOperatorDoesNotFollowOpeningBracket() {
        String expression = "(2+3)*(3";
        validator.setExpression(expression);
        assertFalse(validator.operatorAfterOpeningBracket('/'));
    }

    public void testOperatorsAfterOpeningParenthesis() {
        String expression = "(2+3)+(";
        validator.setExpression(expression);
        assertTrue(validator.operatorAfterOpeningBracket('+'));
    }

}