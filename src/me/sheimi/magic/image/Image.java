/*  Image.java
 *
 *  It is the basic image raw container. And it can encode to and decode from
 *  image raw stored in the hadoop sequence file.
 *
 *  Because the size of value in the sequence file is not the real size, so our
 *  image format stored in the hadoop sequnce file is:
 *
 *  size of image (10 bytes) + image size
 *
 *  @author Reason Zhang
 *  @email  sheimi.zhang@gmail.com
 *  
 *  CopyRight (c) 2012 Magic Team
 *
 */

package me.sheimi.magic.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Image {

	private byte[] image;
	private byte[] encoded;
	private String filename;
	public static final int SIZE_LEN = 10;
	public static final String SIZE_FORMATER = "%10d";
	public static final int THUMBNAIL_HEIGHT = 200;
	public static final int THUMBNAIL_WIDTH = 300;

	public Image(byte[] image, String filename) {
		this.filename = filename;
		this.image = image;
	}

	public Image(InputStream is, int size, String filename) throws IOException {
		this.filename = filename;
		image = new byte[size];
		is.read(image);
	}

	public int getSize() {
		return image.length;
	}

	public String getFilename() {
		return filename;
	}

	public byte[] getImage() {
		return image;
	}

	public byte[] encode() {
		if (encoded == null) {
			byte[] size = encodeSize(image.length);
			encoded = new byte[SIZE_LEN + image.length];
			System.arraycopy(size, 0, encoded, 0, size.length);
			System.arraycopy(image, 0, encoded, size.length, image.length);
		}
		return encoded;
	}

	public byte[] getThumbnail() {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
					getImage()));
			if (image.getHeight() < THUMBNAIL_HEIGHT
					&& image.getWidth() < THUMBNAIL_WIDTH)
				return getImage();
			double rate = image.getWidth() / image.getHeight();
			int height, width;
			if (rate >= THUMBNAIL_WIDTH / THUMBNAIL_HEIGHT) {
				width = THUMBNAIL_WIDTH;
				height = (int) (width / rate);
			} else {
				height = THUMBNAIL_HEIGHT;
				width = (int) (height * rate);
			}

			BufferedImage scaledBI = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = scaledBI.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.drawImage(image, 0, 0, width, height, null);
			g.dispose();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(scaledBI, "png", os);
			return os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Image decode(byte[] src, String filename) {
		int size = decodeSize(src);
		byte[] image = Arrays.copyOfRange(src, SIZE_LEN, SIZE_LEN + size);
		return new Image(image, filename);
	}

	public static byte[] encodeSize(int size) {
		return String.format(SIZE_FORMATER, size).getBytes();
	}

	public static int decodeSize(byte[] src) {
		byte[] len = Arrays.copyOfRange(src, 0, SIZE_LEN);
		int size = Integer.parseInt(new String(len).trim());
		return size;
	}
}
