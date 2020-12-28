package hypermetro.command;


public class CommandSyntax {
    public static final String command =
            "^\\/(?<command>[\\S]*).*$";
    public static final String outputCommand =
            "^\\/(?<command>[\\S]*) (?<laneName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\"))$";
    public static final String appendCommand =
            "^\\/(?<command>[\\S]*) (?<laneName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\")) (?<stationName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\"))$";
    public static final String addHeadCommand =
            "^\\/(?<command>[\\S]*) (?<laneName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\")) (?<stationName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\"))$";
    public static final String removeCommand =
            "^\\/(?<command>[\\S]*) (?<laneName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\")) (?<stationName>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\"))$";
    public static final String connectCommand =
            "^\\/(?<command>[\\S]*) (?<lane1>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\")) (?<station1>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\")) (?<lane2>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\")) (?<station2>([^\\\"\\s]+)|(\\\"[^\\\"]+\\\"))$";
}