package com.owcadev.sheepcheats.ui;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;
//
// This File is a mess
//
public class KeyBinds {

    public static KeyBinding entityHitbox;
    public static KeyBinding playerHitbox;
    public static KeyBinding autoSword;
    public static KeyBinding itemHitbox;
    public static KeyBinding autoClicker;
    public static KeyBinding tileEntityHitbox;
    public static KeyBinding triggerBot;
    public static KeyBinding showstatusinchat;
    public static KeyBinding freeCam;
    public static KeyBinding xray;
    public static KeyBinding menu;

    private static String category = "SheepCheats";

    public void ImportKeybinds() {
        entityHitbox = new KeyBinding("EntityHitbox", Keyboard.KEY_H, category);
        playerHitbox = new KeyBinding("PlayerHitbox", Keyboard.KEY_I, category);
        autoSword = new KeyBinding("AutoSword", Keyboard.KEY_O, category);
        itemHitbox = new KeyBinding("ItemHitbox", Keyboard.KEY_J, category);
        autoClicker = new KeyBinding("AutoClicker", Keyboard.KEY_K, category);
        tileEntityHitbox = new KeyBinding("TileEntityHitbox", Keyboard.KEY_V, category);
        showstatusinchat = new KeyBinding("Show Status cheats", Keyboard.KEY_M, category);
        triggerBot = new KeyBinding("TriggerBot", Keyboard.KEY_NONE, category);
        freeCam = new KeyBinding("FreeCam", Keyboard.KEY_NONE, category);
        // xray = new KeyBinding("Xray", Keyboard.KEY_NONE, category);
        // menu = new KeyBinding("Menu", Keyboard.KEY_RSHIFT, category);

        ClientRegistry.registerKeyBinding(entityHitbox);
        ClientRegistry.registerKeyBinding(playerHitbox);
        ClientRegistry.registerKeyBinding(autoSword);
        ClientRegistry.registerKeyBinding(itemHitbox);
        ClientRegistry.registerKeyBinding(autoClicker);
        ClientRegistry.registerKeyBinding(tileEntityHitbox);
        ClientRegistry.registerKeyBinding(showstatusinchat);
        ClientRegistry.registerKeyBinding(triggerBot);
        ClientRegistry.registerKeyBinding(freeCam);
        // ClientRegistry.registerKeyBinding(xray);
        // ClientRegistry.registerKeyBinding(menu);
    }
}
