/* SeqFileToTar.java - Convert seq files into tar.
 *
 * Copyright (C) 2012 Reason Zhang
 */

package me.sheimi.hadoop.seq;

/* From ant.jar, http://ant.apache.org/ */
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarOutputStream;

/* From hadoop-*-core.jar, http://hadoop.apache.org/
 * Developed with Hadoop 1.0.1. */
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.sheimi.util.SeqImage;

public class SeqFileToTar {

	
  private File inputFile;
  private File outputFile;
  private LocalSetup setup;

  public SeqFileToTar() throws Exception {
    setup = new LocalSetup();
  }

  public void setInput(File inputFile) {
    this.inputFile = inputFile;
  }

  public void setOutput(File outputFile) {
    this.outputFile = outputFile;
  }

  public void execute() throws Exception {
    TarOutputStream output = null;
    SequenceFile.Reader input = null;
    try {
      input = openInputFile();
      output = openOutputFile();
      TarEntry entry;
      Text key = new Text();
      BytesWritable value = new BytesWritable();
      while (input.next(key, value)) {
        String filename = key.toString();
        String[] fns = filename.split("/");
        entry = new TarEntry(fns[fns.length - 1]);
        byte[] data = value.get();

        SeqImage image = SeqImage.decode(data);
        int size = image.getSize();
        byte[] raw = image.getImage();

        System.out.println(size);
        entry.setSize(size);
        output.putNextEntry(entry);
        //output.write(data, LocalSetup.SIZE_LEN, size);
        output.write(raw);
        output.closeEntry();
      }
    } finally {
      if (input != null) { input.close(); }
      if (output != null) { output.close(); }
    }
  }

  private TarOutputStream openOutputFile() throws Exception {
    return new TarOutputStream(new FileOutputStream(outputFile));
  }

  private SequenceFile.Reader openInputFile() throws Exception {
    Path inputPath = new Path(inputFile.getAbsolutePath());
    return new SequenceFile.Reader(setup.getLocalFileSystem(), inputPath, setup.getConf());
  }

  public static void main(String[] args) {
    
    if (args.length != 2) {
      exitWithHelp();
    }

    try {
      SeqFileToTar me = new SeqFileToTar();
      me.setInput(new File(args[0]));
      me.setOutput(new File(args[1]));
      me.execute();
    } catch (Exception e) {
      e.printStackTrace();
      exitWithHelp();
    }
  }

  public static void exitWithHelp() {
    System.err.println("Usage: java org.altlaw.hadoop.TarToSeqFile <tarfile> <output>\n\n" +
                       "<tarfile> may be GZIP or BZIP2 compressed, must have a\n" +
                       "recognizable extension .tar, .tar.gz, .tgz, .tar.bz2, or .tbz2.");
    System.exit(1);
  }
}
