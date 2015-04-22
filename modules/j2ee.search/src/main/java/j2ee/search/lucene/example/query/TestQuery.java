package j2ee.search.lucene.example.query;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;

public class TestQuery {
	static final String	INDEXPATH	="D:/Server/java/search/index/eming/";
	
	/**
	 * 测试各种Query
	 * @throws Exception
	 * @throws CorruptIndexException
	 */
	private static void testQuery() throws Exception {
		/** 获取IndexSearcher */
		IndexReader reader=IndexReader.open(FSDirectory.open(new File(INDEXPATH)));
		IndexSearcher searcher=new IndexSearcher(reader);
		
		/** 1.TermQuery 按词条搜索 */
		Query query=new TermQuery(new Term("contents","嘉靖"));
		
		/** 2.MultiTermQuery */
		
		/** 3.BooleanQuery 布尔搜索 */
		// BooleanQuery query = new BooleanQuery();
		// Query query1 = new TermQuery(new Term("desc","获得"));
		// Query query2 = new TermQuery(new Term("name","白"));
		// query.add(query1, BooleanClause.Occur.SHOULD);
		// query.add(query2, BooleanClause.Occur.MUST);
		
		/** 4.WildcardQuery 通配符搜索 */
		// WildcardQuery query = new WildcardQuery(new Term("desc", "因为*"));//*?
		
		/** 5.PhraseQuery 多关键字的搜索 */
		// PhraseQuery query = new PhraseQuery();
		// query.add(new Term("desc","因为"));
		// query.add(new Term("desc","巨乳"));
		// query.setSlop(5);//通过setSlop()方法来设定一个称之为“坡度”的变量来确定关键字之间是否允许、允许多少个无关词汇的存在
		
		/** 6.PrefixQuery 前缀搜索 */
		// PrefixQuery query = new PrefixQuery(new Term("desc","因为"));
		
		/** 7.MultiPhraseQuery */
		// MultiPhraseQuery query=new MultiPhraseQuery();
		// query.add(new Term[]{new Term("desc","因为"),new Term("desc","巨乳")});
		
		/** 8.FuzzyQuery 相近词语的搜索 */
		// FuzzyQuery query = new FuzzyQuery(new Term("desc","巨乳"));
		
		/** 9.TermRangeQuery 范围查询（主要指文本） */
		// TermRangeQuery query = new TermRangeQuery("desc","250","300",true,true);
		
		/** 10.NumericRangeQuery 范围查询（索引字段必须是 NumbericField） */
		// NumericRangeQuery<Integer> query = NumericRangeQuery.newIntRange("birth", 1980, 1983, true, false);
		
		/** 11.SpanQuery 跨度查询 */
		// SpanTermQuery query1 = new SpanTermQuery(new Term("desc", "因为"));//单独使用SpanTermQuery同TermQuery
		// SpanTermQuery query2 = new SpanTermQuery(new Term("desc", "巨乳"));
		// SpanNearQuery query = new SpanNearQuery(new SpanQuery[]{query1, query2}, 5, true);//数值代表跨度范围
		
		ScoreDoc[] hits=searcher.search(query,10).scoreDocs;
		for(ScoreDoc scoreDoc:hits){
			Document doc=searcher.doc(scoreDoc.doc);
			System.out.println(scoreDoc.score + "\tid:" + doc.get("id") + "\tname:" + doc.get("name") + "\tbirth:" + doc.get("birth") + "\tdesc:" + doc.get("desc"));
		}
	}
	
	public static void main(String[] args) throws Exception {
		// index();
		testQuery();
	}
}