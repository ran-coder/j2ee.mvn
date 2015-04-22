package utils.servlet.trim;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class FilterServletOutputStream extends ServletOutputStream {
	
	private DataOutputStream	stream;
	
	public FilterServletOutputStream(OutputStream outputstream) {
		stream=new DataOutputStream(outputstream);
	}
	
	public void write(int i) throws IOException {
		stream.write(i);
	}
	
	public void write(byte abyte0[]) throws IOException {
		stream.write(abyte0);
	}
	
	public void write(byte abyte0[], int i, int j) throws IOException {
		stream.write(abyte0,i,j);
	}
}
