package j2ee.tutorial.apache.util.jexl;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

public class JexlTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Opera opera=new Opera();
		opera.setName("The Magic Flute");
		opera.setComposer("Mozart");
		opera.setYear(1791);
		String expr="${opera.name} was composed by ${opera.composer} in ${opera.year} .";
		Expression e=ExpressionFactory.createExpression(expr);
		JexlContext jc=JexlHelper.createContext();
		jc.getVars().put("opera",opera);
		String message=(String)e.evaluate(jc);
		System.out.println(message);

	}
static class Opera{
	private String name;
	private String composer;
	private int year;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getComposer() {
		return composer;
	}
	public void setComposer(String composer) {
		this.composer=composer;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year=year;
	}
	
}
}
