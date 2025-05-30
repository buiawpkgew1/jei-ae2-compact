package com.yshs.jeiae2compact.client;

import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Set;

public class TagRecipe {
    private final Set<String> tags;
    private final List<ItemStack> items;

    public TagRecipe(Set<String> tags, List<ItemStack> items) {
        this.tags = tags;
        this.items = items;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<ItemStack> getItems() {
        return items;
    }
} 