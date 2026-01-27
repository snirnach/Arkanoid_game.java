package factory;

import sprites.Block;

public class BlocksFromSymbolsFactory {
    private java.util.Map<String, Integer> spacerWidths;
    private java.util.Map<String, factory.BlockCreator> blockCreators;

    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new java.util.HashMap<>();
        this.blockCreators = new java.util.HashMap<>();
    }

    public void addBlockCreator(String symbol, factory.BlockCreator creator) {
        this.blockCreators.put(symbol, creator);
    }

    public void addSpacerWidth(String symbol, int width) {
        this.spacerWidths.put(symbol, width);
    }

    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    public factory.BlockCreator getBlockCreator(String s) {
        return this.blockCreators.get(s);
    }

    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
    public Block createBlock(String s, int x, int y) {
        factory.BlockCreator creator = this.blockCreators.get(s);
        if (creator != null) {
            return creator.create(x, y);
        }
        return null;
    }
}
