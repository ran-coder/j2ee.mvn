package j2ee.search.lucene.example.query2000w;

import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.commons.io.Charsets;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author ht 索引生成
 */
public class X2000wIndexer{
	
	public static void main(String[] args) throws Exception {
		long start=new Date().getTime();
		int numIndexed=index("F:/2000W/INDEX_DIR/","F:/2000W/all.csv");// 调用index方法
		long end=new Date().getTime();
		System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
	}
	
	/**
	 * 索引dataDir下的.txt文件，并储存在indexDir下，返回索引的文件数量
	 * @param indexDirFileName
	 * @param targetFileName
	 * @return int
	 * @throws java.io.IOException
	 */
	public static int index(String indexDirFileName, String targetFileName) throws IOException {
		File indexDir=new File(indexDirFileName);
		/*if(indexDir.exists() && !indexDir.delete()){
			throw new IOException("delete file fail!");
		}
		if(!indexDir.exists() && !indexDir.mkdir()){
			throw new IOException("createNewFile fail!");
		}*/

		//IndexWriter writer=new IndexWriter(FSDirectory.open(indexDir),new StandardAnalyzer(Version.LUCENE_CURRENT),true,IndexWriter.MaxFieldLength.LIMITED);// 有变化的地方
		IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_30,new StandardAnalyzer(Version.LUCENE_30));
		IndexWriter writer=new IndexWriter(FSDirectory.open(indexDir),config);
		//indexDirectory(writer,dataDir);
		indexFile(writer,targetFileName);
		int numIndexed=writer.numDocs();
		writer.forceMerge(1, true);
		writer.close();
		return numIndexed;
	}
	/** 获得数组第index个元素的值,否则返回defaultValue */
	public static String getArrayValueIndexOf(String[] obj, int index, String defaultValue) {
		/* if(obj==null || obj.length<index) return defaultValue; if(obj[index]==null || obj[index].toString().length()<1) return defaultValue; return obj[index].toString(); */
		try{
			return obj[index];
		}catch(Exception e){
			// log.error("RequestUtil.getArrayIndexValue(Object[],index,defaultValue) error:index="+index+"	Exception:"+e.toString());
			return defaultValue;
		}
	}
	public static void addField(Document doc,String name,String[] arrs,int index){
		String val=getArrayValueIndexOf(arrs,index,null);
		if(val!=null&&val.length()>0)
			doc.add(new Field(name,val,Field.Store.YES,Field.Index.ANALYZED));// 有变化的地方
	}




	private static void indexFile(final IndexWriter writer, String filename) throws IOException {
		File file=new File(filename);
		if(file.isHidden() || !file.exists() || !file.canRead()){ return; }

		System.out.println("Indexing " + file.getCanonicalPath());

		Files.readLines(file, Charsets.UTF_8, new LineProcessor<Object>(){
			long count=0L;
			@Override
			public boolean processLine(String line) throws IOException{
				// return false 停止搜索
				if(line==null || count==0){
					count++;
					return true;
				}
				count++;
				//﻿Name,CardNo,Descriot,CtfTp,CtfId,Gender,Birthday,Address,Zip,Dirty,District1,District2,District3,District4,District5,District6,FirstNm,LastNm,Duty,Mobile,Tel,Fax,EMail,Nation,Taste,Education,Company,CTel,CAddress,CZip,Family,Version,id
				//邵xx,,,ID,510502198904277448,F,19890427,四川省达州市江阳区通滩镇国光村十二社18号,,F,,CHN,51,510502,,,,,,,,,,汉,,,,,,,0,2011-10-9 14:38:54,10414431
				//System.out.println(line.indexOf(keyword));
				String[] lines=line.split(",");
				Document doc=new Document();
				//﻿Name 0;CtfId 4;Gender 5;,Birthday 6;,Address 7
				addField(doc,"Name",lines,0);
				addField(doc,"CtfId",lines,4);
				addField(doc,"Gender",lines,5);
				addField(doc,"Birthday",lines,6);
				addField(doc,"Address",lines,7);
				/*doc.add(new Field("Name",getArrayValueIndexOf(lines,0,null),Field.Store.YES,Field.Index.ANALYZED));// 有变化的地方
				doc.add(new Field("CtfId",getArrayValueIndexOf(lines,4,null),Field.Store.YES,Field.Index.ANALYZED));// 有变化的地方
				doc.add(new Field("Gender",getArrayValueIndexOf(lines,5,null),Field.Store.YES,Field.Index.ANALYZED));// 有变化的地方
				doc.add(new Field("Birthday",getArrayValueIndexOf(lines,6,null),Field.Store.YES,Field.Index.ANALYZED));// 有变化的地方
				doc.add(new Field("Address",getArrayValueIndexOf(lines,7,null),Field.Store.YES,Field.Index.ANALYZED));// 有变化的地方
				*/

				writer.addDocument(doc);

				return true;
			}

			@Override
			public Object getResult(){
				System.out.println("count:"+count);
				return null;
			}
		});


	}
}