package gui_m4;

import com.sun.javafx.css.Declaration;
import com.sun.javafx.css.Rule;
import com.sun.javafx.css.Stylesheet;
import com.sun.javafx.css.parser.CSSParser;
import javafx.collections.ObservableList;
import org.w3c.dom.css.CSSStyleDeclaration;
import utils.ExtraUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by matthew on 7/11/17.
 */
public class CssFileParser
{
    public static GuiStyle parseFile(String fileName)
    {
        String styleString = ExtraUtils.getFileAsString(fileName);

        Stylesheet styleSheet = null;
        CSSParser parser = new CSSParser();

        styleSheet = parser.parse(styleString);

        for (Rule rule : styleSheet.getRules())
        {
            String selector = rule.getSelectors().get(0).toString().replace("*", "");
            ObservableList<Declaration> declarations = rule.getDeclarations();
            for(Declaration declaration : declarations)
            {
                System.out.println(declaration.getProperty());
                System.out.println(declaration.getParsedValue().getValue());
            }
        }

        return null;
    }
}
