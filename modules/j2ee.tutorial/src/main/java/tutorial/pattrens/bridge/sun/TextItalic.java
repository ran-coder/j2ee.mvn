package tutorial.pattrens.bridge.sun;

/**
 *  The RefinedAbstraction
 */
public class TextItalic extends Text {
    private TextImp imp;
    public TextItalic(String type) {
        imp = getTextImp(type);
    }
    public void drawText(String text) {
        System.out.println(text);
        System.out.println("The text is italic text!");
        imp.drawTextImp();
    }
}