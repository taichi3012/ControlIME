package com.github.taichi3012.controlime;

import java.lang.reflect.Method;

import com.sun.jna.Pointer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import static com.sun.jna.platform.win32.WinDef.*;
import static com.github.taichi3012.controlime.IMM32.*;

@Mod(modid = ControlIME.MOD_ID, version = ControlIME.VERSION, useMetadata = true, clientSideOnly = true)
public class ControlIME {

  public static final String MOD_ID = "controlime";
  public static final String VERSION = "1.1.0";
  public static Logger logger;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void onGuiOpen(GuiOpenEvent event) {
    HWND hWND = getHWND();
    if (event.gui == null) {
      ImmAssociateContext(hWND, null);
    } else if (Minecraft.getMinecraft().currentScreen == null){
      ImmAssociateContextEx(hWND, null, new DWORD(16L));
      HIMC hIMC = ImmGetContext(hWND);
      ImmSetConversionStatus(hIMC, new DWORD(0L), new DWORD(8L));
      ImmReleaseContext(hWND, hIMC);
    }
  }

  public static HWND getHWND() {
    try {
      Class<?> clazz = Class.forName("org.lwjgl.WindowsSysImplementation");
      Method getHwnd_method = clazz.getDeclaredMethod("getHwnd");
      getHwnd_method.setAccessible(true);
      long p = (Long) getHwnd_method.invoke(null);
      return new HWND(new Pointer(p));
    } catch (ReflectiveOperationException e) {
      return null;
    }
  }

}
