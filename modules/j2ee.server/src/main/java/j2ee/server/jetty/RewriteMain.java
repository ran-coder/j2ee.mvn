package j2ee.server.jetty;

import org.eclipse.jetty.rewrite.handler.RedirectPatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewritePatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Server;

/**
 * @author yuanwei
 * @version ctreateTime:2012-7-19 上午10:14:32
 */
public class RewriteMain {
	public static void main(String[] args) {
		Server server=new Server(8080);
		
		RewriteHandler rewrite=new RewriteHandler();
		rewrite.setRewriteRequestURI(true);
		rewrite.setRewritePathInfo(false);
		rewrite.setOriginalPathAttribute("requestedPath");
		
		RedirectPatternRule redirect=new RedirectPatternRule();
		redirect.setPattern("/redirect/*");
		redirect.setLocation("redirected");
		rewrite.addRule(redirect);
		
		RewritePatternRule oldToNew=new RewritePatternRule();
		oldToNew.setPattern("/some/old/context");
		oldToNew.setReplacement("/some/new/context");
		rewrite.addRule(oldToNew);
		
		RewriteRegexRule reverse=new RewriteRegexRule();
		reverse.setRegex("/reverse/([^/]*)/(.*)");
		reverse.setReplacement("/reverse/$2/$1");
		rewrite.addRule(reverse);
		
		server.setHandler(rewrite);
	}
}
