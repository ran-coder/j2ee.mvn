package tutorial.apache.util.collections;

import java.net.URL;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;

public class LazyMapExample {
	Map<String,Float>	stockQuotes;

	public static void main(String[] args) throws Exception {
		LazyMapExample example=new LazyMapExample();
		example.start();
	}

	@SuppressWarnings("unchecked")
	public void start() throws Exception {

		StockQuoteTransformer sqTransformer=new StockQuoteTransformer();
		sqTransformer.setQuoteURL(new URL("http://quotes.company.com"));
		sqTransformer.setTimeout(500);

		// Create a Least Recently Used Map with max size = 5
		stockQuotes=new LRUMap(5);
		// Decorate the LRUMap with the StockQuoteTransformer
		stockQuotes=LazyMap.decorate(stockQuotes,sqTransformer);

		// Now use some of the entries in the cache
		@SuppressWarnings("unused")
		Float price=stockQuotes.get("CSCO");
		price=stockQuotes.get("MSFT");
		price=stockQuotes.get("TSC");
		price=stockQuotes.get("TSC");
		price=stockQuotes.get("LU");
		price=stockQuotes.get("P");
		price=stockQuotes.get("P");
		price=stockQuotes.get("MSFT");
		price=stockQuotes.get("LU");

		// Request another price to the Map, this should kick out the LRU item.
		price=stockQuotes.get("AA");

		// CSCO was the first price requested, it is therefore the
		// least recently used.
		if(!stockQuotes.containsKey("CSCO")){
			System.out.println("As expected CSCO was discarded");
		}
	}

	static class StockQuoteTransformer implements Transformer {
		protected URL	quoteURL;
		protected long	timeout;

		public Object transform(Object symbol) {
			QuoteRetriever retriever=new QuoteRetriever((String)symbol);

			try{
				Thread retrieveThread=new Thread(retriever);
				retrieveThread.start();
				retrieveThread.join(timeout);
			}catch(InterruptedException ie){
				System.out.println("Quote request timed out.");
			}

			return retriever.getResult();
		}

		public URL getQuoteURL() {
			return quoteURL;
		}

		public void setQuoteURL(URL url) {
			quoteURL=url;
		}

		public long getTimeout() {
			return timeout;
		}

		public void setTimeout(long l) {
			timeout=l;
		}

		public class QuoteRetriever implements Runnable {
			private String	symbol;
			private Float	result	=new Float(Float.NaN);

			public QuoteRetriever(String symbol) {
				this.symbol=symbol;
			}

			public Float getResult() {
				return result;
			}

			public void run() {
				HttpClient client=new HttpClient();
				try{
					HttpURL url=new HttpURL(quoteURL.toString());
					url.setQuery("symbol",symbol);
					GetMethod getMethod=new GetMethod(url.toString());
					client.executeMethod(getMethod);
					String response=getMethod.getResponseBodyAsString();
					result=new Float(response);
				}catch(NullPointerException nullPointerException){
					nullPointerException.fillInStackTrace();
				}catch (NumberFormatException numberFormatException) {
					numberFormatException.fillInStackTrace();
				}catch (URIException uRIException) {
					uRIException.fillInStackTrace();
				}catch (Exception e) {
					e.fillInStackTrace();
				}
			}

		}
	}

}
