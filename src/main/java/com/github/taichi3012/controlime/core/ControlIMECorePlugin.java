package com.github.taichi3012.controlime.core;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions("com.github.taichi3012.controlime.")
public class ControlIMECorePlugin implements IFMLLoadingPlugin {

  @Override
  public String[] getASMTransformerClass() {
    return new String[] {"com.github.taichi3012.controlime.core.ClassTransformer"};
  }

  @Override
  public String getModContainerClass() {
    return null;
  }

  @Override
  public String getSetupClass() {
    return null;
  }

  @Override
  public void injectData(Map<String, Object> data) {

  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }

}
