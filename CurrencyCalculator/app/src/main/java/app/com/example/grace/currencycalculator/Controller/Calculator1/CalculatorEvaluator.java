package app.com.example.grace.currencycalculator.Controller.Calculator1;

import java.util.LinkedList;

/**
 * Created by GRACE on 12/7/2015.
 */
public class CalculatorEvaluator {
    transient LinkedList<Object> stack;

    void evaluate() {

        Object object = null;
        Double d1,d2;

        if(stack.size() > 1) {
            object = stack.pop();

            if(object instanceof CalculatorOperator) {
                CalculatorOperator operator = (CalculatorOperator) object;
                evaluate();
                d2 = (Double) stack.pop();
                d1 = (Double) stack.pop();
                stack.push(new Double(operator.evaluate(d1.doubleValue(), d2.doubleValue())));
            }
            else {
                evaluate();
                stack.push((object));
            }
        }
    }

    public double evaluate(LinkedList<Object> operations) {
        Double result;
        stack = new LinkedList<Object>(operations);
        evaluate();
        result = (Double)stack.pop();
        return result.doubleValue();
    }
}
