package tutorial.pattrens.bridge.sun;

/**
 *  A test client
 */
public class Test  {
    public Test() {
    }

    public static void main(String[] args) {
        Text myText = new TextBold("Mac");
        myText.drawText("=== A test String ===");

        myText =  new TextBold("Linux");
        myText.drawText("=== A test String ===");

        System.out.println("------------------------------------------");
        
        myText =  new TextItalic("Mac");
        myText.drawText("=== A test String ===");

        myText =  new TextItalic("Linux");
        myText.drawText("=== A test String ===");        
    }
}