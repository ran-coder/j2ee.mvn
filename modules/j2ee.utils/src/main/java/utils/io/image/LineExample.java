package utils.io.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author yuanwei
 * @version ctreateTime:2011-11-10 上午9:24:36
 */
public class LineExample {
	public static void test(int width,int height,String path){
		File target=new File(path);
		if(target==null|| (!target.exists()&&!target.mkdirs()) ){
			return;
		}
		// 定义图像buffer
		BufferedImage bufferImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics=bufferImage.createGraphics();

		// 将图像填充为白色
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,width,height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		///Font font=new Font("Fixedsys",Font.PLAIN,fontHeight);
		// 设置字体。
		///graphics.setFont(font);

		// 画边框。
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0,0,width - 1,height - 1);

		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		graphics.setColor(Color.BLACK);
		/*for(int i=0;i < 160;i++){
			int x=random.nextInt(width);
			int y=random.nextInt(height);
			int xl=random.nextInt(12);
			int yl=random.nextInt(12);
			graphics.drawLine(x,y,x + xl,y + yl);
		}*/
		graphics.drawLine(0,10,width - 1,10);//线
		graphics.drawLine(10,0,10,height);//线
		graphics.drawLine(20,20,50,20);//线
		graphics.drawRect(20,30,20,20);//矩形
		graphics.drawOval(20,60,50,50);//园
		graphics.fillRect(20,150,20,20);//填充矩形
		graphics.fillOval(20,200,50,50);//填充圆形

		// 加上下面这句就能变的圆滑了 
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		try{
			ImageIO.write(bufferImage,"jpg",target);
		}catch(IOException e){
			e.printStackTrace();
		}
		graphics.dispose();
	}
	public static void main(String[] args) {
		test(300,300,"d:/server/java/img/test.jpg");
	}
}
