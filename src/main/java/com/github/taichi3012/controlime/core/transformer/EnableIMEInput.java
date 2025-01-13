package com.github.taichi3012.controlime.core.transformer;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

import com.github.taichi3012.controlime.core.MethodTransformer;

public class EnableIMEInput extends MethodTransformer {

  public EnableIMEInput() {
    super("net.minecraft.client.gui.GuiScreen", "func_146282_l", "()V");
  }

  @Override
  public void transform(MethodNode mn, boolean isObfuscated) {
    mn.instructions.clear();
    mn.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/input/Keyboard", "getEventCharacter", "()C", false);
    mn.visitVarInsn(Opcodes.ISTORE, 1);
    mn.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/input/Keyboard", "getEventKey", "()I", false);
    mn.visitVarInsn(Opcodes.ISTORE, 2);
    Label labelA = new Label();
    mn.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/input/Keyboard", "getEventKeyState", "()Z", false);
    mn.visitJumpInsn(Opcodes.IFNE, labelA);
    Label labelB = new Label();
    mn.visitVarInsn(Opcodes.ILOAD, 2);
    mn.visitJumpInsn(Opcodes.IFNE, labelB);
    mn.visitVarInsn(Opcodes.ILOAD, 1);
    mn.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "isDefined", "(C)Z", false);
    mn.visitJumpInsn(Opcodes.IFEQ, labelB);
    mn.visitLabel(labelA);
    mn.visitVarInsn(Opcodes.ALOAD, 0);
    mn.visitVarInsn(Opcodes.ILOAD, 1);
    mn.visitVarInsn(Opcodes.ILOAD, 2);
    mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", "func_73869_a", "(CI)V", false);
    mn.visitLabel(labelB);
    mn.visitVarInsn(Opcodes.ALOAD, 0);
    mn.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/gui/GuiScreen", "field_146297_k", "Lnet/minecraft/client/Minecraft;");
    mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "func_152348_aa", "()V", false);
    mn.visitInsn(Opcodes.RETURN);
  }

}
