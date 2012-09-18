/* SeqFileToTar.java - Convert seq files into tar.
 *
 * Copyright (C) 2012 Reason Zhang
 */

package me.sheimi.hadoop.seq;

import me.sheimi.magic.image.load.*;
import me.sheimi.magic.image.store.*;
import me.sheimi.magic.image.*;
import java.io.*;

public class SeqFileToTar {

  public static void main(String[] args) throws Exception {
    ImageLoader il = new SeqImageLoader(args[0]);
    ImageStorage is = new TarImageStorage(args[1]);
    new ImageConvertor(il, is).execute();
  }
}
