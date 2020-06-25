package Engine.Core;

import org.lwjgl.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.Scanner;

import static org.lwjgl.BufferUtils.*;

public final class IOUtil {

	private IOUtil() {
	}

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;

		Path path = Paths.get(resource);
		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1) {
					;
				}
			}
		} else {
			try (InputStream source = IOUtil.class.getClassLoader().getResourceAsStream(resource);
					ReadableByteChannel rbc = Channels.newChannel(source)) {
				buffer = createByteBuffer(bufferSize);

				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes == -1) {
						break;
					}
					if (buffer.remaining() == 0) {
						buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
					}
				}
			}
		}

		buffer.flip();
		return buffer;
	}

	public static String loadFile(String path) throws Exception {
		Scanner s = new Scanner(new FileReader(path));

		String v = "";

		while (s.hasNextLine()) {
			v += (s.nextLine() + "\n");
		}
		return v;
	}

	public static void deleteFile(String path) {
		File file = new File(path);

		file.delete();
	}

	public static void findReplace(String path, String str, String str1) throws Throwable {
		String content = loadFile(path);
		String n = content.replaceAll(str, str1);
		File file = new File(path);
		FileWriter p = new FileWriter(file);
		p.write(n);
		p.close();
	}

	public static void clearFile(String PATH_TO_FILE) {
		File f = new File(PATH_TO_FILE);
		if (f.exists()) {
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
