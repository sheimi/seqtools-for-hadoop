package me.sheimi.hbase.image;

import java.io.IOException;

import me.sheimi.hbase.HBaseConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

public class ImageSchema {

	private static final Log LOG = LogFactory.getLog(ImageSchema.class);
	private static Configuration cfg = HBaseConfig.cfg;

	public static final String TABLE_NAME = "image";

	public static final byte[] FAMILY_META = Bytes.toBytes("meta");
	public static final byte[] FAMILY_DATA = Bytes.toBytes("data");
	public static final byte[] FAMILY_TAG = Bytes.toBytes("tags");

	public static final byte[] META_FILENAME = Bytes.toBytes("filename");
	public static final byte[] META_FORMAT = Bytes.toBytes("format");
	public static final byte[] META_SOURCE = Bytes.toBytes("source");
	public static final byte[] META_SIZE = Bytes.toBytes("size");
	public static final byte[] META_HEIGHT = Bytes.toBytes("height");
	public static final byte[] META_WIDTH = Bytes.toBytes("width");

	public static final byte[] DATA_IMAGE = Bytes.toBytes("image");
	
	public static final byte[] TAG_DEFAULT = Bytes.toBytes(1);

	public static void createTable() {
		LOG.info("starting create table ...");
		HBaseAdmin hBaseAdmin;
		try {
			hBaseAdmin = new HBaseAdmin(cfg);
			if (hBaseAdmin.tableExists(TABLE_NAME)) {
				LOG.info(String.format("Table '%s' exists", TABLE_NAME));
				hBaseAdmin.disableTable(TABLE_NAME);
				hBaseAdmin.deleteTable(TABLE_NAME);
				LOG.info(String.format("deleting table '%s' ... ", TABLE_NAME));
			}
			LOG.info(String.format("creating table '%s' ... ", TABLE_NAME));
			HTableDescriptor tableDescriptor = new HTableDescriptor(TABLE_NAME);
			tableDescriptor.addFamily(new HColumnDescriptor(FAMILY_META));
			tableDescriptor.addFamily(new HColumnDescriptor(FAMILY_DATA));
			tableDescriptor.addFamily(new HColumnDescriptor(FAMILY_TAG));
			hBaseAdmin.createTable(tableDescriptor);
			hBaseAdmin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.info("end create table ......");
	}

	public static byte[] genRowKey(String name) {
		long time = System.currentTimeMillis();
		return Bytes.toBytes(time + "_" + name);
	}

	public static void main(String[] args) {
		if (args[0].equals("create_table")) {
			createTable();
		} else {
			System.out.println(new String(genRowKey("hello")));
		}
	}

}
