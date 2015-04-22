package nio.mina.filter;

import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.statistic.ProfilerTimerFilter;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-11-14 下午4:19:31
 *   
 */
public class DefaultProfilerTimerFilter extends ProfilerTimerFilter{
	public DefaultProfilerTimerFilter() {
		setTimeUnit(TimeUnit.SECONDS);
		setEventsToProfile(IoEventType.SESSION_OPENED,IoEventType.MESSAGE_RECEIVED,IoEventType.MESSAGE_SENT,
				IoEventType.SESSION_CREATED,IoEventType.SESSION_OPENED,IoEventType.SESSION_IDLE,IoEventType.SESSION_CLOSED);
	}
	public void stat(long seconds){
		for(;;){
			try{TimeUnit.SECONDS.sleep(seconds);}catch(InterruptedException e){}
			System.out.println("calls	TotalTime	min	max");
			for(IoEventType type:getEventsToProfile()){
				System.out.println(getTotalCalls(type) + "	" + getTotalTime(type) + "	" + getMinimumTime(type) + "	" + getMaximumTime(type)
						+ ":" + type.name());
			}
		}
	}

}
