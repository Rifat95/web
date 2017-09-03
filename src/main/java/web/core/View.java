package web.core;

import java.io.StringWriter;
import java.util.HashMap;
import web.util.ServerException;
import web.util.Util;

public final class View {
	public static final String CORE = "core/";
	public static final String VIEW = "view/";
	public static final String ENTITY = "entity/";

	private String file;
	private HashMap<String, Object> data;

	public View(String name) {
		this(name, VIEW);
	}

	public View(String name, String type) {
		file = "/WEB-INF/templates/" + type + name + ".tpl";
		data = new HashMap<>();
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public View setData(HashMap<String, Object> map) {
		data = map;
		return this;
	}

	public View add(String var, Object value) {
		data.put(var, value);
		return this;
	}

	@Override
	public String toString() {
		data.put("u", Util.getInstance());
		data.put("t", App.getInstance().getT());

		try {
			StringWriter sw = new StringWriter();
			Servlet.getEngine().getTemplate(file).evaluate(sw, data);
			return sw.toString();
		} catch (Exception e) {
			throw new ServerException();
		}
	}
}
