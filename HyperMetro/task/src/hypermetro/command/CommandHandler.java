package hypermetro.command;

import hypermetro.Lane;
import hypermetro.command.handler.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CommandHandler {
    static Pattern commonPattern = Pattern.compile(CommandSyntax.command, Pattern.CASE_INSENSITIVE
    );

    public static CommandHandler getInstance(String command) throws InvalidSyntaxException {
        Matcher matcher = commonPattern.matcher(command);
        if (!matcher.find()) {
            throw new InvalidSyntaxException("Invalid Command!");
        }
        switch (matcher.group("command").toLowerCase()) {
            case "output":
                return OutputCommandHandler.getInstance();
            case "append":
                return AppendCommandHandler.getInstance();
            case "add-head":
                return AddHeadCommandHandler.getInstance();
            case "remove":
                return RemoveCommandHandler.getInstance();
            case "connect":
                return ConnectCommandHandler.getInstance();
            case "exit":
                return null;
            default:
                throw new InvalidSyntaxException("Invalid Command");
        }
    }

    public abstract void executeCommand(Map<String, Lane> laneMap, String commandText);

    protected static String sanitizeString(String str) {
        return str == null ? null : str.replaceAll("\"", "").trim();
    }
}
