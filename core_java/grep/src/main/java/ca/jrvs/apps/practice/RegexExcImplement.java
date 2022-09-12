package ca.jrvs.apps.practice;

import java.util.regex.Pattern;

public class RegexExcImplement implements RegexExc{
    public static void main(String[] args){

    };

    public boolean matchJpeg(String filename) {
        // [^\\s]+ represents the string must contain at least one character.
        // \\. Represents the string should follow by a dot(.).
        // (?i) represents the string ignore the case-sensitive.
        String jpg = "([^\\s]+(\\.(?i)(jpg))$)";
        String jpeg = "([^\\s]+(\\.(?i)(jpeg))$)";
        return Pattern.matches(jpg, filename) || Pattern.matches(jpeg, filename);
    };

    public boolean matchIp(String ip) {
        String ipPattern = "[0-999]%\\.[0-999]%\\.[0-999]%\\.[0-999]%";
        return Pattern.matches(ipPattern, ip);
    };

    public boolean isEmptyLine(String line) {
        String EmptyPattern = "^\\s*$";
        return Pattern.matches(EmptyPattern, line);
    };

}
