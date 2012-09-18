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
import me.sheimi.magic.image.*;
import java.io.*;

public interface ImageStorage {
  public void write(Image image);
  public void close();
}
