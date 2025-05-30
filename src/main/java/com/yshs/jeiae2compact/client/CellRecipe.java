package com.yshs.jeiae2compact.client;

import net.minecraft.world.item.ItemStack;
import java.util.List;

public class CellRecipe {
    private final ItemStack cell;
    private final List<ItemStack> items;

    public CellRecipe(ItemStack cell, List<ItemStack> items) {
        this.cell = cell;
        this.items = items;
    }

    public ItemStack getCell() {
        return cell;
    }

    public List<ItemStack> getItems() {
        return items;
    }
} 