package utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;



public class ZipUtil{

	/** 压缩数据 */
	public static byte[] zip(byte bs[]) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Deflater def = new Deflater();
		DeflaterOutputStream dos = new DeflaterOutputStream(bos, def);
		try {
			dos.write(bs);
			dos.finish();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
	/** 压缩数据 */
	public static byte[] gzip(byte bs[]) throws Exception{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPOutputStream dos = new GZIPOutputStream(bos);
			dos.write(bs);
			dos.finish();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	/**
	 * 压缩目录和文件
	 * @param src 源文件
	 * @param target 压缩后的目标文件
	 * @throws Exception
	 */
	public static void zip(String src, String target) throws Exception {
		OutputStream os = new FileOutputStream(target);
		zip(new File(src), os);
		os.flush();
		os.close();
	}
	/** 压缩目录或文件 */
	public static void zip(File src, OutputStream target) throws Exception {
		List<File> fileList = getSubFiles(src);
		ZipOutputStream zos = new ZipOutputStream(target);
		ZipEntry ze = null;
		byte buf[] = new byte[1024];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			ze = new ZipEntry(getAbsFileName(src, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());
			zos.putNextEntry(ze);
			if (f.isFile()) {
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
			}
		}

		zos.close();
	}
	/** 压缩多个数据流到文件 */
	public static void zip(byte[][] src,String target) throws Exception {
		zip(src,new FileOutputStream(target));
	}
	/** 压缩多个数据流到文件 */
	public static void zip(byte[][] src,OutputStream target) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(target);
		ZipEntry ze = null;
		for (int i=0;i<src.length; i++) {
			ze = new ZipEntry("part"+i);
			ze.setSize(src[i].length);
			zos.putNextEntry(ze);
			zos.write(src[i]);
		}
		zos.close();
	}
	
	public static void zipBatch(String srcFiles[], String destFile) throws Exception {
		OutputStream os = new FileOutputStream(destFile);
		zipBatch(srcFiles, os);
		os.flush();
		os.close();
	}

	public static void zipBatch(String srcFiles[], OutputStream destStream) throws Exception {
		File files[] = new File[srcFiles.length];
		for (int i = 0; i < srcFiles.length; i++) {
			files[i] = new File(srcFiles[i]);
		}

		zipBatch(files, destStream);
	}

	public static void zipBatch(File srcFiles[], OutputStream destStream) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(destStream);
		for (int k = 0; k < srcFiles.length; k++) {
			List<?> fileList = getSubFiles(srcFiles[k]);
			ZipEntry ze = null;
			byte buf[] = new byte[1024];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				ze = new ZipEntry(getAbsFileName(srcFiles[k], f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				if (f.isFile()) {
					InputStream is = new BufferedInputStream(new FileInputStream(f));
					while ((readLen = is.read(buf, 0, 1024)) != -1) {
						zos.write(buf, 0, readLen);
					}
					is.close();
				}
			}

		}

		zos.close();
	}

	public static void zipStream(InputStream is, OutputStream os, String fileName) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(os);
		ZipEntry ze = null;
		byte buf[] = new byte[1024];
		int readLen = 0;
		ze = new ZipEntry(fileName);
		ze.setTime(System.currentTimeMillis());
		zos.putNextEntry(ze);
		long total;
		for (total = 0L; (readLen = is.read(buf, 0, 1024)) != -1; total += readLen) {
			zos.write(buf, 0, readLen);
		}

		ze.setSize(total);
		zos.flush();
		zos.close();
	}

	public static byte[] unzip(byte bs[]) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		Inflater inf = new Inflater();
		InflaterInputStream dis = new InflaterInputStream(bis, inf);
		byte buf[] = new byte[1024];
		int c;
		try {
			while ((c = dis.read(buf)) != -1) {
				bos.write(buf, 0, c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte r[] = bos.toByteArray();
		return r;
	}

	public static byte[] ungzip(byte bs[]) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		byte buf[] = new byte[1024];
		try {
			GZIPInputStream gis = new GZIPInputStream(bis);
			int c;
			while ((c = gis.read(buf)) != -1) {
				bos.write(buf, 0, c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte r[] = bos.toByteArray();
		return r;
	}

	public static boolean unzip(String srcFileName, String destPath) {
		try {
			ZipFile zipFile = new ZipFile(srcFileName);
			@SuppressWarnings("rawtypes")
			Enumeration e = zipFile.entries();
			ZipEntry zipEntry = null;
			if(!(new File(destPath)).mkdirs())return false;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					if(!(new File(destPath + File.separator + zipEntry.getName())).mkdirs())return false;
				} else {
					File f = new File(destPath + File.separator + zipEntry.getName());
					if(!f.getParentFile().mkdirs())return false;
					InputStream in = zipFile.getInputStream(zipEntry);
					OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
					byte buf[] = new byte[1024];
					int c;
					while ((c = in.read(buf)) != -1) {
						out.write(buf, 0, c);
					}
					out.close();
					in.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	public static List<byte[]> unzip(String path) throws IOException {
		List<byte[]> rs=new ArrayList<byte[]>();
		ZipFile zipFile = new ZipFile(path);
		Enumeration<?> e = zipFile.entries();
		ZipEntry zipEntry = null;
		while (e.hasMoreElements()){
			zipEntry = (ZipEntry) e.nextElement();
			if(!zipEntry.isDirectory()){
				InputStream in = zipFile.getInputStream(zipEntry);
				rs.add(FileUtil.inputStream2Byte(in));
				in.close();
			}
		}
		if(rs==null||rs.size()<1)return null;
		return rs;
	}

	public static boolean unzip(String srcFileName, String destPath, boolean isPath) {
		if (isPath) {
			return unzip(srcFileName, destPath);
		}
		try {
			ZipFile zipFile = new ZipFile(srcFileName);
			@SuppressWarnings("rawtypes")
			Enumeration e = zipFile.entries();
			ZipEntry zipEntry = null;
			if(!(new File(destPath)).mkdirs())return false;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (!zipEntry.isDirectory()) {
					String fileName = zipEntry.getName();
					if (fileName.lastIndexOf("/") != -1) {
						fileName = fileName.substring(fileName.lastIndexOf("/"));
					}
					File f = new File(destPath + "/" + fileName);
					InputStream in = zipFile.getInputStream(zipEntry);
					OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
					byte buf[] = new byte[1024];
					int c;
					while ((c = in.read(buf)) != -1) {
						out.write(buf, 0, c);
					}
					out.close();
					in.close();
				}
			}
			zipFile.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		ret.add(baseDir);
		if (baseDir.isDirectory()) {
			File tmp[] = baseDir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				ret.addAll(getSubFiles(tmp[i]));
			}

		}
		return ret;
	}

	private static String getAbsFileName(File baseDir, File realFileName) {
		File real = realFileName;
		File base = baseDir;
		String ret = real.getName();
		if (real.isDirectory()) {
			ret = ret + "/";
		}
		do {
			if (real == base) {
				break;
			}
			real = real.getParentFile();
			if (real == null) {
				break;
			}
			if (real.equals(base)) {
				ret = real.getName() + "/" + ret;
				break;
			}
			ret = real.getName() + "/" + ret;
		} while (true);
		return ret;
	}
	
	public static void main(String args[]) throws Exception {
		ZipUtil.zip("c:/tmp/java/zip/install.dat", "c:/tmp/java/zip/install.dat.zip");
		/*byte bs[] = FileUtil.readFile("c:/tmp/java/zip/install.dat");
		StopWatch sw = new StopWatch();
		sw.start();
		System.out.println(bs.length+"	"+zip(bs).length);
		sw.stop();
		System.out.println("beanListQuery() used " + sw.getTime() + " ms.");*/
		/*zip(new byte[][]{
			FileUtil.serialize(new User("发达省份","dasda额外额外123")),
			FileUtil.serialize(new User("发达省份000","dasda额外额外123")),
		},"c:/tmp/java/zip/zip.zip");*/
		/*List<byte[]> rs=unzip("c:/tmp/java/zip/zip.zip");
		for (int i = 0,j=rs.size(); i < j; i++) {
			System.out.println((User)FileUtil.unserialize(rs.get(i)));
		}*/
		//System.out.println(zip(FileUtil.readFile("c:/tmp/java/zip/install.dat")).length);
		//System.out.println(gzip(FileUtil.readFile("c:/tmp/java/zip/install.dat")).length);
	}
}
