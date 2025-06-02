package com.yshs.jeiae2compact.jei;

import com.yshs.jeiae2compact.JEIAE2Compact;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * JEI 插件入口类，用于注册 MOD 并提供 JEI 运行时访问接口。
 *
 * <p>该插件负责初始化 MOD，并通过静态方法暴露 JEI 的运行时实例，
 * 其他类可以通过此实例与 JEI 交互，例如显示物品信息、触发合成等。</p>
 */
@JeiPlugin
public class JEIAE2CompactPlugin implements IModPlugin {

    private static IJeiRuntime jeiRuntime;

    /**
     * 获取当前 MOD 的唯一标识符。
     *
     * @return MOD ID
     */
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(JEIAE2Compact.MODID, "jei");
    }

    /**
     * 当 JEI 运行时可用时调用。
     *
     * @param runtime JEI 运行时实例
     */
    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime runtime) {
        jeiRuntime = runtime;
    }

    /**
     * 当 JEI 运行时不可用时调用。
     */
    @Override
    public void onRuntimeUnavailable() {
        jeiRuntime = null;
    }

    /**
     * 获取当前 JEI 运行时实例。
     *
     * @return JEI 运行时对象，可能为 null（未初始化）
     */
    public static IJeiRuntime getJeiRuntime() {
        return jeiRuntime;
    }
}