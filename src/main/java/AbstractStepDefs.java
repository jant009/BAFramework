import logger.LoggerFacade;

public class AbstractStepDefs {
    protected static final LoggerFacade LOGGER = new LoggerFacade();

    protected final void LogStepBegin(String stepName, String[] parameters) {
        LOGGER.info("BEGIN - " + stepName + "(" + composeParametersList(parameters) + ")");
    }

    protected final void LogStepBegin(String stepName, String parameter) {
        LogStepBegin(stepName, new String[]{parameter});
    }

    protected final void LogStepBegin(String stepName) {
        LogStepBegin(stepName, "");
    }

    protected final void LogStepEnd(String stepName, String[] parameters) {
        LOGGER.info("END - " + stepName + "(" + composeParametersList(parameters) + ")");
    }

    protected final void LogStepEnd(String stepName, String parameter) {
        LogStepEnd(stepName, new String[]{parameter});
    }

    protected final void LogStepEnd(String stepName) {
        LogStepEnd(stepName, "");
    }

    protected final void LogInfo(String stepName, String message) {
        LOGGER.info(stepName + " - " + message);
    }

    private static String composeParametersList(String[] parameters) {
        return String.join(", ", parameters);
    }
}