package j2ee.tutorial.apache.util.collections;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;

public class TransformerTest {
	public static void main(String[] args) {
		Transformer multiply=new Transformer() {
			public Object transform(Object input) {
				Long number=(Long)input;
				return (Long.valueOf(number.longValue() * 100));
			}
		};
		Transformer increment=new Transformer() {
			public Object transform(Object input) {
				Long number=(Long)input;
				return (Long.valueOf(number.longValue() + 1));
			}
		};
		Transformer[] chainElements=new Transformer[]{ multiply, increment };
		Transformer chain=new ChainedTransformer(chainElements);
		Long original=Long.valueOf(34);
		Long result=(Long)chain.transform(original);
		System.out.println("Original: " + original);
		System.out.println("Result: " + result);

	}
}
