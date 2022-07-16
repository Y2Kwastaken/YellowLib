package sh.miles.yellowlib.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import lombok.AccessLevel;
import lombok.Getter;
import sh.miles.yellowlib.gui.enums.GuiActionType;
import sh.miles.yellowlib.gui.enums.GuiResponseType;
import sh.miles.yellowlib.gui.exceptions.InventoryOutOfBoundsException;
import sh.miles.yellowlib.gui.items.ItemContainer;

/**
 * A Chest GUI class that allows you to create a GUI with a chest inventory
 */
public class ChestGui {

    public static final int MIN_ROWS = 1;
    public static final int MAX_ROWS = 6;
    public static final int DEFAULT_SIZE = 3;
    public static final int COLUMNS = 9;

    @Getter
    private int rows;
    @Getter
    private String title;
    @Getter(value = AccessLevel.PROTECTED)
    private Inventory inventory;
    @Getter(value = AccessLevel.PROTECTED)
    private final Map<GuiPosition, ItemContainer> items;

    /**
     * Creates a new Chest GUI with the given title and rows
     * 
     * @param rows  the number of rows in the inventory
     * @param title the title of the inventory
     */
    public ChestGui(int rows, String title) {
        this.rows = rows;
        this.title = title;
        this.inventory = Bukkit.createInventory(null, rows * COLUMNS, title);
        this.items = new HashMap<>();
        init();
    }

    /**
     * Creates a new Chest GUI with the given title and default rows
     * 
     * @param title the title of the inventory
     */
    public ChestGui(String title) {
        this(DEFAULT_SIZE, title);
    }

    /**
     * Creates a copy of a ChestGui by copying the inventory and item values
     * 
     * @param gui the ChestGui to copy
     */
    public ChestGui(ChestGui gui) {
        this.rows = gui.getRows();
        this.title = gui.getTitle();
        this.inventory = gui.getInventory();
        this.items = new HashMap<>();
    }

    /**
     * initializes the ItemsMap by predefining all GuiPositions
     */
    protected final void init() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                items.put(new GuiPosition(i, j), ItemContainer.EMPTY);
            }
        }
    }

    /**
     * Sets the item at the given position to the given item
     * 
     * @param position the position to set the item at
     * @param item     the item to set at the given position
     * @return
     */
    public GuiResponse<ItemContainer> setItem(GuiPosition position, ItemContainer item) {
        checkBounds(position);
        items.put(position, item);
        inventory.setItem(position.getIndex(), item.getItem());

        return new GuiResponse<>(GuiActionType.SET_ITEM, GuiResponseType.SUCCESS, position, item);
    }

    /**
     * Adds an item to the inventory if a spot is available
     * 
     * @param item the item to add if possible
     */
    public GuiResponse<ItemContainer> addItem(ItemContainer item) {
        final GuiPosition position = getNextEmptyPosition();
        if (position == null) {
            return new GuiResponse<>(GuiActionType.ADD_ITEM, GuiResponseType.FAILURE, null, item);
        }
        setItem(position, item);
        return new GuiResponse<>(GuiActionType.ADD_ITEM, GuiResponseType.SUCCESS, position, item);
    }

    /**
     * Gets the item at the given position and removes it
     * 
     * @param position the position to remove
     * @return
     */
    public GuiResponse<ItemContainer> removeItem(GuiPosition position) {
        checkBounds(position);
        setItem(position, ItemContainer.EMPTY);
        return new GuiResponse<>(GuiActionType.REMOVE_ITEM, GuiResponseType.SUCCESS, position, ItemContainer.EMPTY);
    }

    /**
     * Removes the item
     * 
     * @param item the item to remove
     * @return
     */
    public GuiResponse<ItemContainer> removeItem(ItemContainer item) {
        for (Map.Entry<GuiPosition, ItemContainer> entry : items.entrySet()) {
            if (entry.getValue().equals(item)) {
                return removeItem(entry.getKey());
            }
        }
        return new GuiResponse<>(GuiActionType.REMOVE_ITEM, GuiResponseType.FAILURE, null, item);
    }

    public GuiResponse<ItemContainer> getItem(GuiPosition position) {
        checkBounds(position);
        return new GuiResponse<>(GuiActionType.GET_ITEM, GuiResponseType.SUCCESS, position, items.get(position));
    }

    public GuiResponse<ItemContainer> addAll(Map<GuiPosition, ItemContainer> items) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (items.containsKey(new GuiPosition(i, j))) {
                    setItem(new GuiPosition(i, j), items.get(new GuiPosition(i, j)));
                } else {
                    setItem(new GuiPosition(i, j), ItemContainer.EMPTY);
                }
            }
        }
        return new GuiResponse<>(GuiActionType.ADD_ALL, GuiResponseType.SUCCESS, null, null);
    }

    public GuiResponse<ItemContainer> setTitle(String title) {
        this.title = title;
        this.inventory = Bukkit.createInventory(null, rows * COLUMNS, title);
        addAll(items);
        return new GuiResponse<>(GuiActionType.CHANGE_TITLE, GuiResponseType.SUCCESS, null, null);
    }

    public GuiResponse<ItemContainer> setRows(int rows) {
        if (rows < MIN_ROWS || rows > MAX_ROWS) {
            return new GuiResponse<>(GuiActionType.CHANGE_SIZE, GuiResponseType.FAILURE, null, null);
        }
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, rows * COLUMNS, title);
        if (items.size() > rows * COLUMNS) {
            Set<GuiPosition> positions = items.keySet();
            for (GuiPosition position : positions) {
                if (position.getRows() >= rows) {
                    removeItem(position);
                }
            }
        }
        addAll(items);
        return new GuiResponse<>(GuiActionType.CHANGE_SIZE, GuiResponseType.SUCCESS, null, null);
    }

    public void destroy() {
        items.clear();
        inventory.getViewers().forEach(viewer -> viewer.closeInventory());
        inventory.clear();
    }

    /**
     * Gets the next empty position in the inventory
     * 
     * @return the next empty position or null if there is no empty position
     */
    public final GuiPosition getNextEmptyPosition() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (items.get(new GuiPosition(i, j)).equals(ItemContainer.EMPTY)) {
                    return new GuiPosition(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Opens the inventory for the given human entity
     * 
     * @param ent
     */
    public final void open(HumanEntity ent) {
        ent.openInventory(inventory);
    }

    public final boolean isSameGui(InventoryView view) {
        return view.getTopInventory().equals(inventory);
    }

    /**
     * Checks if the given position is within the bounds of the inventory
     * 
     * @param position the position to check
     * @throws InventoryOutOfBoundsException if the position is out of bounds
     */
    public static final void checkBounds(GuiPosition position) throws InventoryOutOfBoundsException {
        if (position.getRows() < MIN_ROWS || position.getRows() > MAX_ROWS) {
            throw new InventoryOutOfBoundsException(position);
        }
    }
}
