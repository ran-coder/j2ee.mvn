package j2ee.research.codec;


public class NumUtil{
	/*
	 * 演算法执行过程如下：
	 *	1.将卡号上的每个数字乘上其权重（weight），如果卡号上的数字个数是偶数，那么第一个数字的权重就是2，若是奇数，那么权重就给1，剩下来的数字，根据第一个数字依序给定。
	 *	  例如某卡卡号的数字个数为偶数，那么从第一位数字开始的权重依序为2、1、2、1、2、1 ...。
	 *	2.如果数字乘上自己的权重后比9还大，那么就从这加权数字里扣除9。
	 *	3.接下来将所有处理过的加权数字全部加总起来，并且除以10，取其余数。
	 *	4.用10减去此余数(如果余数大于0)应该是0，否则就可能是输入过程有误，也有可能卡号是假的。
	 *	最后一位是刚才算出来的3位的总和除以10，取其余数;---此步是迅速查看是否是假卡
	 */
	private static int basicNum=9;//基数，当大于些基数减去些基数
	private static int weight=2;//权重
	private static int currentNum=0;//当前卡的数字
	private static String chars="23456789abcdefghjklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

	public static void main(String[] s){
		//随机号向量
		StringBuilder sRand=new StringBuilder();
		int charsLength=chars.length();
		try{
			System.out.println(CardUtil.decrypt(null));
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int lastNum=genarateCode("00100001");
		String md5Str=CardUtil.md5("00100001"+"Cu67Vp9ZCd8pxvqx");
		String i=String.valueOf((Integer.valueOf(((char) md5Str.charAt(0)))))+String.valueOf((Integer.valueOf(((char) md5Str.charAt(1)))));
		System.out.println(i);
		System.out.println(i.substring(0, 2));
		System.out.println(i.substring(0, 2)+lastNum);
		System.out.println(lastNum(""+i.substring(0, 2)+lastNum));
		String numStr=""+lastNum+i.substring(0, 2)+lastNum(""+i.substring(0, 2)+lastNum);
		System.out.println(numStr);
		System.out.println(md5Str);

		System.out.println("7108".equals(numStr));
	}

	/** 取最后三位数的和求模10 */
	public static int lastNum(String str){
		int currentNum=0;
		int sum=0;
		for(int i=str.length()-3; i<str.length(); i++){
			currentNum=Integer.valueOf(str.substring(i, i+1));
			sum+=currentNum;
		}
		return sum%10;
	}

	/** 生成单个校验码卡的前8位 */
	public static int genarateCode(String cardNO){
		int length=cardNO.length();//卡长度
		int sum=0;//总和
		for(int i=0; i<length; i++){
			currentNum=Integer.valueOf(cardNO.substring(i, i+1));
			if(length%2==0){
				if(i%2==0){
					currentNum=currentNum*weight;
				}
			}else{
				if(i%2!=0){
					currentNum=currentNum*weight;
				}
			}
			currentNum=getCurrentNum(currentNum, basicNum);
			sum+=currentNum;
		}
		return sum%10==0 ? 0 : (10-sum%10);
	}

	/** 减去基数 */
	public static int getCurrentNum(int num, int basicNO){
		int i=num;
		while(i>basicNO && i-basicNO>=0){
			i=i-basicNO;
		}
		return i;
	}


}
