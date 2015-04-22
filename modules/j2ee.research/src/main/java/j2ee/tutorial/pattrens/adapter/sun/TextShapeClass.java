package j2ee.tutorial.pattrens.adapter.sun;

/**
 *  The Class Adapter in this sample 
 */
public class TextShapeClass  extends Text implements Shape {
    public TextShapeClass() {
    }
    public void draw() {
        System.out.println("Draw a shap ! Impelement Shape interface !");
    }
    public void border() {
        System.out.println("Set the border of the shap ! Impelement Shape interface !");
    }
}