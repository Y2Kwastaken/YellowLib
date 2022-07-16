package sh.miles.yellowlib.items.immutable;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * An Immutable ItemStack simplifies the process of creating safe ItemStack
 * fields that can't be edited
 * previously you would have to use a static getter and clone the item stack
 * this class removes that heavy lifting
 * by providing a ItemStack with readOnly access at all levels including the
 * item meta and throws an {@link ImmutableViolationException}
 * to prevent modification. All set methods are deprecated and prewarn users
 * that they will throw an exception.
 */
@SuppressWarnings("deprecation")
public class ImmutableItemStack extends ItemStack {

    private static final String EXCEPTION_MESSAGE = "You are trying to modify an immutable ItemStack which is an illegal operation";

    private ImmutableItemMeta immutableItemMeta;

    public ImmutableItemStack(ItemStack itemStack) {
        super(itemStack);
        this.immutableItemMeta = new ImmutableItemMeta(getItemMeta());
    }

    public ImmutableItemStack(Material material) {
        super(material);
        this.immutableItemMeta = new ImmutableItemMeta(getItemMeta());
    }

    public ImmutableItemStack(Material material, int amount) {
        super(material, amount);
        this.immutableItemMeta = new ImmutableItemMeta(getItemMeta());
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void addEnchantment(Enchantment ench, int level) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void addEnchantments(Map<Enchantment, Integer> enchantments) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void addUnsafeEnchantment(Enchantment ench, int level) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void setAmount(int amount) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void setData(MaterialData data) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void setDurability(short durability) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public boolean setItemMeta(ItemMeta itemMeta) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated this method is deprecated due to the fact this is an immutable
     *             item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no result in the item stack while using this method
     */
    @Deprecated
    @Override
    public void setType(Material type) {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    /**
     * @deprecated This method is deprecated due to the fact that this is an
     *             immutable item stack
     *             Use of this method will just throw a
     *             {@link ImmutableViolationException}
     *             there is no return value rather use
     *             {@link #getImmutableItemMeta()}
     */
    @Override
    @Deprecated
    public ItemMeta getItemMeta() {
        throw new ImmutableViolationException(EXCEPTION_MESSAGE);
    }

    public ImmutableItemMeta getImmutableItemMeta() {
        return immutableItemMeta;
    }

}
