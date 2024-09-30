package com.mattmx.nametags.entity.trait;

import com.mattmx.nametags.NameTags;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

public class SneakTrait extends Trait {
    private int previousOpacity = 0;
    private boolean isSneaking = false;

    public void updateSneak(boolean sneaking) {
        this.isSneaking = sneaking;
        getTag().modify((meta) -> {
            Color color = Color.fromARGB(meta.getBackgroundColor());

            if (sneaking) {

                // If it's transparent then we shouldn't do anything really
                if (color.getAlpha() == 0) {
                    return;
                }

                previousOpacity = color.getAlpha();

                meta.setBackgroundColor(withCustomSneakOpacity(color).asARGB());
            } else {
                meta.setBackgroundColor(color.setAlpha(previousOpacity).asARGB());
            }
        });
        getTag().getPassenger().refresh();
    }

    public Color withCustomSneakOpacity(@NotNull Color previous) {
        int sneakAmount = NameTags.getInstance()
            .getConfig()
            .getInt("sneak.opacity", 70);

        return previous.setAlpha(sneakAmount);
    }

    public boolean isSneaking() {
        return isSneaking;
    }
}
