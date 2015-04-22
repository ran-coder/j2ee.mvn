package utils.io.image;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Comparator;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-6-18 上午11:04:55
 *   
 */
public class BufferedImageArrayComparator implements Comparator<BufferedImage[]>,Serializable{
	private static final long	serialVersionUID	=-8745332310549883869L;
	private boolean vertical;
	public BufferedImageArrayComparator(){}
	public BufferedImageArrayComparator(boolean vertical){
		this.vertical=vertical;
	}
	public int compare(BufferedImage[] o1, BufferedImage[] o2) {
		if(o1==null&&o2!=null) return 1;
		if(o2==null&&o1!=null) return -1;
		if(o2==null&&o1==null) return 0;
		int width1=0,height1=0;
		int width2=0,height2=0;
		for(BufferedImage o:o1){
			width1+=(o==null?0:o.getWidth());
			height1+=(o==null?0:o.getHeight());
		}
		for(BufferedImage o:o2){
			width2+=(o==null?0:o.getWidth());
			height2+=(o==null?0:o.getHeight());
		}
		if(vertical){//垂直
			return width1<width2?1:-1;
		}else{//水平
			return height1<height2?1:-1;
		}
	}
	
}
