package utils.arithmetic;

import org.apache.commons.lang.StringUtils;

import utils.NumberUtil;

public class Similarity {
	// http://googlechinablog.com/2006/07/12.html
	/** cos相似度 */
	public static double cos(double[] a, double[] b) {
		if(a==null || a.length<1 || b==null || b.length<1)return 0d;
		double top=0;
		int length=Math.min(a.length,b.length);
		if(length <= 0) return 0;
		for(int i=0;i < length;i++){
			top+=a[i] * b[i];
		}
		double temp=0;
		for(int i=0;i < length;i++){
			temp+=a[i] * a[i];
		}
		if(temp <= 0) return 0;
		top=top / Math.sqrt(temp);
		temp=0;
		for(int i=0;i < length;i++){
			temp+=b[i] * b[i];
		}
		if(temp <= 0) return 0;
		return top / Math.sqrt(temp);
	}

	public static double cos(String str1, String str2) {
		return cos(NumberUtil.toDoubles(str1),NumberUtil.toDoubles(str2));
	}

	public static int combinat(String strA, String strB) {
		int lenA=(int)strA.length();
		int lenB=(int)strB.length();
		int[][] c=new int[lenA + 1][lenB + 1]; // Record the distance of all begin points of
		// each string

		// i: begin point of strA
		// j: begin point of strB
		for(int i=0;i < lenA;i++)
			c[i][lenB]=lenA - i;
		for(int j=0;j < lenB;j++)
			c[lenA][j]=lenB - j;
		c[lenA][lenB]=0;

		for(int i=lenA - 1;i >= 0;i--)
			for(int j=lenB - 1;j >= 0;j--){
				if(strB.charAt(j) == strA.charAt(i)) c[i][j]=c[i + 1][j + 1];
				else{
					c[i][j]=Math.min(Math.min(c[i][j + 1],c[i + 1][j]),c[i + 1][j + 1])+1;
				}
			}

		return c[0][0];
	}

	public static void main(String[] args) {
		System.out.println(cos(new double[]{ 1.0, 2.0, 3.0 },new double[]{ 1.1, 2.1, 3.1 }));
		System.out.println(cos(new double[]{ 1.0, 2.0, 3.0 },new double[]{ 2.1, 2.1, 3.1 }));
		String str1="25eae511-515e-4b75-b2f6-7102272bd45b";
		String str2="24eae511-5a5e-4b75-b2f6-0102272bd45b";
		System.out.println(combinat(str1,str2));
		System.out.println(StringUtils.getLevenshteinDistance(str1,str2));
		System.out.println(StringUtils.indexOfDifference(str1,str2));
		System.out.println(cos(str1,str2));

		System.out.println(new StringBuilder().toString());
	}
}
