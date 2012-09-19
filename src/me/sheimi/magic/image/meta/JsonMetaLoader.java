package me.sheimi.magic.image.meta;

import java.io.*;
import java.util.*;
import me.sheimi.util.*;
import org.codehaus.jackson.map.*;

public class JsonMetaLoader extends MetaLoader {

  private Map<String, Object> metas;
  private ObjectMapper mapper = new ObjectMapper();

  public JsonMetaLoader(File file) throws IOException {
    metas = mapper.readValue(file, Map.class);
  }

  public JsonMetaLoader(String filename) throws IOException {
    this(new File(filename));
  }

  public String get(String key, String metaKey) {
    Map<String, Object> meta = (Map<String, Object>)(metas.get(key));
    return (String) meta.get(metaKey);
  }

  public Map<String, Object> get(String key) {
    return (Map<String, Object>) metas.get(key);
  }

}
