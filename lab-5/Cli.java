import java.util.ArrayList;
import java.util.HashMap;

class CliParser {
    private String[] booleanFlagsKeys;
    private String[] inputFlagsKeys;

    public CliParser(String[] booleanFlags, String[] inputFlags) {
        booleanFlagsKeys = booleanFlags.clone();
        inputFlagsKeys = inputFlags.clone();
    }

    public CliOutput parse(String[] args) {
        var booleanFlags = new HashMap<String, Boolean>();
        var inputFlags = new HashMap<String, String>();
        for (var booleanFlag : booleanFlagsKeys)
            booleanFlags.put(booleanFlag, false);
        for (var inputFlag : inputFlagsKeys)
            inputFlags.put(inputFlag, "");

        var currentInputFlag = "";
        var noFlags = new ArrayList<String>();
        for (String arg : args) {
            if (currentInputFlag != "" && !arg.startsWith("-")) {
                inputFlags.replace(currentInputFlag, arg);
                currentInputFlag = "";
            } else if (!arg.startsWith("-")) {
                noFlags.add(arg);
            } else if (currentInputFlag != "") {
                return new CliOutput("Не найден параметр флага " + currentInputFlag);
            } else if (booleanFlags.containsKey(arg)) {
                booleanFlags.replace(arg, true);
            } else if (inputFlags.containsKey(arg)) {
                currentInputFlag = arg;
            } else {
                return new CliOutput("Не найден флаг " + currentInputFlag);
            }
        }
        return new CliOutput(booleanFlags, inputFlags, noFlags);
    }

    public static class CliOutput {
        private final boolean status;
        private final String message;
        private final HashMap<String, Boolean> booleanFlags;
        private final HashMap<String, String> inputFlags;
        private final ArrayList<String> noFlags;

        private CliOutput(HashMap<String, Boolean> booleanFlags, HashMap<String, String> inputFlags, ArrayList<String> noFlags) {
            this.status = true;
            this.message = "";
            this.booleanFlags = new HashMap<>(booleanFlags);
            this.inputFlags = new HashMap<>(inputFlags);
            this.noFlags = new ArrayList<>(noFlags);
        }

        private CliOutput(String message) {
            this.status = false;
            this.message = message;
            this.booleanFlags = new HashMap<>();
            this.inputFlags = new HashMap<>();
            this.noFlags = new ArrayList<>();
        }

        public boolean getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public HashMap<String, Boolean> getBooleanFlags() {
            return booleanFlags;
        }

        public HashMap<String, String> getInputFlags() {
            return inputFlags;
        }

        public ArrayList<String> noFlags() {
            return noFlags;
        }
    }
}