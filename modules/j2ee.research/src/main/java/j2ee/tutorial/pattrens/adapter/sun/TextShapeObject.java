package j2ee.tutorial.pattrens.adapter.sun;

/**
 *  The Object Adapter in this sample 
 */
public class TextShapeObject  implements Shape {
    private Text txt;
    public TextShapeObject(Text t) {
        txt = t;
    }
    public void draw() {
        System.out.println("Draw a shap ! Impelement Shape interface !");
    }
    public void border() {
        System.out.println("Set the border of the shap ! Impelement Shape interface !");
    }
    
    public void setContent(String str) {
        txt.setContent(str);
    }
    public String getContent() {
        return txt.getContent();
    }


}