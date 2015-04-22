package tutorial.apache.util.collections;

import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.OrPredicate;
import org.apache.commons.lang.ArrayUtils;

public class CompoundPredicateExample {
	public static void main(String[] args) {
		CompoundPredicateExample example=new CompoundPredicateExample();
		example.start();
	}

	@SuppressWarnings("unchecked")
	public void start() {
		Predicate circuit=createPredicate();
		Object[] inputsArray=new Object[][]{ { "A", Boolean.TRUE }, { "B", Boolean.FALSE }, { "C", Boolean.TRUE },
				{ "D", Boolean.FALSE }, { "E", Boolean.FALSE } };
		Map<String,Object> inputs=ArrayUtils.toMap(inputsArray);
		boolean result=circuit.evaluate(inputs);
		System.out.println("The circuit fired?: " + result);
	}

	public Predicate createPredicate() {
		Predicate aPredicate=new InputPredicate("A");
		Predicate bPredicate=new InputPredicate("B");
		Predicate cPredicate=new InputPredicate("C");
		Predicate dPredicate=new InputPredicate("D");
		Predicate ePredicate=new InputPredicate("E");
		Predicate expression1=new AndPredicate(aPredicate,bPredicate);
		Predicate expression2=new OrPredicate(cPredicate,dPredicate);
		Predicate[] secondLevel=new Predicate[]{ expression1, expression2, ePredicate };
		//Predicate topLevel=new NotPredicate(secondLevel);
		System.out.println(java.util.Arrays.toString(secondLevel));
		return null;
	}
}

class InputPredicate implements Predicate {
	private String	inputKey;

	public InputPredicate(String inputKey) {
		this.inputKey=inputKey;
	}

	@SuppressWarnings("unchecked")
	public boolean evaluate(Object object) {
		boolean satisfies=false;
		Map<String, Object> inputMap=(Map<String, Object>)object;
		Boolean input=(Boolean)inputMap.get(inputKey);
		if(input != null){
			satisfies=input.booleanValue();
		}
		return satisfies;
	}
}
