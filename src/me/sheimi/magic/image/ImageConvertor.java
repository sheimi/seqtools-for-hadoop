/* Convert Image Formats
 *
 * Copyright (C) 2012 Reason Zhang
 */

package me.sheimi.hadoop.seq;

import me.sheimi.magic.image.load.*;
import me.sheimi.magic.image.store.*;
import java.io.*;

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

}
