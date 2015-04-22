package tutorial.pattrens.bridge.sun;

/**
 *  The ConcreteImplementor
 */
public class TextImpLinux implements TextImp {
    public TextImpLinux() {
    }
    public void drawTextImp() {
        System.out.println("The text has a Linux style !");
    }
}