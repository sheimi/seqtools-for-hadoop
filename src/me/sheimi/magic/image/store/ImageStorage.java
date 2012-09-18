package me.sheimi.magic.image.store;

import java.util.*;
import me.sheimi.magic.image.*;
import java.io.*;

public interface ImageStorage {
  public void write(Image image);
  public void close();
}
