package me.sheimi.hbase.filter;

import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

import me.sheimi.hbase.image.ImageSchema;
import me.sheimi.util.OptionList;

public class TagFilterCreator extends FilterCreator {

	@Override
	public void addFilter(OptionList options, FilterList filterList) {
		while (true) {
			String tag = options.next();
			if (tag.charAt(0) == '-') {
				break;
			}
			SingleColumnValueFilter filter = new SingleColumnValueFilter(
					ImageSchema.FAMILY_TAG, Bytes.toBytes(tag),
					CompareOp.EQUAL, ImageSchema.TAG_DEFAULT);
			filterList.addFilter(filter);
		}
		options.moveBack();
	}

}
