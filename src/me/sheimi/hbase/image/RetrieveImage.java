package me.sheimi.hbase.image;

import java.io.IOException;
import java.util.Arrays;

import me.sheimi.hbase.HBaseConfig;
import me.sheimi.magic.image.Image;
import me.sheimi.magic.image.store.ImageStorage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;

public class RetrieveImage {

	private static final Log LOG = LogFactory.getLog(RetrieveImage.class);
	private static Configuration cfg = HBaseConfig.cfg;

	private ImageStorage storage;
	private FilterList filterList = new FilterList();

	public RetrieveImage(ImageStorage storage) {
		this.storage = storage;
	}

	public void scanTable() {
		try {
			HTable table = new HTable(cfg, ImageSchema.TABLE_NAME);
			Scan scan = new Scan();
			scan.setFilter(filterList);
			ResultScanner rs = table.getScanner(scan);
			packImage(rs);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initFilters(String[] args) {
		for (String arg : args) {
		}
	}

	private void packImage(ResultScanner rs) {
		LOG.info("Begin Pack Images ... ");
		for (Result r : rs) {
			byte[] filename = r.getValue(ImageSchema.FAMILY_META,
					ImageSchema.META_FILENAME);
			byte[] image_raw = r.getValue(ImageSchema.FAMILY_DATA,
					ImageSchema.DATA_IMAGE);
			Image image = new Image(image_raw, Bytes.toString(filename));
			storage.write(image);
		}
		storage.close();
		LOG.info("Image Pack Finished ... ");
	}

	public static void main(String[] args) {
		RetrieveImage ri = new RetrieveImage(ImageStorage.getStorage(args[0]));
		if (args.length > 1) {
			ri.initFilters(Arrays.copyOfRange(args, 1, args.length));
		}
		ri.scanTable();
	}

}
