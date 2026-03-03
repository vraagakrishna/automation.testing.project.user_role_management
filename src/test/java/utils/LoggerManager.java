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
        Logger rootLogger = Logger.getLogger(className);

        // Only add handlers once
        if (rootLogger.getHandlers().length == 0) {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(getFormatter());
            rootLogger.addHandler(consoleHandler);

            // Level
            rootLogger.setLevel(Level.INFO);
        }

        return rootLogger;
    }

    public static void initializeFileLogging() {
        if (isInitialized) return;

        try {
            File latestReportFolder = getLatestReportFolder();

            String logPath = latestReportFolder.getAbsolutePath() + "/execution.log";

            FileHandler fileHandler = new FileHandler(logPath, true);
            fileHandler.setFormatter(getFormatter());

            Logger rootLogger = LogManager.getLogManager()
                                          .getLogger("");
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
