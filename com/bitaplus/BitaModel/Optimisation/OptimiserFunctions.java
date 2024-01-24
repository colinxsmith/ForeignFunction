
package com.bitaplus.BitaModel.Optimisation;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

public class OptimiserFunctions {
  public static double lm_eps = Math.abs((((double) 4) / 3 - 1) * 3 - 1); // Machine accuracy
  public static String libraryname = "safeqp.dll";

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
      back = aa.getUtf8String(0);
    }

    catch (Throwable e) {
      System.out.println(e);
    }
    return back;
  }
}