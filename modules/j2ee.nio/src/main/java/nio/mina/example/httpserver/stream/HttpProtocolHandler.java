package nio.mina.example.httpserver.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;

/**
 * A simplistic HTTP protocol handler that replies back the URL and headers which a client requested.
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 576402 $, $Date: 2007-09-17 21:37:27 +0900 (월, 17 9월 2007) $
 */
public class HttpProtocolHandler extends StreamIoHandler {
	@Override
	protected void processStreamIo(IoSession session, InputStream in, OutputStream out) {
		// You *MUST* execute stream I/O logic in a separate thread.
		new Worker(in,out).start();
	}

	private static class Worker extends Thread {
		private final InputStream	in;

		private final OutputStream	out;

		public Worker(InputStream in, OutputStream out) {
			setDaemon(true);
			this.in=in;
			this.out=out;
		}

		@Override
		public void run() {
			String url;
			Map<String, String> headers=new TreeMap<String, String>();
			BufferedReader in=new BufferedReader(new InputStreamReader(this.in));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.out)));

			try{
				// Get request URL.
				url=in.readLine();
				if(url==null)return;
				url=url.split(" ")[1];

				// Read header
				String line;
				while((line=in.readLine()) != null && !line.equals("")){
					String[] tokens=line.split(": ");
					headers.put(tokens[0],tokens[1]);
				}

				// Write header
				out.println("HTTP/1.0 200 OK");
				out.println("Content-Type: text/html");
				out.println("Server: MINA Example");
				out.println();

				// Write content
				out.println("<html><head></head><body>");
				out.println("<h3>Request Summary for: " + url + "</h3>");
				out.println("<table border=\"1\"><tr><th>Key</th><th>Value</th></tr>");

				for(Entry<String, String> e:headers.entrySet()){
					out.println("<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
				}

				out.println("</table>");

				for(int i=0;i < 20;i++){
					out.print("" + i + ",");
				}

				out.println("</body></html>");
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
				try{
					in.close();
				}catch(IOException e){
				}
			}
		}
	}
}