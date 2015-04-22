package utils.io.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class IMG extends JFrame {
	private static final long	serialVersionUID	= 1L;

	public static void main(String[] args) {
		IMG t = new IMG();
		t.setLocation(100, 100);
		t.setSize(500, 400);
		t.getContentPane().setBackground(Color.green);
		t.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D e = new Ellipse2D.Double(10, 50, 70, 70);
		// 加上下面这句就能变的圆滑了 
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(Color.RED);
		g2.draw(e);
		//g2.fill(e);
	}
}
