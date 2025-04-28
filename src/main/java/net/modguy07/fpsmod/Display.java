package net.modguy07.fpsmod;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.lwjgl.glfw.GLFW;

public class Display implements ClientModInitializer
{
    private static final Identifier DISPLAY_LAYER = Identifier.of(FPSDisplayMod.MOD_ID, "display-layer");
    static int x = 5;
    static int y = 5;
    public static String corner = "Top Left"; // for display

    private static KeyBinding settingsKey;

    @Override
    public void onInitializeClient() {
        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fpsmod.settings", // keybind name
                InputUtil.Type.KEYSYM, // type of binding
                GLFW.GLFW_KEY_PAGE_DOWN, // default key
                "category.fpsmod.keys" // Controls menu category name
        ));

        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.MISC_OVERLAYS, DISPLAY_LAYER, Display::render));
    }

    private static void render(DrawContext cont, RenderTickCounter renderTickCounter) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (settingsKey.isPressed()) {
                client.setScreen(
                        new SettingsMenu(Text.empty())
                ); // to be implemented
            }
        });
        MinecraftClient client = MinecraftClient.getInstance();
        int color = 16777215; // 0xFFFFFF
        int FPS = client.getCurrentFps();

        pickASide(client);

        cont.drawText(client.textRenderer, FPS + " FPS", x, y, color, true);
    }

    private static void pickASide(MinecraftClient client) {
        if (corner.equals("Top Left")) { // womp womp intellij, i refuse to use a switch statement xD
            x = 5;
            y = 5;
        } else if (corner.equals("Top Right")) {
            x = client.getWindow().getScaledWidth() - 5 - client.textRenderer.getWidth(client.getCurrentFps() + " FPS");
            y = 5;
        } else if (corner.equals("Bottom Right")) {
            x = client.getWindow().getScaledWidth() - 5 - client.textRenderer.getWidth(client.getCurrentFps() + " FPS");
            y = client.getWindow().getScaledHeight() - 5 - client.textRenderer.fontHeight;
        } else if (corner.equals("Bottom Left")) {
            x = 5;
            y = client.getWindow().getScaledHeight() - 5 - client.textRenderer.fontHeight;
        }
    }
}
