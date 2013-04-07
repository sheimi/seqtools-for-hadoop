package me.sheimi.magic.image.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.sheimi.util.ReflectionUtils;

public abstract class MetaLoader {

	public abstract Map<String, Object> getMetas(String fileName);
	
	public abstract List<String> getTags(String fileName);

	private static Map<String, Class<? extends MetaLoader>> loaders;
	static {
		loaders = new HashMap<String, Class<? extends MetaLoader>>();
		loaders.put("json", JsonMetaLoader.class);
	}

	public static MetaLoader getLoader(String filename) {
		String[] fns = filename.split("\\.");
		String subfix = fns[fns.length - 1];
		Class<? extends MetaLoader> clazz = loaders.get(subfix);
		MetaLoader loader = ReflectionUtils.newInstance(clazz, filename);
		return loader;
	}

	public static void main(String[] args) {
		MetaLoader loader = MetaLoader.getLoader(args[0]);
		Map<String, Object> metas = loader.getMetas("hello");
		for (Map.Entry<String, Object> entry : metas.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}

}
