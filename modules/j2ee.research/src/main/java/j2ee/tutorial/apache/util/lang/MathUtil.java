package j2ee.tutorial.apache.util.lang;

import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.lang.math.IntRange;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.Range;
import org.apache.commons.math.stat.descriptive.rank.Max;
import org.apache.commons.math.stat.descriptive.rank.Min;

public class MathUtil {
	public static void main(String[] args) {
		Fraction numer1=Fraction.getFraction(3,4);
		Fraction numer2=Fraction.getFraction(51,3509);
		Fraction numerator=numer1.multiplyBy(numer2);
		Fraction denominator=Fraction.getFraction(41,59);
		Fraction fraction=numerator.divideBy(denominator);
		Fraction result=fraction.reduce();
		System.out.println("as Fraction: " + result.reduce().toString());
		System.out.println("as double: " + result.doubleValue());

		double[] array={ 0.2, 0.4, 0.5, -3.0, 4.223, 4.226 };
		System.out.println(NumberUtils.max(array));// returns 4.226
		System.out.println(NumberUtils.min(array));// returns -3.0
		Max maximum=new Max();
		Min minimum=new Min();
		System.out.println(maximum.evaluate(array,0,array.length));
		System.out.println(minimum.evaluate(array,0,array.length));

		Range safeSpeed=new DoubleRange(0.0,65.0);
		double currentSpeed=93;
		if(!safeSpeed.containsDouble(currentSpeed)){
			System.out.println("Warning, current speed is unsafe.");
		}

		IntRange recordRange=new IntRange(60.0,100.0);
		IntRange daysRange=new IntRange(NumberUtils.min(0,100,55),NumberUtils.max(3,6,9));
		if(!recordRange.containsRange(daysRange)){
			System.out.println("Today is a record temperature day!");
		}

	}
}
