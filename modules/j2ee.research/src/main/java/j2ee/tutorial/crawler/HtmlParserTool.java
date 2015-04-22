package j2ee.tutorial.crawler;

/**
 * @author yuanwei
 * @version ctreateTime:2011-4-18 下午2:56:37
 */
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

public class HtmlParserTool {
	// 获取一个网站上的链接,filter 用来过滤链接
	public static Set<String> extracLinks(String url, LinkFilter filter) {
		Set<String> links=new HashSet<String>();
		try{
			Parser parser=new Parser(url);
			parser.setEncoding("gb2312");
			// OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
			OrFilter linkFilter=new OrFilter(new NodeFilter[]{
					new NodeClassFilter(ImageTag.class),
					new NodeClassFilter(FrameTag.class),
					new NodeClassFilter(ScriptTag.class),
					new NodeClassFilter(StyleTag.class),
					new NodeClassFilter(LinkTag.class)
					//frameFilter
					});
			// 得到所有经过过滤的标签
			NodeList list=parser.extractAllNodesThatMatch(linkFilter);
			Node tag=null;
			String linkUrl=null;
			for(int i=0,j=list.size();i<j;i++){
				tag=list.elementAt(i);
				//System.out.println(tag.getClass());
				if(tag instanceof ImageTag){
					linkUrl=((ImageTag)tag).getImageURL();
				}else if(tag instanceof FrameTag){
					linkUrl=((FrameTag)tag).getAttribute("src") ;
				}else if(tag instanceof ScriptTag){
					linkUrl=( ((ScriptTag)tag).getAttribute("src") );
				}else if(tag instanceof StyleTag){
					linkUrl=( ((StyleTag)tag).getAttribute("href") );
				}else if(tag instanceof LinkTag){
					linkUrl=( ((LinkTag)tag).getLink() );
				}
				if(filter.accept(linkUrl)) links.add(linkUrl);
				linkUrl=null;
				tag=null;
			}
		}catch(ParserException e){
			e.printStackTrace();
		}
		return links;
	}

	// 测试的 main 方法
	public static void main(String[] args) {
		Set<String> links=HtmlParserTool.extracLinks("http://list.image.baidu.com/t/image_category/image_travel.html",new LinkFilter() {
			public boolean accept(String url) {
				if(url==null|| url.trim().length()<1)return false;
				if("/".equals(url) || url.startsWith("#")){
					return false;
				}
				return true;
			}

		});
		for(String link:links)
			System.out.println(link);
	}
}
