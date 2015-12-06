package app.com.example.grace.currencycalculator.Controller.Calculator1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private ArrayList<TokenType> listOfTokens;
    private Pattern separator;
    private Matcher matcher;

    public Tokenizer(Collection<TokenType> typesOfTokens, String separator) {
        listOfTokens = new ArrayList<TokenType>(typesOfTokens);
        this.separator = Pattern.compile(separator);
        matcher = this.separator.matcher("");
    }

    public void setInput(CharSequence input) {
        matcher.reset(input);
    }

    public Token getNextToken() {
        Iterator<TokenType> iterator;
        Token returnToken = null;
        iterator = listOfTokens.iterator();
        while (iterator.hasNext()) {
            TokenType tokenType = iterator.next();
            matcher.usePattern(tokenType.getPattern());

            if (matcher.lookingAt()) {
                Token token = new Token(tokenType.getType().toString(), matcher.group());
                matcher.region(matcher.end(), matcher.regionEnd());
                returnToken = token;
            }
        }
        if (matcher.hitEnd()) {
            returnToken = null;
        }
        return returnToken;
    }
}

