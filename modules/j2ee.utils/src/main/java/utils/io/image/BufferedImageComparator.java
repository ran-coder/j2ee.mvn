package utils.io.image;

import java.awt.image.BufferedImage;
import java.util.Comparator;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-6-18 上午11:04:55
 *   
 */
public class BufferedImageComparator implements Comparator<BufferedImage>{
	private boolean vertical;
	public BufferedImageComparator(){}
	public BufferedImageComparator(boolean vertical){
		this.vertical=vertical;
	}
	public int compare(BufferedImage o1, BufferedImage o2) {
		if(o1==null&&o2!=null) return 1;
		if(o2==null&&o1!=null) return -1;
		if(o2==null&&o1==null) return 0;
		if(vertical){//垂直
			if(o1.getWidth()==o2.getWidth()){
				if(o1.getHeight()<o2.getHeight()) return 0;
				return o1.getHeight()<o2.getHeight()?1:-1;
			}
			return o1.getWidth()<o2.getWidth()?1:-1;
		}else{//水平
			if(o1.getHeight()<o2.getHeight()){
				if(o1.getWidth()==o2.getWidth()) return 0;
				return o1.getWidth()<o2.getWidth()?1:-1;
			}
			return o1.getHeight()<o2.getHeight()?1:-1;
		}
	}
	
}
