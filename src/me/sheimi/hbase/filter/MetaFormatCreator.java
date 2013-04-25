package me.sheimi.hbase.filter;

import me.sheimi.hbase.image.ImageSchema;
import me.sheimi.util.OptionList;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class MetaFormatCreator extends FilterCreator {

	@Override
	public void addFilter(OptionList options, FilterList filterList) {
		String format = options.next();
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				ImageSchema.FAMILY_META, ImageSchema.META_FORMAT,
				CompareOp.EQUAL, Bytes.toBytes(format));
		filterList.addFilter(filter);
	}

}
