package utils;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class LoggerManager {

    // <editor-fold desc="Class Fields / Constants">
    private static boolean isInitialized = false;
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public static Logger getLogger(String className) {
        Logger logger = Logger.getLogger(className);

        // Only add handlers once
        if (logger.getHandlers().length == 0) {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(getFormatter());
            logger.addHandler(consoleHandler);

            // Level
            logger.setLevel(Level.INFO);
        }

        return logger;
    }

    public static void initializeFileLogging() {
        if (isInitialized) return;

        try {
            File latestReportFolder = getLatestReportFolder();

            String logPath = latestReportFolder.getAbsolutePath() + "/execution.log";

            Logger rootLogger = LogManager.getLogManager()
                                          .getLogger("");

            // Remove default console handler (this causes duplicates)
            for (Handler handler : rootLogger.getHandlers()) {
                if (handler instanceof ConsoleHandler)
                    rootLogger.removeHandler(handler);
            }

            FileHandler fileHandler = new FileHandler(logPath, true);
            fileHandler.setFormatter(getFormatter());

            rootLogger.addHandler(fileHandler);

            isInitialized = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void logToReport(String message) {
        ExtentCucumberAdapter.addTestStepLog(">>> " + message);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private static Formatter getFormatter() {
        return new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(
                        "[%1$tF %1$tT] [%2$s] %3$s%n",
                        lr.getMillis(),
                        lr.getLoggerName(),
                        lr.getMessage()
                );
            }
        };
    }

    private static File getLatestReportFolder() {
        String baseFolder = "ExtentReports/Report_ ";
        String dateTimePattern = "d_MMM_YY__HH_mm";

        String timestamp = LocalDateTime.now()
                                        .format(DateTimeFormatter.ofPattern(dateTimePattern));

        String reportFolderPath = baseFolder + timestamp;

        File reportFolder = new File(reportFolderPath);

        if (!reportFolder.exists())
            reportFolder.mkdirs();

        return reportFolder;
    }
    // </editor-fold>

}
