package com.yshs.jeiae2compact.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.yshs.jeiae2compact.util.TagUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import java.util.function.Supplier;

public class TagCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tag")
            .then(Commands.literal("add")
                .then(Commands.argument("tag", StringArgumentType.string())
                    .executes(context -> {
                        ItemStack item = context.getSource().getPlayerOrException().getMainHandItem();
                        String tag = StringArgumentType.getString(context, "tag");
                        TagUtil.addTag(item, tag);
                        context.getSource().sendSuccess(() -> Component.literal("已添加标签: " + tag), true);
                        return 1;
                    })))
            .then(Commands.literal("remove")
                .then(Commands.argument("tag", StringArgumentType.string())
                    .executes(context -> {
                        ItemStack item = context.getSource().getPlayerOrException().getMainHandItem();
                        String tag = StringArgumentType.getString(context, "tag");
                        TagUtil.removeTag(item, tag);
                        context.getSource().sendSuccess(() -> Component.literal("已移除标签: " + tag), true);
                        return 1;
                    })))
            .then(Commands.literal("list")
                .executes(context -> {
                    ItemStack item = context.getSource().getPlayerOrException().getMainHandItem();
                    context.getSource().sendSuccess(() -> Component.literal("标签: " + String.join(", ", TagUtil.getTags(item))), true);
                    return 1;
                })));
    }
} 