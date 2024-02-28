package com.bitaplus.BitaModel.Optimisation;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Info {
    static double risk(double a) {
      return a * a * a;
    }
  
    static double f1d(double as, double seek) {
      return risk(as) - seek;
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
      passer = passer.reinterpret(8);
      var kk = passer.getAtIndex(ValueLayout.JAVA_DOUBLE, 0);
      var back = f1d(a, kk);
      return back;
    }
  }
  
  