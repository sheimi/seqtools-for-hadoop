package me.sheimi.hbase.filter;

import java.util.HashMap;
import java.util.Map;

import me.sheimi.util.OptionList;

import org.apache.hadoop.hbase.filter.FilterList;

public abstract class FilterCreator {
	private static Map<String, FilterCreator> loaders;
	static {
		loaders = new HashMap<String, FilterCreator>();
		loaders.put("-tag", new TagFilterCreator());
		loaders.put("-source", new MetaSourceFilterCreator());
		loaders.put("-filename", new MetaFilenameCreator());
		loaders.put("-format", new MetaFormatCreator());

	}

	public static FilterCreator getFilterCreator(OptionList options) {
		String option = options.next();
		FilterCreator creator = loaders.get(option);
		return creator;
	}

	public abstract void addFilter(OptionList options, FilterList filterList);
}
