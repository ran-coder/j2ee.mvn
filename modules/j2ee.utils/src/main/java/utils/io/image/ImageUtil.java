package utils.io.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.AssertUtil;

public class ImageUtil {
	private static Logger log=LoggerFactory.getLogger(ImageUtil.class);
	public final static String PNG="png";
	public final static String GIF="gif";
	public final static String JPG="jpg";
	public final static String BMP="bmp";
	/**
	 * 按源图比例缩放，生成高质量缩略图
	 * @param imageFile 源图文件
	 * @param thumbnailFile 要保存到的缩略图文件
	 * @param maxWidth 缩略图的最大宽度
	 * @param maxHeight 缩略图的最大高度
	 * @throws IOException 读取源图imageFile出错，或者用户指定的thumbnailFile无法被创建时
	 */
	public static void scaleImage(String src, String target, int maxWidth, int maxHeight) throws IOException {
		if(src == null){ throw new IllegalArgumentException("src connot be null!"); }
		if(target == null){ throw new IllegalArgumentException("target connot be null!"); }
		File srcFile=new File(src);
		File targetFile=new File(target);
		scaleImage(srcFile,targetFile,maxWidth,maxHeight);
	}

	public static void scaleImage(File srcFile, File targetFile, int maxWidth, int maxHeight) throws IOException {
		if(srcFile == null){ throw new IllegalArgumentException("imageFile connot be null!"); }
		if(targetFile == null){ throw new IllegalArgumentException("thumbnailFile connot be null!"); }

		// System.out.println("srcFile:"+srcFile.getAbsolutePath());
		if(!srcFile.canRead()){ throw new IllegalArgumentException("srcFile can not read!"); }
		if(!srcFile.canWrite()){ throw new IllegalArgumentException("srcFile can not write!"); }
		if(!srcFile.exists()){ throw new IllegalArgumentException("srcFile is not exists!"); }
		if(!targetFile.exists() && !targetFile.createNewFile()){ throw new IllegalArgumentException("targetFile is not exists!"); }

		try{
			scaleImage(ImageIO.read(srcFile),targetFile,maxWidth,maxHeight);
		}catch(IOException e){
			throw new IOException("Connot create thumbnail, Please check 'imageFile' or 'targetFile'!");
		}
	}

	public static void scaleImage(InputStream input, File targetFile, int maxWidth, int maxHeight) throws IOException {
		if(input == null){ throw new IllegalArgumentException("input connot be null!"); }
		if(targetFile == null){ throw new IllegalArgumentException("thumbnailFile connot be null!"); }
		// System.out.println("srcFile:"+srcFile.getAbsolutePath());
		if(!targetFile.exists() && !targetFile.createNewFile()){ throw new IllegalArgumentException("targetFile is not exists!"); }

		try{
			scaleImage(ImageIO.read(input),targetFile,maxWidth,maxHeight);
		}catch(IOException e){
			throw new IOException("Connot create thumbnail, Please check 'imageFile' or 'targetFile'!");
		}finally{
			if(input != null) input.close();
		}
	}

	public static void scaleImage(BufferedImage bufferedImage, File targetFile, int maxWidth, int maxHeight) throws IOException {
		if(bufferedImage == null){ throw new IllegalArgumentException("bufferedImage connot be null!"); }
		if(targetFile == null){ throw new IllegalArgumentException("targetFile connot be null!"); }
		if(maxWidth <= 0){ throw new IllegalArgumentException("maxWidth must > 0"); }
		if(maxHeight <= 0){ throw new IllegalArgumentException("maxHeight must > 0"); }

		/* 源图宽和高 */
		int imageWidth=bufferedImage.getWidth();
		int imageHeight=bufferedImage.getHeight();
		double scaleZ=0;
		if(maxWidth >= imageWidth || maxHeight >= imageHeight){
			// return;
		}else{
			/* 按比例缩放图像 */
			scaleZ=(double)imageWidth / imageHeight;
			if(scaleZ > 0){
				imageWidth=maxWidth;
				imageHeight=(int)(maxWidth / scaleZ);
			}else{
				imageWidth=(int)(maxHeight * scaleZ);
				imageHeight=maxHeight;
			}
		}
		System.out.println("scaleZ=" + scaleZ + ",(" + maxWidth + "," + maxHeight + "),(" + imageWidth + "," + imageHeight + ")");

		/* 根据源图和缩略图宽高生成一张图片？ */
		/*ImageFilter filter=new java.awt.image.AreaAveragingScaleFilter(imageWidth,imageHeight);
		ImageProducer producer=new FilteredImageSource(bufferedImage.getSource(),filter);
		Image newImage=Toolkit.getDefaultToolkit().createImage(producer);
		ImageIcon imageIcon=new ImageIcon(newImage);
		Image scaleImage=imageIcon.getImage();*/

		BufferedImage targetBufferedImage=new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d=targetBufferedImage.createGraphics();
		//g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.drawImage(scaleImage,0,0,null);//Image.SCALE_SMOOTH
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		g2d.drawImage(bufferedImage.getScaledInstance(imageWidth, imageHeight,  Image.SCALE_SMOOTH), 0, 0,  null); 
		g2d.dispose();

		ImageIO.write(targetBufferedImage,"jpg",targetFile);

	}

	public static void scaleImage(String src, String target, double scale) throws IOException {
		if(src == null){ throw new IllegalArgumentException("imageFile connot be null!"); }
		if(target == null){ throw new IllegalArgumentException("thumbnailFile connot be null!"); }
		File srcFile=new File(src);
		File targetFile=new File(target);

		try{
			BufferedImage image=ImageIO.read(srcFile);

			/* 源图宽和高 */
			int imageWidth=(int)(image.getWidth() * scale);
			int imageHeight=(int)(image.getHeight() * scale);

			/* 根据源图和缩略图宽高生成一张图片？ */
			ImageFilter filter=new java.awt.image.AreaAveragingScaleFilter(imageWidth,imageHeight);
			ImageProducer producer=new FilteredImageSource(image.getSource(),filter);
			Image newImage=Toolkit.getDefaultToolkit().createImage(producer);
			ImageIcon imageIcon=new ImageIcon(newImage);
			Image scaleImage=imageIcon.getImage();

			BufferedImage thumbnail=new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d=thumbnail.createGraphics();
			g2d.drawImage(scaleImage,0,0,null);
			g2d.dispose();

			ImageIO.write(thumbnail,"jpeg",targetFile);

		}catch(IOException e){
			throw new IOException("Connot create thumbnail, Please check 'imageFile' or 'thumbnailFile'!");
		}
	}

	/**
	 * 旋转图像。
	 * @param bufferedImage 图像。
	 * @param degree 旋转角度。
	 * @return 旋转后的图像。
	 */
	public static BufferedImage rotateImage(final BufferedImage bufferedImage, final int degree) {
		int width=bufferedImage.getWidth();
		int height=bufferedImage.getHeight();
		int type=bufferedImage.getColorModel().getTransparency();

		BufferedImage image=new BufferedImage(width,height,type);
		Graphics2D graphics2D=image.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		graphics2D.rotate(Math.toRadians(degree),(double)width / 2,(double)height / 2);
		graphics2D.drawImage(bufferedImage,0,0,null);

		try{
			return image;
		}finally{
			if(graphics2D != null){
				graphics2D.dispose();
			}
		}
	}

	/**
	 * 将图像按照指定的比例缩放。 比如需要将图像放大20%，那么调用时scale参数的值就为20；如果是缩小，则scale值为-20。
	 * @param bufferedImage 图像。
	 * @param scale 缩放比例。
	 * @return 缩放后的图像。
	 */
	public static BufferedImage resizeImageScale(final BufferedImage bufferedImage, final int scale) {
		if(scale == 0){ return bufferedImage; }

		int width=bufferedImage.getWidth();
		int height=bufferedImage.getHeight();

		int newWidth=0;
		int newHeight=0;

		double nowScale=(double)Math.abs(scale) / 100;
		if(scale > 0){
			newWidth=(int)(width * (1 + nowScale));
			newHeight=(int)(height * (1 + nowScale));
		}else if(scale < 0){
			newWidth=(int)(width * (1 - nowScale));
			newHeight=(int)(height * (1 - nowScale));
		}

		return resizeImage(bufferedImage,newWidth,newHeight);
	}

	/**
	 * 将图像缩放到指定的宽高大小。
	 * @param bufferedImage 图像。
	 * @param width 新的宽度。
	 * @param height 新的高度。
	 * @return 缩放后的图像。
	 */
	public static BufferedImage resizeImage(final BufferedImage bufferedImage, final int width, final int height) {
		//int type=bufferedImage.getColorModel().getTransparency();
		BufferedImage image=new BufferedImage(width,height,bufferedImage.getColorModel().getTransparency());

		Graphics2D graphics2D=image.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		//graphics2D.drawImage(bufferedImage,0,0,width,height,0,0,bufferedImage.getWidth(),bufferedImage.getHeight(),null);
		//更清晰
		graphics2D.drawImage(bufferedImage.getScaledInstance(width, height,  Image.SCALE_SMOOTH), 0, 0,  null);

		try{
			return image;
		}finally{
			if(graphics2D != null){
				graphics2D.dispose();
			}
		}
	}

	/**
	 * 将图像水平翻转。
	 * @param bufferedImage 图像。
	 * @return 翻转后的图像。
	 */
	public static BufferedImage flipImage(final BufferedImage bufferedImage) {
		int width=bufferedImage.getWidth();
		int height=bufferedImage.getHeight();
		int type=bufferedImage.getColorModel().getTransparency();

		BufferedImage image=new BufferedImage(width,height,type);
		Graphics2D graphics2D=image.createGraphics();
		graphics2D.drawImage(bufferedImage,0,0,width,height,width,0,0,height,null);

		try{
			return image;
		}finally{
			if(graphics2D != null){
				graphics2D.dispose();
			}
		}
	}

	/**
	 * 获取图片的类型。如果是 gif、jpg、png、bmp 以外的类型则返回null。
	 * @param imageBytes 图片字节数组。
	 * @return 图片类型。
	 * @throws java.io.IOException IO异常。
	 */
	public static String getImageType(final byte[] imageBytes) throws IOException {
		ByteArrayInputStream input=new ByteArrayInputStream(imageBytes);
		ImageInputStream imageInput=ImageIO.createImageInputStream(input);
		Iterator<ImageReader> iterator=ImageIO.getImageReaders(imageInput);
		String type=null;
		if(iterator.hasNext()){
			ImageReader reader=iterator.next();
			type=reader.getFormatName().toUpperCase();
		}

		try{
			return type;
		}finally{
			if(imageInput != null){
				imageInput.close();
			}
		}
	}

	/**
	 * 验证图片大小是否超出指定的尺寸。未超出指定大小返回true，超出指定大小则返回false。
	 * @param imageBytes 图片字节数组。
	 * @param width 图片宽度。
	 * @param height 图片高度。
	 * @return 验证结果。未超出指定大小返回true，超出指定大小则返回false。
	 * @throws java.io.IOException IO异常。
	 */
	public static boolean checkImageSize(final byte[] imageBytes, final int width, final int height) throws IOException {
		BufferedImage image=byteToImage(imageBytes);
		int sourceWidth=image.getWidth();
		int sourceHeight=image.getHeight();
		if(sourceWidth > width || sourceHeight > height){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 将图像字节数组转化为BufferedImage图像实例。
	 * @param imageBytes 图像字节数组。
	 * @return BufferedImage图像实例。
	 * @throws java.io.IOException IO异常。
	 */
	public static BufferedImage byteToImage(final byte[] imageBytes) throws IOException {
		ByteArrayInputStream input=new ByteArrayInputStream(imageBytes);
		BufferedImage image=ImageIO.read(input);

		try{
			return image;
		}finally{
			if(input != null){
				input.close();
			}
		}
	}

	/**
	 * 将BufferedImage持有的图像转化为指定图像格式的字节数组。
	 * @param bufferedImage 图像。
	 * @param formatName 图像格式名称。
	 * @return 指定图像格式的字节数组。
	 * @throws java.io.IOException IO异常。
	 */
	public static byte[] imageToByte(final BufferedImage bufferedImage, final String formatName) throws IOException {
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		ImageIO.write(bufferedImage,formatName,output);

		try{
			return output.toByteArray();
		}finally{
			if(output != null){
				output.close();
			}
		}
	}

	/**
	 * 将图像以指定的格式进行输出，调用者需自行关闭输出流。
	 * @param bufferedImage 图像。
	 * @param output 输出流。
	 * @param formatName 图片格式名称。
	 * @throws java.io.IOException IO异常。
	 */
	public static void writeImage(final BufferedImage bufferedImage, final OutputStream output, final String formatName) throws IOException {
		ImageIO.write(bufferedImage,formatName,output);
	}

	/**
	 * 将图像以指定的格式进行输出，调用者需自行关闭输出流。
	 * @param imageBytes 图像的byte数组。
	 * @param output 输出流。
	 * @param formatName 图片格式名称。
	 * @throws java.io.IOException IO异常。
	 */
	public static void writeImage(final byte[] imageBytes, final OutputStream output, final String formatName) throws IOException {
		BufferedImage image=byteToImage(imageBytes);
		ImageIO.write(image,formatName,output);
	}

	/** 使图像透明 */
	public static BufferedImage translucent(BufferedImage target){
		Graphics2D g2d = target.createGraphics();  
		target = g2d.getDeviceConfiguration().createCompatibleImage(target.getWidth(), target.getHeight(),Transparency.TRANSLUCENT);  
		g2d.dispose();
		return target;
	}
	/************************ 图片合并 ************************/
	public static void merge(String output, String type,String... pics) {
		int len = (pics==null?0:pics.length);
		AssertUtil.isTrue( len>1,"pics len < 1:"+len);

		int outputHeight = 0,outputWidth=0;
		int maxHeight = 0,maxWidth=0;
		BufferedImage[] bufferedImages = new BufferedImage[len];

		int[][] ImageDatas = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				bufferedImages[i] = ImageIO.read(new File(pics[i]));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			int width = bufferedImages[i].getWidth();outputWidth=Math.max(width,outputWidth);
			int height = bufferedImages[i].getHeight();outputHeight += height;
			maxHeight=Math.max(width,maxHeight);
			maxWidth=Math.max(height,maxWidth);
			ImageDatas[i] = new int[width * height];// 从图片中读取RGB
			ImageDatas[i] = bufferedImages[i].getRGB(0, 0, width, height,ImageDatas[i], 0, width);

			log.debug(i+":width-"+width+",height-"+height+",outputWidth-"+outputWidth+",outputHeight-"+outputHeight);
		}
		//for(int i = 0; i < bufferedImages.length; i++)outputHeight += bufferedImages[i].getHeight();

		AssertUtil.isTrue(outputHeight > 1,"outputHeight < 1");

		// 生成新图片
		BufferedImage saveImage = new BufferedImage(outputWidth,outputHeight, BufferedImage.TYPE_INT_RGB);
		saveImage=translucent(saveImage);
		int startY=0;
		int startX=0;
		int offset=0;
		for (int i = 0; i < len; i++) {
			//saveImage.setRGB(startX,startY,dst_width,bufferedImages[i].getHeight(),ImageDatas[i],offset, dst_width);
			saveImage.setRGB(startX,startY,bufferedImages[i].getWidth(),bufferedImages[i].getHeight(),ImageDatas[i],offset, bufferedImages[i].getWidth());
			startY += bufferedImages[i].getHeight();
		}
		try {
			ImageIO.write(saveImage, type, new File(output));// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public static void combine(String output,String picDir,String type,final boolean vertical,int imageOffset,String... pics){
		if(pics==null)return;
		for(int i=0,j=pics.length;i<j;i++){
			pics[i]=picDir+pics[i];
		}
		combine(output,type,vertical,imageOffset,pics);
	}
	public static void combine(String output,String picDir,String type,final boolean vertical,int imageOffset){
		if(picDir==null)return;
		File picFile=new File(picDir);
		combine(output,picDir,type,vertical,imageOffset,picFile.list());
	}
	public static void autoCombine(String output,String picDir,String type,final boolean vertical,int imageOffset,String... pics){
		if(pics==null)return;
		for(int i=0,j=pics.length;i<j;i++){
			pics[i]=picDir+pics[i];
		}
		autoCombine(output,type,vertical,imageOffset,pics);
	}
	public static void autoCombine(String output,String picDir,String type,final boolean vertical,int imageOffset){
		if(picDir==null)return;
		File picFile=new File(picDir);
		autoCombine(output,picDir,type,vertical,imageOffset,picFile.list());
	}
	/**
	 * 图像合并成一个
	 * .i_1101{ background-position: 0 -959px; width: 88px; height: 56px; }
	 * <br/>.i_1101{ background-position: x  y; width: 88px; height: 56px; }
	 * @param output
	 * @param type jpg png 
	 * @param vertical 垂直合并
	 * @param imageOffset 合并图片间隔
	 * @param pics
	 */
	public static void combine(String output,String type,final boolean vertical,int imageOffset,String... pics){
		if(pics==null)return;
		int len = pics.length;
		AssertUtil.isTrue( len>1,"pics len < 1:"+len);

		int outputHeight= 0,outputWidth=0;
		int maxHeight= 0,maxWidth=0;
		if(vertical)outputHeight=imageOffset*(len-1);
		else outputWidth=imageOffset*(len-1);
		BufferedImage[] bufferedImages = new BufferedImage[len];
		Map<BufferedImage,String> picNames=new HashMap<BufferedImage,String>();

		File input=null;
		for (int i = 0; i < len; i++) {
			try {
				input=new File(pics[i]);
				bufferedImages[i] = ImageIO.read(input);
				String name=input.getName();
				picNames.put(bufferedImages[i],name.substring(0,name.indexOf('.')));
			} catch (Exception e) {
				log.error(pics[i],e);
				//e.printStackTrace();
				return;
			}
		}
		Arrays.sort(bufferedImages,new BufferedImageComparator(vertical));
		int[][] ImageDatas = new int[len][];
		for (int i = 0; i < len; i++) {
			int width = bufferedImages[i].getWidth();outputWidth=Math.max(width,outputWidth);
			int height = bufferedImages[i].getHeight();outputHeight += height;
			maxHeight=Math.max(width,maxHeight);
			maxWidth=Math.max(height,maxWidth);
			ImageDatas[i] = new int[width * height];// 从图片中读取RGB
			ImageDatas[i] = bufferedImages[i].getRGB(0, 0, width, height,ImageDatas[i], 0, width);

			//log.debug(i+":width-"+width+",height-"+height+",outputWidth-"+outputWidth+",outputHeight-"+outputHeight);
		}
		//for(int i = 0; i < bufferedImages.length; i++)outputHeight += bufferedImages[i].getHeight();

		AssertUtil.isTrue(outputHeight > 1,"outputHeight < 1");

		// 生成新图片
		BufferedImage saveImage = new BufferedImage(outputWidth,outputHeight, BufferedImage.TYPE_INT_RGB);
		saveImage=translucent(saveImage);
		int startY=0;
		int startX=0;
		int offset=0;
		char newLine='\n';
		StringBuilder css=new StringBuilder(".all{background-image: url('all.png');}").append(newLine);
		for (int i = 0; i < len; i++) {
			int width=bufferedImages[i].getWidth();
			int height=bufferedImages[i].getHeight();
			css.append(".i_").append(picNames.get(bufferedImages[i]))
				.append("{background-position:").append(startX).append("px ")
				.append(vertical?'-':' ').append(startY).append("px; width:")
				.append(width).append("px;height:")
				.append(height).append("px;}").append(newLine)
				;
			//saveImage.setRGB(startX,startY,dst_width,bufferedImages[i].getHeight(),ImageDatas[i],offset, dst_width);
			saveImage.setRGB(startX,startY,width,height,ImageDatas[i],offset, bufferedImages[i].getWidth());
			//startY += bufferedImages[i].getHeight();
			if(vertical)startY += height+imageOffset;
			else startX += width+imageOffset;
		}
		System.out.println(css);
		try {
			ImageIO.write(saveImage, type, new File(output));// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private static void appendCss(StringBuilder builder,String name,int startX,int startY,int width,int height,boolean vertical){
		builder.append(".i_").append(name)
		.append("{background-position:").append(startX).append("px ")
		.append(vertical?'-':' ').append(startY).append("px; width:")
		.append(width).append("px;height:")
		.append(height).append("px;}").append('\n')
		;
	}
	public static void autoCombine(String output,String type,final boolean vertical,int imageOffset,String... pics){
		if(pics==null)return;
		int len = pics.length;
		AssertUtil.isTrue( len>1,"pics len < 1:"+len);

		int outputHeight= 0,outputWidth=0;
		int maxHeight= 0,maxWidth=0;
		if(vertical)outputHeight=imageOffset*(len-1);
		else outputWidth=imageOffset*(len-1);

		BufferedImage[] bufferedImages = new BufferedImage[len];
		Map<BufferedImage,String> picNames=new HashMap<BufferedImage,String>();

		File input=null;
		for (int i = 0; i < len; i++) {
			try {
				input=new File(pics[i]);
					bufferedImages[i] = ImageIO.read(input);
					String name=input.getName();
					picNames.put(bufferedImages[i],name.substring(0,name.indexOf('.')));
			} catch (IOException e) {
				log.error(pics[i],e);
				//e.printStackTrace();
				return;
			}
		}
		Arrays.sort(bufferedImages,new BufferedImageComparator(vertical));
		
		int[][] imageDatas = new int[len][];
		for (int i = 0; i < len; i++) {
			int width = bufferedImages[i].getWidth();
			int height = bufferedImages[i].getHeight();
			if(vertical){//竖排
				outputWidth=Math.max(width,outputWidth);
				outputHeight += height;
			}else{//横排
				outputHeight=Math.max(height,outputHeight);
				outputWidth += width;
				//if(i!=(len-1))outputWidth += imageOffset;
			}

			maxHeight=Math.max(width,maxHeight);
			maxWidth=Math.max(height,maxWidth);
			imageDatas[i] = new int[width * height];// 从图片中读取RGB
			imageDatas[i] = bufferedImages[i].getRGB(0, 0, width, height,imageDatas[i], 0, width);
			//log.debug(i+":width-"+width+",height-"+height+",outputWidth-"+outputWidth+",outputHeight-"+outputHeight);
		}
		//for(int i = 0; i < bufferedImages.length; i++)outputHeight += bufferedImages[i].getHeight();
		@SuppressWarnings("unused")
		int lastWidth=0,lastHeight=0;
		int startY=0, startX=0;
		for (int i = 0; i < len; i++) {
			int width = bufferedImages[i].getWidth();
			int height = bufferedImages[i].getHeight();
			if(vertical){//竖排
				if( (startX+width)<=outputWidth ){
					lastHeight=Math.max(lastHeight,height);
				}else{
					startX=0;
					startY += lastHeight+imageOffset;
					lastHeight=height;
				}
				startX+=width+imageOffset;
				if(i==(len-1))startY+=lastHeight;
			}else{//横排
				//outputHeight=Math.max(height,outputHeight);
				//outputWidth += width;
				//if(i!=(len-1))outputWidth += imageOffset;
			}
		}
		//System.out.println(outputHeight+","+startY);
		if(vertical)outputHeight=startY;

		AssertUtil.isTrue(outputHeight > 1,"outputHeight < 1");

		// 生成新图片
		BufferedImage saveImage = new BufferedImage(outputWidth,outputHeight, BufferedImage.TYPE_INT_RGB);
		saveImage=translucent(saveImage);
		startY=0;startX=0;
		int offset=0;
		//char newLine='\n';
		StringBuilder css=new StringBuilder(".all{background-image: url('all.png');}\n");
		lastWidth=0;lastHeight=0;
		for (int i = 0; i < len; i++) {
			//System.out.println(picNames.get(bufferedImages[i]));
			int width=bufferedImages[i].getWidth();
			int height=bufferedImages[i].getHeight();
			//log.debug(i+":(width,height)="+width+","+height);
			/*css.append(".i_").append(picNames.get(bufferedImages[i]))
				.append("{background-position:").append(startX).append("px ")
				.append(vertical?'-':' ').append(startY).append("px; width:")
				.append(width).append("px;height:")
				.append(height).append("px;}").append(newLine)
				;*/
			if(vertical){//竖排
				//System.out.println("*******************************");
				//System.out.println("1:"+startX+","+startX+","+width);
				if( (startX+width)<=outputWidth && !( height*2<=lastHeight )){
					saveImage.setRGB(startX,startY,width,height,imageDatas[i],offset,width);
					appendCss(css,picNames.get(bufferedImages[i]),startX,startY,width,height,vertical);
					//if(startX==0)startY += height+imageOffset;
					startX+=width+imageOffset;
					lastHeight=Math.max(lastHeight,height);
				}else{
					//换行
					startX=0;
					startY += lastHeight+imageOffset;
					//lastHeight=0;
					saveImage.setRGB(startX,startY,width,height,imageDatas[i],offset,width);
					appendCss(css,picNames.get(bufferedImages[i]),startX,startY,width,height,vertical);
					startX+=width+imageOffset;
					lastHeight=height;
				}
				if(i==(len-1))startY+=lastHeight;
			}else{//横排
				saveImage.setRGB(startX,startY,width,height,imageDatas[i],offset,width);
				startX += width+imageOffset;
				lastWidth=width;
			}
		}

		try {
			ImageIO.write(saveImage, type, new File(output));// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(css);
		return;
	}
	public static void main(String[] args) {
		try{
			// int width = 90;
			// int height = 90;
			// createThumbnail("C:\\测试用图片\\1.bmp", "C:\\1.bmp", width, height);
			// createThumbnail("C:\\测试用图片\\九寨沟.jpg", "C:\\九寨沟.jpg", width,height);
			//scaleImage("D:/Server/server/tomcat-6.0.29-x64/webapps/ec/images/blog/big/clyy84a7ghgna38w9n7mqqyat.jpg","C:/scale/clyy_small_50.jpg",161,94);
			//scaleImage("D:/Server/java/images/big.jpg","D:/Server/java/images/small.jpg",280,190);
			/*merge("D:/Server/src/ec/web/static/images/saas/share_all.jpg","jpg",
					"D:/Server/src/ec/web/static/images/saas/share_1.jpg",
					"D:/Server/src/ec/web/static/images/saas/share_2.jpg",
					"D:/Server/src/ec/web/static/images/saas/share_3.jpg",
					"D:/Server/src/ec/web/static/images/saas/share_4.jpg",
					"D:/Server/src/ec/web/static/images/saas/share_5.jpg",
					"D:/Server/src/ec/web/static/images/saas/share_6.jpg");
			merge("D:/Server/src/ec/web/static/images/png_all.jpg","png",
					"D:/Server/src/ec/web/static/images/png/intro_header_1.jpg",
					"D:/Server/src/ec/web/static/images/png/intro_header_2.jpg");*/
			//combine("D:/Server/src/ec/web/static/images/png_all.png","D:/Server/src/ec/web/static/images/png/","png",true,10,"1001.jpg","1002.jpg","1003.jpg","1004.jpg","1005_zhaopin.jpg","1006_right_card.jpg","1007_logo.png","1008_fengcai.jpg");
			//autoCombine("D:/Server/src/ec/web/static/images/ec_all.png","D:/Server/src/ec/web/static/images/png/","png",true,20);
			/*merge("C:/Users/ran/Desktop/youxiaoxianjie.jpg","jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_10.jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_11.jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_12.jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_13.jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_14.jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_15.jpg",
					"C:/Users/ran/Desktop/youxiao/youxiaoxianjie_16.jpg");*/
			//autoCombine("D:/Server/src/crm/web/static/images/min/all.png","D:/Server/src/crm/web/static/images/min/","png",true,20);
			merge("D:/Server/src/crm/web/static/crm_all20120806.png","png","D:/Server/src/crm/web/static/ui-icons_2e83ff_256x240.png","D:/Server/src/crm/web/static/crm_all20120803_256.png","D:/Server/src/crm/web/static/small.png");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
