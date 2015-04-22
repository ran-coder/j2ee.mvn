package utils.io.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/******************************************************************* 
 * 该JavaBean可以直接在其他Java应用程序中调用，实现屏幕的"拍照" <br> 
 * This JavaBean is used to snapshot the GUI in a <br> 
 * Java application! You can embeded <br> 
 * it in to your java application source code, and us <br> 
 * it to snapshot the right GUI of the application <br> 
 *  
 * @see javax.ImageIO 
 * @author liluqun (<a href="mailto:liluqun@263.net">liluqun@263.net</a>) JAVA世纪网(java2000.net) 
 * @version 1.0 
 *****************************************************/

public class GuiCamera {
	private String	fileName;															// 文件的前缀  
	private String	defaultName			= "GuiCamera";
	static int		serialNum			= 0;
	private String	imageFormat;														// 图像文件的格式  
	private String	defaultImageFormat	= "png";
	Dimension		d					= Toolkit.getDefaultToolkit().getScreenSize();

	/**************************************************************** 
	 * 默认的文件前缀为GuiCamera，文件格式为PNG格式 <br> 
	 * The default construct will use the default <br> 
	 * Image file surname "GuiCamera", <br> 
	 * and default image format "png" 
	 ****************************************************************/
	public GuiCamera() {
		fileName = defaultName;
		imageFormat = defaultImageFormat;

	}

	/**************************************************************** 
	 * @param s 
	 *          the surname of the snapshot file <br> 
	 * @param format 
	 *          the format of the image file, <br> 
	 *          it can be "jpg" or "png" <br> 
	 *          本构造支持JPG和PNG文件的存储 
	 ****************************************************************/
	public GuiCamera(String path, String format) {
		fileName = path;
		imageFormat = format;
	}

	/**************************************************************** 
	 * 对屏幕进行拍照 <br> 
	 * snapShot the Gui once 
	 ****************************************************************/
	public void snapShot() {

		try {
			// 拷贝屏幕到一个BufferedImage对象screenshot  
			BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(),
					(int) d.getHeight()));
			serialNum++;
			// 根据文件前缀变量和文件格式变量，自动生成文件名  
			String name = fileName + getNow() + "." + imageFormat;
			File f = new File(name);
			System.out.print("Save   File   " + name);
			// 将screenshot对象写入图像文件  
			ImageIO.write(screenshot, imageFormat, f);
			System.out.println("\n..Finished!\n");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	public static String getNow()	{
		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
	}

	public static void main(String[] args) {
		GuiCamera cam = new GuiCamera("d:/server/java/photograph/", "png");//       
		cam.snapShot();
	}
}