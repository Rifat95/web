package web.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.util.Util;

/**
 * Thread local singleton
 */
public final class App {
	private static final ThreadLocal<App> INSTANCE = new ThreadLocal<App>() {
		@Override
		protected App initialValue() {
			return new App();
		}
	};

	private Request request;
	private HttpServletResponse response;
	private Session session;
	private Page page;
	private int userId;
	private ArrayList<String> userPermissions;
	private Translator t;
	private Connection connection;

	private App() {}

	public static App getInstance() {
		return INSTANCE.get();
	}

	public Request getRequest() {
		return request;
	}

	public Session getSession() {
		return session;
	}

	public Page getPage() {
		return page;
	}

	public int getUserId() {
		return userId;
	}

	public Translator getT() {
		return t;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setUser(int id, ArrayList<String> permissions) {
		session.set("userId", id);
		session.set("userPermissions", permissions);
		userId = id;
		userPermissions = permissions;
	}

	public boolean access(String permission) {
		return userPermissions.contains(permission);
	}

	HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * Must be called at the start of core.Servlet.process() only.
	 */
	@SuppressWarnings("unchecked")
	void init(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		request = new Request(servletRequest);
		response = servletResponse;
		session = new Session(servletRequest.getSession());
		page = new Page();
		userId = (int) session.get("userId", 0);
		userPermissions = (ArrayList<String>) session.get("userPermissions", Util.strList("guest"));
		t = new Translator();
	}

	void setConnection(Connection c) {
		connection = c;
	}

	/**
	 * Must be called at the end of core.Servlet.process() only, to avoid memory leak.
	 */
	void clean() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// Ignore
			}
		}

		INSTANCE.remove();
	}
}
