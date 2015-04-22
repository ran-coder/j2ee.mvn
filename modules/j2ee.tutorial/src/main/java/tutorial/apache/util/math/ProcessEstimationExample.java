package tutorial.apache.util.math;

import org.apache.commons.lang.math.RandomUtils;

import utils.NumberUtil;

public class ProcessEstimationExample {
	private ProcessEstimator	estimate;

	public static void main(String[] args) {
		ProcessEstimationExample example=new ProcessEstimationExample();
		example.begin();
	}

	public void begin() {
		estimate=new ProcessEstimator(10000,100);
		estimate.start();

		for(int i=0;i < 10000;i++){
			// Print status every 1000 items
			printStatus(i);
			performLengthyProcess();
			estimate.unitCompleted();
		}

		estimate.stop();

		System.out.println("Completed " + estimate.getUnits() + " in " + estimate.getTimeSpent() + " ms.");
	}

	private void printStatus(int i) {
		if(i % 1000 == 0){
			System.out.println("Completed: " + estimate.getCompleted() + " of " + estimate.getUnits() + "	"
					+ NumberUtil.percent(estimate.getCompleted(),estimate.getUnits()));
			System.out.println("\tTime Spent: " + estimate.getTimeSpent() + " ms" + ", Time Remaining: "
					+ estimate.projectedTimeRemaining() + " ms");
		}
	}

	private void performLengthyProcess() {
		try{
			Thread.sleep(RandomUtils.nextInt(10));
		}catch(Exception e){
		}
	}
}
