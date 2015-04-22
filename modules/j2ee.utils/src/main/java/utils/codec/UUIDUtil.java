package utils.codec;

import java.util.UUID;

public class UUIDUtil {
	static String uuidDigits(long val, int digits,int radix) {
		long hi=1L << (digits * 4);
		return Long.toString(hi | (val & (hi - 1)),radix).substring(1);
	}
	static String uuidDigitsDebug(long val, int digits,int radix,String msg) {
		long hi=1L << (digits * 4);
		long i=hi | (val & (hi - 1));
		String s=null;
		if(radix==64){
			s=ByteUtil.Number.long2Base64String(i);
		}else{
			s=Long.toString(i,radix);
		}
		String r=s;//.substring(1);
		/*System.out.println("************ "+msg+" ************");
		System.out.println(ByteUtil.toBinaryString(val)+":val");
		System.out.println(ByteUtil.toBinaryString(hi)+":hi");
		System.out.println(ByteUtil.toBinaryString(i)+":i");
		System.out.println(ByteUtil.toBinaryString(ByteUtil.Number.bytes2Long(Base64.decodeBase64(r)))+":s.substring(1)");
		System.out.println(ByteUtil.toBinaryString(i&(hi-1))+":i&hi");
		System.out.println(s+":s");
		System.out.println(r+":r");*/
		return r;
	}
	static UUID uuid=UUID.randomUUID();
	/**
	 * @param radix 2-32,64
	 * @return
	 */
	public static String getUUID(int radix) {
		//UUID uuid=UUID.randomUUID();
		/*
		 * (digits(mostSigBits >> 32, 8) + "-" +
		 * digits(mostSigBits >> 16, 4) + "-" +
		 * digits(mostSigBits, 4) + "-" +
		 * digits(leastSigBits >> 48, 4) + "-" +
		 * digits(leastSigBits, 12))
		 */
		long mostSigBits=uuid.getMostSignificantBits();
		long leastSigBits=uuid.getLeastSignificantBits();
		//System.out.println(ByteUtil.toBinaryString(mostSigBits)+":mostSigBits");
		//System.out.println(ByteUtil.toBinaryString(leastSigBits)+":leastSigBits");
		if(radix==64){
			return ByteUtil.Number.long2Base64String(uuid.getMostSignificantBits())+ByteUtil.Number.long2Base64String(uuid.getLeastSignificantBits());
		}

		/*StringBuilder result=new StringBuilder()
			.append(uuidDigits(mostSigBits >> 32,8,radix,":mostSigBits >> 32,8,"+radix))
			.append(uuidDigits(mostSigBits >> 16,4,radix,":mostSigBits >> 16,4,"+radix))
			.append(uuidDigits(mostSigBits >> 4,4,radix,":mostSigBits >> 4,4,"+radix))

			.append(uuidDigits(leastSigBits >> 48, 4,radix,"leastSigBits >> 48, 4,"+radix))
			.append(uuidDigits(leastSigBits, 12,radix,"leastSigBits, 12,"+radix))
			;*/
		//System.out.println(ByteUtil.Number.long2Base64String(mostSigBits)+ByteUtil.Number.long2Base64String(leastSigBits));
		//System.out.println(ByteUtil.Number.long2Base64String(mostSigBits));
		//System.out.println(ByteUtil.Number.long2Base64String(leastSigBits));
		StringBuilder result=new StringBuilder()
		.append(uuidDigits(mostSigBits >> 32,8,radix))
		.append(uuidDigits(mostSigBits >> 16,4,radix))
		.append(uuidDigits(mostSigBits >> 4,4,radix))

		.append(uuidDigits(leastSigBits >> 48, 4,radix))
		.append(uuidDigits(leastSigBits, 12,radix))
		;
		return result.toString();
	}

	public static void main(String[] args) {
		/*UUID id=UUID.randomUUID();
		String temp=id.toString();
		System.out.println(temp);
		System.out.println(UUID.fromString(temp).toString());
		System.out.println(UUID.fromString(temp).version());
		System.out.println(UUID.randomUUID().toString());*/
		System.out.println(getUUID(10));
		System.out.println(getUUID(16));
		System.out.println(getUUID(36));
		System.out.println(getUUID(64));
	}

}