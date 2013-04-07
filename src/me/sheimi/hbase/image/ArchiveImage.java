package me.sheimi.hbase.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.sheimi.hbase.HBaseConfig;
import me.sheimi.magic.image.Image;
import me.sheimi.magic.image.load.ImageLoader;
import me.sheimi.magic.image.meta.MetaLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

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

		Put put = new Put(ImageSchema.genRowKey(image.getFilename()));

		Map<String, Object> metas = metaLoader.getMetas(image.getFilename());
		for (Map.Entry<String, Object> entry : metas.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof Integer) {
				put.add(ImageSchema.FAMILY_META, Bytes.toBytes(entry.getKey()),
						Bytes.toBytes((Integer) value));
			} else {
				put.add(ImageSchema.FAMILY_META, Bytes.toBytes(entry.getKey()),
						Bytes.toBytes((String) value));
			}
		}

		List<String> tags = metaLoader.getTags(image.getFilename());
		for (String tag : tags) {
			put.add(ImageSchema.FAMILY_TAG, Bytes.toBytes(tag),
					Bytes.toBytes(1));
		}

		put.add(ImageSchema.FAMILY_META, ImageSchema.META_FILENAME,
				Bytes.toBytes(image.getFilename()));
		put.add(ImageSchema.FAMILY_DATA, ImageSchema.DATA_IMAGE,
				image.getImage());
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

	public static void main(String[] args) {
		ArchiveImage ai = new ArchiveImage(ImageLoader.getLoader(args[0]),
				MetaLoader.getLoader(args[1]));
		ai.execute();
	}

}
