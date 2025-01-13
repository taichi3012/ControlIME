package com.github.taichi3012.controlime.core;

import net.minecraft.client.gui.GuiTextField;

import com.github.taichi3012.controlime.ControlIME;

public class Hook {

  public static void onSetFocus(GuiTextField textField, boolean arg) {
    if (textField.isFocused() == arg)
      return;

    if (arg && textField.isEnabled) {
      ControlIME.enableIME();
      return;
    }
    if (!arg) {
      ControlIME.disableIME();
    }
  }

}
