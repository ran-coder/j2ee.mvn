package tutorial.apache.util.lang;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.DoubleRange;

public class ValidateTest {
	public static final DoubleRange	LAT_RANGE	=new DoubleRange(-90.0,90.0);
	public static final DoubleRange	LON_RANGE	=new DoubleRange(-180.0,180.0);

	public static void main(String[] args) {
		int param1=10;
		Object[] param2={ 1, 2, 3 };
		Collection<String> param3=new ArrayList<String>();
		param3.add("list1");
		Validate.isTrue(param1 > 0,"param must be greater than zero");
		Validate.notEmpty(param2,"param2 must not be empty");
		Validate.notEmpty(param3,"param3 must not be empty");
		Validate.noNullElements(param3,"param3 cannot contain null elements");

		Validate.isTrue(LAT_RANGE.containsDouble(50.1),"Lat not in range " + LAT_RANGE.getMinimumDouble()+","+LAT_RANGE.getMaximumDouble());
		Validate.isTrue(LON_RANGE.containsDouble(99),"Lon not in range " + LAT_RANGE.getMinimumDouble()+","+LAT_RANGE.getMaximumDouble());

	}
}
