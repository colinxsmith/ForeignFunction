
package com.bitaplus.BitaModel.Optimisation;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

public class OptimiserFunctions {
  public static double lm_eps = Math.abs((((double) 4) / 3 - 1) * 3 - 1); // Machine accuracy
  public static String libraryname = "safeqp";

  public static double[][] Allocate2D(int n, int m) {
    double[][] y = new double[n][m];
    return y;
  }

  public static double[][] oneD2twoD(int n, int m, double[] ONED, Boolean transpose) {
    double[][] TWOD = (double[][]) new double[n][];
    if (transpose) {
      for (var i = 0; i < n; ++i) {
        TWOD[i] = new double[m];
        for (var j = 0; j < m; ++j) {
          TWOD[i][j] = ONED[i * m + j];
        }
      }
    } else {
      for (var i = 0; i < n; ++i) {
        TWOD[i] = new double[m];
        for (var j = 0; j < m; ++j) {
          TWOD[i][j] = ONED[i + j * n];
        }
      }
    }
    return TWOD;
  }

  public static double[] twoD2oneD(int n, int nf, double[][] TWOD, Boolean transpose) {
    assert n == TWOD.length;
    double[] ONED = new double[nf * n];
    if (transpose) {
      for (var i = 0; i < n; ++i) {
        for (var j = 0; j < nf; ++j) {
          ONED[j + i * nf] = TWOD[i][j];
        }
      }
    } else {
      for (var i = 0; i < n; ++i) {
        for (var j = 0; j < nf; ++j) {
          ONED[j * n + i] = TWOD[i][j];
        }
      }
    }
    return ONED;
  }

  public static double ddotvec(long n, double[] a, double[] b) {
    double back;
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var ddotvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("ddotvec").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      var A = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, a.length);
      for (int i = 0; i < a.length; i++) {
        A.setAtIndex(ValueLayout.JAVA_DOUBLE, i, a[i]);
      }
      var B = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, b.length);
      for (int i = 0; i < b.length; i++) {
        B.setAtIndex(ValueLayout.JAVA_DOUBLE, i, b[i]);
      }
      back = (double) ddotvecnative.invokeExact(n, A, B);
    } catch (Throwable e) {
      System.out.println(e);
      back = 0.0;
    }
    return back;
  }

  public static void dsubvec(long n, double[] a, double[] b, double[] c) {
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var dsubvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("dsubvec").orElseThrow(),
          FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      var A = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, a.length);
      for (int i = 0; i < a.length; i++) {
        A.setAtIndex(ValueLayout.JAVA_DOUBLE, i, a[i]);
      }
      var B = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, b.length);
      for (int i = 0; i < b.length; i++) {
        B.setAtIndex(ValueLayout.JAVA_DOUBLE, i, b[i]);
      }
      var C = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, c.length);
      for (int i = 0; i < b.length; i++) {
        C.setAtIndex(ValueLayout.JAVA_DOUBLE, i, c[i]);
      }
      dsubvecnative.invokeExact(n, A, B, C);
      for (int i = 0; i < b.length; i++) {
        c[i] = C.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void factor_model_process(long n, long nf, double[][] FL, double[] FC, double[] SV,
      double[] Q) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var factormodelprocessnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("factor_model_process").orElseThrow(),
          FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS,
              ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
      double[] FLOAD = twoD2oneD((int) n, (int) nf, FL, Boolean.FALSE);
      var FL_t = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD.length);
      for (int i = 0; i < FLOAD.length; i++) {
        FL_t.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD[i]);
      }
      var FC_t = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FC.length);
      for (int i = 0; i < FC.length; i++) {
        FC_t.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FC[i]);
      }
      var SV_t = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SV.length);
      for (int i = 0; i < SV.length; i++) {
        SV_t.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SV[i]);
      }
      var Q_t = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      for (int i = 0; i < Q.length; i++) {
        Q_t.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
      }
      factormodelprocessnative.invokeExact(n, nf, FL_t, FC_t, SV_t, Q_t);
      for (int i = 0; i < Q.length; ++i) {
        Q[i] = Q_t.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void daddvec(long n, double[] a, double[] b, double[] c) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var daddvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("daddvec").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      var aa = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, a.length);
      for (int i = 0; i < a.length; i++) {
        aa.setAtIndex(ValueLayout.JAVA_DOUBLE, i, a[i]);
      }
      var bb = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, b.length);
      for (int i = 0; i < b.length; i++) {
        bb.setAtIndex(ValueLayout.JAVA_DOUBLE, i, b[i]);
      }
      var cc = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, c.length);
      for (int i = 0; i < c.length; i++) {
        cc.setAtIndex(ValueLayout.JAVA_DOUBLE, i, c[i]);
      }
      daddvecnative.invokeExact(
          n,
          aa,
          bb,
          cc);
      for (int i = 0; i < a.length; i++) {
        a[i] = aa.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
      for (int i = 0; i < b.length; i++) {
        b[i] = bb.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
      for (int i = 0; i < c.length; i++) {
        c[i] = cc.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static double dsumvec(long n, double[] ss) {
    double back;
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var dsumvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("dsumvec").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      var ssss = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, ss.length);
      for (int i = 0; i < ss.length; i++) {
        ssss.setAtIndex(ValueLayout.JAVA_DOUBLE, i, ss[i]);
      }
      back = (double) dsumvecnative.invokeExact(
          n,
          ssss);
      for (int i = 0; i < ss.length; i++) {
        ss[i] = ssss.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0.0;
    }
    return back;
  }

  public static String version() {
    char[] buff = new char[400];
    String back = new String(buff);// Make a string of length 400 to house optimiser version string. This is more
                                   // than enough
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var versionnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("version").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      var aa = foreign.allocateUtf8String(back);
      MemorySegment bb = (MemorySegment) versionnative.invokeExact(aa);
      bb = bb.reinterpret(Long.MAX_VALUE);
      back = bb.getUtf8String(0);
    }

    catch (Throwable e) {
      System.out.println(e);
    }
    return back;
  }

  public static String cversion() {
    char[] buff = new char[400];
    String back = new String(buff);// Make a string of length 400 to house optimiser version string. This is more
                                   // than enough
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var versionnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("cversion").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      var aa = foreign.allocateUtf8String(back);
      MemorySegment bb = (MemorySegment) versionnative.invokeExact(aa);
      bb = bb.reinterpret(Long.MAX_VALUE);
      back=bb.getUtf8String(0);
    }

    catch (Throwable e) {
      System.out.println(e);
    }
    return back;
  }

  public static  int pickoutstrings(long nstocks, String[] stocklist, long M_nstocks, String[] M_stocklist, String[] Q, long[] Order) {
    int back; 
   try (Arena foreign = Arena.ofConfined()) { 
   final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
   var pickoutstringsnative = Linker.nativeLinker().downcallHandle(
   safeqp.find("pickoutstrings").orElseThrow(),
   FunctionDescriptor.of(ValueLayout.JAVA_INT,
     ValueLayout.JAVA_LONG ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_LONG ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ));
   var stockliststocklist = foreign.allocateArray(ValueLayout.ADDRESS, stocklist.length);
   for (int i = 0; i < stocklist.length; i++) {
     MemorySegment k5=foreign.allocateUtf8String(stocklist[i]);
     k5.setUtf8String(0,stocklist[i]);
     stockliststocklist.setAtIndex(ValueLayout.ADDRESS, i, k5);}
   var M_stocklistM_stocklist = foreign.allocateArray(ValueLayout.ADDRESS, M_stocklist.length);
   for (int i = 0; i < M_stocklist.length; i++) {
     MemorySegment k5=foreign.allocateUtf8String(M_stocklist[i]);
     k5.setUtf8String(0,M_stocklist[i]);
     M_stocklistM_stocklist.setAtIndex(ValueLayout.ADDRESS, i, k5);}
   var QQ = foreign.allocateArray(ValueLayout.ADDRESS, Q.length);
   for (int i = 0; i < Q.length; i++) {
     MemorySegment k5=foreign.allocateUtf8String(Q[i]);
     k5.setUtf8String(0,Q[i]);
     QQ.setAtIndex(ValueLayout.ADDRESS, i, k5);}
   var OrderOrder = foreign.allocateArray(ValueLayout.JAVA_LONG, Order.length);
   for (int i = 0; i < Order.length; i++) {
     OrderOrder.setAtIndex(ValueLayout.JAVA_LONG, i, Order[i]);}
   back = (int) pickoutstringsnative.invokeExact(
     nstocks ,
     stockliststocklist ,
     M_nstocks ,
     M_stocklistM_stocklist ,
     QQ ,
     OrderOrder );
   for (int i = 0; i < stocklist.length; i++) {
     var k8=stockliststocklist.getAtIndex(ValueLayout.ADDRESS, i);
     k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
     stocklist[i] = k8.getUtf8String(0);}
   for (int i = 0; i < M_stocklist.length; i++) {
     var k8=M_stocklistM_stocklist.getAtIndex(ValueLayout.ADDRESS, i);
     k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
     M_stocklist[i] = k8.getUtf8String(0);}
   for (int i = 0; i < Q.length; i++) {
     var k8=QQ.getAtIndex(ValueLayout.ADDRESS, i);
     k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
     Q[i] = k8.getUtf8String(0);}
   for (int i = 0; i < Order.length; i++) {
     Order[i]=OrderOrder.getAtIndex(ValueLayout.JAVA_LONG, i);}
   }
   catch (Throwable e) {       System.out.println(e);       back = 0;       }
   return back;}

}