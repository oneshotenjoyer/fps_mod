package net.modguy07.fpsmod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.Objects;

public class SettingsMenu extends Screen {
    public SettingsMenu(Text title) { super(title); }

    protected void init() {
        ButtonWidget changeBTN = ButtonWidget.builder(Text.of("Change corner"), (btn) -> {
            if (Objects.equals(Display.corner, "Top Left")) {
                Display.corner = "Top Right";
            } else if (Objects.equals(Display.corner, "Top Right")) {
                Display.corner = "Bottom Right";
            } else if (Objects.equals(Display.corner, "Bottom Right")) {
                Display.corner = "Bottom Left";
            } else if (Objects.equals(Display.corner, "Bottom Left")) {
                Display.corner = "Top Left";
            } // set corner
        }).dimensions(40, 40, 120, 20).build();
        this.addDrawableChild(changeBTN);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        context.drawText(this.textRenderer, Display.corner, 200, 46, 0xFFFFFFFF, true);
    }
}
