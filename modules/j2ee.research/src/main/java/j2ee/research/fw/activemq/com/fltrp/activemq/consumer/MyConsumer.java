package j2ee.research.fw.activemq.com.fltrp.activemq.consumer;



public interface MyConsumer {

	public void onMessage(Object model);
	public void before(MyMessage mymessage);
	public void after();

}