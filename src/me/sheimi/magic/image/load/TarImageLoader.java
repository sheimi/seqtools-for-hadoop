package me.sheimi.magic.image.load;

import java.util.*;
import me.sheimi.magic.image.*;
import java.io.*;

import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import java.util.zip.GZIPInputStream;

public class TarImageLoader extends ImageLoader {

  private TarInputStream input;
  private TarEntry next;
  
  public TarImageLoader(TarInputStream input) {
    this.input = input;
    getNextEntry();
  }

  public TarImageLoader(File file) {
    try {
      InputStream fileStream = new FileInputStream(file);
      InputStream theStream = null;
      String filename = file.getName();
      if (filename.endsWith(".tar.gz") || filename.endsWith(".tgz")) {
        theStream = new GZIPInputStream(fileStream);
      } else if (filename.endsWith(".tar.bz2") || filename.endsWith(".tbz2")) {
        fileStream.skip(2);
        theStream = new CBZip2InputStream(fileStream);
      } else {
        theStream = fileStream;
      }
      input = new TarInputStream(theStream); // set inputstream
      getNextEntry();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public TarImageLoader(String filename) {
    this(new File(filename));
  }

  public Image next() {
    if (end)
      return null;
    try {
      TarEntry current = next;
      String filename = current.getName();
      int size = (int) current.getSize();
      Image image = new Image(input, size, filename);
      System.out.println(image.getSize());
      System.out.println(image.getFilename());
      getNextEntry();
      return image;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public void close() {
    try {
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void getNextEntry() {
    try {
      next = input.getNextEntry();
    } catch (IOException e) {
      e.printStackTrace();
      next = null;
    } 
    if (next == null) {
      end = true;
      close();
      return;
    }
    if (next.isDirectory()) {
      getNextEntry();
      return;
    }
    String filename = next.getName();
    String[] fns = filename.split("/");
    if (fns[fns.length - 1].charAt(0) == '.') {
      getNextEntry();
      return;
    }
  }
}
