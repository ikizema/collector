package ivan.dev.web.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public final class RuntimeConfig {
	
	public static enum Env {LOCAL, DEV, QA, UAT, PROD}
	
    @Option(name="-e", aliases = { "--env" }, usage="Environnement <Opt>", metaVar="[LOCAL, DEV, QA, UAT, PROD]", required=false)
    private static Env env = Env.LOCAL;
	
    @Option(name="-p",aliases = { "--port" }, usage="Port number <Opt>", metaVar="PORT", required=false)
    private static int port = 8080;
    
    @Option(name="-h",aliases = { "--help" }, usage="Print help message.", hidden=true)
    private static boolean help = false;

    @Argument
    private static List<String> arguments = new ArrayList<String>();

	public static int getPort() {
		return port;
	}

	public static List<String> getArguments() {
		return arguments;
	}

	public static Env getEnv() {
		return env;
	}
	
	public static boolean isHelp() {
		return help;
	}

	public static Map<String, Object> getProperties() {
		Map<String,Object> properties = new HashMap<String, Object>();
		properties.put("env", env);
		properties.put("port", port);
		return properties;
	}
}
