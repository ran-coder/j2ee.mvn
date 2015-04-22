package utils.io.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-10 上午10:11:11
 *   
 */
public class ImageDrawUtil {
	public static void drawArray(String path,int width,int height,boolean[][] booleans){
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

		// 画边框。
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0,0,width - 1,height - 1);

		graphics.setColor(Color.BLACK);
		if(booleans!=null&&booleans.length>0){
			int x=0,y=0;
			for(int i=0,j=booleans.length;i<j;i++){
				x=0;
				if(booleans[i]!=null&&booleans[i].length>0)
					for(int m=0,n=booleans[i].length;m<n;m++){
						if(booleans[i][m]){
							graphics.fillRect(x,y,5,5);//填充矩形
						}else{
							graphics.drawRect(x,y,5,5);//矩形
						}
						x+=10;
					}
				y+=10;
			}
		}
		/*graphics.drawLine(0,10,width - 1,10);//线
		graphics.drawLine(10,0,10,height);//线
		graphics.drawLine(20,20,50,20);//线
		graphics.drawRect(20,30,20,20);//矩形
		graphics.drawOval(20,60,50,50);//园
		graphics.fillRect(20,150,20,20);//填充矩形
		graphics.fillOval(20,200,50,50);//填充圆形
		 */
		try{
			ImageIO.write(bufferImage,"jpeg",target);
		}catch(IOException e){
			e.printStackTrace();
		}
		graphics.dispose();
	}

	public static void drawArray(String path,boolean[][] ba){
		drawArray(path,400,400,ba);
	}

	public static void drawArray(String path,int width,int height,byte[][] bytes){
		boolean[][] booleans=null;
		if(bytes!=null&&bytes.length>0){
			booleans=new boolean[bytes.length][];;
			for(int i=0,j=bytes.length;i<j;i++){
				//booleans[i]=(bytes[i]!=0);
				if(bytes[i]!=null){
					booleans[i]=new boolean[bytes[i].length];
					for(int m=0,n=bytes[i].length;m<n;m++){
						booleans[i][m]=(bytes[i][m]!=0);
					}
				}
			}
			drawArray(path,width,height,booleans);
		}
	}
	public static void drawArray(String path,byte[][] bytes){
		drawArray(path,400,400,bytes);
	}
	public static void main(String[] args) {
		byte[][] bytes=new byte[][]{
				{0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,1,1,1,1,1,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0},
		};

		drawArray("d:/server/java/img/bytes.jpg",bytes);
	}
}
