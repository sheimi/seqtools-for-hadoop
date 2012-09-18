/* TarToSeqFile.java - Convert tar files into Hadoop SequenceFiles.
 *
 * Copyright (C) 2012 Reason Zhang
 */

package me.sheimi.hadoop.seq;

import me.sheimi.magic.image.load.*;
import me.sheimi.magic.image.store.*;
import me.sheimi.magic.image.*;
import java.io.*;

public class TarToSeqFile {
	
  public static void main(String[] args) throws Exception {

    ImageLoader il = new TarImageLoader(args[0]);
    ImageStorage is = new SeqImageStorage(args[1]);
    new ImageConvertor(il, is).execute();
    
  }

}
