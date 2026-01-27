package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Objects;

import animations.ColorsParser;
import biuoop.DrawSurface;
import factory.*;
import sprites.Sprite;
import sprites.Block;
import geometry.Velocity;

public class LevelSpecificationReader {

    public List<LevelInformation> fromReader(Reader reader) {
        List<LevelInformation> levels = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);

        try {
            String line;
            Level currentLevel = null;
            BlocksFromSymbolsFactory blockFactory = null;
            boolean readingBlocks = false;
            int currentY = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.equals("START_LEVEL")) {
                    currentLevel = new Level();
                    blockFactory = null;
                    readingBlocks = false;
                    continue;
                }

                if (line.equals("END_LEVEL")) {
                    if (currentLevel != null) {
                        levels.add(currentLevel);
                        currentLevel = null;
                    }
                    continue;
                }


                if (readingBlocks) {
                    if (line.equals("END_BLOCKS")) {
                        readingBlocks = false;
                        continue;
                    }
                    if (currentLevel != null) {
                        parseBlockLine(line, currentLevel, blockFactory, currentY);
                        currentY += currentLevel.rowHeight;
                    }
                    continue;
                }


                if (line.equals("START_BLOCKS")) {
                    readingBlocks = true;
                    if (currentLevel != null) {
                        currentY = currentLevel.blocksStartY;
                    }
                    continue;
                }

                String[] parts = line.split(":");
                if (parts.length != 2) {
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();

                if (currentLevel != null) {
                    switch (key) {
                        case "level_name":
                            currentLevel.levelName = value;
                            break;
                        case "ball_velocities":
                            currentLevel.velocities = parseVelocities(value);
                            break;
                        case "background":
                            currentLevel.background = parseBackground(value);
                            break;
                        case "paddle_speed":
                            currentLevel.paddleSpeed = Integer.parseInt(value);
                            break;
                        case "paddle_width":
                            currentLevel.paddleWidth = Integer.parseInt(value);
                            break;
                        case "blocks_start_x":
                            currentLevel.blocksStartX = Integer.parseInt(value);
                            break;
                        case "blocks_start_y":
                            currentLevel.blocksStartY = Integer.parseInt(value);
                            break;
                        case "row_height":
                            currentLevel.rowHeight = Integer.parseInt(value);
                            break;
                        case "num_blocks":
                            currentLevel.numberOfBlocksToRemove = Integer.parseInt(value);
                            break;
                        case "block_definitions":
                            blockFactory = loadBlockFactory(value);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return levels;
    }


    private List<Velocity> parseVelocities(String s) {
        List<Velocity> vels = new ArrayList<>();
        String[] pairs = s.split(" ");
        for (String pair : pairs) {
            String[] values = pair.split(",");
            if (values.length == 2) {
                double angle = Double.parseDouble(values[0]);
                double speed = Double.parseDouble(values[1]);
                vels.add(Velocity.fromAngleAndSpeed(angle, speed));
            }
        }
        return vels;
    }

    private BlocksFromSymbolsFactory loadBlockFactory(String filename) {

        try {
            Reader reader = new InputStreamReader(
                    Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(filename)));
            return BlocksDefinitionReader.fromReader(reader);
        } catch (Exception e) {
            System.err.println("Failed to load block definitions: " + filename);
            return null;
        }
    }

    private void parseBlockLine(String line, Level level, BlocksFromSymbolsFactory factory, int y) {
        if (factory == null) return;

        int x = level.blocksStartX;
        for (int i = 0; i < line.length(); i++) {
            String symbol = String.valueOf(line.charAt(i));

            if (factory.isSpaceSymbol(symbol)) {
                x += factory.getSpaceWidth(symbol);
            } else if (factory.isBlockSymbol(symbol)) {
                Block b = factory.createBlock(symbol, x, y);
                if (b != null) {
                    level.blocks.add(b);
                    x += (int) b.getCollisionRectangle().getWidth();
                }
            }
        }
    }

    private Sprite parseBackground(String s) {
        if (s.startsWith("color")) {
            Color color = ColorsParser.colorFromString(s);

            return new Sprite() {

                @Override
                public void drawOn(DrawSurface d) {
                    d.setColor(color);
                    d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                }
                @Override
                public void timePassed() {}
            };
        }

        if (s.startsWith("image")) {
            String path = s.substring("image(".length(), s.length() - 1);
            try {
                java.io.InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
                if (is == null)
                    return null;

                java.awt.Image originalImage = javax.imageio.ImageIO.read(is);
                final java.awt.Image bgImage = originalImage.getScaledInstance(800, 600, java.awt.Image.SCALE_DEFAULT);

                return new Sprite() {
                    @Override
                    public void drawOn(DrawSurface d) {
                        d.drawImage(0, 0, bgImage);
                    }
                    @Override
                    public void timePassed() {}
                };
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        return null;
    }


    private static class Level implements LevelInformation {
        String levelName;
        List<Velocity> velocities = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();
        Sprite background;
        int paddleSpeed;
        int paddleWidth;
        int blocksStartX;
        int blocksStartY;
        int rowHeight;
        int numberOfBlocksToRemove;

        @Override
        public int numberOfBalls() { return velocities.size(); }
        @Override
        public List<Velocity> initialBallVelocities() { return velocities; }
        @Override
        public String levelName() { return levelName; }
        @Override
        public Sprite getBackground() { return background; }
        @Override
        public List<Block> blocks() { return blocks; }
        @Override
        public int numberOfBlocksToRemove() { return numberOfBlocksToRemove; }
        @Override
        public int paddleSpeed() { return paddleSpeed; }
        @Override
        public int paddleWidth() { return paddleWidth; }
    }
}
