package com.bitaplus.BitaModel.Optimisation;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

// compile with javac  --enable-preview  --source 21 -Xlint:preview  Info.java

public class Info {
  public double seek;

  public static double getseek(Object ppp) {
    return ((Info) ppp).seek;
  }

  public static double tester(double aa, double seeks) {
    return aa * aa * aa - seeks;
  }

  public static double passer(double aa, Object passer) {
    return ((Info) passer).f1d(aa);
  }

  public double f1d(double a) {// For goal seek the function name f1d is defined in the SWIG interface code. We
    // must call it f1d
    double back = risk(a);
    System.out.println("a=" + a + "\ta*a*a=" + back + "\tresult=" + (back - seek));
    return (back - seek);
  }

  static double risk(double a) {
    return a * a * a;
  }

  static double f1d(double a, double seek) {
    var back=risk(a);
    System.out.println("a=" + a + "\ta*a*a=" + back + "\tresult=" + (back - seek));
    return back - seek;
  }

  static double f1df1d(double as, Object passer) {
    double back = -1;
    MethodHandle mh = null;
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    try {
      mh = lookup.findStatic(passer.getClass(), "passer",
          MethodType.methodType(double.class, double.class, Object.class));
      back = (double) mh.invokeExact(as, passer);
    } catch (Throwable u) {
      System.out.println(u);
    }
    return back;
  }

  static double passerFunc(double a, MemorySegment passer) {
    if (passer.byteSize() == 0)
      passer = passer.reinterpret(8);
    var kk = passer.get(ValueLayout.JAVA_DOUBLE, 0);
    var back = f1d(a, kk);
    return back;
  }
  static double passerMinFunc(double a, MemorySegment passer) {
    var back = f1d(a, 0);
    return back;
  }
}
