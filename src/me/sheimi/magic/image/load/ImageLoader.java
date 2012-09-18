package me.sheimi.magic.image.load;

import java.util.*;
import me.sheimi.magic.image.*;
import java.io.*;

public abstract class ImageLoader implements Iterator<Image> {
  public abstract boolean hasNext();
  public abstract Image next();
  public abstract void close();
  public void remove() {
  }
}
