package net.splodgebox.monthlycrates.data;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.splodgebox.monthlycrates.utils.ItemStackBuilder;
import net.splodgebox.monthlycrates.utils.ItemUtils;
import net.splodgebox.monthlycrates.utils.SkullCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Reward {

    private final double chance;
    private final XMaterial material;
    private final int amount;
    private final String name;
    private final List<String> lore;
    private final HashMap<Enchantment, Integer> enchants;
    private final List<String> commands;
    private final boolean giveItem;
    private final List<String> nbt;
    private final int customModelData;
    private final String data;

    public ItemStack create() {
        ItemStack itemStack;

        if (material == XMaterial.PLAYER_HEAD && data != null && !data.isEmpty()) {
            itemStack = SkullCreator.itemFromBase64(data);
        } else {
            itemStack = material.parseItem();
        }

        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(itemStack)
                .setName(name)
                .setAmount(amount)
                .setLore(lore)
                .addEnchants(enchants);

        if (customModelData > 0) {
            itemStack = ItemUtils.setCustomModelData(itemStackBuilder.build(), customModelData);
        } else {
            itemStack = itemStackBuilder.build();
        }

        if (!nbt.isEmpty()) {
            NBTItem nbtItem = new NBTItem(itemStack);
            nbt.stream().map(tag -> tag.split(":")).forEach(index -> nbtItem.setString(index[0], index[1]));
            itemStack = nbtItem.getItem();
        }

        return itemStack;
    }
}
