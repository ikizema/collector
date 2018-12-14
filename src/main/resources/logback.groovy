import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ivan.dev.web.cli.RuntimeConfig
import static RuntimeConfig.Env.LOCAL
import static RuntimeConfig.Env.DEV
import static RuntimeConfig.Env.QA
import static RuntimeConfig.Env.UAT
import static RuntimeConfig.Env.PROD
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.OFF

appname = "easyweb"
generalPattern = "%d{HH:mm:ss.SSS} [%thread] [%-5level] [%logger{6}] : %msg%n"
bySecond = timestamp("yyyyMMdd'T'HHmmss")
logGeneralFilePath = "/var/log/apps/${appname}-${bySecond}.log"
logErrorFilePath = "/var/log/apps/${appname}-ERROR-${bySecond}.log"

// Environement dependent
switch (RuntimeConfig.getEnv()) {
	case LOCAL :
		logGeneralFilePath = "./tmp/log/${appname}-${bySecond}.log"
		init()
		logger("ivan.dev.web", DEBUG, ["STDOUT", "FILE"], false)
		root(INFO, ["STDOUT", "FILE"])
		break
	case DEV :
		init()
		logger("ivan.dev.web", DEBUG, ["STDOUT","FILE"], false)
		root(INFO, ["STDOUT","FILE"])
		root(ERROR, ["FILE-ERROR"])
		break
	case QA :
		init()
		root(INFO, ["STDOUT","FILE"])
		root(ERROR, ["FILE-ERROR"])
		break
	case UAT :
		init()
		root(INFO, ["STDOUT","FILE"])
		root(ERROR, ["FILE-ERROR"])
		break
	case PROD :
		init()
		root(INFO, ["STDOUT","FILE"])
		root(ERROR, ["FILE-ERROR"])
		break
	default: println "Please set environment.";
 }

def init() {

	appender("STDOUT", ConsoleAppender) {
		encoder(PatternLayoutEncoder) {
			pattern = generalPattern
		}
	}
	
	appender("FILE", FileAppender) {
		file = logGeneralFilePath
		append = true
		encoder(PatternLayoutEncoder) {
			pattern = generalPattern
		}
	}
	
	if (RuntimeConfig.getEnv() != LOCAL ){
		appender("FILE-ERROR", FileAppender) {
			file = logErrorFilePath
			append = true
			encoder(PatternLayoutEncoder) {
				pattern = generalPattern
			}
		}
	}
}

scan("30 seconds")