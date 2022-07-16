package sh.miles.yellowlib.items.immutable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Multimap;

/**
 * Immutable ItemMeta ensures that itemMeta is not modified after its creation
 */
public class ImmutableItemMeta {
    
    private final ItemMeta itemMeta;
    private final ImmutablePersistentDataContainer persistentDataContainer;
    public ImmutableItemMeta(ItemMeta itemMeta){
        this.itemMeta = itemMeta;
        this.persistentDataContainer = new ImmutablePersistentDataContainer(itemMeta.getPersistentDataContainer());
    }

    public ImmutablePersistentDataContainer getPersistentDataContainer() {
        return persistentDataContainer;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return itemMeta.getAttributeModifiers();
    }
    
    public int getCustomModelData(){
        return itemMeta.getCustomModelData();
    }
    
    public String getDisplayName(){
        return itemMeta.getDisplayName();
    }

    public int getEnchantLevel(Enchantment ench) {
        return itemMeta.getEnchantLevel(ench);
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return itemMeta.getEnchants();
    }

    public Set<ItemFlag> getItemFlags() {
        return itemMeta.getItemFlags();
    }

    public String getLocalizedName() {
        return itemMeta.getLocalizedName();
    }
    
    public List<String> getLore(){
        return itemMeta.getLore();
    }

    public boolean hasAttributeModifiers() {
        return itemMeta.hasAttributeModifiers();
    }

    public boolean hasCustomModelData() {
        return itemMeta.hasCustomModelData();
    }

    public boolean hasDisplayName() {
        return itemMeta.hasDisplayName();
    }

    public boolean hasEnchant(Enchantment ench) {
        return itemMeta.hasEnchant(ench);
    }

    public boolean hasEnchants() {
        return itemMeta.hasEnchants();
    }

    public boolean hasItemFlag(ItemFlag flag) {
        return itemMeta.hasItemFlag(flag);
    }

    public boolean hasLocalizedName() {
        return itemMeta.hasLocalizedName();
    }

    public boolean hasLore() {
        return itemMeta.hasLore();
    }

    public boolean isUnbreakable() {
        return itemMeta.isUnbreakable();
    }

    
}
