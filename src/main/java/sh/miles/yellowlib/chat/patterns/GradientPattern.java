package sh.miles.yellowlib.chat.patterns;

import java.awt.Color;
import java.util.regex.Matcher;

import sh.miles.yellowlib.chat.Colorize;

/**
 * Represents a gradient color pattern which can be applied to a String.
 */
public class GradientPattern implements Pattern {

    private static final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<gradient:([0-9A-Fa-f]{6})>(.*?)</gradient:([0-9A-Fa-f]{6})>");

    /**
     * Applies a gradient pattern to the provided String.
     * Output might me the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with applied pattern
     */
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String start = matcher.group(1);
            String end = matcher.group(3);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), Colorize.color(content, new Color(Integer.parseInt(start, 16)), new Color(Integer.parseInt(end, 16))));
        }
        return string;
    }

}
