package utils.codec;

import java.io.UnsupportedEncodingException;

public class Base64 {
	public String encode(String str,int mode) {
		String rs="";
		try {
			if(mode==1) {
				rs=org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes("UTF8"));
			}else {
				rs=new String(org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes("UTF8")));
			}
		}catch(UnsupportedEncodingException e) {
			//LogUtil.print("base64加解密出错:"+e.getLocalizedMessage());
		}
		return rs;
	}

	public static void main(String[] args) {
		byte[] bs="base64加解密".getBytes();
		System.out.println(bs.length);
		System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(bs).getBytes().length);
	}
}
