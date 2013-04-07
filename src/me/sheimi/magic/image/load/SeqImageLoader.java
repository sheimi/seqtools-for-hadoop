package me.sheimi.magic.image.load;

import java.io.File;
import java.io.IOException;

import me.sheimi.hadoop.LocalSetup;
import me.sheimi.magic.image.Image;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class SeqImageLoader extends ImageLoader {

	private SequenceFile.Reader input;
	private LocalSetup setup;
	private Text key = new Text();
	private BytesWritable value = new BytesWritable();

	{
		try {
			setup = new LocalSetup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SeqImageLoader(SequenceFile.Reader input) {
		this.input = input;
		getNextEntry();
	}

	public SeqImageLoader(File file) throws IOException {
		Path inputPath = new Path(file.getAbsolutePath());
		input = new SequenceFile.Reader(setup.getLocalFileSystem(), inputPath,
				setup.getConf());
		getNextEntry();
	}

	public SeqImageLoader(String filename) throws IOException {
		this(new File(filename));
	}

	public Image next() {
		if (end)
			return null;
		String filename = key.toString();
		String[] fns = filename.split("/");
		byte[] data = value.getBytes();
		Image image = Image.decode(data, fns[fns.length - 1]);
		getNextEntry();
		return image;
	}

	private void getNextEntry() {
		try {
			end = !input.next(key, value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
