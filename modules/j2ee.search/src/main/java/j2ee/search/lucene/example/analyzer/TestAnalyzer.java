package j2ee.search.lucene.example.analyzer;

import java.io.StringReader;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestAnalyzer {
	static Analyzer	ik	=new IKAnalyzer();
	static Analyzer pading = new PaodingAnalyzer();
	
	private static void testAnalyzer(Analyzer analyzer,String str) throws Exception {
		TokenStream ts=analyzer.tokenStream(null,new StringReader(str));
		CharTermAttribute termAtt=ts.getAttribute(CharTermAttribute.class);

		while(ts.incrementToken()){
			String token=new String(termAtt.buffer(),0,termAtt.length());
			System.out.println(token);
		}
	}
	
	public static void main(String[] args) throws Exception {
		testAnalyzer(ik,"喜欢游佐七海的人估计都偏好日本学生系列");
		testAnalyzer(pading,"喜欢游佐七海的人估计都偏好日本学生系列");
	}
	
}