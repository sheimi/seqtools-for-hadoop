package me.sheimi.hbase;

import java.io.IOException;

import me.sheimi.hbase.filter.IntegerComparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class ClientTest {
	static Configuration cfg = null;
	static {
		cfg = HBaseConfiguration.create();
	}

	public static void main(String[] args) throws IOException {

		SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
				Bytes.toBytes("meta"), Bytes.toBytes("width"),
				CompareFilter.CompareOp.GREATER_OR_EQUAL,
				new IntegerComparator(300));

		HTable table = new HTable(cfg, "image");
		Scan s = new Scan();
		s.setFilter(filter1);
		ResultScanner ss = table.getScanner(s);
		for (Result r : ss) {
			System.out.println(Bytes.toInt(r.getValue(Bytes.toBytes("meta"),
					Bytes.toBytes("width"))));
		}
		try {
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
