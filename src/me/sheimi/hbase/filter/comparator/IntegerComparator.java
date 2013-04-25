package me.sheimi.hbase.filter.comparator;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.*;

public class IntegerComparator extends WritableByteArrayComparable {

	public IntegerComparator() {
	}

	public IntegerComparator(int value) {
		super(Bytes.toBytes(value));
	}

	@Override
	public int compareTo(byte[] value, int offset, int length) {
		int v1 = Bytes.toInt(value, offset, length);
		int v2 = Bytes.toInt(getValue());
		return v2 - v1;
	}
}
