package me.sheimi.magic.image.store;

import java.io.File;
import java.io.IOException;

import me.sheimi.hadoop.LocalSetup;
import me.sheimi.magic.image.Image;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class SeqImageStorage extends ImageStorage {

  private static final Log LOG = LogFactory.getLog(SeqImageStorage.class);
  private SequenceFile.Writer output;
  private LocalSetup setup;
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
