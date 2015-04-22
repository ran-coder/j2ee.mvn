// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package utils.io;



import java.io.*;

/**
 * Optimized byte and character stream utilities.
 */
public class Streams{
	/** Default IO buffer size (16 KB).*/
	public final static int IO_BUFFER_SIZE = 16384;
	public final static String UTF_8            = "UTF-8";
	public final static String US_ASCII			= "US-ASCII";
	public final static String ISO_8859_1       = "ISO-8859-1";

	// ---------------------------------------------------------------- silent close
	public static <T extends Closeable> void close(T target) {
		if (target != null) {
			try {
				target.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}
	public static <T extends Closeable & Flushable> void flushClose(T target) {
		if (target != null) {
			try {
				target.flush();
			} catch (IOException ioex) {
				// ignore
			}
			try {
				target.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}
	public static <T extends Flushable> void flush(T target) {
		if (target != null) {
			try {
				target.flush();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}
	/**
	 * Closes an input stream and releases any system resources associated with
	 * this stream. No exception will be thrown if an I/O error occurs.
	 */
	public static void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}

	/**
	 * Closes an output stream and releases any system resources associated with
	 * this stream. No exception will be thrown if an I/O error occurs.
	 */
	public static void close(OutputStream out) {
		if (out != null) {
			try {
				out.flush();
			} catch (IOException ioex) {
				// ignore
			}
			try {
				out.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}

	/**
	 * Closes a character-input stream and releases any system resources
	 * associated with this stream. No exception will be thrown if an I/O error
	 * occurs.
	 */
	public static void close(Reader in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}

	/**
	 * Closes a character-output stream and releases any system resources
	 * associated with this stream. No exception will be thrown if an I/O error
	 * occurs.
	 */
	public static void close(Writer out) {
		if (out != null) {
			try {
				out.flush();
			} catch (IOException ioex) {
				// ignore
			}
			try {
				out.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}



	// ---------------------------------------------------------------- copy

	/**
	 * Copies input stream to output stream using buffer. Streams don't have
	 * to be wrapped to buffered, since copying is already optimized.
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[IO_BUFFER_SIZE];
		int count = 0;
		int read;
		while (true) {
			read = input.read(buffer, 0, IO_BUFFER_SIZE);
			if (read == -1) {
				break;
			}
			output.write(buffer, 0, read);
			count += read;
		}
		return count;
	}
	/**
	 * Copies specified number of bytes from input stream to output stream using buffer.
	 */
	public static int copy(InputStream input, OutputStream output, int byteCount) throws IOException {
		byte buffer[] = new byte[IO_BUFFER_SIZE];
		int count = 0;
		int read;
		while (byteCount > 0) {
			if (byteCount < IO_BUFFER_SIZE) {
				read = input.read(buffer, 0, byteCount);
			} else {
				read = input.read(buffer, 0, IO_BUFFER_SIZE);
			}
			if (read == -1) {
				break;
			}
			byteCount -= read;
			count += read;
			output.write(buffer, 0, read);
		}
		return count;
	}




	/**
	 * Copies input stream to writer using buffer.
	 */
	public static void copy(InputStream input, Writer output) throws IOException {
		copy(input, output, UTF_8);
	}
	/**
	 * Copies specified number of bytes from input stream to writer using buffer.
	 */
	public static void copy(InputStream input, Writer output, int byteCount) throws IOException {
		copy(input, output, UTF_8, byteCount);
	}
	/**
	 * Copies input stream to writer using buffer and specified encoding.
	 */
	public static void copy(InputStream input, Writer output, String encoding) throws IOException {
		copy(new InputStreamReader(input, encoding), output);
	}
	/**
	 * Copies specified number of bytes from input stream to writer using buffer and specified encoding.
	 */
	public static void copy(InputStream input, Writer output, String encoding, int byteCount) throws IOException {
		copy(new InputStreamReader(input, encoding), output, byteCount);
	}

    /**
	 * Copies reader to writer using buffer.
	 * Streams don't have to be wrapped to buffered, since copying is already optimized.
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[IO_BUFFER_SIZE];
		int count = 0;
		int read;
		while ((read = input.read(buffer, 0, IO_BUFFER_SIZE)) >= 0) {
			output.write(buffer, 0, read);
			count += read;
		}
		output.flush();
		return count;
	}
	/**
	 * Copies specified number of characters from reader to writer using buffer.
	 */
	public static int copy(Reader input, Writer output, int charCount) throws IOException {
		char buffer[] = new char[IO_BUFFER_SIZE];
		int count = 0;
		int read;
		while (charCount > 0) {
			if (charCount < IO_BUFFER_SIZE) {
				read = input.read(buffer, 0, charCount);
			} else {
				read = input.read(buffer, 0, IO_BUFFER_SIZE);
			}
			if (read == -1) {
				break;
			}
			charCount -= read;
			count += read;
			output.write(buffer, 0, read);
		}
		return count;
	}



	/**
	 * Copies reader to output stream using buffer.
	 */
	public static void copy(Reader input, OutputStream output) throws IOException {
		copy(input, output, UTF_8);
	}
	/**
	 * Copies specified number of characters from reader to output stream using buffer.
	 */
	public static void copy(Reader input, OutputStream output, int charCount) throws IOException {
		copy(input, output, UTF_8, charCount);
	}
	/**
	 * Copies reader to output stream using buffer and specified encoding.
	 */
	public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
		Writer out = new OutputStreamWriter(output, encoding);
		copy(input, out);
		out.flush();
	}
	/**
	 * Copies specified number of characters from reader to output stream using buffer and specified encoding.
	 */
	public static void copy(Reader input, OutputStream output, String encoding, int charCount) throws IOException {
		Writer out = new OutputStreamWriter(output, encoding);
		copy(input, out, charCount);
		out.flush();
	}


	// ---------------------------------------------------------------- read bytes

	/**
	 * Reads all available bytes from InputStream as a byte array.
	 * Uses <code>in.available()</code> to determine the size of input stream.
	 * This is the fastest method for reading input stream to byte array, but
	 * depends on stream implementation of <code>available()</code>.
	 * Buffered internally.
	 */
	public static byte[] readAvailableBytes(InputStream in) throws IOException {
		int l = in.available();
		byte byteArray[] = new byte[l];
		int i = 0, j;
		while ((i < l) && (j = in.read(byteArray, i, l - i)) >= 0) {
			i +=j;
		}
		if (i < l) {
			throw new IOException("Failed to completely read input stream");
		}
		return byteArray;
	}



	// ---------------------------------------------------------------- compare content

	/**
	 * Compares the content of two byte streams.
	 *
	 * @return <code>true</code> if the content of the first stream is equal
	 *         to the content of the second stream.
	 */
	public static boolean compare(InputStream input1, InputStream input2) throws IOException {
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }
        int ch = input1.read();
        while (ch != -1) {
            int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }
        int ch2 = input2.read();
		return (ch2 == -1);
	}
	/**
	 * Compares the content of two character streams.
	 *
	 * @return <code>true</code> if the content of the first stream is equal
	 *         to the content of the second stream.
	 */
	public static boolean compare(Reader input1, Reader input2) throws IOException {
        if (!(input1 instanceof BufferedReader)) {
            input1 = new BufferedReader(input1);
        }
        if (!(input2 instanceof BufferedReader)) {
            input2 = new BufferedReader(input2);
        }

        int ch = input1.read();
        while (ch != -1) {
            int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }
        int ch2 = input2.read();
        return (ch2 == -1);
    }

}
