package bboe.prometheusproxy.cfprometheusproxy;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class MetricCleaner {
    private Set<Character> validGuidCharacters = new HashSet<>(Arrays.asList('a', 'b','c','d','e','f','0','1','2','3','4','5','6','7','8','9','0'));

    public String cleanMessage(String message) {
        String[] lines = message.split("\\r?\\n");

        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if(result.length() > 0) {
                result.append('\n');
            }
            result.append(cleanMessageLine(line));
        }

        return result.toString();
    }

    public String cleanMessageLine(String messageLine) {
        if(messageLine.startsWith("#")) {
            if(messageLine.startsWith("# TYPE ")) {
                String remainder = messageLine.substring(7);
                int nextWhitespace = remainder.indexOf(' ');
                if(nextWhitespace == -1) {
                    return messageLine;
                }

                String fieldName = remainder.substring(0,nextWhitespace);
                String newFieldName = cleanFieldName(fieldName);

                return "# TYPE " + newFieldName + remainder.substring(nextWhitespace);
            } else if(messageLine.startsWith("# HELP ")) {
                String remainder = messageLine.substring(7);
                int nextWhitespace = remainder.indexOf(' ');
                if(nextWhitespace == -1) {
                    return messageLine;
                }

                String fieldName = remainder.substring(0,nextWhitespace);
                String newFieldName = cleanFieldName(fieldName);

                return "# HELP " + newFieldName + remainder.substring(nextWhitespace);
            } else {
                return messageLine;
            }
        } else {
            int bracketPos = messageLine.indexOf('{');
            if(bracketPos < 0) {
                bracketPos = messageLine.indexOf(' ');
            }
            if(bracketPos < 0) {
                return messageLine;
            }

            String fieldName = messageLine.substring(0, bracketPos);
            String newFieldName = cleanFieldName(fieldName);

            return newFieldName + messageLine.substring(bracketPos);
        }
    }

    public String cleanFieldName(String fieldName) {
        boolean startsWithUnderscore = fieldName.startsWith("_");
        if(!startsWithUnderscore) {
            return fieldName.substring(0,1) + cleanFieldName(fieldName.substring(1));
        } else {
            String fieldNameNoUnderscores = fieldName.replaceAll("_", "");
            if(fieldNameNoUnderscores.length() < 32) {
                return fieldName;
            }
            String potentialGUID = fieldNameNoUnderscores.substring(0, 32);
            if(isRemovableGUID(potentialGUID)) {
                return trimGUIDFromString(potentialGUID, fieldName.substring(1));
            } else {
                return fieldName.substring(0,1) + cleanFieldName(fieldName.substring(1));
            }
        }
    }

    public String trimGUIDFromString(String guid, String fromString) {
        if(guid == null || guid.length() == 0) {
            return fromString;
        }
        if(fromString == null || fromString.length() == 0) {
            return "";
        }
        if(fromString.startsWith("_")) {
            return trimGUIDFromString(guid, fromString.substring(1));
        }

        if(guid.toCharArray()[0] == fromString.toCharArray()[0]) {
            return trimGUIDFromString(guid.substring(1), fromString.substring(1));
        } else {
            return fromString;
        }
    }

    public boolean isRemovableGUID(String string) {
        if(string == null) {
            return false;
        }

        if(string.length() != 32) {
            return false;
        }

        char[] chars = string.toCharArray();
        for(char c : chars) {
            if(validGuidCharacters.contains(c) == false) {
                return false;
            }
        }

        return true;
    }
}
