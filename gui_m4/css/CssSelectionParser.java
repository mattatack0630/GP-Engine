package gui_m4.css;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matthew on 6/30/17.
 */
public class CssSelectionParser
{
    public static final Pattern COMBO_SELECTORS = Pattern.compile(
            CssGeneralSiblingsSelector.NAME + "|" +
                    CssDirectChildSelector.NAME + "|" +
                    CssDecendentSelector.NAME + "|" +
                    CssOrSelector.NAME);

    public static final Pattern BASIC_SELECTORS = Pattern.compile(
            "[" + CssIdSelector.NAME + "]" + "|" +
                    "[" + CssClassSelector.NAME + "]");

    public static CssSelector parseSelector(String input)
    {
        CssSelector selector = null;

        int comboMatch = findLast(COMBO_SELECTORS, input);

        if (comboMatch != -1)
        {
            int i = comboMatch;
            CssSelector left = parseSelector(input.substring(0, i));
            CssSelector right = parseSelector(input.substring(i + 1, input.length()));
            selector = generateComboSelector(input.charAt(i), left, right);
        } else
        {
            int basicMatch = findLast(BASIC_SELECTORS, input);

            if (basicMatch != -1)
            {
                char type = input.charAt(basicMatch);
                String formattedInput = input.replace("" + type, "");
                selector = generateBasicSelector(type, formattedInput);
            } else
            {
                return new CssTypeSelector(input);
            }
        }

        return selector;
    }

    private static CssSelector generateBasicSelector(char type, String input)
    {
        CssSelector selector = null;
        switch (type)
        {
            case CssIdSelector.NAME:
                selector = new CssIdSelector(input);
                break;

            case CssClassSelector.NAME:
                selector = new CssClassSelector(input);
                break;
        }
        return selector;
    }

    private static CssSelector generateComboSelector(char type, CssSelector left, CssSelector right)
    {
        CssSelector selector = null;
        switch (type)
        {
            case CssGeneralSiblingsSelector.NAME:
                selector = new CssGeneralSiblingsSelector(left, right);
                break;
            case CssDirectChildSelector.NAME:
                selector = new CssDirectChildSelector(left, right);
                break;
            case CssDecendentSelector.NAME:
                selector = new CssDecendentSelector(left, right);
                break;
            case CssOrSelector.NAME:
                selector = new CssOrSelector(left, right);
                break;
        }
        return selector;
    }

    private static int findLast(Pattern pattern, String input)
    {
        StringBuilder revInput = new StringBuilder(input).reverse();
        Matcher matcher = pattern.matcher(revInput);

        if (matcher.find())
        {
            return input.length() - 1 - matcher.start();
        }

        return -1;
    }
}
