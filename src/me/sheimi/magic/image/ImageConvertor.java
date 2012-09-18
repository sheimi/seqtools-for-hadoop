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

import me.sheimi.magic.image.load.*;
import me.sheimi.magic.image.store.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.Constructor;

public class ImageConvertor {

  private ImageLoader il;
  private ImageStorage is;

  public ImageConvertor(ImageLoader il, ImageStorage is) {
    this.il = il;
    this.is = is;
  }

  public void execute() throws IOException{
    while (il.hasNext()) {
      is.write(il.next());
    }
    il.close();
    is.close();
  }

  private static Map<String, Class<? extends ImageLoader>> loaderMap;
  private static Map<String, Class<? extends ImageStorage>> storageMap;

  static {
    loaderMap = new HashMap<String, Class<? extends ImageLoader>>();
    storageMap = new HashMap<String, Class<? extends ImageStorage>>();

    loaderMap.put("seq", SeqImageLoader.class);
    loaderMap.put("tar", TarImageLoader.class);

    storageMap.put("seq", SeqImageStorage.class);
    storageMap.put("tar", TarImageStorage.class);

  }

  public static void main(String [] args) throws Exception {

    String input = args[0];
    String output = args[1];

    String[] is = input.split("\\.");
    String[] os = output.split("\\.");

    Class<? extends ImageLoader> loaderClz = loaderMap.get(is[is.length - 1]);
    Class<? extends ImageStorage> storageClz = storageMap.get(os[os.length - 1]);

    ImageLoader loader = newInstance(loaderClz, input);
    ImageStorage storage = newInstance(storageClz, output);

    new ImageConvertor(loader, storage).execute();

  }

  public static <T> T newInstance(Class<T> clazz, String param) {
    T result;
    try {
      Constructor<T> meth = clazz.getDeclaredConstructor(String.class);
      result = meth.newInstance(param);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return result;
  }

}
