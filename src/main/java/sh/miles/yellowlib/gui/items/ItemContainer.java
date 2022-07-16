package sh.miles.yellowlib.gui.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemContainer {
    
    public static final ItemContainer EMPTY = new ItemContainer(Material.AIR);

    private final ItemStack item;

    public ItemContainer(Material material) {
        this(new ItemStack(material));
    }

    public ItemContainer(ItemContainer item) {
        this(item.getItem());
    }
}
