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

import java.util.Arrays;
import java.io.*;

public class Image {

  private byte[] image;
  private byte[] encoded;
  private String filename;
	public static final int SIZE_LEN = 10;
	public static final String SIZE_FORMATER = "%10d";

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
