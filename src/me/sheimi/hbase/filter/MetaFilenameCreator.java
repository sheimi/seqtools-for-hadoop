package me.sheimi.hbase.filter;

import me.sheimi.hbase.image.ImageSchema;
import me.sheimi.util.OptionList;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;

public class MetaFilenameCreator extends FilterCreator {

	@Override
	public void addFilter(OptionList options, FilterList filterList) {
		String filename = options.next();
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				ImageSchema.FAMILY_META, ImageSchema.META_FILENAME,
				CompareOp.EQUAL, new RegexStringComparator(filename));
		filterList.addFilter(filter);
	}

}
