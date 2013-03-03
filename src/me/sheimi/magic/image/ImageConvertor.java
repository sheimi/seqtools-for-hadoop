/*  ImageConvertor.java
 *
 *  It can convert images from format A to format B:
 *
 *  such as from .tar to .seq or .seq to .tar
 *
 *  @author Reason Zhang
 *  @email  sheimi.zhang@gmail.com
 *  
 *  CopyRight (c) 2012 Magic Team
 *
 */

package me.sheimi.magic.image;

import java.io.IOException;

import me.sheimi.magic.image.load.ImageLoader;
import me.sheimi.magic.image.store.ImageStorage;

public class ImageConvertor {

	private ImageLoader il;
	private ImageStorage is;

	public ImageConvertor(ImageLoader il, ImageStorage is) {
		this.il = il;
		this.is = is;
	}

	public void execute() throws IOException {
		while (il.hasNext()) {
			is.write(il.next());
		}
		il.close();
		is.close();
	}

	public static void main(String[] args) throws Exception {
		ImageLoader loader = ImageLoader.getLoader(args[0]);
		ImageStorage storage = ImageStorage.getStorage(args[1]);
		new ImageConvertor(loader, storage).execute();
	}

}
