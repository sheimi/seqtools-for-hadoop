package me.sheimi.magic.image.store;

import java.util.*;
import me.sheimi.magic.image.*;
import java.io.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import me.sheimi.hadoop.seq.LocalSetup;

public class SeqImageStorage implements ImageStorage {

  SequenceFile.Writer output;
  LocalSetup setup;
  {
    try {
      setup = new LocalSetup();  
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SeqImageStorage(SequenceFile.Writer output) {
    this.output = output;
  }

  public SeqImageStorage(File file) throws IOException {
    Path outputPath = new Path(file.getAbsolutePath());
    this.output = SequenceFile.createWriter(setup.getLocalFileSystem(),
                                    setup.getConf(),
                                    outputPath,
                                    Text.class, BytesWritable.class,
                                    SequenceFile.CompressionType.BLOCK);
  }

  public SeqImageStorage(String filename) throws IOException {
    this(new File(filename));
  }

  public void write(Image image) {
    Text key = new Text(image.getFilename());
    BytesWritable value = new BytesWritable(image.encode());
    try {
      output.append(key, value);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}