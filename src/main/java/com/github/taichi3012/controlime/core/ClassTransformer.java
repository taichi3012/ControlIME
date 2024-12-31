package com.github.taichi3012.controlime.core;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer {

  private static final String TARGET_CLASS_NAME = "net.minecraft.client.gui.GuiScreen";
  private static final String TARGET_METHOD_NAME = "handleKeyboardInput";
  private static final String TARGET_METHOD_OBF_NAME = "l";
  private static final String TARGET_METHOD_DESC = "()V";

  @Override
  public byte[] transform(String name, String transformedName, byte[] basicClass) {
    if (!TARGET_CLASS_NAME.equals(transformedName))
      return basicClass;

    ClassReader cr = new ClassReader(basicClass);
    ClassNode cn = new ClassNode();
    cr.accept(cn, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);

    for (MethodNode mn : cn.methods) {
      if (!TARGET_METHOD_DESC.equals(mn.desc) || !(TARGET_METHOD_OBF_NAME.equals(mn.name) || TARGET_METHOD_NAME.equals(mn.name)))
        continue;

      boolean isObfuscated = TARGET_METHOD_OBF_NAME.equals(mn.name);
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
      mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", isObfuscated ? "func_73869_a" : "keyTyped", "(CI)V", false);
      mn.visitLabel(labelB);
      mn.visitVarInsn(Opcodes.ALOAD, 0);
      mn.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/gui/GuiScreen", isObfuscated ? "field_146297_k" : "mc", "Lnet/minecraft/client/Minecraft;");
      mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/Minecraft", isObfuscated ? "func_152348_aa" : "dispatchKeypresses", "()V", false);
      mn.visitInsn(Opcodes.RETURN);
    }
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    cn.accept(cw);
    return cw.toByteArray();
  }

}
