package j2ee.server.jetty;

import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

/**
 * @author yuanwei
 * @version ctreateTime:2011-7-21 上午10:35:58
 */
public class SystemOutLog extends AbstractLifeCycle implements RequestLog {
	public void log(Request request, Response response) {
		StringBuilder buf=new StringBuilder(256);

		buf.append(request.getServerName());
		buf.append(' ');

		String addr=request.getHeader(HttpHeaders.X_FORWARDED_FOR);

		if(addr == null) addr=request.getRemoteAddr();

		buf.append(addr);
		buf.append(" - ");
		Authentication authentication=request.getAuthentication();
		if(authentication instanceof Authentication.User) buf.append(((Authentication.User)authentication).getUserIdentity().getUserPrincipal().getName());
		else buf.append(" - ");

		buf.append(" [");
		buf.append(request.getTimeStampBuffer().toString());

		buf.append("] \"");
		buf.append(request.getMethod());
		buf.append(' ');
		buf.append(request.getUri().toString());
		buf.append(' ');
		buf.append(request.getProtocol());
		buf.append("\" ");
		if(request.getAsyncContinuation().isInitial()){
			int status=response.getStatus();
			if(status <= 0) status=404;
			buf.append((char)('0' + ((status / 100) % 10)));
			buf.append((char)('0' + ((status / 10) % 10)));
			buf.append((char)('0' + (status % 10)));
		}else buf.append("Async");

		long responseLength=response.getContentCount();
		if(responseLength >= 0){
			buf.append(' ');
			if(responseLength > 99999) buf.append(responseLength);
			else{
				if(responseLength > 9999) buf.append((char)('0' + ((responseLength / 10000) % 10)));
				if(responseLength > 999) buf.append((char)('0' + ((responseLength / 1000) % 10)));
				if(responseLength > 99) buf.append((char)('0' + ((responseLength / 100) % 10)));
				if(responseLength > 9) buf.append((char)('0' + ((responseLength / 10) % 10)));
				buf.append((char)('0' + (responseLength) % 10));
			}
			buf.append(' ');
		}else buf.append(" - ");

		long now=System.currentTimeMillis();

		long d=request.getDispatchTime();
		buf.append(' ');
		buf.append(now - (d == 0 ? request.getTimeStamp() : d));

		buf.append(' ');
		buf.append(now - request.getTimeStamp());

		buf.append(StringUtil.__LINE_SEPARATOR);
		System.out.println(buf.toString());
	}

}
