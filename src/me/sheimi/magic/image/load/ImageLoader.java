package me.sheimi.magic.image.load;

import java.util.*;
import me.sheimi.magic.image.*;
import java.io.*;

public abstract class ImageLoader implements Iterator<Image> {
  protected boolean end = false;
  public abstract Image next();
  public abstract void close();
  public boolean hasNext() {
    return !end;
  }
  public void remove() {
  }
}
