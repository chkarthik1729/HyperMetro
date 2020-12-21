import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;


public class Tests extends StageTest<String> {
    // testing a case from example
    @DynamicTestingMethod
    CheckResult test1() {

        TestedProgram main = new TestedProgram(Main.class);

        String output = main.start("./test/baltimore.txt").trim();

        checkDepots(output);
        checkOutputLength(output, 14);

        assertStations(output, new String[]{"Owings Mills", "Old Court", "Milford Mill", "Reiserstown Plaza",
            "Rogers Avenue", "West Cold Spring", "Mondawmin", "Penn North", "Uptown", "State Center",
            "Lexington Market", "Charles Center", "Shot Tower/Market Place", "Johns Hopkins Hospital"});

        return CheckResult.correct();
    }

    // empty file test
    @DynamicTestingMethod
    CheckResult test2() {

        TestedProgram main = new TestedProgram(Main.class);

        String output = main.start("./test/empty.txt");
        if (output.trim().length() != 0) {
            return CheckResult.wrong("Your program should not print anything if the file is empty.");
        }

        return CheckResult.correct();
    }

    // not existing file check
    @DynamicTestingMethod
    CheckResult test3() {

        TestedProgram main = new TestedProgram(Main.class);
        String output = main.start("tHiS_fIlE_DoEs_nOt_ExIsT.txt").toLowerCase();

        if (output.trim().length() == 0) {
            return CheckResult.wrong("The program did not print anything when the file that doesn't exist was passed.");
        }
        if (output.startsWith("depot") || output.endsWith("depot") || !output.contains("error")) {
            return CheckResult.wrong("It looks like the program did not print an error message when the file that doesn't exist was passed.\n" +
                "Your output should contain 'error'.");
        }

        return CheckResult.correct();
    }

    // positive test with another metro
    @DynamicTestingMethod
    CheckResult test4() {

        TestedProgram main = new TestedProgram(Main.class);
        String output = main.start("./test/samara.txt").trim();

        checkDepots(output);
        checkOutputLength(output, 10);

        assertStations(output, new String[]{"Alabinskaya", "Rossiyskaya", "Moskovskaya", "Gagarinskaya",
            "Sportivnaya", "Sovetskaya", "Pobeda", "Bezymyanka", "Kirovskaya", "Yungorodok"});

        return CheckResult.correct();
    }

    // checks for "depot" at the start and at the end
    void checkDepots(String output) {
        output = output.trim().toLowerCase();
        if (!output.startsWith("depot")) {
            throw new WrongAnswer("Your output should start with 'depot'.");
        } else if (!output.endsWith("depot")) {
            throw new WrongAnswer("Your output should end with 'depot'.");
        }
    }


    // checks number of stations in output
    void checkOutputLength(String output, int correctLength) {
        int length = output.trim().split("\n").length;
        if (length != correctLength) {
            throw new WrongAnswer("You output contains wrong number of lines.\n" +
                "Expected: " + correctLength + " lines\n" +
                "Your output: " + length + " lines");
        }
    }

    // checks stations
    void assertStations(String output, String[] stations) {

        String[] sOutput = output.trim().split("\n");

        for (int i = 0; i < stations.length; i++) {

            String currentLine = sOutput[i].trim();
            if (currentLine.split("-").length != 3) {
                throw new WrongAnswer("There is should be 3 stations in one line.\n" +
                    "Treat 'depot' as a station name");
            }

            // Checking the first line
            if (i == 0) {
                for (int j = 0; j < 2; j++) {
                    if (!currentLine.contains(stations[i + j])) {
                        throw new WrongAnswer("Can't find station '" + stations[i + j] + "' in the line number " + (i + 1));
                    }
                }
                // Checking the last line
            } else if (i == stations.length - 1) {
                for (int j = 0; j < 2; j++) {
                    if (!currentLine.contains(stations[i + j - 1])) {
                        throw new WrongAnswer("Can't find station '" + stations[i + j - 1] + "' in the line number " + (i + 1));
                    }
                }
                // Checking the rest lines
            } else {
                for (int j = 0; j < 3; j++) {
                    if (!currentLine.contains(stations[i + j - 1])) {
                        throw new WrongAnswer("Can't find station '" + stations[i + j - 1] + "' in the line number " + (i + 1));
                    }
                }
            }
        }
    }
}