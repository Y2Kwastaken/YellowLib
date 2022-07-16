package sh.miles.yellowlib.gui;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sh.miles.yellowlib.gui.enums.GuiActionType;
import sh.miles.yellowlib.gui.enums.GuiResponseType;
import sh.miles.yellowlib.gui.items.ItemContainer;

/**
 * A class that represents a response to a GUI action
 * @param <T>
 */
@Getter(value = AccessLevel.PUBLIC)
@AllArgsConstructor
public class GuiResponse<T extends ItemContainer> {
    
    private final GuiActionType action;
    private final GuiResponseType response;
    private final GuiPosition position;
    private final T item;



}
