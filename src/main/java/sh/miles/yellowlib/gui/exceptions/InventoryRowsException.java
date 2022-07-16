package sh.miles.yellowlib.gui.exceptions;

import sh.miles.yellowlib.gui.ChestGui;

public class InventoryRowsException extends IllegalArgumentException {
    
    public InventoryRowsException(int rows) {
        super("Illegal inventory rows: " + rows + " rows must be between " + ChestGui.MIN_ROWS + " and " + ChestGui.MAX_ROWS);
    }
}
