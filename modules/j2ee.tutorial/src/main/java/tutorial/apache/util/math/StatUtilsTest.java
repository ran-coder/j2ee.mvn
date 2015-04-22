package tutorial.apache.util.math;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.apache.commons.math.stat.StatUtils;

public class StatUtilsTest {
	public static void main(String[] args) {
		double[] values=new double[]{ 2.3, 5.4, 6.2, 7.3, 23.3 };
		System.out.println("min: " + StatUtils.min(values));
		System.out.println("max: " + StatUtils.max(values));
		System.out.println("mean: " + StatUtils.mean(values));
		System.out.println("product: " + StatUtils.product(values));
		System.out.println("sum: " + StatUtils.sum(values));
		System.out.println("variance: " + StatUtils.variance(values));
		
		double[][] coefficients={ { 3.0, 20.0, 89.0 }, { 4.0, 40.0, 298.0 }, { 7.0, 21.0, 0.42 } };
		values=new double[]{1324, 2999, 2039 };
		RealMatrix matrix=new RealMatrixImpl(coefficients);

		double[] answers=matrix.solve(values);
		System.out.println("Answers: " + ArrayUtils.toString(answers));

	}
}
