package me.sheimi.magic.image.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import me.sheimi.magic.image.Image;

public class FolderImageStorage extends ImageStorage {

	private File mDir;

	public FolderImageStorage(String filename) {
		mDir = new File(filename);
		if (!mDir.exists()) {
			mDir.mkdir();
		}
	}

	@Override
	public void write(Image image) {
		File file = new File(mDir, image.getFilename());
		try {
			OutputStream os = new FileOutputStream(file);
			os.write(image.getImage());
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close() {
	}

}
