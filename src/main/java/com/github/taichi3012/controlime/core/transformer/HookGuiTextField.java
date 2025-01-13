package com.github.taichi3012.controlime.core.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.github.taichi3012.controlime.core.MethodTransformer;

public class HookGuiTextField {

  private static final String CLASS_NAME = "net.minecraft.client.gui.GuiTextField";

  public static class HookSetFocus extends MethodTransformer {

    public HookSetFocus() {
      super(CLASS_NAME, "func_146195_b", "(Z)V");
    }

    @Override
    public void transform(MethodNode mn, boolean isObfuscated) {
      InsnList list = new InsnList();
      list.add(new VarInsnNode(Opcodes.ALOAD, 0));
      list.add(new VarInsnNode(Opcodes.ILOAD, 1));
      String hookDesc = String.format("(L%s;Z)V", CLASS_NAME.replace('.', '/'));
      list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/taichi3012/controlime/core/Hook", "onSetFocus", hookDesc, false));
      mn.instructions.insert(list);
    }

  }

}
