package com.github.taichi3012.controlime.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.taichi3012.controlime.core.transformer.EnableIMEInput;

public class ClassTransformer implements IClassTransformer {

  private final Map<String, List<MethodTransformer>> methodTransformers = ImmutableSet.of(
    new EnableIMEInput()
  ).stream().collect(Collectors.groupingBy(mt -> mt.className));

  @Override
  public byte[] transform(String name, String transformedName, byte[] basicClass) {
    if (!methodTransformers.containsKey(transformedName))
      return basicClass;

    boolean isObfuscated = !name.equals(transformedName);
    String owner = name.replace('.', '/');
    Map<String, MethodTransformer> transformers = methodTransformers.get(transformedName).stream().collect(
      Collectors.toMap(
        mt -> isObfuscated ? mt.srgName : mapMethodName(owner, mt.srgName, mt.srgDesc),
        mt -> mt
      )
    );
    ClassNode cn = new ClassNode();
    new ClassReader(basicClass).accept(cn, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
    for (MethodNode mn : cn.methods) {
      String methodName = isObfuscated ? mapMethodName(owner, mn.name, mn.desc) : mn.name;
      MethodTransformer mt = transformers.get(methodName);
      if (mt == null || (!isObfuscated && !mt.srgDesc.equals(mn.desc)))
        continue;

      mt.transform(mn, isObfuscated);
      transformers.remove(methodName);
      if (transformers.isEmpty())
        break;
    }
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    cn.accept(cw);
    return cw.toByteArray();
  }

  private static String mapMethodName(String owner, String name, String desc) {
    return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc);
  }

}
