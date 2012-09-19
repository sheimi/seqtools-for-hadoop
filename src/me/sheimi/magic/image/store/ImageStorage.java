/*  ImageStorage.java
 *
 *  Store image to file
 *
 *  @author Reason Zhang
 *  @email  sheimi.zhang@gmail.com
 *  
 *  CopyRight (c) 2012 Magic Team
 *
 */
package me.sheimi.magic.image.store;

import java.util.*;
import java.io.*;
import me.sheimi.util.*;
import me.sheimi.magic.image.*;

public abstract class ImageStorage {
  public abstract  void write(Image image);
  public abstract void close();

  private static Map<String, Class<? extends ImageStorage>> storages;
  static {
    storages = new HashMap<String, Class<? extends ImageStorage>>();
    storages.put("seq", SeqImageStorage.class);
    storages.put("tar", TarImageStorage.class);
  }
  public static ImageStorage getStorage(String filename) {
    String[] fns = filename.split("\\.");
    String subfix = fns[fns.length - 1];
    Class<? extends ImageStorage> storageClz = storages.get(subfix);
    ImageStorage storage = ReflectionUtils.newInstance(storageClz, filename);
    return storage;
  }

}
