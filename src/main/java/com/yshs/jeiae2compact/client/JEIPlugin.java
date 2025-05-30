package com.yshs.jeiae2compact.client;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;
import com.yshs.jeiae2compact.util.AE2ItemUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ICellInventory;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.tryParse(JEIAE2Compact.MODID + ":jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CellCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        List<CellRecipe> recipes = new ArrayList<>();
        
        // 获取所有存储单元
        for (BlockEntity blockEntity : level.getBlockEntityTickers()) {
            if (blockEntity instanceof appeng.blockentity.storage.DriveBlockEntity drive) {
                for (int i = 0; i < 10; i++) { // 假设最多10个存储单元
                    ItemStack cell = drive.getInternalInventory().getStackInSlot(i);
                    if (AE2ItemUtil.isStorageCell(cell)) {
                        List<ItemStack> items = AE2ItemUtil.getCellItems(cell);
                        if (!items.isEmpty()) {
                            recipes.add(new CellRecipe(cell, items));
                        }
                    }
                }
            }
        }

        registration.addRecipes(CellCategory.TYPE, recipes);
    }
} 