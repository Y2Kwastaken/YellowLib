package sh.miles.yellowlib.gui;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A class that represents a position in a GUI.
 */
@AllArgsConstructor
@Data
public class GuiPosition {

    private final int column;
    private final int rows;

    public int getIndex() {
        return (rows * column) - 1;
    }

    @Override
    public String toString() {
        return "(" + column + ", " + rows + ")";
    }

    public static GuiPosition fromIndex(int index) {
        int column = index % ChestGui.COLUMNS;
        int rows = index / ChestGui.COLUMNS;
        return new GuiPosition(column, rows);
    }
}
