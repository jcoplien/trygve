package configuration;

public class ConfigurationOptions {
	public static boolean roleDebug1Enabled() {	// general
		return false;	/* ROLEDEBUG */
	}
	public static boolean roleDebug2Enabled() {	// walktrace
		return false;	/* ROLEDEBUG2 */
	}
	public static boolean parameterDebugFlagSet() {
		return parameterDebugFlag_;
	}
	public static void setParameterDebugFlag(boolean tf) {
		parameterDebugFlag_ = tf;
	}
	private static boolean parameterDebugFlag_;
}
