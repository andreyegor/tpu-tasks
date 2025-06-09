import java.util.ArrayList;
import java.util.HashMap;

public class CliIn {
    private final boolean status;
    private final String message;
    private final ArrayList<String> noFlags;
    private final HashMap<String, Boolean> booleanFlags;
    private final HashMap<String, String> inputFlags;

    private CliIn(boolean status, StringBuilder message, ArrayList<String> noFlags,
            HashMap<String, Boolean> booleanFlags, HashMap<String, String> inputFlags) {
        this.status = status;
        this.message = message.toString();
        this.noFlags = new ArrayList<>(noFlags);
        this.booleanFlags = new HashMap<>(booleanFlags);
        this.inputFlags = new HashMap<>(inputFlags);
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Boolean> getBooleanFlags() {
        return new HashMap<>(booleanFlags);
    }

    public HashMap<String, String> getInputFlags() {
        return new HashMap<>(inputFlags);
    }

    public ArrayList<String> getNoFlags() {
        return new ArrayList<>(noFlags);
    }

    public static class Parser {
        private static final String FLAG_NOT_FOUND_MSG = "Флаг %s не существует.\n";
        private static final String FLAG_INPUT_NOT_FOUND_MSG = "Не найден параметр флага %s.\n";

        private boolean status;
        private final StringBuilder message;
        private final ArrayList<String> noFlags;
        private final HashMap<String, Boolean> booleanFlags;
        private final HashMap<String, String> inputFlags;

        Parser(String[] booleanFlags, String[] inputFlags) {

            this.status = true;
            this.message = new StringBuilder();
            this.noFlags = new ArrayList<>();
            this.booleanFlags = new HashMap<>();
            this.inputFlags = new HashMap<>();
            for (var booleanFlag : booleanFlags)
                this.booleanFlags.put(booleanFlag, false);
            for (var inputFlag : inputFlags)
                this.inputFlags.put(inputFlag, "");
        }

        public Parser parse(String[] args) {
            var curInputFlag = "";
            for (String arg : args) {
                if (!curInputFlag.isEmpty() && !arg.startsWith("-")) {
                    inputFlags.replace(curInputFlag, arg);
                    curInputFlag = "";
                } else if (!arg.startsWith("-")) {
                    noFlags.add(arg);
                } else if (!curInputFlag.isEmpty()) {
                    status = false;
                    message.append(String.format(FLAG_INPUT_NOT_FOUND_MSG, curInputFlag));
                    curInputFlag = "";
                } else if (booleanFlags.containsKey(arg)) {
                    booleanFlags.replace(arg, true);
                } else if (inputFlags.containsKey(arg)) {
                    curInputFlag = arg;
                } else {
                    status = false;
                    message.append(String.format(FLAG_NOT_FOUND_MSG, arg));
                }
            }
            return this;
        }

        public CliIn build() {
            return new CliIn(status, message, noFlags, booleanFlags, inputFlags);
        }
    }
}