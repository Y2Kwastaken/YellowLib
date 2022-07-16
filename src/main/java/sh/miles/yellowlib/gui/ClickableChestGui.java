package sh.miles.yellowlib.gui;

import java.util.List;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import sh.miles.yellowlib.PluginInstanceHolder;
import sh.miles.yellowlib.gui.enums.GuiActionType;
import sh.miles.yellowlib.gui.enums.GuiResponseType;
import sh.miles.yellowlib.gui.events.ChestGuiOpenEvent;
import sh.miles.yellowlib.gui.events.GuiEventHandler;
import sh.miles.yellowlib.gui.items.EventItemContainer;
import sh.miles.yellowlib.gui.items.ItemContainer;

/**
 * A Chest GUI class that allows you to create events and handle them
 */
public class ClickableChestGui extends ChestGui {

    @Getter
    private List<ChestGuiOpenEvent> openEvents;
    @Getter
    private List<ChestGuiOpenEvent> closeEvents;
    @Getter
    private GuiEventHandler eventHandler;

    public ClickableChestGui(int rows, String title) {
        super(rows, title);
        this.eventHandler = new GuiEventHandler(this);
        registerEvents();
    }

    public ClickableChestGui(String title) {
        super(title);
        this.eventHandler = new GuiEventHandler(this);
        registerEvents();
    }

    public ClickableChestGui(ClickableChestGui gui) {
        super(gui);
        this.openEvents = gui.getOpenEvents();
        this.closeEvents = gui.getCloseEvents();
        this.eventHandler = gui.getEventHandler();
        registerEvents();
    }

    protected final void registerEvents() {
        PluginInstanceHolder.getInstance().getServer().getPluginManager().registerEvents(eventHandler,
                PluginInstanceHolder.getInstance());
    }
    
    /**
     * scraps the entire inventory and clears all events
     */
    public final void destroy() {
        HandlerList.unregisterAll(eventHandler);
        super.destroy();
    }

    public GuiResponse<EventItemContainer> setItem(GuiPosition position, EventItemContainer item) {
        super.setItem(position, item);
        return new GuiResponse<>(GuiActionType.SET_ITEM, GuiResponseType.SUCCESS, position, item);
    }

    public GuiResponse<EventItemContainer> addItem(EventItemContainer item) {
        final GuiResponse<ItemContainer> response = super.addItem(item);
        if (response.getResponse() != GuiResponseType.SUCCESS) {
            return new GuiResponse<>(GuiActionType.ADD_ITEM, response.getResponse(), response.getPosition(), item);
        }
        return new GuiResponse<>(GuiActionType.ADD_ITEM, GuiResponseType.SUCCESS, response.getPosition(), item);
    }

    public GuiResponse<EventItemContainer> removeItem(EventItemContainer item) {
        final GuiResponse<ItemContainer> response = super.removeItem(item);
        if (response.getResponse() != GuiResponseType.SUCCESS) {
            return new GuiResponse<>(GuiActionType.REMOVE_ITEM, response.getResponse(), response.getPosition(), item);
        }
        return new GuiResponse<>(GuiActionType.REMOVE_ITEM, GuiResponseType.SUCCESS, response.getPosition(), item);
    }

    public GuiResponse<EventItemContainer> getEventItem(GuiPosition position) {
        final GuiResponse<ItemContainer> response = super.getItem(position);
        if (response.getResponse() != GuiResponseType.SUCCESS) {
            return new GuiResponse<>(GuiActionType.GET_ITEM, response.getResponse(), response.getPosition(), null);
        }

        if (response.getItem() instanceof EventItemContainer) {
            return new GuiResponse<>(GuiActionType.GET_ITEM, GuiResponseType.SUCCESS, response.getPosition(),
                    (EventItemContainer) response.getItem());
        }

        return new GuiResponse<>(GuiActionType.GET_ITEM, GuiResponseType.FAILURE, response.getPosition(), null);
    }

}
