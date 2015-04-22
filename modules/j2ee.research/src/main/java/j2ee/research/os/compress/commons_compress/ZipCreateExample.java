package j2ee.research.os.compress.commons_compress;
import j2ee.research.os.compress.Compress;

import java.io.*;
import java.util.zip.*;
public class ZipCreateExample{
	static void createZipFile(String zipFileName) throws IOException{
		String input=Compress.BASE_DIR+"./customer1.txt";
		String output=Compress.BASE_DIR+zipFileName;
		FileOutputStream dest = new FileOutputStream(output);
		ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(dest));
		zipOutputStream.setMethod(ZipOutputStream.DEFLATED); //Entries can be added to a ZIP file in a compressed (DEFLATED) form
		zipOutputStream.setLevel(Deflater.DEFAULT_COMPRESSION);

		File file = new File(input);
		final int BUFFER = 2048;
		byte data[] = new byte[BUFFER];
		BufferedInputStream origin = null;
		FileInputStream fi = new FileInputStream(file);
		origin = new BufferedInputStream(fi, BUFFER);

		int index = file.getAbsolutePath().indexOf(":") + 1;
		String relativePath = file.getPath().substring(index);

		zipOutputStream.putNextEntry(new ZipEntry(relativePath));
		//sevenZOutput.putArchiveEntry(sevenZOutput.createArchiveEntry(new File(Compress.BASE_DIR+"./customer1.txt"), "customer1.txt"));
		int count;
		while((count = origin.read(data, 0, BUFFER)) != -1) {
			zipOutputStream.write(data, 0, count);
		}
		origin.close();
		zipOutputStream.close();
	}
	public static void main(String[] args)  throws Exception{
	//createZipFile("./7z/SevenZUsage-createZipFile.zip");
	// input file
	FileInputStream in = new FileInputStream(Compress.BASE_DIR+"./unzip/22.xls");

	// out put file
	ZipOutputStream out = new ZipOutputStream(new FileOutputStream(Compress.BASE_DIR+"./zip/ZipCreateExample.zip"));

	// name the file inside the zip  file
	out.putNextEntry(new ZipEntry("22.xls"));

	// buffer size
	byte[] b = new byte[1024];
	int count;

	while ((count = in.read(b)) > 0) {
		System.out.println();
		out.write(b, 0, count);
	}
	out.close();
	in.close();
}
}