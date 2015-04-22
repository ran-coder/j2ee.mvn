package nio.socket.s1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

class AsyncClient {
	private SocketChannel		sc;
	private final static int	MAX_LENGTH	=1024;
	private ByteBuffer			readBuff	=ByteBuffer.allocate(MAX_LENGTH);
	private ByteBuffer			writeBuff	=ByteBuffer.allocate(MAX_LENGTH);
	private static String		host		="127.0.0.1";
	private static int			port		=9090;

	public AsyncClient() {
		try{
			InetSocketAddress addr=new InetSocketAddress(host,port);
			// 生成一个socketchannel
			sc=SocketChannel.open();

			// 连接到server
			sc.connect(addr);
			while(!sc.finishConnect());
			System.out.println("connection has been established!...");

			while(true){
				// 回射消息
				String echo;
				try{
					System.err.print("Enter msg you'd like to send:");
					BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
					// 输入回射消息
					echo=br.readLine();

					// 把回射消息放入w_buff中
					writeBuff.clear();
					writeBuff.put(echo.getBytes());
					writeBuff.flip();
				}catch(IOException ioe){
					System.err.println("sth. is wrong with br.readline() ");
				}

				// 发送消息 while(w_buff.hasRemaining())
				sc.write(writeBuff);
				writeBuff.clear();

				// 进入接收状态
				recive();
				// 间隔1秒
				TimeUnit.SECONDS.sleep(1);

			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}

	// //////////
	// 读取server端发回的数据，并显示
	public void recive() throws IOException {
		int count;
		readBuff.clear();
		count=sc.read(readBuff);
		readBuff.flip();
		byte[] temp=new byte[readBuff.limit()];
		readBuff.get(temp);
		System.out.println("reply is " + count + " long, and content is: " + new String(temp));
	}

	public static void main(String args[]) {
		new AsyncClient();
	}
}
