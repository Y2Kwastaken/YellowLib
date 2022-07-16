package sh.miles.yellowlib.gui.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import lombok.AllArgsConstructor;
import sh.miles.yellowlib.gui.ClickableChestGui;
import sh.miles.yellowlib.gui.GuiPosition;
import sh.miles.yellowlib.gui.GuiResponse;
import sh.miles.yellowlib.gui.enums.GuiResponseType;
import sh.miles.yellowlib.gui.items.EventItemContainer;

@AllArgsConstructor
public class GuiEventHandler implements Listener {
    
    private ClickableChestGui gui;
    
    @EventHandler
    public void onChestGuiOpen(InventoryOpenEvent e){
        if(!gui.isSameGui(e.getView())){
            return;
        }

        gui.getOpenEvents().forEach(event -> event.onChestGuiOpen(gui));
    }

    @EventHandler
    public void onChestGuiClose(InventoryOpenEvent e){
        if(!gui.isSameGui(e.getView())){
            return;
        }

        gui.getCloseEvents().forEach(event -> event.onChestGuiOpen(gui));
    }

    @EventHandler
    public void onChestGuiClick(InventoryClickEvent e){
        if(!gui.isSameGui(e.getView())){
            return;
        }

        final GuiPosition position = GuiPosition.fromIndex(e.getSlot());
        final GuiResponse<EventItemContainer> response = gui.getEventItem(position);
        if(response.getResponse() != GuiResponseType.SUCCESS){
            return;
        }
        
        response.getItem().getClickEvent().onClick(e, response.getItem());
    }
}
