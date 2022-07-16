package sh.miles.yellowlib.gui.exceptions;

import sh.miles.yellowlib.gui.GuiPosition;

public class InventoryOutOfBoundsException extends IllegalArgumentException {
    
    public InventoryOutOfBoundsException(GuiPosition position) {
        super("The given position: " + position + " is out of bounds.");
    }
}
