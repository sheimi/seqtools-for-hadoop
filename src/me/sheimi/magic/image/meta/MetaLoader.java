package me.sheimi.magic.image.meta;

import java.util.*;
import java.io.*;
import me.sheimi.util.*;
import me.sheimi.magic.image.*;

public abstract class MetaLoader {

  public abstract String get(String key, String metaKey); 
  public abstract Map<String, Object> get(String key);

  private static Map<String, Class<? extends MetaLoader>> loaders;
  static {
    loaders = new HashMap<String, Class<? extends MetaLoader>>();
    loaders.put("json", JsonMetaLoader.class);
  }
  public static MetaLoader getLoader(String filename) {
    String[] fns = filename.split("\\.");
    String subfix = fns[fns.length - 1];
    Class<? extends MetaLoader> clazz = loaders.get(subfix);
    MetaLoader loader = ReflectionUtils.newInstance(clazz, filename);
    return loader;
  }

  public static void main(String [] args) {
    MetaLoader loader = MetaLoader.getLoader(args[0]);
    Map<String, Object> meta = loader.get("hello");
    for (Map.Entry<String, Object> entry: meta.entrySet()) {
      System.out.println(entry.getKey());
      System.out.println(entry.getValue());
    }
  }

}
