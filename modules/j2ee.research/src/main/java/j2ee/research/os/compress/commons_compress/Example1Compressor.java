package j2ee.research.os.compress.commons_compress;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-12 16:36
 * To change this template use File | Settings | File Templates.
 */
public class Example1Compressor{
	public static void main(String[] args) throws Exception{
		FileInputStream inputStream = new FileInputStream("src.tar");
		FileOutputStream outputStream = new FileOutputStream("src.tar.xz");

		CompressorOutputStream gzippedOut = new CompressorStreamFactory()	.createCompressorOutputStream(CompressorStreamFactory.GZIP, outputStream);
		ArchiveInputStream input = new ArchiveStreamFactory().createArchiveInputStream(inputStream);
		CompressorInputStream input1 = new CompressorStreamFactory().createCompressorInputStream(inputStream);

	}
}
