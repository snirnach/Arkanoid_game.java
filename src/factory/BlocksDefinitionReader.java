package factory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import animations.*;
import sprites.Block;
import geometry.Point;
import geometry.Rectangle;


public final class BlocksDefinitionReader {

    private BlocksDefinitionReader() { }

    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();
        Map<String, String> defaultVals = new HashMap<>();

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("default")) {
                    Map<String, String> parsed = parseKeyValuePairs(line.substring("default".length()).trim());
                    defaultVals.putAll(parsed);
                } else if (line.startsWith("bdef")) {
                    // start from defaults then override with this bdef's properties
                    Map<String, String> props = new HashMap<>(defaultVals);
                    props.putAll(parseKeyValuePairs(line.substring("bdef".length()).trim()));

                    String symbol = props.get("symbol");
                    if (symbol == null) continue;

                    final int width = parseIntOrDefault(props.get("width"), 0);
                    final int height = parseIntOrDefault(props.get("height"), 0);
                    final int hitPoints = parseIntOrDefault(props.get("hit_points"), 1);

                    final String fillStr = props.get("fill");
                    final String strokeStr = props.get("stroke");

                    final Color strokeColor = parseColorOrDefault(strokeStr, null);

                    Color tempColor = null;
                    Image tempImage = null;

                    if (fillStr != null && fillStr.startsWith("image(")) {
                        String path = fillStr.substring("image(".length(), fillStr.length() - 1);
                        Image originalImage = loadImage(path);

                        if (originalImage != null) {
                            tempImage = originalImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
                        }
                    } else {
                        tempColor = parseColorOrDefault(fillStr, Color.BLACK);
                    }

                    final Color fillColor = tempColor;
                    final Image blockImage = tempImage;


                    BlockCreator creator = new BlockCreator() {
                        @Override
                        public Block create(int xpos, int ypos) {
                            Rectangle rect = new Rectangle(new Point(xpos, ypos), width, height);

                            Block b = new Block(rect, fillColor != null ? fillColor : Color.WHITE);

                            if (blockImage != null) {
                                b.setImage(blockImage);
                            }

                            if (strokeColor != null) {
                                b.setStroke(strokeColor);
                            }
                            if (hitPoints > 0) {
                                b.setHitPoints(hitPoints);
                            }
                            return b;
                        }
                    };

                    factory.addBlockCreator(symbol, creator);
                } else if (line.startsWith("sdef")) {
                    Map<String, String> parsed = parseKeyValuePairs(line.substring("sdef".length()).trim());
                    String symbol = parsed.get("symbol");
                    String widthStr = parsed.get("width");
                    if (symbol != null && widthStr != null) {
                        int spacerWidth = parseIntOrDefault(widthStr, 0);
                        factory.addSpacerWidth(symbol, spacerWidth);
                    }
                }
            }
        } catch (IOException e) {
            // Print error and return whatever factory has been built so far
            System.err.println("Error reading blocks definitions: " + e.getMessage());
        }

        return factory;
    }

    private static Map<String, String> parseKeyValuePairs(String input) {
        Map<String, String> map = new HashMap<>();
        if (input == null || input.isEmpty()) {
            return map;
        }
        String[] tokens = input.split("\\s+");
        for (String token : tokens) {
            int idx = token.indexOf(':');
            if (idx > 0 && idx + 1 <= token.length()) {
                String key = token.substring(0, idx);
                String value = token.substring(idx + 1);
                map.put(key, value);
            }
        }
        return map;
    }

    private static int parseIntOrDefault(String s, int def) {
        if (s == null) {
            return def;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static Color parseColorOrDefault(String s, Color def) {
        if (s == null) {
            return def;
        }
        try {
            return ColorsParser.colorFromString(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static java.awt.Image loadImage(String imageName) {
        try {
            java.io.InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
            if (is == null) {
                System.err.println("Error: Could not find image: " + imageName);
                return null;
            }
            return javax.imageio.ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}
