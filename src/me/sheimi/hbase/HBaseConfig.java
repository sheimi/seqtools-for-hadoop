package me.sheimi.hbase;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.conf.Configuration;

public class HBaseConfig {
	public static Configuration cfg = null;
	static {
		cfg = HBaseConfiguration.create();
	}
}
