package sh.miles.yellowlib.items;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import lombok.Getter;
import lombok.NonNull;

/**
 * SkullItem class used for editing skull texutres
 */
public final class SkullItem {

    @Getter
    private ItemStack skull;

    private SkullItem() {
        this.skull = new ItemStack(Material.PLAYER_HEAD);
    }

    private SkullItem(ItemStack itemStack) {
        this.skull = itemStack;
    }

    /**
     * Create a SkullItem from a ItemStack.
     * 
     * @param url the url location of the texture
     */
    public void setTexture(@NonNull final String url) {
        SkullMeta skullMeta = (SkullMeta) this.skull.getItemMeta();
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        profile.setTextures(textures);
        skullMeta.setOwnerProfile(profile);
        skull.setItemMeta(skullMeta);
    }

    /**
     * Sets the texture to the skull of the player profile
     * if you want to get a plyers PlayerProfile user Player#getPlayerProfile
     * 
     * @param profile the player profile to set
     */
    public void setTexture(@NonNull final PlayerProfile profile) {
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwnerProfile(profile);
        skull.setItemMeta(skullMeta);
    }

    /**
     * Checks if the ItemStack has skull meta to use
     * 
     * @param itemStack the item stack to check
     * @return true if item is a skull
     */
    public static boolean isSkull(ItemStack itemStack) {
        return itemStack.getItemMeta() instanceof SkullMeta;
    }

    /**
     * Create a SkullItem from a ItemStack.
     * 
     * @param url the url location of the texture
     * @return
     */
    public static ItemStack of(@NonNull final String url) {
        SkullItem skullItem = new SkullItem();
        skullItem.setTexture(url);
        return skullItem.getSkull();
    }

    /**
     * Create a SkullItem from a ItemStack.
     * 
     * @param itemStack the ItemStack to create from
     * @param url       the url location of the texture
     * @return the itemstack with applied texture
     */
    public static ItemStack of(@NonNull final ItemStack itemStack, @NonNull final String url) {
        if (!isSkull(itemStack)) {
            throw new IllegalArgumentException("itemStack does not contain SkullMeta to utilize for this method");
        }
        SkullItem skullItem = new SkullItem(itemStack);
        skullItem.setTexture(url);
        return skullItem.getSkull();
    }

    /**
     * Create a SkullItem from a ItemStack.
     * 
     * @param profile the player profile to set
     * @return the itemstack with applied texture
     */
    public static ItemStack of(@NonNull final PlayerProfile profile) {
        SkullItem skullItem = new SkullItem();
        skullItem.setTexture(profile);
        return skullItem.getSkull();
    }

    /**
     * Create a SkullItem from a ItemStack.
     * 
     * @param itemStack the ItemStack to create from
     * @param profile   the player profile to set
     * @return the itemstack with applied texture
     */
    public static ItemStack of(@NonNull final ItemStack itemStack, @NonNull final PlayerProfile profile) {
        if (!isSkull(itemStack)) {
            throw new IllegalArgumentException("itemStack does not contain SkullMeta to utilize for this method");
        }
        SkullItem skullItem = new SkullItem(itemStack);
        skullItem.setTexture(profile);
        return skullItem.getSkull();
    }

}
