/**
 * 
 */
package utils.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yuanwei **Util.java的方法 都为static
 */
public class FileUtil {
	public static final byte BOM[] = {-17, -69, -65};
	public static int bufferSize=1024;
	/** 读取文件 */
	public static byte[] readFile(String path) {
		try {
			return readFileExcp(path);
		} catch (Exception e) {}
		return null;
	}
	/** 读取文件 */
	public static byte[] readFileExcp(String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
		int size = (int) file.length();
		byte[] byteKey = new byte[size];
		FileInputStream fin = new FileInputStream(file);
		fin.read(byteKey);
		fin.close();
		return byteKey;
	}
	
	public static byte[] randomAccessRead(String path) throws IOException{
		RandomAccessFile random=new RandomAccessFile(path,"r");
		byte[] byteKey = new byte[bufferSize];
		random.seek(random.length()/2+100);
		if(random.read(byteKey)==-1){
			return null;
		}
		random.close();
		return byteKey;
	}
	
	public static byte[] mappedRead(String path) throws IOException{
		int   length=0x8FFFFFF;   //128Mb
		MappedByteBuffer out=new RandomAccessFile(path,"rw").getChannel().map(FileChannel.MapMode.READ_WRITE,0,length);  
		for(int i=0;i<length;i++)out.put((byte)'x');
		System.out.println( "Finished   writing ");  
		for(int i=length/2;i<length/2+6;i++)  
			System.out.print((char)out.get(i));//read   file
		System.out.println();
		return null;
	}
	
	/** 按照行读取 */
	public static List<String> readFileLine(String path, String charset){
		List<String> rs=new ArrayList<String>();
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		try{
			File fl = new File(path);
			fileReader = new FileReader(fl);
			bufferedReader = new BufferedReader(fileReader);
			String currentLine;
			while((currentLine = bufferedReader.readLine())!=null){
				//System.out.println(currentLine);
				rs.add(currentLine);
			}	
		}catch(Exception e){System.out.println(e.toString());}
		finally{
			if(bufferedReader!=null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(fileReader!=null){
				try{
					fileReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
		return rs;
	}
	
	/** 读取文件 */
	public static FileInputStream readFileStreamExcp(String path) throws IOException{
		return new FileInputStream(new File(path));
	}
	/** 读取文件 */
	public static String readFile(String path, String charset) {
		try {return readFileExcp(path,charset);} catch (Exception e) {}
		return null;
	}
	/** 读取文件 */
	public static String readFileExcp(String path, String charset) throws IOException {
		byte[] bs=readFileExcp(path);
		if(bs==null)return null;
		return new String(bs,charset);
		//return new String(bs).getBytes(charset).toString();
	}
	
	/**
	 * @param directory 绝对路径 如: c:/windows
	 * @param filterFile 包含文件类型 new String[]{".log",".exe"} 为null时全部
	 * @param mode mode=0,读取全部;1，读取目录;2，文件;3,遍历子目录
	 * @recursive 是否遍历子目录
	 * @return
	 */
	public static List<String> listFiles(String directory,final String[] filterFile, int mode,boolean recursive){
		File dir = new File(directory);
		if(dir==null || !dir.isDirectory())return null;
		List<String> list=new ArrayList<String>();
		if(mode==1){//读取目录
			File[] fs = null;
			fs = dir.listFiles(new FileFilter(){
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			if(fs==null)return null;
			for (File f:fs) list.add(f.getName());
		}else if(mode==2){//文件
			//FileUtils.listFiles(new File("C:/WINDOWS/system32"), new SuffixFileFilter(new String[]{".dll",".log"}), FalseFileFilter.INSTANCE).toArray();
			File[] fs = null;
			fs = dir.listFiles(new FileFilter(){
				public boolean accept(File file){
					return file.isFile();
				}
			});
			if(fs==null)return null;
			
			if(filterFile==null || filterFile.length<1){
				for (File f:fs) list.add(f.getName());
			}else{
				FilenameFilter filter=new FilenameFilter(){
					public boolean accept(File file, String suffixName) {
						return file.getName().endsWith(suffixName);
					}
				};
				for (int i = 0; i < filterFile.length; i++) {
					for (File f:fs){
						if(filter.accept(f, filterFile[i]) )	list.add(f.getName());
					}
				}
				
			}
			
		}
		if(list==null || list.size()<1)return null;
		return list;
	}
	
	public static String getFileName(String path){
		try{
			return new File(path).getName();
		}catch(Exception e){
			e.fillInStackTrace();
		}
		return "";
	}
	
	public static boolean writeFile(String msg,String charset, String path) {
		try {
			writeFileExcp(msg, charset, path);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static void writeFileExcp(String msg,String charset, String path)throws IOException {
		byte[] bs = null;
		if(charset!=null && charset.length()>0)
			bs = msg.getBytes(charset);
		else
			bs = msg.getBytes();
		writeFileExcp(bs, path);
	}
	public static void writeFileExcp(byte[] msg, String path) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(msg);
		fos.close();
	}
	public static void writeFileAndPathExcp(byte[] msg, String path) throws Exception {
		mkfileExcp(path);
		//writeFileExcp(msg,"utf-8",path);
		writeFileExcp(msg,path);
	}
	public static boolean writeFile(byte[] msg, String path) throws IOException {
		try {
			writeFileExcp(msg,path);
			return true;
		} catch (Exception e) {	}
		return false;
	}
	
	/** InputStream转byte[] */
	public static byte[] inputStream2Byte(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			baos.write(ch);
		}
		byte data[] = baos.toByteArray();
		baos.close();
		is.close();
		return data;
	}
	/** byte[]转InputStream */
	public static InputStream byte2InputStream(byte[] bs) throws IOException {
		return new ByteArrayInputStream(bs);
	}

	public static void input2OutputStream(InputStream in, OutputStream out) throws IOException {
		byte[] data=new byte[1024];
		int len=0;
		while((len=in.read(data)) > 0)
			out.write(data,0,len);
	}
	
	/** 创建目录 */
    public static boolean mkdir(String path){
        File dir = new File(path);
        if(!dir.exists()){
            return dir.mkdirs();
        }
        return true;
    }
    /** 创建文件(如果路径不存在创建失败) */
    public static boolean mkfile(String path){
        File dir = new File(path);
        //System.out.println(dir.getParent()+"	"+dir.getName());
        if(!dir.exists()){
            try {
            	new File(dir.getParent()).mkdirs();
				return dir.createNewFile();
			} catch (IOException e) {}
        }
        return false;
    }
    /**
    * 创建任意深度的文件所在文件夹,可以用来替代直接new File(path)。
    *
    * @param path
    * @return File对象
    */
    public static File createFile(String path) {
		File file=new File(path);
		// 寻找父目录是否存在
		File parent=new File(file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator)));
		// 如果父目录不存在，则递归寻找更上一层目录
		if(!parent.exists()){
			createFile(parent.getPath());
			// 创建父目录
			if(!parent.mkdirs())return null;
		}
		return file;
	}
    
    /** 创建文件(如果路径不存在创建失败) */
    public static boolean mkfileExcp(String path) throws Exception{
        File dir = new File(path);
        //System.out.println(dir.getParent()+"	"+dir.getName());
        if(!dir.exists()){
            try {
            	new File(dir.getParent()).mkdirs();
				return dir.createNewFile();
			} catch (Exception e) {
				throw e;
			}
        }
        return false;
    }

	/** 将对象系列化,抛出异常	 */
	public static byte[] serializeExcp(Serializable obj)throws Exception{
        return getSerializeOutputStreamExcp(obj).toByteArray();
	}
	/** 将对象系列化,抛出异常	 */
	public static ByteArrayOutputStream getSerializeOutputStreamExcp(Serializable obj)throws Exception{
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        return bos;
	}
	/** 将对象系列化,不抛出异常 */
	public static byte[] serialize(Serializable obj){
		try{return serializeExcp(obj);}catch(Exception e){}
        return null;
	}
	/** 将对象系列化并写入文件,抛出异常 */
	public static void serializeExcp(Serializable obj,String path)throws Exception{
		mkfile(path);
		serializeExcp(obj,new FileOutputStream(path));
	}
	/** 将对象系列化并写入OutputStream,抛出异常 */
	public static void serializeExcp(Serializable obj,OutputStream os)throws Exception{
		//mkfile(path);
		//FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
	}
	/** 将对象系列化并写入文件,不抛出异常 */
	public static boolean serialize(Serializable obj,String file){
		try {
			serializeExcp(obj, file);
			return true;
		}catch(Exception e){}
		return false;
	}
	/** 将对象反系列化,抛出异常 */
	public static Object unserializeExcp(byte bs[])throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
        Object obj = ois.readObject();
        ois.close();
        return obj;
	}
	/** 将对象反系列化,不抛出异常 */
	public static Object unserialize(byte bs[]){
       try {
    	   return unserializeExcp(bs);
		} catch (Exception e) {}
		return null;
	}
	/** 将对象反系列化,抛出异常 */
	public static Object unserializeExcp(String file)throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object obj= ois.readObject();
        ois.close();
        return obj;
	}
	/** 将对象反系列化,抛出异常 */
	public static Object unserialize(String file){
		try {
			return unserializeExcp(file);
		} catch (Exception e) {}
		return null;

	}
	
	public static void printMemory(){
		System.out.println(
			(double)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024)
			+"	"+(double)Runtime.getRuntime().totalMemory()/(1024)
			+"	"+(double)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())
			/Runtime.getRuntime().totalMemory()+"x100%"
		);
	}
	public static void printCostTime(String msg,long start){
		System.out.println(msg+(System.currentTimeMillis()-start));
	}

	public static void main(String[] args) throws Exception {
		//User user=new User("发达省份","dasda额外额外123");
		//User user0=(User)unserialize(serialize(user));
		//System.out.println(user0);
		/*serializeExcp(user,"c:/tmp/java/user.dat");
		User user1=(User)unserialize("c:/tmp/java/user.dat");
		System.out.println(user1);*/
		//mkfile("c:/tmp/java/../../user0.dat");
		//long start=System.currentTimeMillis();
		/*readFile("c:/tmp/java/zip/install.dat");
		System.out.println("readFile:"+(System.currentTimeMillis()-start));
		
		start=System.currentTimeMillis();
		FileUtils.readFileToByteArray(new File("c:/tmp/java/zip/install.dat"));
		System.out.println("FileUtils.readFileToByteArray:"+(System.currentTimeMillis()-start));

		start=System.currentTimeMillis();
		readFile("c:/tmp/java/zip/install.dat","utf-8");
		System.out.println("readFile:"+(System.currentTimeMillis()-start));*/
		/*start=System.currentTimeMillis();
		printMemory();
		
		readFileExcp("c:/tmp/java/zip/book.txt","utf-8");
		System.out.println("===========readFileExcp:"+(System.currentTimeMillis()-start));
		printMemory();
		
		start=System.currentTimeMillis();
		FileUtils.readFileToString(new File("c:/tmp/java/zip/book.txt"),"utf-8");
		System.out.println("==========readFileToString:"+(System.currentTimeMillis()-start));
		printMemory();*/

		//System.out.println(Arrays.toString(listFiles("D:/Server/server/Tomcat/webapps/img/", null, 1,false).toArray() ) );
		//System.out.println(Arrays.toString(listFiles("C:/WINDOWS/system32", new String[]{".log"}, 2,false).toArray() ) );

		//System.out.println( new String(readFile("D:/app_user.sql"),"utf-8") );
		printMemory();
		System.out.println(new String(mappedRead("E:/iso/winxp_sp3.iso")));
		printMemory();
		System.out.println(new String(randomAccessRead("E:/iso/winxp_sp3.iso")));
		printMemory();
	}

}
