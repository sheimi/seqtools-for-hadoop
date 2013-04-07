package me.sheimi.magic.image.meta;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Here is the definition of meta file
 * json object
 * 	key: filename
 *  value: json object
 *  	key1: metas
 *  	value: json object
 *  		key: metaName
 *  		value: metaValue
 *  	key2: tags
 *  	value: list [tagNames]
 * 
 * @author sheimi
 */
public class JsonMetaLoader extends MetaLoader {

	private Map<String, Object> datas;
	private ObjectMapper mapper = new ObjectMapper();
	
	private static final String KEY_METAS = "metas";
	private static final String KEY_TAGS = "tags";

	@SuppressWarnings("unchecked")
	public JsonMetaLoader(File file) throws IOException {
		datas = mapper.readValue(file, Map.class);
	}

	public JsonMetaLoader(String filename) throws IOException {
		this(new File(filename));
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMetas(String fileName) {
		Map<String, Object> data = (Map<String, Object>) datas.get(fileName);
		Map<String, Object> metas = (Map<String, Object>) data.get(KEY_METAS);
		return metas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTags(String fileName) {
		Map<String, Object> data = (Map<String, Object>) datas.get(fileName);
		List<String> tags = (List<String>) data.get(KEY_TAGS);
		return tags;
	}

}
