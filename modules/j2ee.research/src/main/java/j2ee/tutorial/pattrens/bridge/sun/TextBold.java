package j2ee.tutorial.pattrens.bridge.sun;

public class TextBold extends Text {
    private TextImp imp;
    public TextBold(String type) {
        imp = getTextImp(type);
    }
    public void drawText(String text) {
        System.out.println(text);
        System.out.println("The text is bold text!");
        imp.drawTextImp();
    }
}