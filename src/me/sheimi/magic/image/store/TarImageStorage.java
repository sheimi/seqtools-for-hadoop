package me.sheimi.magic.image.store;

import java.util.*;
import me.sheimi.magic.image.*;
import java.io.*;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarOutputStream;


public class TarImageStorage extends ImageStorage {

  private TarOutputStream output = null;

  public TarImageStorage(TarOutputStream output) {
    this.output = output;
  }

  public TarImageStorage(File file) throws IOException {
    output = new TarOutputStream(new FileOutputStream(file));
  }

  public TarImageStorage(String filename) throws IOException {
    this(new File(filename));
  }

  public void write(Image image) {
    try {
      TarEntry entry = new TarEntry(image.getFilename());
      int size = image.getSize();
      byte[] raw = image.getImage();
      //System.out.println(size);
      entry.setSize(size);
      output.putNextEntry(entry);
      output.write(raw);
      output.closeEntry();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
