package web.core;

import javax.servlet.http.HttpSession;

public final class Session {
	private HttpSession servletSession;

	public Session(HttpSession servletSession) {
		this.servletSession = servletSession;
	}

	public Object get(String attr) {
		return servletSession.getAttribute(attr);
	}

	public Object get(String attr, Object defaultValue) {
		Object sessionAttr = servletSession.getAttribute(attr);

		if (sessionAttr != null) {
			return sessionAttr;
		} else {
			servletSession.setAttribute(attr, defaultValue);
			return defaultValue;
		}
	}
}