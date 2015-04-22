package j2ee.research.spring.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {
	private static Logger log=LoggerFactory.getLogger(UTF8StringHttpMessageConverter.class);
	private static final MediaType	utf8				=new MediaType("text","html",Charset.forName("UTF-8"));
	private boolean					writeAcceptCharset	=true;
	
	@Override
	protected MediaType getDefaultContentType(String dumy) {
		return utf8;
	}
	
	protected List<Charset> getAcceptedCharsets() {
		return Arrays.asList(utf8.getCharSet());
	}
	
	protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
		log.debug("writeInternal");
		if(this.writeAcceptCharset){
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset=utf8.getCharSet();
		FileCopyUtils.copy(s,new OutputStreamWriter(outputMessage.getBody(),charset));
	}
	
	public boolean isWriteAcceptCharset() {
		return writeAcceptCharset;
	}
	
	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset=writeAcceptCharset;
	}
	
}