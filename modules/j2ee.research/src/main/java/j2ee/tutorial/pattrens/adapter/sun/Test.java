package j2ee.tutorial.pattrens.adapter.sun;

public class Test {
    public static void main(String[] args) {
        Text myText = new Text();
        TextShapeObject myTextShapeObject = new TextShapeObject(myText);
        myTextShapeObject.draw();
        myTextShapeObject.border();
        myTextShapeObject.setContent("A test text !");
        System.out.println("The content in Text Shape is :" + myTextShapeObject.getContent());
        
    }
}
