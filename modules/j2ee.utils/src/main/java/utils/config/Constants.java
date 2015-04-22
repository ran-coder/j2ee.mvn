package utils.config;


public class Constants {
	private static ConfigUtil	config;
	static {
		if (config == null)	config = new ConfigUtil("jdbc","webutil");
	}

	public final static boolean	WEBUTIL_DOENCODE	= config.getBoolean("webutil.doencode");
	public final static String	WEBUTIL_ENCODE		= config.get("webutil.encode", null);
	public final static String	WEBUTIL_OLDCHARSET	= config.get("webutil.oldCharset", null);
	public final static String	WEBUTIL_NEWCHARSET	= config.get("webutil.newCharset", null);

	public final static String	DB_DATABASE			= config.get("webutil.db.database", null);
	public final static String	DB_SOURCE			= config.get("webutil.db.source", null);
	public final static String	DB_JNDI				= config.get("webutil.db.jndi", null);
	public final static String	DB_DRIVER			= config.get("webutil.db.driver", null);
	public final static String	DB_URL				= config.get("webutil.db.url", null);
	public final static String	DB_USERNAME			= config.get("webutil.db.username", null);
	public final static String	DB_PASSWORD			= config.get("webutil.db.password", null);
}
