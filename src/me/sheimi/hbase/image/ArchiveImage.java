package me.sheimi.hbase.image;

import java.io.*;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.commons.logging.*;

import me.sheimi.hbase.*;
import me.sheimi.magic.image.*;
import me.sheimi.magic.image.load.*;
import me.sheimi.magic.image.meta.*;

public class ArchiveImage {

  private static final Log LOG = LogFactory.getLog(ArchiveImage.class);
  private static Configuration cfg = HBaseConfig.cfg;

  private ImageLoader imageLoader;
  private MetaLoader metaLoader;

  public ArchiveImage(ImageLoader imageLoader, MetaLoader metaLoader) {
    this.imageLoader = imageLoader;
    this.metaLoader = metaLoader;
  }

  public Put genPut(Image image) {
    LOG.info("generating hbase entry for image: " + image.getFilename());

    Map<String, Object> meta = metaLoader.get(image.getFilename());
    Put put = new Put(ImageSchema.genRowKey(image.getFilename()));
    for (Map.Entry<String, Object> entry: meta.entrySet()) {
      Object value = entry.getValue();
      if (value instanceof Integer) {
        put.add(ImageSchema.FAMILY_META, Bytes.toBytes(entry.getKey()),
                Bytes.toBytes((Integer)value));
      } else {
        put.add(ImageSchema.FAMILY_META, Bytes.toBytes(entry.getKey()),
                Bytes.toBytes((String)value));
      }
    }
    put.add(ImageSchema.FAMILY_DATA, ImageSchema.DATA_IMAGE, image.getImage());
    return put;
  }

  public void execute() {
    LOG.info("starting inserting image ...");
    HTablePool pool = new HTablePool(cfg, 1000);
    HTable table = (HTable) pool.getTable(ImageSchema.TABLE_NAME);
    List<Put> puts = new ArrayList<Put>();
    while (imageLoader.hasNext()) {
      Put put = genPut(imageLoader.next());
      puts.add(put);
    }
    imageLoader.close();
    LOG.info("inserting ...  ");
    try {
      table.put(puts);
    } catch (IOException e) {
      e.printStackTrace();
    }
    LOG.info("inserted ...  ");
    try {
      table.close();
      pool.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String [] args) {
    ArchiveImage ai = new ArchiveImage(ImageLoader.getLoader(args[0]),
                                       MetaLoader.getLoader(args[1]));
    ai.execute();
  }

}
