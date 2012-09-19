package me.sheimi.hbase.filter;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.*;

public class IntegerComparator extends WritableByteArrayComparable {

  public IntegerComparator() { }
  //int valueI;

  public IntegerComparator(int value) {
    super(Bytes.toBytes(value));
    //valueI = value;
  }

  public int compareTo(byte[] value) {
    return 0;
    /*
    int v = Bytes.toInt(value);
    System.out.println(v);
    return valueI - v;
    */
  }

  @Override
  public int compareTo(byte [] value, int offset, int length) {
    throw new UnsupportedOperationException();
  }
}
