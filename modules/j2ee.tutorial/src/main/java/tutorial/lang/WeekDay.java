package tutorial.lang;

import java.util.EnumSet;

public enum WeekDay {
	MONDAY {
		@Override
		public void printWeekDay() {
			System.out.println("Today is Monday!");
		}
	},
	TUESDAY {
		@Override
		public void printWeekDay() {
			System.out.println("Today is Tuesday!");
		}
	},
	WENSDAY {
		@Override
		public void printWeekDay() {
			System.out.println("Today is Wensday!");
		}
	},
	THURSDAY {
		@Override
		public void printWeekDay() {
			System.out.println("Today is Thursday!");
		}
	},
	FRIDAY {
		@Override
		public void printWeekDay() {
			System.out.println("Today is Friday!");
		}
	};

	/**
	 * 根据工作日的不同打印不同的信息。
	 */
	public abstract void printWeekDay();

	public static void main(String[] args) {
		WeekDay.MONDAY.printWeekDay();
		for(WeekDay weekDay:EnumSet.allOf(WeekDay.class)){
			weekDay.printWeekDay();
		}
	}
}
