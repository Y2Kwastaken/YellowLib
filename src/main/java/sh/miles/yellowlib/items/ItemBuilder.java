package sh.miles.yellowlib.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import sh.miles.yellowlib.PluginInstanceHolder;
import sh.miles.yellowlib.chat.Colorize;
import sh.miles.yellowlib.items.immutable.ImmutableItemStack;

/**
 * Builds an ItemStack
 */
public class ItemBuilder {

    public static final String MATERIAL = "material";
    public static final String AMOUNT = "amount";
    public static final String NAME = "name";
    public static final String ENCHANTMENTS = "enchantments";
    public static final String ENCHANTMENTS_SEPERATOR = "-";
    public static final String LORE = "lore";
    public static final String ITEMFLAGS = "itemflags";
    public static final String MODELDATA = "modeldata";
    private static final String PATH_TEMPLATE = "%s.%s";

    private ItemStack item;

    public ItemBuilder(@NonNull final ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(@NonNull final Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(@NonNull final Material material, final int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemBuilder setAmount(final int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(@NonNull final String displayName) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCustomModelData(final int modelData) {
        final ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(modelData);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(@NonNull final List<String> lore) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(@NonNull final String... lore) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable(final boolean unbreakable) {
        final ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(@NonNull final Enchantment enchantment, final int level) {
        final ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantments(@NonNull final Map<Enchantment, Integer> enchantments) {
        final ItemMeta meta = item.getItemMeta();
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlags(@NonNull final List<ItemFlag> itemFlags) {
        return addItemFlags(itemFlags.toArray(ItemFlag[]::new));
    }

    public ItemBuilder addItemFlags(@NonNull final ItemFlag... itemFlags) {
        final ItemMeta meta = item.getItemMeta();
        for (ItemFlag itemFlag : itemFlags) {
            meta.addItemFlags(itemFlag);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return item;
    }

    public ImmutableItemStack buildImmutable() {
        return new ImmutableItemStack(item);
    }

    public static final FormattedStack buildData(ItemStack item) {
        final ItemMeta itemMeta = item.getItemMeta();

        final List<String> tempEnchantmentList = new ArrayList<>();
        item.getEnchantments().forEach((Enchantment enchantment, Integer level) -> {
            tempEnchantmentList
                    .add(enchantment.getKey().getKey() + ENCHANTMENTS_SEPERATOR + String.valueOf(level));
        });

        final List<String> tempItemFlagsList = new ArrayList<>();
        itemMeta.getItemFlags().forEach((ItemFlag itemFlag) -> tempItemFlagsList.add(itemFlag.toString()));

        return new FormattedStack(item.getType().toString(), item.getAmount(), itemMeta.getDisplayName(),
                itemMeta.getCustomModelData(), itemMeta.getLore(), tempEnchantmentList, tempItemFlagsList);
    }

    /**
     * Sends a ItemStack to a configuration file specified at a path will only
     * overwrite current sections if set to true
     * 
     * @param itemStack the item stack to send
     * @param config    the configuration file to send the file to
     * @param path      the path at which the item should be sent
     * @param overwrite whether to overwrite any other sections present at the path
     *                  given
     */
    public static final void sendStackToConfig(@NonNull final ItemStack itemStack,
            @NonNull final FileConfiguration config, @NonNull final String path,
            final boolean overwrite) {
        if (config.getConfigurationSection(path) != null && overwrite) {
            PluginInstanceHolder.getPluginLogger()
                    .info(String.format("There is already a stack in %s at %s", config.getName(), path));
            return;
        }

        FormattedStack formattedStack = buildData(itemStack);
        config.set(String.format(PATH_TEMPLATE, path, MATERIAL), formattedStack.getMaterial());
        config.set(String.format(PATH_TEMPLATE, path, AMOUNT), formattedStack.getAmount());
        config.set(String.format(PATH_TEMPLATE, path, NAME), formattedStack.getName());
        config.set(String.format(PATH_TEMPLATE, path, LORE), formattedStack.getLore());
        config.set(String.format(PATH_TEMPLATE, path, ENCHANTMENTS), formattedStack.getEnchantments());
        config.set(String.format(PATH_TEMPLATE, path, ITEMFLAGS), formattedStack.getItemFlags());
        config.set(String.format(PATH_TEMPLATE, path, MODELDATA), formattedStack.getModelData());
    }

    /**
     * Retrieves a ItemStack from a configuration file specified at a path
     * 
     * @param config the configuration file to retrieve the item from
     * @param path   the path at which the item is stored
     * @return the item stack retrieved
     */
    public static final ItemStack retrieveStackFromConfig(@NonNull final FileConfiguration config,
            @NonNull final String path) {

        final ConfigurationSection section = config.getConfigurationSection(path);

        // Material
        final Material material = Material.getMaterial(section.getString(MATERIAL));
        // checks for idiots
        if (material == null) {
            PluginInstanceHolder.getPluginLogger()
                    .info(String.format("The material %s does not exist in the config %s at the path %s",
                            section.getString(MATERIAL), config.getName(), path));
            return null;
        }

        final ItemBuilder item = new ItemBuilder(material);

        final int amount = section.getInt(AMOUNT);
        if (amount == 0) {
            PluginInstanceHolder.getPluginLogger().info(
                    String.format("The amount for the item in the config %s at %s is invalid", config.getName(), path));
            return item.build();
        }

        item.setAmount(amount);

        final int modelData = section.getInt(MODELDATA);
        if (modelData != 0) {
            item.setCustomModelData(modelData);
        }

        final String name = section.getString(NAME);
        if (name != null) {
            item.setDisplayName(Colorize.process(name));
        }

        final List<String> lore = section.getStringList(LORE);
        if (lore != null) {
            item.setLore(Colorize.process(lore));
        }

        final List<String> enchantments = section.getStringList(ENCHANTMENTS);
        if (enchantments != null) {
            enchantments.forEach((String enchantmentFormat) -> {
                String[] splitEName = enchantmentFormat.split(ENCHANTMENTS_SEPERATOR);

                try {
                    final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(splitEName[0]));
                    final int level = Integer.parseInt(splitEName[1]);

                    item.addEnchantment(enchantment, level);
                } catch (Exception e) {
                    PluginInstanceHolder.getPluginLogger()
                            .info(String.format("The enchantment %s does not exist in the config %s at %s",
                                    splitEName[1],
                                    config.getName(), path));
                }
            });
        }

        final List<String> itemFlags = section.getStringList(ITEMFLAGS);
        if (itemFlags != null) {

            for (String stringItemFlag : itemFlags) {
                try {
                    item.addItemFlags(ItemFlag.valueOf(stringItemFlag));
                } catch (Exception e) {
                    PluginInstanceHolder.getPluginLogger()
                            .info(String.format("The ItemFlag %s does not exist in teh config %s at %s", stringItemFlag,
                                    config.getName(), path));
                }
            }
        }

        return item.build();
    }

    @Data
    @AllArgsConstructor
    private static final class FormattedStack {

        private String material;
        private Integer amount;
        private String name;
        private Integer modelData;
        private List<String> lore;
        private List<String> enchantments;
        private List<String> itemFlags;

    }
}
