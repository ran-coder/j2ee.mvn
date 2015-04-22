package nio.netty.example.chapter1;


import nio.netty.example.SimpleServer;
import nio.netty.example.chapter1.handler.UnixTimeEncoder;
import nio.netty.example.chapter1.handler.UnixTimeServerHandler;

public class UnixTimeServer {

	public static void main(String[] args) throws Exception {
		new SimpleServer(8080).start(
				new UnixTimeEncoder(),
				new UnixTimeServerHandler()
		);
	}
}