package com.owcadev.sheepcheats;

import com.owcadev.sheepcheats.combat.AutoClicker;
import com.owcadev.sheepcheats.combat.AutoSword;
import com.owcadev.sheepcheats.combat.TriggerBot;
import com.owcadev.sheepcheats.render.ESP;
import com.owcadev.sheepcheats.render.FreeCam;
import com.owcadev.sheepcheats.ui.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = "sheepcheats", version = "alpha-2", clientSideOnly = true)
public class sheepcheats {

    private boolean[] keyStates = new boolean[256];
    private static final Minecraft mc = Minecraft.getMinecraft();

    public boolean entityhitbox = false;
    public boolean playerhitbox = true;
    public boolean itemhitbox = true;
    public boolean autosword = true;
    public boolean autoclicker = false;
    public boolean tileEntityhitbox = true;
    public boolean triggerbot = false;
    public boolean freeCam = false;

    private boolean isChatOpen = false;
    private static String notificationText = "";
    private static long notificationEndTime = 0;

    private ESP renderHitboxes = new ESP();
    private AutoClicker clicker = new AutoClicker();
    private TriggerBot triggerBot = new TriggerBot();
    private AutoSword sword = new AutoSword();
    private FreeCam cam = new FreeCam();
    private KeyBinds key = new KeyBinds();

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void preInit(net.minecraftforge.fml.common.event.FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        key.ImportKeybinds();
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        triggerBot.triggerbot(event, triggerbot);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        renderHitboxes.EntityHitbox(event, entityhitbox, playerhitbox, itemhitbox, tileEntityhitbox);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (autosword) {
            sword.autoSword(event);
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (autoclicker) {
            clicker.autoclicker();
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            renderNotification();
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() != null && event.getGui().getClass().getName().equals("net.minecraft.client.gui.GuiChat")) {
            isChatOpen = true;
        } else if (isChatOpen) {
            isChatOpen = false;
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!isChatOpen && key.entityHitbox.isPressed()) {
            entityhitbox = !entityhitbox;
            displayNotification("EntityHitbox is: " + checkturn(entityhitbox), 3000);
        }
        if (!isChatOpen && key.playerHitbox.isPressed()) {
            playerhitbox = !playerhitbox;
            displayNotification("PlayerHitbox is: " + checkturn(playerhitbox), 3000);
        }
        if (!isChatOpen && key.autoSword.isPressed()) {
            autosword = !autosword;
            displayNotification("AutoSword is: " + checkturn(autosword), 3000);
        }
        if (!isChatOpen && key.itemHitbox.isPressed()) {
            itemhitbox = !itemhitbox;
            displayNotification("ItemHitbox is: " + checkturn(itemhitbox), 3000);
        }
        if (!isChatOpen && key.autoClicker.isPressed()) {
            autoclicker = !autoclicker;
            displayNotification("AutoClicker is: " + checkturn(autoclicker), 3000);
        }
        if (!isChatOpen && key.triggerBot.isPressed()){
            triggerbot = !triggerbot;
            displayNotification("TriggerBot is: " + checkturn(triggerbot), 3000);
        }
        /*
        if (!isChatOpen && key.freeCam.isPressed()){
            freeCam = !freeCam;
            displayNotification("FreeCam is: " + checkturn(freeCam), 3000);
            cam.toggleFreecam(freeCam);
        }
         */
        if (!isChatOpen && key.tileEntityHitbox.isPressed()) {
            tileEntityhitbox = !tileEntityhitbox;
            displayNotification("TileEntityHitbox is: " + checkturn(tileEntityhitbox), 3000);
        }
        if (!isChatOpen && key.showstatusinchat.isPressed()) {
            sendMessageToCLient(
                    "EntityHitbox is: " + checkturn(entityhitbox) + "\n" +
                            "PlayerHitbox is: " + checkturn(playerhitbox) + "\n" +
                            "AutoSword is: " + checkturn(autosword) + "\n" +
                            "ItemHitbox is: " + checkturn(itemhitbox) + "\n" +
                            "AutoClicker is: " + checkturn(autoclicker) + "\n" +
                            "TileEntityHitbox is: " + checkturn(tileEntityhitbox) + "\n" +
                            "TriggerBot is: " + checkturn(triggerbot) + "\n"
            );
        }
    }

    private void renderNotification() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.fontRenderer == null) {
            return;
        }

        if (System.currentTimeMillis() < notificationEndTime) {
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int screenWidth = scaledResolution.getScaledWidth();
            int screenHeight = scaledResolution.getScaledHeight();

            int notificationWidth = mc.fontRenderer.getStringWidth(notificationText);
            int notificationHeight = mc.fontRenderer.FONT_HEIGHT;

            int notificationX = screenWidth - notificationWidth - 5;
            int notificationY = screenHeight - 50;

            Gui.drawRect(notificationX - 2, notificationY - 2, notificationX + notificationWidth + 2, notificationY + notificationHeight + 2, 0x90000000);
            mc.fontRenderer.drawStringWithShadow(notificationText, notificationX, notificationY, 0xFFFFFF);
        }
    }

    public static void displayNotification(String text, int durationMillis) {
        notificationText = text;
        notificationEndTime = System.currentTimeMillis() + durationMillis;
    }
    private void sendMessageToChat(String message) {
        mc.player.connection.sendPacket(new CPacketChatMessage(message));
    }

    private void sendMessageToCLient(String message) {
        if (mc.player != null) {
            ITextComponent textComponent = new TextComponentString(message);
            mc.player.sendMessage(textComponent);
        }
    }

    private String checkturn(boolean check) {
        if (check) {
            return "On";
        } else {
            return "Off";
        }
    }
}
