package sh.miles.yellowlib.gui.events;

import org.bukkit.event.inventory.InventoryClickEvent;

import sh.miles.yellowlib.gui.items.ItemContainer;

public interface ItemClickEvent {
    
    void onClick(InventoryClickEvent e, ItemContainer clickedItem);
    
}
