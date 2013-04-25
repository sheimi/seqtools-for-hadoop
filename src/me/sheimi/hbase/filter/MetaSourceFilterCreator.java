package me.sheimi.hbase.filter;

import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

import me.sheimi.hbase.image.ImageSchema;
import me.sheimi.util.OptionList;

public class MetaSourceFilterCreator extends FilterCreator {

	@Override
	public void addFilter(OptionList options, FilterList filterList) {
		String source = options.next();
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				ImageSchema.FAMILY_META, ImageSchema.META_SOURCE,
				CompareOp.EQUAL, new RegexStringComparator(source));
		filterList.addFilter(filter);
	}

}
