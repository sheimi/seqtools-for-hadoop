package me.sheimi.magic.image.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import me.sheimi.magic.image.Image;

public class FolderImageLoader extends ImageLoader {

	private File[] mFiles;
	private int mIndex;

	public FolderImageLoader(String filename) {
		File file = new File(filename);
		mFiles = file.listFiles();
		mIndex = 0;
	}

	@Override
	public Image next() {
		if (!hasNext()) {
			return null;
		}
		Image current = getCurrentEntry();
		mIndex++;
		if (mIndex >= mFiles.length) {
			end = true;
		}
		return current;
	}

	private Image getCurrentEntry() {
		if (mIndex >= mFiles.length)
			return null;
		File file = mFiles[mIndex];
		try {
			InputStream is = new FileInputStream(file);
			Image image = new Image(is, (int) file.length(), file.getName());
			System.out.println(file.getName());
			is.close();
			return image;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void close() {

	}

}