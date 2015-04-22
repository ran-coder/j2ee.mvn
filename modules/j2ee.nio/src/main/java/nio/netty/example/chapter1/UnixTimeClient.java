package nio.netty.example.chapter1;


import nio.netty.example.SimpleClient;
import nio.netty.example.chapter1.handler.UnixTimeClientHandler;
import nio.netty.example.chapter1.handler.UnixTimeDecoder;



public class UnixTimeClient {

	public static void main(String[] args) throws Exception {
		new SimpleClient("127.0.0.1",8080).connect(
				new UnixTimeDecoder(),
				new UnixTimeClientHandler()
		);
	}
}
