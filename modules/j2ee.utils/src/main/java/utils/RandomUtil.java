package utils;

import java.util.Random;

import utils.codec.ByteUtil;

public class RandomUtil {
	/**
	 * 获得随即字符串 [0-9]+
	 * @param len int 返回字符串长度
	 * @return String
	 */
	public static String getRandomIntString(int len) {
		StringBuilder rs = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			rs.append(random.nextInt(10));
		}

		return rs.toString();
	}

	/**
	 * 获得随即字符串 
	 * @param len int 返回字符串长度
	 * @return String
	 */
	public static String getRandomChars(int len) {
		byte[] code = ByteUtil.URL_SAFE_ENCODE_TABLE;
		StringBuilder rs = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < len; i++) {
			rs.append((char)code[random.nextInt(36)]);
		}

		return rs.toString();
	}
	/**
	 * 获得随即字符串 
	 * @param len int 返回字符串长度
	 * @return String
	 */
	public static String getRandomChars64(int len) {
		byte[] code = ByteUtil.URL_SAFE_ENCODE_TABLE;
		StringBuilder rs = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < len; i++) {
			rs.append((char)code[random.nextInt(64)]);
		}

		return rs.toString();
	}
	/**
	 * get a integer array filled with random integer without reduplicate [min, max)
	 * @param min    the minimum value
	 * @param max the maximum value
	 * @param size the capacity of the array
	 * @return a integer array filled with random integer without redupulicate
	 */
	public static int[] getRandomIntWithoutReduplicate(int min, int max, int size) {
		int[] result = new int[size];

		int arraySize = max - min;
		int[] intArray = new int[arraySize];
		// init intArray
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = i + min;
		}
		// get randome interger without reduplicate
		int temp;
		for (int i = 0; i < size; i++) {
			int c = getRandomInt(min, max - i);
			int index = c - min;
			temp=intArray[index];
			intArray[index]=intArray[arraySize-1-i];
			intArray[arraySize-1-i]=temp;
			result[i] = intArray[arraySize-1-i];
		}

		return result;
	}

	/**
	 * get a random Integer with the range [min, max)
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the random Integer value
	 */
	public static int getRandomInt(int min, int max) {
		return min + (int)(Math.random() * (max - min));
	}

	/**
	 * get a random double with the range [min, max)
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the random double value
	 */
	public static double getRandomDouble(double min, double max) {
		return min + (Math.random() * (max - min));
	}

	/**
	 * 
	 * @return a random char with the range ASCII 33(!) to ASCII 126(~)
	 */
	public static char getRandomChar() {
		// from ASCII code 33 to ASCII code 126
		int firstChar = 33; // "!"
		int lastChar = 126; // "~"
		char result = (char) (getRandomInt(firstChar, lastChar + 1));
		return result;
	}

	/**
	 * 
	 * @return a random char with the range [0-9],[a-z],[A-Z]
	 */
	public static char getRandomNormalChar() {
		// include 0-9,a-z,A-Z
		int number = getRandomInt(0, 62);
		int zeroChar = 48;
		int nineChar = 57;
		int aChar = 97;
		int zChar = 122;
		int AChar = 65;
		int ZChar = 90;

		char result;

		if (number < 10) {
			result = (char) (getRandomInt(zeroChar, nineChar + 1));
			return result;

		} else if (number >= 10 && number < 36) {
			result = (char) (getRandomInt(AChar, ZChar + 1));
			return result;
		} else if (number >= 36 && number < 62) {
			result = (char) (getRandomInt(aChar, zChar + 1));
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @param length the length of the String
	 * @return a String filled with random char
	 */
	public static String getRandomString(int length) {
		// include ASCII code from 33 to 126
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length; i++) {
			result.append(getRandomChar());
		}
		return result.toString();
	}

	/**
	 *     
	 * @param length the length of the String
	 * @return a String filled with normal random char
	 */
	public static String getRandomNormalString(int length) {
		// include 0-9,a-z,A-Z
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length; i++) {
			result.append(getRandomNormalChar());
		}
		return result.toString();
	}
	/*
	 * 算法:中国铁路规定，计价里程按里程区间阶梯状计价。
	 * 1-200公里分20区段每区段10公里，
	 * 201-400公里分10区段每区段20公里，
	 * 401-700公里分10区段每区段30公里，
	 * 701-1100公里分10区段每区段40公里，
	 * 1101-1600公里分10区段每区段50公里，
	 * 1601-2200公里分10区段每区段60公里， 
	 * 2201-2900公里分10区段每区段70公里，
	 * 2901-3700公里分10区段每区段80公里， 
	 * 3701-4600公里分10区段每区段90公里， 
	 * 4600公里以上每区段100公里。
	 * 计价里程按各段所在区段计算，然后求和。 
	 * 递远递减： 
	 * 1-200公里区间，递减率0%。 
	 * 201-500公里区间，递减率10%。
	 * 501-1000公里区间，递减率20%。 
	 * 1001-1500公里区间，递减率30%。 
	 * 1501-2500公里区间，递减率40%。
	 * 2500公里以上区间，递减率50%。 
	 * 将计价里程各段按所在递远递减区段优惠率计算后，各段求和得优惠後里程，
	 * 即为产生费用的里程，用于下一步票价计算。
	 */
	public static int getFavourableTollDistance(int km) {

		// 判断里程区间
		if (km > 0) {
			if (km > 200) {
				if (km > 400) {
					if (km > 700) {
						if (km > 1100) {
							if (km > 1600) {
								if (km > 2200) {
									if (km > 2900) {
										if (km > 3700) {
											if (km > 4600) {
												km = ((km - 4600) / 100 + 1) * 100 + 4600;
											} else {
												km = ((km - 3700) / 90 + 1) * 90 + 3700;
											}
										} else {
											km = ((km - 2900) / 80 + 1) * 80 + 2900;
										}
									} else {
										km = ((km - 2200) / 70 + 1) * 70 + 2200;
									}
								} else {
									km = ((km - 1600) / 60 + 1) * 60 + 1600;
								}
							} else {
								km = ((km - 1100) / 50 + 1) * 50 + 1100;
							}
						} else {
							km = ((km - 700) / 40 + 1) * 40 + 700;
						}
					} else {
						km = ((km - 400) / 30 + 1) * 30 + 400;
					}
				} else {
					km = ((km - 200) / 20 + 1) * 20 + 200;
				}
			} else {
				km = (km / 10 + 1) * 10;
			}
		} else {
			km = 0;
		}
		// 递远递减优惠
		if (km > 2500) {
			km = (int) ((km - 2500) * 0.5 + 1000 * 0.6 + 500 * 0.7 + 500 * 0.8 + 300 * 0.9 + 200 * 1);
		} else {
			if (km > 1500) {
				km = (int) ((km - 1500) * 0.6 + 500 * 0.7 + 500 * 0.8 + 300 * 0.9 + 200 * 1);
			} else {
				if (km > 1000) {
					km = (int) ((km - 1000) * 0.7 + 500 * 0.8 + 300 * 0.9 + 200 * 1);
				} else {
					if (km > 500) {
						km = (int) ((km - 500) * 0.8 + 300 * 0.9 + 200 * 1);
					} else {
						if (km > 200) {
							km = (int) ((km - 200) * 0.9 + 200 * 1);
						} else {
							if (km > 0) {

							} else {
								km = 0;
							}
						}
					}
				}
			}
		}

		return km;
	}
	
	public static void main(String args[]) {
		/*System.out.println(getRandomIntString(4));
		System.out.println(getRandomCharString(4));
		System.out.println( getRandomNormalString( 4 ) );
        int[] test = getRandomIntWithoutReduplicate( 0, 40, 39 );
        Arrays.sort( test );
        for (int i = 0; i < test.length; i++) {
			System.out.println( i+":"+test[i] );
		}*/
		System.out.println(getFavourableTollDistance(1000));

	}
}
