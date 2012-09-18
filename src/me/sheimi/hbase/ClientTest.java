package me.sheimi.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.*;
import java.io.IOException;

public class ClientTest {
  static Configuration cfg = null;
  static {
    cfg = HBaseConfiguration.create();
  }
  public static void main(String [] args) throws IOException {
    
    SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
      Bytes.toBytes("meta"),
      Bytes.toBytes("size"),
      CompareFilter.CompareOp.EQUAL,
      Bytes.toBytes("100*100")
    );

    HTable table = new HTable(cfg, "image");
    Scan s = new Scan();
    s.setFilter(filter1);
    ResultScanner ss = table.getScanner(s);
    for(Result r:ss){
      System.out.println(new String(r.getValue(Bytes.toBytes("data"),
              Bytes.toBytes("data"))));
    }
  }
}

