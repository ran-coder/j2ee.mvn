package j2ee.research.codec;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-19 16:42
 * To change this template use File | Settings | File Templates.
 */
public class CardCodec{
	private static final String PATH="D:/Server/java/card/card20131219.txt";

	public static void main(String[] args) throws IOException{
		Files.readLines(new File(PATH), Charsets.UTF_8, new LineProcessor<Object>(){
			long count=0L;

			@Override
			public boolean processLine(String line) throws IOException{
				if(line==null) return false;

				if(count<1000){
					count++;
					System.out.println(CardUtil.readLine(line));
					return true;
				}
				//false 停止
				return false;
			}

			@Override
			public Object getResult(){
				return null;
			}
		});
	}
}
