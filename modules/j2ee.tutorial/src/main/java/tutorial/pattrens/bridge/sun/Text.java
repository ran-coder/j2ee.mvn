package tutorial.pattrens.bridge.sun;

/**
 *  The Abstract of Text 
 */
public abstract class Text  {
    public abstract void drawText(String text);
    protected TextImp getTextImp(String type) {
        if("Mac".equals(type)) {
            return new TextImpMac();
        } else if(type.equals("Linux")) {
            return new TextImpLinux();
        } else {
            return new TextImpMac();
        }
    }
}