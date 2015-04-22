package utils.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LaoZiZhuCaptchaServlet extends HttpServlet {
	private static final long	serialVersionUID	= -6937819310714713072L;
	//private static String		charsLong			= "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	private static String		charsShort			= "0123456789";
	private static String		chars				= charsShort;
	private static int		lineBack			= 35;
	private static int		lineFront			= 15;

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			int charsLength=chars.length();
		response.setHeader("ragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires",0);
		int width=70, height=20;
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics graphics=image.getGraphics();
		Random random=new Random();
		graphics.setColor(getRandColor(200,250));
		graphics.fillRect(0,0,width,height);
		graphics.setFont(new Font("Times New Roman",Font.ITALIC,height));
		graphics.setColor(getRandColor(160,200));
		for(int i=0;i < lineBack;i++){
			int x=random.nextInt(width);
			int y=random.nextInt(height);
			int xl=random.nextInt(12);
			int yl=random.nextInt(12);
			graphics.drawLine(x,y,x + xl,y + yl);
		}
		StringBuffer sRand=new StringBuffer();
		String[] fontNames={ "Times New Roman", "Arial", "Book antiqua", "" };
		for(int i=0;i < 4;i++){
			graphics.setFont(new Font(fontNames[random.nextInt(3)],Font.ITALIC,height));
			char rand=chars.charAt(random.nextInt(charsLength));
			sRand.append(rand);
			graphics.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));
			graphics.drawString(String.valueOf(rand),16 * i + random.nextInt(6) + 3,height - random.nextInt(4));
		}
		graphics.setColor(getRandColor(160,200));
		for(int i=0;i < lineFront;i++){
			int x=random.nextInt(width);
			int y=random.nextInt(height);
			int xl=random.nextInt(width);
			int yl=random.nextInt(width);
			graphics.drawLine(x,y,x + xl,y + yl);
		}
		request.getSession().setAttribute("Login_Image_Code",sRand.toString());
		graphics.dispose();

		OutputStream os=null;
		try{
			os=response.getOutputStream();
			ImageIO.write(image,"JPEG",os);
			os.flush();
		}catch(IOException e){
			
		}finally{
			if(os!=null){
				try{
					os.close();
				}catch(IOException e1){
				}
			}
		}

	}

	public static int getLineBack() {
		return lineBack;
	}

	public static void setLineBack(int lineBack) {
		LaoZiZhuCaptchaServlet.lineBack = lineBack;
	}

	public static int getLineFront() {
		return lineFront;
	}

	public static void setLineFront(int lineFront) {
		LaoZiZhuCaptchaServlet.lineFront = lineFront;
	}
}
