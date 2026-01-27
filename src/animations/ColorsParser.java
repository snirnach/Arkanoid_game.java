package animations;

public class ColorsParser {
    public static java.awt.Color colorFromString(String s) {
        // Strip "color(" and ")"
        if (s.startsWith("color(") && s.endsWith(")")) {
            s = s.substring(6, s.length() - 1);
        } else {
            return null; // Invalid format
        }

        // Case A: RGB
        if (s.startsWith("RGB(") && s.endsWith(")")) {
            String rgbValues = s.substring(4, s.length() - 1);
            String[] parts = rgbValues.split(",");
            if (parts.length != 3) {
                return null; // Invalid RGB format
            }
            try {
                int r = Integer.parseInt(parts[0].trim());
                int g = Integer.parseInt(parts[1].trim());
                int b = Integer.parseInt(parts[2].trim());
                return new java.awt.Color(r, g, b);
            } catch (NumberFormatException e) {
                return null; // Invalid number format
            }
        }

        // Case B: Named colors
        switch (s.toLowerCase()) {
            case "red":
                return java.awt.Color.RED;
            case "blue":
                return java.awt.Color.BLUE;
            case "black":
                return java.awt.Color.BLACK;
            case "green":
                return java.awt.Color.GREEN;
            case "yellow":
                return java.awt.Color.YELLOW;
            case "cyan":
                return java.awt.Color.CYAN;
            case "gray":
                return java.awt.Color.GRAY;
            case "lightgray":
                return java.awt.Color.LIGHT_GRAY;
            case "white":
                return java.awt.Color.WHITE;
            case "orange":
                return java.awt.Color.ORANGE;
            case "pink":
                return java.awt.Color.PINK;
            default:
                return java.awt.Color.WHITE; // Default color
        }
    }
}
