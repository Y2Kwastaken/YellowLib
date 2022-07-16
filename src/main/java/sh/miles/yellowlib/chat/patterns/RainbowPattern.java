package sh.miles.yellowlib.chat.patterns;

import java.util.regex.Matcher;

import sh.miles.yellowlib.chat.Colorize;

public class RainbowPattern implements Pattern {
    

    private static final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<rainbow([0-9]{1,3})>(.*?)</rainbow>");

    /**
     * Applies a rainbow pattern to the provided String.    
     * Output might me the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with applied pattern
     */
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), Colorize.rainbow(content, Float.parseFloat(saturation)));
        }
        return string;
    }

}
