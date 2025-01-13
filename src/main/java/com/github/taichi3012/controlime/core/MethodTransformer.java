package com.github.taichi3012.controlime.core;

import org.objectweb.asm.tree.MethodNode;

public abstract class MethodTransformer {

  public final String className;
  public final String srgName;
  public final String srgDesc;

  protected MethodTransformer(String className, String srgName, String srgDesc) {
    this.className = className;
    this.srgName = srgName;
    this.srgDesc = srgDesc;
  }

  public abstract void transform(MethodNode mn, boolean isObfuscated);

}
