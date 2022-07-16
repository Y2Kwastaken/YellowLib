package sh.miles.yellowlib.gui.items;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import sh.miles.yellowlib.gui.events.ItemClickEvent;

public class EventItemContainer extends ItemContainer {
    
    @Getter
    private ItemClickEvent clickEvent;
    public EventItemContainer(ItemStack item, ItemClickEvent clickEvent) {
        super(item);
        this.clickEvent = clickEvent;
    }
    
    public EventItemContainer(ItemContainer item, ItemClickEvent clickEvent) {
        super(item);
        this.clickEvent = clickEvent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventItemContainer container) {
            return super.equals(container) && this.clickEvent.equals(container.clickEvent);
        }
        return false;
    }
}
