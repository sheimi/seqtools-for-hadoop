package me.sheimi.hbase.image;

import java.io.IOException;
import java.util.Arrays;

import me.sheimi.hbase.HBaseConfig;
import me.sheimi.hbase.filter.FilterCreator;
import me.sheimi.magic.image.Image;
import me.sheimi.magic.image.store.ImageStorage;
import me.sheimi.util.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;

public class RetrieveRowKey {

	private static final Log LOG = LogFactory.getLog(RetrieveRowKey.class);
	private static Configuration cfg = HBaseConfig.cfg;

	private ImageStorage mStorage;
	private FilterList mFilterList;
	private OptionList mOptionList;

	public RetrieveRowKey(ImageStorage storage) {
		mStorage = storage;
		mFilterList = new FilterList();
	}

	public void scanTable() {
		try {
			HTable table = new HTable(cfg, ImageSchema.TABLE_NAME);
			Scan scan = new Scan();
			scan.setFilter(mFilterList);
			ResultScanner rs = table.getScanner(scan);
			packImage(rs);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			mStorage.write(image);
		}
		mStorage.close();
		LOG.info("Image Pack Finished ... ");
	}

	/**
	 * [output name]
	 * 
	 * @param args
	 *            [filter args]
	 */
	public static void main(String[] args) {
		RetrieveRowKey ri = new RetrieveRowKey(ImageStorage.getStorage(args[0]));
		if (args.length > 1) {
			ri.initFilters(Arrays.copyOfRange(args, 1, args.length));
		}
		ri.scanTable();
	}

	/**
	 * filter args
	 * 
	 * @param args
	 */
	public void initFilters(String[] args) {
		mOptionList = new OptionList(args);
		while (mOptionList.hasNext()) {
			FilterCreator.getFilterCreator(mOptionList).addFilter(mOptionList,
					mFilterList);
		}
	}

}
