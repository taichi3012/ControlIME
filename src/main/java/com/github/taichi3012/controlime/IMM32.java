package com.github.taichi3012.controlime;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

import static com.sun.jna.platform.win32.WinDef.*;

public class IMM32 {

  static {
    Native.register("imm32");
  }

  public static native HIMC ImmGetContext(HWND hWnd);
  public static native boolean ImmReleaseContext(HWND hWnd, HIMC hIMC);
  public static native HIMC ImmAssociateContext(HWND hWND, HIMC hIMC);
  public static native boolean ImmAssociateContextEx(HWND hWND, HIMC hIMC, DWORD dwFlag);
  public static native boolean ImmSetConversionStatus(HIMC hIMC, DWORD dwConversion, DWORD dwSentence);
  public static native boolean ImmGetConversionStatus(HIMC hIMC, IntByReference conversion, IntByReference sentence);

  public static class HIMC extends WinNT.HANDLE {

    public HIMC() {

    }

    public HIMC(Pointer p) {
      super(p);
    }

  }

}
