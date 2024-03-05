
package com.bitaplus.BitaModel.Optimisation;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;

import java.lang.foreign.MemorySegment;

import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class OptimiserFunctions {
  public static double lm_eps = Math.abs((((double) 4) / 3 - 1) * 3 - 1); // Machine accuracy
  public static String libraryname = "safeqp";

  public static double[][] Allocate2D(int n, int m) {
    double[][] y = new double[n][m];
    return y;
  }

  public static double[][] oneD2twoD(int n, int m, double[] ONED) {
    return oneD2twoD(n, m, ONED, Boolean.FALSE);
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

  public static double[] twoD2oneD(int n, int nf, double[][] TWOD) {
    return twoD2oneD(n, nf, TWOD, Boolean.FALSE);
  }

  public static double[] twoD2oneD(int n, int nf, double[][] TWOD, Boolean transpose) {
    if (TWOD == null) {
      return null;
    }
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

  public static String Return_Message(int ifail) {
    String back;
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var Return_Messagenative = Linker.nativeLinker().downcallHandle(
          safeqp.find("Return_Message").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT));
      MemorySegment bbb = (MemorySegment) Return_Messagenative.invokeExact(
          ifail);
      bbb = bbb.reinterpret(Long.MAX_VALUE);
      back = bbb.getUtf8String(0);
    } catch (Throwable e) {
      System.out.println(e);
      back = "";
    }
    return back;
  }

  public static double ddotvec(long n, double[] a, double[] b) {
    double back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var ddotvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("ddotvec").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment aa;
      if (a == null) {
        aa = MemorySegment.NULL;
      } else {
        aa = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, a.length);
      }
      if (a != null) {
        for (int i = 0; i < a.length; i++) {
          aa.setAtIndex(ValueLayout.JAVA_DOUBLE, i, a[i]);
        }
      }
      MemorySegment bb;
      if (b == null) {
        bb = MemorySegment.NULL;
      } else {
        bb = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, b.length);
      }
      if (b != null) {
        for (int i = 0; i < b.length; i++) {
          bb.setAtIndex(ValueLayout.JAVA_DOUBLE, i, b[i]);
        }
      }
      back = (double) ddotvecnative.invokeExact(
          n,
          aa,
          bb);
      if (a != null)
        for (int i = 0; i < a.length; i++) {
          a[i] = aa.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (b != null)
        for (int i = 0; i < b.length; i++) {
          b[i] = bb.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static void dsubvec(long n, double[] a, double[] b, double[] c) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var dsubvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("dsubvec").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment aa;
      if (a == null) {
        aa = MemorySegment.NULL;
      } else {
        aa = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, a.length);
      }
      if (a != null) {
        for (int i = 0; i < a.length; i++) {
          aa.setAtIndex(ValueLayout.JAVA_DOUBLE, i, a[i]);
        }
      }
      MemorySegment bb;
      if (b == null) {
        bb = MemorySegment.NULL;
      } else {
        bb = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, b.length);
      }
      if (b != null) {
        for (int i = 0; i < b.length; i++) {
          bb.setAtIndex(ValueLayout.JAVA_DOUBLE, i, b[i]);
        }
      }
      MemorySegment cc;
      if (c == null) {
        cc = MemorySegment.NULL;
      } else {
        cc = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, c.length);
      }
      if (c != null) {
        for (int i = 0; i < c.length; i++) {
          cc.setAtIndex(ValueLayout.JAVA_DOUBLE, i, c[i]);
        }
      }
      dsubvecnative.invokeExact(
          n,
          aa,
          bb,
          cc);
      if (a != null)
        for (int i = 0; i < a.length; i++) {
          a[i] = aa.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (b != null)
        for (int i = 0; i < b.length; i++) {
          b[i] = bb.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (c != null)
        for (int i = 0; i < c.length; i++) {
          c[i] = cc.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static int days_left(String[] aversion) {
    int back;
    aversion[0] = "q";
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var days_leftnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("days_left").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS));
      var aversionaversion = foreign.allocateArray(ValueLayout.ADDRESS, aversion.length);
      for (int i = 0; i < aversion.length; i++) {
        MemorySegment k5 = foreign.allocateUtf8String(aversion[i]);
        k5.setUtf8String(0, aversion[i]);
        aversionaversion.setAtIndex(ValueLayout.ADDRESS, i, k5);
      }
      back = (int) days_leftnative.invokeExact(
          aversionaversion);
      for (int i = 0; i < aversion.length; i++) {
        var k8 = aversionaversion.getAtIndex(ValueLayout.ADDRESS, i);
        k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
        aversion[i] = k8.getUtf8String(0);
      }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static void factor_model_process(long n, long nf, double[][] FL, double[] FC, double[] SV, double[] Q) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var factor_model_processnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("factor_model_process").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      double[] FL1d = twoD2oneD((int) n, (int) nf, FL); // Get the integer arguments correct!
      var FL1dFL1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FL1d.length);
      for (int i = 0; i < FL1d.length; i++) {
        FL1dFL1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FL1d[i]);
      }
      var FCFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FC.length);
      for (int i = 0; i < FC.length; i++) {
        FCFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FC[i]);
      }
      var SVSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SV.length);
      for (int i = 0; i < SV.length; i++) {
        SVSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SV[i]);
      }
      var QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      for (int i = 0; i < Q.length; i++) {
        QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
      }
      factor_model_processnative.invokeExact(
          n,
          nf,
          FL1dFL1d,
          FCFC,
          SVSV,
          QQ);
      for (int i = 0; i < FL1d.length; i++) {
        FL1d[i] = FL1dFL1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
      for (int i = 0; i < FC.length; i++) {
        FC[i] = FCFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
      for (int i = 0; i < SV.length; i++) {
        SV[i] = SVSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
      }
      for (int i = 0; i < Q.length; i++) {
        Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
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
    double back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var dsumvecnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("dsumvec").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment ssss;
      if (ss == null) {
        ssss = MemorySegment.NULL;
      } else {
        ssss = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, ss.length);
      }
      if (ss != null) {
        for (int i = 0; i < ss.length; i++) {
          ssss.setAtIndex(ValueLayout.JAVA_DOUBLE, i, ss[i]);
        }
      }
      back = (double) dsumvecnative.invokeExact(
          n,
          ssss);
      if (ss != null)
        for (int i = 0; i < ss.length; i++) {
          ss[i] = ssss.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
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
      back = bb.getUtf8String(0);
    }

    catch (Throwable e) {
      System.out.println(e);
    }
    return back;
  }

  public static int pickoutstrings(long nstocks, String[] stocklist, long M_nstocks, String[] M_stocklist, String[] Q,
      long[] Order) {
    int back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var pickoutstringsnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("pickoutstrings").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_INT,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment stockliststocklist;
      if (stocklist == null) {
        stockliststocklist = MemorySegment.NULL;
      } else {
        stockliststocklist = foreign.allocateArray(ValueLayout.ADDRESS, stocklist.length);
      }
      if (stocklist != null) {
        for (int i = 0; i < stocklist.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocklist[i]);
          k5.setUtf8String(0, stocklist[i]);
          stockliststocklist.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment M_stocklistM_stocklist;
      if (M_stocklist == null) {
        M_stocklistM_stocklist = MemorySegment.NULL;
      } else {
        M_stocklistM_stocklist = foreign.allocateArray(ValueLayout.ADDRESS, M_stocklist.length);
      }
      if (M_stocklist != null) {
        for (int i = 0; i < M_stocklist.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(M_stocklist[i]);
          k5.setUtf8String(0, M_stocklist[i]);
          M_stocklistM_stocklist.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.ADDRESS, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(Q[i]);
          k5.setUtf8String(0, Q[i]);
          QQ.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment OrderOrder;
      if (Order == null) {
        OrderOrder = MemorySegment.NULL;
      } else {
        OrderOrder = foreign.allocateArray(ValueLayout.JAVA_LONG, Order.length);
      }
      if (Order != null) {
        for (int i = 0; i < Order.length; i++) {
          OrderOrder.setAtIndex(ValueLayout.JAVA_LONG, i, Order[i]);
        }
      }
      back = (int) pickoutstringsnative.invokeExact(
          nstocks,
          stockliststocklist,
          M_nstocks,
          M_stocklistM_stocklist,
          QQ,
          OrderOrder);
      if (stocklist != null)
        for (int i = 0; i < stocklist.length; i++) {
          var k8 = stockliststocklist.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocklist[i] = k8.getUtf8String(0);
        }
      if (M_stocklist != null)
        for (int i = 0; i < M_stocklist.length; i++) {
          var k8 = M_stocklistM_stocklist.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          M_stocklist[i] = k8.getUtf8String(0);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          var k8 = QQ.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          Q[i] = k8.getUtf8String(0);
        }
      if (Order != null)
        for (int i = 0; i < Order.length; i++) {
          Order[i] = OrderOrder.getAtIndex(ValueLayout.JAVA_LONG, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static short Optimise_internalCVPAFbl(long n, int nfac, String[] stocknames, double[] w_opt, long m,
      double[][] AAA, double[] L, double[] U, double[] alpha, double[] benchmark, double[] QMATRIX, double gamma,
      double[] Initial, double delta, double[] buy, double[] sell, double kappa, int basket, int trades, int revise,
      int costs, double min_holding, double min_trade, int m_LS, int Fully_Invested, double Rmin, double Rmax,
      int m_Round, double[] min_lot, double[] size_lot, int[] shake, long ncomp, double[] Composite, double LSValue,
      long npiece, double[] hpiece, double[] pgrad, long nabs, double[][] Abs_A, long mabs, long[] I_A, double[] Abs_U,
      double[] FFC, double[][] FLOAD, double[] SSV, double minRisk, double maxRisk, double[] ogamma, double[] mask,
      int log, String logfile, int downrisk, double downfactor, int longbasket, int shortbasket, int tradebuy,
      int tradesell, double zetaS, double zetaF, double ShortCostScale, double LSValuel, double[] Abs_L) {
    short back;
    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var Optimise_internalCVPAFblnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("Optimise_internalCVPAFbl").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_SHORT,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS));
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment w_optw_opt;
      if (w_opt == null) {
        w_optw_opt = MemorySegment.NULL;
      } else {
        w_optw_opt = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w_opt.length);
      }
      if (w_opt != null) {
        for (int i = 0; i < w_opt.length; i++) {
          w_optw_opt.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w_opt[i]);
        }
      }
      double[] AAA1d = twoD2oneD((int) m, (int) n, AAA); // Get the integer arguments correct!
      MemorySegment AAA1dAAA1d;
      if (AAA1d == null) {
        AAA1dAAA1d = MemorySegment.NULL;
      } else {
        AAA1dAAA1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, AAA1d.length);
      }
      if (AAA1d != null) {
        for (int i = 0; i < AAA1d.length; i++) {
          AAA1dAAA1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, AAA1d[i]);
        }
      }
      MemorySegment LL;
      if (L == null) {
        LL = MemorySegment.NULL;
      } else {
        LL = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, L.length);
      }
      if (L != null) {
        for (int i = 0; i < L.length; i++) {
          LL.setAtIndex(ValueLayout.JAVA_DOUBLE, i, L[i]);
        }
      }
      MemorySegment UU;
      if (U == null) {
        UU = MemorySegment.NULL;
      } else {
        UU = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, U.length);
      }
      if (U != null) {
        for (int i = 0; i < U.length; i++) {
          UU.setAtIndex(ValueLayout.JAVA_DOUBLE, i, U[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment QMATRIXQMATRIX;
      if (QMATRIX == null) {
        QMATRIXQMATRIX = MemorySegment.NULL;
      } else {
        QMATRIXQMATRIX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, QMATRIX.length);
      }
      if (QMATRIX != null) {
        for (int i = 0; i < QMATRIX.length; i++) {
          QMATRIXQMATRIX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, QMATRIX[i]);
        }
      }
      MemorySegment InitialInitial;
      if (Initial == null) {
        InitialInitial = MemorySegment.NULL;
      } else {
        InitialInitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Initial.length);
      }
      if (Initial != null) {
        for (int i = 0; i < Initial.length; i++) {
          InitialInitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Initial[i]);
        }
      }
      MemorySegment buybuy;
      if (buy == null) {
        buybuy = MemorySegment.NULL;
      } else {
        buybuy = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, buy.length);
      }
      if (buy != null) {
        for (int i = 0; i < buy.length; i++) {
          buybuy.setAtIndex(ValueLayout.JAVA_DOUBLE, i, buy[i]);
        }
      }
      MemorySegment sellsell;
      if (sell == null) {
        sellsell = MemorySegment.NULL;
      } else {
        sellsell = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, sell.length);
      }
      if (sell != null) {
        for (int i = 0; i < sell.length; i++) {
          sellsell.setAtIndex(ValueLayout.JAVA_DOUBLE, i, sell[i]);
        }
      }
      MemorySegment min_lotmin_lot;
      if (min_lot == null) {
        min_lotmin_lot = MemorySegment.NULL;
      } else {
        min_lotmin_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_lot.length);
      }
      if (min_lot != null) {
        for (int i = 0; i < min_lot.length; i++) {
          min_lotmin_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_lot[i]);
        }
      }
      MemorySegment size_lotsize_lot;
      if (size_lot == null) {
        size_lotsize_lot = MemorySegment.NULL;
      } else {
        size_lotsize_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, size_lot.length);
      }
      if (size_lot != null) {
        for (int i = 0; i < size_lot.length; i++) {
          size_lotsize_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, size_lot[i]);
        }
      }
      MemorySegment shakeshake;
      if (shake == null) {
        shakeshake = MemorySegment.NULL;
      } else {
        shakeshake = foreign.allocateArray(ValueLayout.JAVA_INT, shake.length);
      }
      if (shake != null) {
        for (int i = 0; i < shake.length; i++) {
          shakeshake.setAtIndex(ValueLayout.JAVA_INT, i, shake[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      MemorySegment hpiecehpiece;
      if (hpiece == null) {
        hpiecehpiece = MemorySegment.NULL;
      } else {
        hpiecehpiece = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, hpiece.length);
      }
      if (hpiece != null) {
        for (int i = 0; i < hpiece.length; i++) {
          hpiecehpiece.setAtIndex(ValueLayout.JAVA_DOUBLE, i, hpiece[i]);
        }
      }
      MemorySegment pgradpgrad;
      if (pgrad == null) {
        pgradpgrad = MemorySegment.NULL;
      } else {
        pgradpgrad = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pgrad.length);
      }
      if (pgrad != null) {
        for (int i = 0; i < pgrad.length; i++) {
          pgradpgrad.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pgrad[i]);
        }
      }
      double[] Abs_A1d = twoD2oneD((int) nabs, (int) n, Abs_A); // Get the integer arguments correct!
      MemorySegment Abs_A1dAbs_A1d;
      if (Abs_A1d == null) {
        Abs_A1dAbs_A1d = MemorySegment.NULL;
      } else {
        Abs_A1dAbs_A1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_A1d.length);
      }
      if (Abs_A1d != null) {
        for (int i = 0; i < Abs_A1d.length; i++) {
          Abs_A1dAbs_A1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_A1d[i]);
        }
      }
      MemorySegment I_AI_A;
      if (I_A == null) {
        I_AI_A = MemorySegment.NULL;
      } else {
        I_AI_A = foreign.allocateArray(ValueLayout.JAVA_LONG, I_A.length);
      }
      if (I_A != null) {
        for (int i = 0; i < I_A.length; i++) {
          I_AI_A.setAtIndex(ValueLayout.JAVA_LONG, i, I_A[i]);
        }
      }
      MemorySegment Abs_UAbs_U;
      if (Abs_U == null) {
        Abs_UAbs_U = MemorySegment.NULL;
      } else {
        Abs_UAbs_U = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_U.length);
      }
      if (Abs_U != null) {
        for (int i = 0; i < Abs_U.length; i++) {
          Abs_UAbs_U.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_U[i]);
        }
      }
      MemorySegment FFCFFC;
      if (FFC == null) {
        FFCFFC = MemorySegment.NULL;
      } else {
        FFCFFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FFC.length);
      }
      if (FFC != null) {
        for (int i = 0; i < FFC.length; i++) {
          FFCFFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FFC[i]);
        }
      }
      double[] FLOAD1d = twoD2oneD((int) n, (int) nfac, FLOAD); // Get the integer arguments correct!
      MemorySegment FLOAD1dFLOAD1d;
      if (FLOAD1d == null) {
        FLOAD1dFLOAD1d = MemorySegment.NULL;
      } else {
        FLOAD1dFLOAD1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD1d.length);
      }
      if (FLOAD1d != null) {
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1dFLOAD1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD1d[i]);
        }
      }
      MemorySegment SSVSSV;
      if (SSV == null) {
        SSVSSV = MemorySegment.NULL;
      } else {
        SSVSSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SSV.length);
      }
      if (SSV != null) {
        for (int i = 0; i < SSV.length; i++) {
          SSVSSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SSV[i]);
        }
      }
      MemorySegment ogammaogamma;
      if (ogamma == null) {
        ogammaogamma = MemorySegment.NULL;
      } else {
        ogammaogamma = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, ogamma.length);
      }
      if (ogamma != null) {
        for (int i = 0; i < ogamma.length; i++) {
          ogammaogamma.setAtIndex(ValueLayout.JAVA_DOUBLE, i, ogamma[i]);
        }
      }
      MemorySegment maskmask;
      if (mask == null) {
        maskmask = MemorySegment.NULL;
      } else {
        maskmask = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, mask.length);
      }
      if (mask != null) {
        for (int i = 0; i < mask.length; i++) {
          maskmask.setAtIndex(ValueLayout.JAVA_DOUBLE, i, mask[i]);
        }
      }
      var logfilelogfile = foreign.allocateUtf8String(logfile);
      logfilelogfile.setUtf8String(0, logfile);
      MemorySegment Abs_LAbs_L;
      if (Abs_L == null) {
        Abs_LAbs_L = MemorySegment.NULL;
      } else {
        Abs_LAbs_L = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_L.length);
      }
      if (Abs_L != null) {
        for (int i = 0; i < Abs_L.length; i++) {
          Abs_LAbs_L.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_L[i]);
        }
      }
      back = (short) Optimise_internalCVPAFblnative.invokeExact(
          n,
          nfac,
          stocknamesstocknames,
          w_optw_opt,
          m,
          AAA1dAAA1d,
          LL,
          UU,
          alphaalpha,
          benchmarkbenchmark,
          QMATRIXQMATRIX,
          gamma,
          InitialInitial,
          delta,
          buybuy,
          sellsell,
          kappa,
          basket,
          trades,
          revise,
          costs,
          min_holding,
          min_trade,
          m_LS,
          Fully_Invested,
          Rmin,
          Rmax,
          m_Round,
          min_lotmin_lot,
          size_lotsize_lot,
          shakeshake,
          ncomp,
          CompositeComposite,
          LSValue,
          npiece,
          hpiecehpiece,
          pgradpgrad,
          nabs,
          Abs_A1dAbs_A1d,
          mabs,
          I_AI_A,
          Abs_UAbs_U,
          FFCFFC,
          FLOAD1dFLOAD1d,
          SSVSSV,
          minRisk,
          maxRisk,
          ogammaogamma,
          maskmask,
          log,
          logfilelogfile,
          downrisk,
          downfactor,
          longbasket,
          shortbasket,
          tradebuy,
          tradesell,
          zetaS,
          zetaF,
          ShortCostScale,
          LSValuel,
          Abs_LAbs_L);
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w_opt != null)
        for (int i = 0; i < w_opt.length; i++) {
          w_opt[i] = w_optw_opt.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (AAA1d != null)
        for (int i = 0; i < AAA1d.length; i++) {
          AAA1d[i] = AAA1dAAA1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (L != null)
        for (int i = 0; i < L.length; i++) {
          L[i] = LL.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (U != null)
        for (int i = 0; i < U.length; i++) {
          U[i] = UU.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (QMATRIX != null)
        for (int i = 0; i < QMATRIX.length; i++) {
          QMATRIX[i] = QMATRIXQMATRIX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Initial != null)
        for (int i = 0; i < Initial.length; i++) {
          Initial[i] = InitialInitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (buy != null)
        for (int i = 0; i < buy.length; i++) {
          buy[i] = buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (sell != null)
        for (int i = 0; i < sell.length; i++) {
          sell[i] = sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_lot != null)
        for (int i = 0; i < min_lot.length; i++) {
          min_lot[i] = min_lotmin_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (size_lot != null)
        for (int i = 0; i < size_lot.length; i++) {
          size_lot[i] = size_lotsize_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (shake != null)
        for (int i = 0; i < shake.length; i++) {
          shake[i] = shakeshake.getAtIndex(ValueLayout.JAVA_INT, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (hpiece != null)
        for (int i = 0; i < hpiece.length; i++) {
          hpiece[i] = hpiecehpiece.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (pgrad != null)
        for (int i = 0; i < pgrad.length; i++) {
          pgrad[i] = pgradpgrad.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Abs_A1d != null)
        for (int i = 0; i < Abs_A1d.length; i++) {
          Abs_A1d[i] = Abs_A1dAbs_A1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (I_A != null)
        for (int i = 0; i < I_A.length; i++) {
          I_A[i] = I_AI_A.getAtIndex(ValueLayout.JAVA_LONG, i);
        }
      if (Abs_U != null)
        for (int i = 0; i < Abs_U.length; i++) {
          Abs_U[i] = Abs_UAbs_U.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FFC != null)
        for (int i = 0; i < FFC.length; i++) {
          FFC[i] = FFCFFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FLOAD1d != null)
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1d[i] = FLOAD1dFLOAD1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (SSV != null)
        for (int i = 0; i < SSV.length; i++) {
          SSV[i] = SSVSSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (ogamma != null)
        for (int i = 0; i < ogamma.length; i++) {
          ogamma[i] = ogammaogamma.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (mask != null)
        for (int i = 0; i < mask.length; i++) {
          mask[i] = maskmask.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      logfile = logfilelogfile.getUtf8String(0);
      if (Abs_L != null)
        for (int i = 0; i < Abs_L.length; i++) {
          Abs_L[i] = Abs_LAbs_L.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static short CvarOptimise(long n, long tlen, double[] DATA, long number_included, double CVar_averse,
      double getRisk, String[] stocknames, double[] w_opt, long m, double[][] AAA, double[] L, double[] U,
      double[] alpha, double[] benchmark, double[] Q, double gamma, double[] initial, double delta, int basket,
      int trades, int revise, double[] min_holding, double[] min_trade, int m_LS, int Fully_Invested, double Rmin,
      double Rmax, int round, double[] min_lot, double[] size_lot, int[] shake, double LSValue, long nabs,
      double[][] Abs_A, long mabs, long[] I_A, double[] Abs_U, double[] ogamma, double[] mask, int log, String logfile,
      int longbasket, int shortbasket, double LSValuel, double[] Abs_L) {
    short back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CvarOptimisenative = Linker.nativeLinker().downcallHandle(
          safeqp.find("CvarOptimise").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_SHORT,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS));
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment w_optw_opt;
      if (w_opt == null) {
        w_optw_opt = MemorySegment.NULL;
      } else {
        w_optw_opt = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w_opt.length);
      }
      if (w_opt != null) {
        for (int i = 0; i < w_opt.length; i++) {
          w_optw_opt.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w_opt[i]);
        }
      }
      double[] AAA1d = twoD2oneD((int) m, (int) n, AAA); // Get the integer arguments correct!
      MemorySegment AAA1dAAA1d;
      if (AAA1d == null) {
        AAA1dAAA1d = MemorySegment.NULL;
      } else {
        AAA1dAAA1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, AAA1d.length);
      }
      if (AAA1d != null) {
        for (int i = 0; i < AAA1d.length; i++) {
          AAA1dAAA1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, AAA1d[i]);
        }
      }
      MemorySegment LL;
      if (L == null) {
        LL = MemorySegment.NULL;
      } else {
        LL = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, L.length);
      }
      if (L != null) {
        for (int i = 0; i < L.length; i++) {
          LL.setAtIndex(ValueLayout.JAVA_DOUBLE, i, L[i]);
        }
      }
      MemorySegment UU;
      if (U == null) {
        UU = MemorySegment.NULL;
      } else {
        UU = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, U.length);
      }
      if (U != null) {
        for (int i = 0; i < U.length; i++) {
          UU.setAtIndex(ValueLayout.JAVA_DOUBLE, i, U[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment initialinitial;
      if (initial == null) {
        initialinitial = MemorySegment.NULL;
      } else {
        initialinitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, initial.length);
      }
      if (initial != null) {
        for (int i = 0; i < initial.length; i++) {
          initialinitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, initial[i]);
        }
      }
      MemorySegment min_holdingmin_holding;
      if (min_holding == null) {
        min_holdingmin_holding = MemorySegment.NULL;
      } else {
        min_holdingmin_holding = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_holding.length);
      }
      if (min_holding != null) {
        for (int i = 0; i < min_holding.length; i++) {
          min_holdingmin_holding.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_holding[i]);
        }
      }
      MemorySegment min_trademin_trade;
      if (min_trade == null) {
        min_trademin_trade = MemorySegment.NULL;
      } else {
        min_trademin_trade = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_trade.length);
      }
      if (min_trade != null) {
        for (int i = 0; i < min_trade.length; i++) {
          min_trademin_trade.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_trade[i]);
        }
      }
      MemorySegment min_lotmin_lot;
      if (min_lot == null) {
        min_lotmin_lot = MemorySegment.NULL;
      } else {
        min_lotmin_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_lot.length);
      }
      if (min_lot != null) {
        for (int i = 0; i < min_lot.length; i++) {
          min_lotmin_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_lot[i]);
        }
      }
      MemorySegment size_lotsize_lot;
      if (size_lot == null) {
        size_lotsize_lot = MemorySegment.NULL;
      } else {
        size_lotsize_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, size_lot.length);
      }
      if (size_lot != null) {
        for (int i = 0; i < size_lot.length; i++) {
          size_lotsize_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, size_lot[i]);
        }
      }
      MemorySegment shakeshake;
      if (shake == null) {
        shakeshake = MemorySegment.NULL;
      } else {
        shakeshake = foreign.allocateArray(ValueLayout.JAVA_INT, shake.length);
      }
      if (shake != null) {
        for (int i = 0; i < shake.length; i++) {
          shakeshake.setAtIndex(ValueLayout.JAVA_INT, i, shake[i]);
        }
      }
      double[] Abs_A1d = twoD2oneD((int) nabs, (int) n, Abs_A); // Get the integer arguments correct!
      MemorySegment Abs_A1dAbs_A1d;
      if (Abs_A1d == null) {
        Abs_A1dAbs_A1d = MemorySegment.NULL;
      } else {
        Abs_A1dAbs_A1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_A1d.length);
      }
      if (Abs_A1d != null) {
        for (int i = 0; i < Abs_A1d.length; i++) {
          Abs_A1dAbs_A1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_A1d[i]);
        }
      }
      MemorySegment I_AI_A;
      if (I_A == null) {
        I_AI_A = MemorySegment.NULL;
      } else {
        I_AI_A = foreign.allocateArray(ValueLayout.JAVA_LONG, I_A.length);
      }
      if (I_A != null) {
        for (int i = 0; i < I_A.length; i++) {
          I_AI_A.setAtIndex(ValueLayout.JAVA_LONG, i, I_A[i]);
        }
      }
      MemorySegment Abs_UAbs_U;
      if (Abs_U == null) {
        Abs_UAbs_U = MemorySegment.NULL;
      } else {
        Abs_UAbs_U = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_U.length);
      }
      if (Abs_U != null) {
        for (int i = 0; i < Abs_U.length; i++) {
          Abs_UAbs_U.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_U[i]);
        }
      }
      MemorySegment ogammaogamma;
      if (ogamma == null) {
        ogammaogamma = MemorySegment.NULL;
      } else {
        ogammaogamma = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, ogamma.length);
      }
      if (ogamma != null) {
        for (int i = 0; i < ogamma.length; i++) {
          ogammaogamma.setAtIndex(ValueLayout.JAVA_DOUBLE, i, ogamma[i]);
        }
      }
      MemorySegment maskmask;
      if (mask == null) {
        maskmask = MemorySegment.NULL;
      } else {
        maskmask = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, mask.length);
      }
      if (mask != null) {
        for (int i = 0; i < mask.length; i++) {
          maskmask.setAtIndex(ValueLayout.JAVA_DOUBLE, i, mask[i]);
        }
      }
      var logfilelogfile = foreign.allocateUtf8String(logfile);
      logfilelogfile.setUtf8String(0, logfile);
      MemorySegment Abs_LAbs_L;
      if (Abs_L == null) {
        Abs_LAbs_L = MemorySegment.NULL;
      } else {
        Abs_LAbs_L = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_L.length);
      }
      if (Abs_L != null) {
        for (int i = 0; i < Abs_L.length; i++) {
          Abs_LAbs_L.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_L[i]);
        }
      }
      back = (short) CvarOptimisenative.invokeExact(
          n,
          tlen,
          DATADATA,
          number_included,
          CVar_averse,
          getRisk,
          stocknamesstocknames,
          w_optw_opt,
          m,
          AAA1dAAA1d,
          LL,
          UU,
          alphaalpha,
          benchmarkbenchmark,
          QQ,
          gamma,
          initialinitial,
          delta,
          basket,
          trades,
          revise,
          min_holdingmin_holding,
          min_trademin_trade,
          m_LS,
          Fully_Invested,
          Rmin,
          Rmax,
          round,
          min_lotmin_lot,
          size_lotsize_lot,
          shakeshake,
          LSValue,
          nabs,
          Abs_A1dAbs_A1d,
          mabs,
          I_AI_A,
          Abs_UAbs_U,
          ogammaogamma,
          maskmask,
          log,
          logfilelogfile,
          longbasket,
          shortbasket,
          LSValuel,
          Abs_LAbs_L);
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w_opt != null)
        for (int i = 0; i < w_opt.length; i++) {
          w_opt[i] = w_optw_opt.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (AAA1d != null)
        for (int i = 0; i < AAA1d.length; i++) {
          AAA1d[i] = AAA1dAAA1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (L != null)
        for (int i = 0; i < L.length; i++) {
          L[i] = LL.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (U != null)
        for (int i = 0; i < U.length; i++) {
          U[i] = UU.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (initial != null)
        for (int i = 0; i < initial.length; i++) {
          initial[i] = initialinitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_holding != null)
        for (int i = 0; i < min_holding.length; i++) {
          min_holding[i] = min_holdingmin_holding.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_trade != null)
        for (int i = 0; i < min_trade.length; i++) {
          min_trade[i] = min_trademin_trade.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_lot != null)
        for (int i = 0; i < min_lot.length; i++) {
          min_lot[i] = min_lotmin_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (size_lot != null)
        for (int i = 0; i < size_lot.length; i++) {
          size_lot[i] = size_lotsize_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (shake != null)
        for (int i = 0; i < shake.length; i++) {
          shake[i] = shakeshake.getAtIndex(ValueLayout.JAVA_INT, i);
        }
      if (Abs_A1d != null)
        for (int i = 0; i < Abs_A1d.length; i++) {
          Abs_A1d[i] = Abs_A1dAbs_A1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (I_A != null)
        for (int i = 0; i < I_A.length; i++) {
          I_A[i] = I_AI_A.getAtIndex(ValueLayout.JAVA_LONG, i);
        }
      if (Abs_U != null)
        for (int i = 0; i < Abs_U.length; i++) {
          Abs_U[i] = Abs_UAbs_U.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (ogamma != null)
        for (int i = 0; i < ogamma.length; i++) {
          ogamma[i] = ogammaogamma.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (mask != null)
        for (int i = 0; i < mask.length; i++) {
          mask[i] = maskmask.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      logfile = logfilelogfile.getUtf8String(0);
      if (Abs_L != null)
        for (int i = 0; i < Abs_L.length; i++) {
          Abs_L[i] = Abs_LAbs_L.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static  short CvarOptimiseCR(long n, long tlen, double[] DATA, long number_included, double CVar_averse, double getRisk, String[] stocknames, double[] w_opt, long m, double[][] AAA, double[] L, double[] U, double[] alpha, double[] benchmark, double[] Q, double gamma, double[] initial, double delta, int basket, int trades, int revise, double[] min_holding, double[] min_trade, int m_LS, int Fully_Invested, double Rmin, double Rmax, int round, double[] min_lot, double[] size_lot, int[] shake, double LSValue, long nabs, double[][] Abs_A, long mabs, long[] I_A, double[] Abs_U, double[] ogamma, double[] mask, int log, String logfile, int longbasket, int shortbasket, double LSValuel, double[] Abs_L, int costs, double[] buy, double[] sell, int CVar_constraint, double CVarMin, double CVarMax, short relCvar) {
    short back=-12345;
    
      try (Arena foreign = Arena.ofConfined()) { 
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CvarOptimiseCRnative = Linker.nativeLinker().downcallHandle(
      safeqp.find("CvarOptimiseCR").orElseThrow(),
      FunctionDescriptor.of(ValueLayout.JAVA_SHORT,
        ValueLayout.JAVA_LONG ,
        ValueLayout.JAVA_LONG ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_LONG ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_LONG ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_INT ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.JAVA_INT ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.JAVA_LONG ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_LONG ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_INT ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_INT ,
        ValueLayout.ADDRESS ,
        ValueLayout.ADDRESS ,
        ValueLayout.JAVA_INT ,
        ValueLayout.JAVA_DOUBLE ,
        ValueLayout.JAVA_DOUBLE ,
    MemorySegment DATADATA;
    if(DATA==null){
      DATADATA = MemorySegment.NULL;}
    else{	DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);}
    if(DATA!=null){
      for (int i = 0; i < DATA.length; i++) {
        DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);}}
    MemorySegment stocknamesstocknames;
    if(stocknames==null){
      stocknamesstocknames = MemorySegment.NULL;}
    else{	stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);}
    if(stocknames!=null){
      for (int i = 0; i < stocknames.length; i++) {
        MemorySegment k5=foreign.allocateUtf8String(stocknames[i]);
        k5.setUtf8String(0,stocknames[i]);
        stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);}}
    MemorySegment w_optw_opt;
    if(w_opt==null){
      w_optw_opt = MemorySegment.NULL;}
    else{	w_optw_opt = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w_opt.length);}
    if(w_opt!=null){
      for (int i = 0; i < w_opt.length; i++) {
        w_optw_opt.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w_opt[i]);}}
      double[] AAA1d = twoD2oneD((int) m, (int) n, AAA);	//Get the integer arguments correct!
    MemorySegment AAA1dAAA1d;
    if(AAA1d==null){
      AAA1dAAA1d = MemorySegment.NULL;}
    else{	AAA1dAAA1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, AAA1d.length);}
    if(AAA1d!=null){
      for (int i = 0; i < AAA1d.length; i++) {
        AAA1dAAA1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, AAA1d[i]);}}
    MemorySegment LL;
    if(L==null){
      LL = MemorySegment.NULL;}
    else{	LL = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, L.length);}
    if(L!=null){
      for (int i = 0; i < L.length; i++) {
        LL.setAtIndex(ValueLayout.JAVA_DOUBLE, i, L[i]);}}
    MemorySegment UU;
    if(U==null){
      UU = MemorySegment.NULL;}
    else{	UU = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, U.length);}
    if(U!=null){
      for (int i = 0; i < U.length; i++) {
        UU.setAtIndex(ValueLayout.JAVA_DOUBLE, i, U[i]);}}
    MemorySegment alphaalpha;
    if(alpha==null){
      alphaalpha = MemorySegment.NULL;}
    else{	alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);}
    if(alpha!=null){
      for (int i = 0; i < alpha.length; i++) {
        alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);}}
    MemorySegment benchmarkbenchmark;
    if(benchmark==null){
      benchmarkbenchmark = MemorySegment.NULL;}
    else{	benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);}
    if(benchmark!=null){
      for (int i = 0; i < benchmark.length; i++) {
        benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);}}
    MemorySegment QQ;
    if(Q==null){
      QQ = MemorySegment.NULL;}
    else{	QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);}
    if(Q!=null){
      for (int i = 0; i < Q.length; i++) {
        QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);}}
    MemorySegment initialinitial;
    if(initial==null){
      initialinitial = MemorySegment.NULL;}
    else{	initialinitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, initial.length);}
    if(initial!=null){
      for (int i = 0; i < initial.length; i++) {
        initialinitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, initial[i]);}}
    MemorySegment min_holdingmin_holding;
    if(min_holding==null){
      min_holdingmin_holding = MemorySegment.NULL;}
    else{	min_holdingmin_holding = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_holding.length);}
    if(min_holding!=null){
      for (int i = 0; i < min_holding.length; i++) {
        min_holdingmin_holding.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_holding[i]);}}
    MemorySegment min_trademin_trade;
    if(min_trade==null){
      min_trademin_trade = MemorySegment.NULL;}
    else{	min_trademin_trade = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_trade.length);}
    if(min_trade!=null){
      for (int i = 0; i < min_trade.length; i++) {
        min_trademin_trade.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_trade[i]);}}
    MemorySegment min_lotmin_lot;
    if(min_lot==null){
      min_lotmin_lot = MemorySegment.NULL;}
    else{	min_lotmin_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_lot.length);}
    if(min_lot!=null){
      for (int i = 0; i < min_lot.length; i++) {
        min_lotmin_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_lot[i]);}}
    MemorySegment size_lotsize_lot;
    if(size_lot==null){
      size_lotsize_lot = MemorySegment.NULL;}
    else{	size_lotsize_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, size_lot.length);}
    if(size_lot!=null){
      for (int i = 0; i < size_lot.length; i++) {
        size_lotsize_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, size_lot[i]);}}
    MemorySegment shakeshake;
    if(shake==null){
      shakeshake = MemorySegment.NULL;}
    else{	shakeshake = foreign.allocateArray(ValueLayout.JAVA_INT, shake.length);}
    if(shake!=null){
      for (int i = 0; i < shake.length; i++) {
        shakeshake.setAtIndex(ValueLayout.JAVA_INT, i, shake[i]);}}
      double[] Abs_A1d = twoD2oneD((int) nabs, (int) n, Abs_A);	//Get the integer arguments correct!
    MemorySegment Abs_A1dAbs_A1d;
    if(Abs_A1d==null){
      Abs_A1dAbs_A1d = MemorySegment.NULL;}
    else{	Abs_A1dAbs_A1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_A1d.length);}
    if(Abs_A1d!=null){
      for (int i = 0; i < Abs_A1d.length; i++) {
        Abs_A1dAbs_A1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_A1d[i]);}}
    MemorySegment I_AI_A;
    if(I_A==null){
      I_AI_A = MemorySegment.NULL;}
    else{	I_AI_A = foreign.allocateArray(ValueLayout.JAVA_LONG, I_A.length);}
    if(I_A!=null){
      for (int i = 0; i < I_A.length; i++) {
        I_AI_A.setAtIndex(ValueLayout.JAVA_LONG, i, I_A[i]);}}
    MemorySegment Abs_UAbs_U;
    if(Abs_U==null){
      Abs_UAbs_U = MemorySegment.NULL;}
    else{	Abs_UAbs_U = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_U.length);}
    if(Abs_U!=null){
      for (int i = 0; i < Abs_U.length; i++) {
        Abs_UAbs_U.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_U[i]);}}
    MemorySegment ogammaogamma;
    if(ogamma==null){
      ogammaogamma = MemorySegment.NULL;}
    else{	ogammaogamma = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, ogamma.length);}
    if(ogamma!=null){
      for (int i = 0; i < ogamma.length; i++) {
        ogammaogamma.setAtIndex(ValueLayout.JAVA_DOUBLE, i, ogamma[i]);}}
    MemorySegment maskmask;
    if(mask==null){
      maskmask = MemorySegment.NULL;}
    else{	maskmask = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, mask.length);}
    if(mask!=null){
      for (int i = 0; i < mask.length; i++) {
        maskmask.setAtIndex(ValueLayout.JAVA_DOUBLE, i, mask[i]);}}
    var logfilelogfile = foreign.allocateUtf8String(logfile);
    logfilelogfile.setUtf8String(0,logfile);
    MemorySegment Abs_LAbs_L;
    if(Abs_L==null){
      Abs_LAbs_L = MemorySegment.NULL;}
    else{	Abs_LAbs_L = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_L.length);}
    if(Abs_L!=null){
      for (int i = 0; i < Abs_L.length; i++) {
        Abs_LAbs_L.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_L[i]);}}
    MemorySegment buybuy;
    if(buy==null){
      buybuy = MemorySegment.NULL;}
    else{	buybuy = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, buy.length);}
    if(buy!=null){
      for (int i = 0; i < buy.length; i++) {
        buybuy.setAtIndex(ValueLayout.JAVA_DOUBLE, i, buy[i]);}}
    MemorySegment sellsell;
    if(sell==null){
      sellsell = MemorySegment.NULL;}
    else{	sellsell = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, sell.length);}
    if(sell!=null){
      for (int i = 0; i < sell.length; i++) {
        sellsell.setAtIndex(ValueLayout.JAVA_DOUBLE, i, sell[i]);}}
      back = (short) CvarOptimiseCRnative.invokeExact(
        n ,
        tlen ,
        DATADATA ,
        number_included ,
        CVar_averse ,
        getRisk ,
        stocknamesstocknames ,
        w_optw_opt ,
        m ,
        AAA1dAAA1d ,
        LL ,
        UU ,
        alphaalpha ,
        benchmarkbenchmark ,
        QQ ,
        gamma ,
        initialinitial ,
        delta ,
        basket ,
        trades ,
        revise ,
        min_holdingmin_holding ,
        min_trademin_trade ,
        m_LS ,
        Fully_Invested ,
        Rmin ,
        Rmax ,
        round ,
        min_lotmin_lot ,
        size_lotsize_lot ,
        shakeshake ,
        LSValue ,
        nabs ,
        Abs_A1dAbs_A1d ,
        mabs ,
        I_AI_A ,
        Abs_UAbs_U ,
        ogammaogamma ,
        maskmask ,
        log ,
        logfilelogfile ,
        longbasket ,
        shortbasket ,
        LSValuel ,
        Abs_LAbs_L ,
        costs ,
        buybuy ,
        sellsell ,
        CVar_constraint ,
        CVarMin ,
        CVarMax ,
        relCvar );
      if(DATA!=null) for (int i = 0; i < DATA.length; i++) {
        DATA[i]=DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(stocknames!=null) for (int i = 0; i < stocknames.length; i++) {
        var k8=stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
        k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
        stocknames[i] = k8.getUtf8String(0);}
      if(w_opt!=null) for (int i = 0; i < w_opt.length; i++) {
        w_opt[i]=w_optw_opt.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(AAA1d!=null) for (int i = 0; i < AAA1d.length; i++) {
        AAA1d[i]=AAA1dAAA1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(L!=null) for (int i = 0; i < L.length; i++) {
        L[i]=LL.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(U!=null) for (int i = 0; i < U.length; i++) {
        U[i]=UU.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(alpha!=null) for (int i = 0; i < alpha.length; i++) {
        alpha[i]=alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(benchmark!=null) for (int i = 0; i < benchmark.length; i++) {
        benchmark[i]=benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(Q!=null) for (int i = 0; i < Q.length; i++) {
        Q[i]=QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(initial!=null) for (int i = 0; i < initial.length; i++) {
        initial[i]=initialinitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(min_holding!=null) for (int i = 0; i < min_holding.length; i++) {
        min_holding[i]=min_holdingmin_holding.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(min_trade!=null) for (int i = 0; i < min_trade.length; i++) {
        min_trade[i]=min_trademin_trade.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(min_lot!=null) for (int i = 0; i < min_lot.length; i++) {
        min_lot[i]=min_lotmin_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(size_lot!=null) for (int i = 0; i < size_lot.length; i++) {
        size_lot[i]=size_lotsize_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(shake!=null) for (int i = 0; i < shake.length; i++) {
        shake[i]=shakeshake.getAtIndex(ValueLayout.JAVA_INT, i);}
      if(Abs_A1d!=null) for (int i = 0; i < Abs_A1d.length; i++) {
        Abs_A1d[i]=Abs_A1dAbs_A1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(I_A!=null) for (int i = 0; i < I_A.length; i++) {
        I_A[i]=I_AI_A.getAtIndex(ValueLayout.JAVA_LONG, i);}
      if(Abs_U!=null) for (int i = 0; i < Abs_U.length; i++) {
        Abs_U[i]=Abs_UAbs_U.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(ogamma!=null) for (int i = 0; i < ogamma.length; i++) {
        ogamma[i]=ogammaogamma.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(mask!=null) for (int i = 0; i < mask.length; i++) {
        mask[i]=maskmask.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
    logfile=logfilelogfile.getUtf8String(0);
      if(Abs_L!=null) for (int i = 0; i < Abs_L.length; i++) {
        Abs_L[i]=Abs_LAbs_L.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(buy!=null) for (int i = 0; i < buy.length; i++) {
        buy[i]=buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      if(sell!=null) for (int i = 0; i < sell.length; i++) {
        sell[i]=sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
      }
      catch (Throwable e) {       System.out.println(e);       back = 0;       }
      return back;}

  public static short CvarOptimiseC(long n, long tlen, double[] DATA, long number_included, double CVar_averse,
      double getRisk, String[] stocknames, double[] w_opt, long m, double[][] AAA, double[] L, double[] U,
      double[] alpha, double[] benchmark, double[] Q, double gamma, double[] initial, double delta, int basket,
      int trades, int revise, double[] min_holding, double[] min_trade, int m_LS, int Fully_Invested, double Rmin,
      double Rmax, int round, double[] min_lot, double[] size_lot, int[] shake, double LSValue, long nabs,
      double[][] Abs_A, long mabs, long[] I_A, double[] Abs_U, double[] ogamma, double[] mask, int log, String logfile,
      int longbasket, int shortbasket, double LSValuel, double[] Abs_L, int costs, double[] buy, double[] sell,
      int CVar_constraint, double CVarMin, double CVarMax) {
    short back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CvarOptimiseCnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("CvarOptimiseC").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_SHORT,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE));
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment w_optw_opt;
      if (w_opt == null) {
        w_optw_opt = MemorySegment.NULL;
      } else {
        w_optw_opt = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w_opt.length);
      }
      if (w_opt != null) {
        for (int i = 0; i < w_opt.length; i++) {
          w_optw_opt.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w_opt[i]);
        }
      }
      double[] AAA1d = twoD2oneD((int) m, (int) n, AAA); // Get the integer arguments correct!
      MemorySegment AAA1dAAA1d;
      if (AAA1d == null) {
        AAA1dAAA1d = MemorySegment.NULL;
      } else {
        AAA1dAAA1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, AAA1d.length);
      }
      if (AAA1d != null) {
        for (int i = 0; i < AAA1d.length; i++) {
          AAA1dAAA1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, AAA1d[i]);
        }
      }
      MemorySegment LL;
      if (L == null) {
        LL = MemorySegment.NULL;
      } else {
        LL = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, L.length);
      }
      if (L != null) {
        for (int i = 0; i < L.length; i++) {
          LL.setAtIndex(ValueLayout.JAVA_DOUBLE, i, L[i]);
        }
      }
      MemorySegment UU;
      if (U == null) {
        UU = MemorySegment.NULL;
      } else {
        UU = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, U.length);
      }
      if (U != null) {
        for (int i = 0; i < U.length; i++) {
          UU.setAtIndex(ValueLayout.JAVA_DOUBLE, i, U[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment initialinitial;
      if (initial == null) {
        initialinitial = MemorySegment.NULL;
      } else {
        initialinitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, initial.length);
      }
      if (initial != null) {
        for (int i = 0; i < initial.length; i++) {
          initialinitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, initial[i]);
        }
      }
      MemorySegment min_holdingmin_holding;
      if (min_holding == null) {
        min_holdingmin_holding = MemorySegment.NULL;
      } else {
        min_holdingmin_holding = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_holding.length);
      }
      if (min_holding != null) {
        for (int i = 0; i < min_holding.length; i++) {
          min_holdingmin_holding.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_holding[i]);
        }
      }
      MemorySegment min_trademin_trade;
      if (min_trade == null) {
        min_trademin_trade = MemorySegment.NULL;
      } else {
        min_trademin_trade = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_trade.length);
      }
      if (min_trade != null) {
        for (int i = 0; i < min_trade.length; i++) {
          min_trademin_trade.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_trade[i]);
        }
      }
      MemorySegment min_lotmin_lot;
      if (min_lot == null) {
        min_lotmin_lot = MemorySegment.NULL;
      } else {
        min_lotmin_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, min_lot.length);
      }
      if (min_lot != null) {
        for (int i = 0; i < min_lot.length; i++) {
          min_lotmin_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, min_lot[i]);
        }
      }
      MemorySegment size_lotsize_lot;
      if (size_lot == null) {
        size_lotsize_lot = MemorySegment.NULL;
      } else {
        size_lotsize_lot = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, size_lot.length);
      }
      if (size_lot != null) {
        for (int i = 0; i < size_lot.length; i++) {
          size_lotsize_lot.setAtIndex(ValueLayout.JAVA_DOUBLE, i, size_lot[i]);
        }
      }
      MemorySegment shakeshake;
      if (shake == null) {
        shakeshake = MemorySegment.NULL;
      } else {
        shakeshake = foreign.allocateArray(ValueLayout.JAVA_INT, shake.length);
      }
      if (shake != null) {
        for (int i = 0; i < shake.length; i++) {
          shakeshake.setAtIndex(ValueLayout.JAVA_INT, i, shake[i]);
        }
      }
      double[] Abs_A1d = twoD2oneD((int) nabs, (int) n, Abs_A); // Get the integer arguments correct!
      MemorySegment Abs_A1dAbs_A1d;
      if (Abs_A1d == null) {
        Abs_A1dAbs_A1d = MemorySegment.NULL;
      } else {
        Abs_A1dAbs_A1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_A1d.length);
      }
      if (Abs_A1d != null) {
        for (int i = 0; i < Abs_A1d.length; i++) {
          Abs_A1dAbs_A1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_A1d[i]);
        }
      }
      MemorySegment I_AI_A;
      if (I_A == null) {
        I_AI_A = MemorySegment.NULL;
      } else {
        I_AI_A = foreign.allocateArray(ValueLayout.JAVA_LONG, I_A.length);
      }
      if (I_A != null) {
        for (int i = 0; i < I_A.length; i++) {
          I_AI_A.setAtIndex(ValueLayout.JAVA_LONG, i, I_A[i]);
        }
      }
      MemorySegment Abs_UAbs_U;
      if (Abs_U == null) {
        Abs_UAbs_U = MemorySegment.NULL;
      } else {
        Abs_UAbs_U = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_U.length);
      }
      if (Abs_U != null) {
        for (int i = 0; i < Abs_U.length; i++) {
          Abs_UAbs_U.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_U[i]);
        }
      }
      MemorySegment ogammaogamma;
      if (ogamma == null) {
        ogammaogamma = MemorySegment.NULL;
      } else {
        ogammaogamma = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, ogamma.length);
      }
      if (ogamma != null) {
        for (int i = 0; i < ogamma.length; i++) {
          ogammaogamma.setAtIndex(ValueLayout.JAVA_DOUBLE, i, ogamma[i]);
        }
      }
      MemorySegment maskmask;
      if (mask == null) {
        maskmask = MemorySegment.NULL;
      } else {
        maskmask = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, mask.length);
      }
      if (mask != null) {
        for (int i = 0; i < mask.length; i++) {
          maskmask.setAtIndex(ValueLayout.JAVA_DOUBLE, i, mask[i]);
        }
      }
      var logfilelogfile = foreign.allocateUtf8String(logfile);
      logfilelogfile.setUtf8String(0, logfile);
      MemorySegment Abs_LAbs_L;
      if (Abs_L == null) {
        Abs_LAbs_L = MemorySegment.NULL;
      } else {
        Abs_LAbs_L = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Abs_L.length);
      }
      if (Abs_L != null) {
        for (int i = 0; i < Abs_L.length; i++) {
          Abs_LAbs_L.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Abs_L[i]);
        }
      }
      MemorySegment buybuy;
      if (buy == null) {
        buybuy = MemorySegment.NULL;
      } else {
        buybuy = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, buy.length);
      }
      if (buy != null) {
        for (int i = 0; i < buy.length; i++) {
          buybuy.setAtIndex(ValueLayout.JAVA_DOUBLE, i, buy[i]);
        }
      }
      MemorySegment sellsell;
      if (sell == null) {
        sellsell = MemorySegment.NULL;
      } else {
        sellsell = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, sell.length);
      }
      if (sell != null) {
        for (int i = 0; i < sell.length; i++) {
          sellsell.setAtIndex(ValueLayout.JAVA_DOUBLE, i, sell[i]);
        }
      }
      back = (short) CvarOptimiseCnative.invokeExact(
          n,
          tlen,
          DATADATA,
          number_included,
          CVar_averse,
          getRisk,
          stocknamesstocknames,
          w_optw_opt,
          m,
          AAA1dAAA1d,
          LL,
          UU,
          alphaalpha,
          benchmarkbenchmark,
          QQ,
          gamma,
          initialinitial,
          delta,
          basket,
          trades,
          revise,
          min_holdingmin_holding,
          min_trademin_trade,
          m_LS,
          Fully_Invested,
          Rmin,
          Rmax,
          round,
          min_lotmin_lot,
          size_lotsize_lot,
          shakeshake,
          LSValue,
          nabs,
          Abs_A1dAbs_A1d,
          mabs,
          I_AI_A,
          Abs_UAbs_U,
          ogammaogamma,
          maskmask,
          log,
          logfilelogfile,
          longbasket,
          shortbasket,
          LSValuel,
          Abs_LAbs_L,
          costs,
          buybuy,
          sellsell,
          CVar_constraint,
          CVarMin,
          CVarMax);
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w_opt != null)
        for (int i = 0; i < w_opt.length; i++) {
          w_opt[i] = w_optw_opt.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (AAA1d != null)
        for (int i = 0; i < AAA1d.length; i++) {
          AAA1d[i] = AAA1dAAA1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (L != null)
        for (int i = 0; i < L.length; i++) {
          L[i] = LL.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (U != null)
        for (int i = 0; i < U.length; i++) {
          U[i] = UU.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (initial != null)
        for (int i = 0; i < initial.length; i++) {
          initial[i] = initialinitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_holding != null)
        for (int i = 0; i < min_holding.length; i++) {
          min_holding[i] = min_holdingmin_holding.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_trade != null)
        for (int i = 0; i < min_trade.length; i++) {
          min_trade[i] = min_trademin_trade.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (min_lot != null)
        for (int i = 0; i < min_lot.length; i++) {
          min_lot[i] = min_lotmin_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (size_lot != null)
        for (int i = 0; i < size_lot.length; i++) {
          size_lot[i] = size_lotsize_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (shake != null)
        for (int i = 0; i < shake.length; i++) {
          shake[i] = shakeshake.getAtIndex(ValueLayout.JAVA_INT, i);
        }
      if (Abs_A1d != null)
        for (int i = 0; i < Abs_A1d.length; i++) {
          Abs_A1d[i] = Abs_A1dAbs_A1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (I_A != null)
        for (int i = 0; i < I_A.length; i++) {
          I_A[i] = I_AI_A.getAtIndex(ValueLayout.JAVA_LONG, i);
        }
      if (Abs_U != null)
        for (int i = 0; i < Abs_U.length; i++) {
          Abs_U[i] = Abs_UAbs_U.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (ogamma != null)
        for (int i = 0; i < ogamma.length; i++) {
          ogamma[i] = ogammaogamma.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (mask != null)
        for (int i = 0; i < mask.length; i++) {
          mask[i] = maskmask.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      logfile = logfilelogfile.getUtf8String(0);
      if (Abs_L != null)
        for (int i = 0; i < Abs_L.length; i++) {
          Abs_L[i] = Abs_LAbs_L.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (buy != null)
        for (int i = 0; i < buy.length; i++) {
          buy[i] = buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (sell != null)
        for (int i = 0; i < sell.length; i++) {
          sell[i] = sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static double CVarValue(long n, long tlen, double[] DATA, long number_included, double[] w) {
    double back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CVarValuenative = Linker.nativeLinker().downcallHandle(
          safeqp.find("CVarValue").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      back = (double) CVarValuenative.invokeExact(
          n,
          tlen,
          DATADATA,
          number_included,
          ww);
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static void CVarGrad(long n, long tlen, double[] DATA, long number_included, double[] w, double[] grad) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CVarGradnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("CVarGrad").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment gradgrad;
      if (grad == null) {
        gradgrad = MemorySegment.NULL;
      } else {
        gradgrad = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, grad.length);
      }
      if (grad != null) {
        for (int i = 0; i < grad.length; i++) {
          gradgrad.setAtIndex(ValueLayout.JAVA_DOUBLE, i, grad[i]);
        }
      }
      CVarGradnative.invokeExact(
          n,
          tlen,
          DATADATA,
          number_included,
          ww,
          gradgrad);
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (grad != null)
        for (int i = 0; i < grad.length; i++) {
          grad[i] = gradgrad.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static double CVarValueO(long n, long tlen, double[] DATA, long number_included, double[] w) {
    double back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CVarValueOnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("CVarValueO").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      back = (double) CVarValueOnative.invokeExact(
          n,
          tlen,
          DATADATA,
          number_included,
          ww);
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static double CVarValuen(long n, long tlen, double[] DATA, long number_included, double[] w) {
    double back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var CVarValuennative = Linker.nativeLinker().downcallHandle(
          safeqp.find("CVarValuen").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      back = (double) CVarValuennative.invokeExact(
          n,
          tlen,
          DATADATA,
          number_included,
          ww);
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static short OptCVar(long nstocks, long m, long t, double beta, double[] w_opt, double[] DATA, double[] lower,
      double[] upper, double[] A, int log, double[] CVaR, double[] VaR) {
    short back = -12345;

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var OptCVarnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("OptCVar").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_SHORT,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment w_optw_opt;
      if (w_opt == null) {
        w_optw_opt = MemorySegment.NULL;
      } else {
        w_optw_opt = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w_opt.length);
      }
      if (w_opt != null) {
        for (int i = 0; i < w_opt.length; i++) {
          w_optw_opt.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w_opt[i]);
        }
      }
      MemorySegment DATADATA;
      if (DATA == null) {
        DATADATA = MemorySegment.NULL;
      } else {
        DATADATA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, DATA.length);
      }
      if (DATA != null) {
        for (int i = 0; i < DATA.length; i++) {
          DATADATA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, DATA[i]);
        }
      }
      MemorySegment lowerlower;
      if (lower == null) {
        lowerlower = MemorySegment.NULL;
      } else {
        lowerlower = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, lower.length);
      }
      if (lower != null) {
        for (int i = 0; i < lower.length; i++) {
          lowerlower.setAtIndex(ValueLayout.JAVA_DOUBLE, i, lower[i]);
        }
      }
      MemorySegment upperupper;
      if (upper == null) {
        upperupper = MemorySegment.NULL;
      } else {
        upperupper = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, upper.length);
      }
      if (upper != null) {
        for (int i = 0; i < upper.length; i++) {
          upperupper.setAtIndex(ValueLayout.JAVA_DOUBLE, i, upper[i]);
        }
      }
      MemorySegment AA;
      if (A == null) {
        AA = MemorySegment.NULL;
      } else {
        AA = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, A.length);
      }
      if (A != null) {
        for (int i = 0; i < A.length; i++) {
          AA.setAtIndex(ValueLayout.JAVA_DOUBLE, i, A[i]);
        }
      }
      MemorySegment CVaRCVaR;
      if (CVaR == null) {
        CVaRCVaR = MemorySegment.NULL;
      } else {
        CVaRCVaR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, CVaR.length);
      }
      if (CVaR != null) {
        for (int i = 0; i < CVaR.length; i++) {
          CVaRCVaR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, CVaR[i]);
        }
      }
      MemorySegment VaRVaR;
      if (VaR == null) {
        VaRVaR = MemorySegment.NULL;
      } else {
        VaRVaR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, VaR.length);
      }
      if (VaR != null) {
        for (int i = 0; i < VaR.length; i++) {
          VaRVaR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, VaR[i]);
        }
      }
      back = (short) OptCVarnative.invokeExact(
          nstocks,
          m,
          t,
          beta,
          w_optw_opt,
          DATADATA,
          lowerlower,
          upperupper,
          AA,
          log,
          CVaRCVaR,
          VaRVaR);
      if (w_opt != null)
        for (int i = 0; i < w_opt.length; i++) {
          w_opt[i] = w_optw_opt.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (DATA != null)
        for (int i = 0; i < DATA.length; i++) {
          DATA[i] = DATADATA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (lower != null)
        for (int i = 0; i < lower.length; i++) {
          lower[i] = lowerlower.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (upper != null)
        for (int i = 0; i < upper.length; i++) {
          upper[i] = upperupper.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (A != null)
        for (int i = 0; i < A.length; i++) {
          A[i] = AA.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (CVaR != null)
        for (int i = 0; i < CVaR.length; i++) {
          CVaR[i] = CVaRCVaR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (VaR != null)
        for (int i = 0; i < VaR.length; i++) {
          VaR[i] = VaRVaR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static void PropertiesC(long n, int nfac, String[] stocknames, double[] w, double[] alpha, double[] benchmark,
      double[] QMATRIX, double[] risk, double[] arisk, double[] Rrisk, double[] rreturn, double[] areturn,
      double[] Rreturn, double[] MCAR, double[] MCTR, double[] MCRR, double[] FMCRR, double[] FMCTR, double[] bbeta,
      double[] FX, double[] RFX, double[][] FLOAD, double[] FFC, double[] SSV, long ncomp, double[] Composite) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var PropertiesCnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("PropertiesC").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment QMATRIXQMATRIX;
      if (QMATRIX == null) {
        QMATRIXQMATRIX = MemorySegment.NULL;
      } else {
        QMATRIXQMATRIX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, QMATRIX.length);
      }
      if (QMATRIX != null) {
        for (int i = 0; i < QMATRIX.length; i++) {
          QMATRIXQMATRIX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, QMATRIX[i]);
        }
      }
      MemorySegment riskrisk;
      if (risk == null) {
        riskrisk = MemorySegment.NULL;
      } else {
        riskrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, risk.length);
      }
      if (risk != null) {
        for (int i = 0; i < risk.length; i++) {
          riskrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, risk[i]);
        }
      }
      MemorySegment ariskarisk;
      if (arisk == null) {
        ariskarisk = MemorySegment.NULL;
      } else {
        ariskarisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, arisk.length);
      }
      if (arisk != null) {
        for (int i = 0; i < arisk.length; i++) {
          ariskarisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, arisk[i]);
        }
      }
      MemorySegment RriskRrisk;
      if (Rrisk == null) {
        RriskRrisk = MemorySegment.NULL;
      } else {
        RriskRrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Rrisk.length);
      }
      if (Rrisk != null) {
        for (int i = 0; i < Rrisk.length; i++) {
          RriskRrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Rrisk[i]);
        }
      }
      MemorySegment rreturnrreturn;
      if (rreturn == null) {
        rreturnrreturn = MemorySegment.NULL;
      } else {
        rreturnrreturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, rreturn.length);
      }
      if (rreturn != null) {
        for (int i = 0; i < rreturn.length; i++) {
          rreturnrreturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, rreturn[i]);
        }
      }
      MemorySegment areturnareturn;
      if (areturn == null) {
        areturnareturn = MemorySegment.NULL;
      } else {
        areturnareturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, areturn.length);
      }
      if (areturn != null) {
        for (int i = 0; i < areturn.length; i++) {
          areturnareturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, areturn[i]);
        }
      }
      MemorySegment RreturnRreturn;
      if (Rreturn == null) {
        RreturnRreturn = MemorySegment.NULL;
      } else {
        RreturnRreturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Rreturn.length);
      }
      if (Rreturn != null) {
        for (int i = 0; i < Rreturn.length; i++) {
          RreturnRreturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Rreturn[i]);
        }
      }
      MemorySegment MCARMCAR;
      if (MCAR == null) {
        MCARMCAR = MemorySegment.NULL;
      } else {
        MCARMCAR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCAR.length);
      }
      if (MCAR != null) {
        for (int i = 0; i < MCAR.length; i++) {
          MCARMCAR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCAR[i]);
        }
      }
      MemorySegment MCTRMCTR;
      if (MCTR == null) {
        MCTRMCTR = MemorySegment.NULL;
      } else {
        MCTRMCTR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCTR.length);
      }
      if (MCTR != null) {
        for (int i = 0; i < MCTR.length; i++) {
          MCTRMCTR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCTR[i]);
        }
      }
      MemorySegment MCRRMCRR;
      if (MCRR == null) {
        MCRRMCRR = MemorySegment.NULL;
      } else {
        MCRRMCRR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCRR.length);
      }
      if (MCRR != null) {
        for (int i = 0; i < MCRR.length; i++) {
          MCRRMCRR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCRR[i]);
        }
      }
      MemorySegment FMCRRFMCRR;
      if (FMCRR == null) {
        FMCRRFMCRR = MemorySegment.NULL;
      } else {
        FMCRRFMCRR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCRR.length);
      }
      if (FMCRR != null) {
        for (int i = 0; i < FMCRR.length; i++) {
          FMCRRFMCRR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCRR[i]);
        }
      }
      MemorySegment FMCTRFMCTR;
      if (FMCTR == null) {
        FMCTRFMCTR = MemorySegment.NULL;
      } else {
        FMCTRFMCTR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCTR.length);
      }
      if (FMCTR != null) {
        for (int i = 0; i < FMCTR.length; i++) {
          FMCTRFMCTR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCTR[i]);
        }
      }
      MemorySegment bbetabbeta;
      if (bbeta == null) {
        bbetabbeta = MemorySegment.NULL;
      } else {
        bbetabbeta = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, bbeta.length);
      }
      if (bbeta != null) {
        for (int i = 0; i < bbeta.length; i++) {
          bbetabbeta.setAtIndex(ValueLayout.JAVA_DOUBLE, i, bbeta[i]);
        }
      }
      MemorySegment FXFX;
      if (FX == null) {
        FXFX = MemorySegment.NULL;
      } else {
        FXFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FX.length);
      }
      if (FX != null) {
        for (int i = 0; i < FX.length; i++) {
          FXFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FX[i]);
        }
      }
      MemorySegment RFXRFX;
      if (RFX == null) {
        RFXRFX = MemorySegment.NULL;
      } else {
        RFXRFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, RFX.length);
      }
      if (RFX != null) {
        for (int i = 0; i < RFX.length; i++) {
          RFXRFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, RFX[i]);
        }
      }
      double[] FLOAD1d = twoD2oneD((int) n, (int) nfac, FLOAD); // Get the integer arguments correct!
      MemorySegment FLOAD1dFLOAD1d;
      if (FLOAD1d == null) {
        FLOAD1dFLOAD1d = MemorySegment.NULL;
      } else {
        FLOAD1dFLOAD1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD1d.length);
      }
      if (FLOAD1d != null) {
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1dFLOAD1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD1d[i]);
        }
      }
      MemorySegment FFCFFC;
      if (FFC == null) {
        FFCFFC = MemorySegment.NULL;
      } else {
        FFCFFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FFC.length);
      }
      if (FFC != null) {
        for (int i = 0; i < FFC.length; i++) {
          FFCFFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FFC[i]);
        }
      }
      MemorySegment SSVSSV;
      if (SSV == null) {
        SSVSSV = MemorySegment.NULL;
      } else {
        SSVSSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SSV.length);
      }
      if (SSV != null) {
        for (int i = 0; i < SSV.length; i++) {
          SSVSSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SSV[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      PropertiesCnative.invokeExact(
          n,
          nfac,
          stocknamesstocknames,
          ww,
          alphaalpha,
          benchmarkbenchmark,
          QMATRIXQMATRIX,
          riskrisk,
          ariskarisk,
          RriskRrisk,
          rreturnrreturn,
          areturnareturn,
          RreturnRreturn,
          MCARMCAR,
          MCTRMCTR,
          MCRRMCRR,
          FMCRRFMCRR,
          FMCTRFMCTR,
          bbetabbeta,
          FXFX,
          RFXRFX,
          FLOAD1dFLOAD1d,
          FFCFFC,
          SSVSSV,
          ncomp,
          CompositeComposite);
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (QMATRIX != null)
        for (int i = 0; i < QMATRIX.length; i++) {
          QMATRIX[i] = QMATRIXQMATRIX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (risk != null)
        for (int i = 0; i < risk.length; i++) {
          risk[i] = riskrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (arisk != null)
        for (int i = 0; i < arisk.length; i++) {
          arisk[i] = ariskarisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Rrisk != null)
        for (int i = 0; i < Rrisk.length; i++) {
          Rrisk[i] = RriskRrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (rreturn != null)
        for (int i = 0; i < rreturn.length; i++) {
          rreturn[i] = rreturnrreturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (areturn != null)
        for (int i = 0; i < areturn.length; i++) {
          areturn[i] = areturnareturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Rreturn != null)
        for (int i = 0; i < Rreturn.length; i++) {
          Rreturn[i] = RreturnRreturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCAR != null)
        for (int i = 0; i < MCAR.length; i++) {
          MCAR[i] = MCARMCAR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCTR != null)
        for (int i = 0; i < MCTR.length; i++) {
          MCTR[i] = MCTRMCTR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCRR != null)
        for (int i = 0; i < MCRR.length; i++) {
          MCRR[i] = MCRRMCRR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCRR != null)
        for (int i = 0; i < FMCRR.length; i++) {
          FMCRR[i] = FMCRRFMCRR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCTR != null)
        for (int i = 0; i < FMCTR.length; i++) {
          FMCTR[i] = FMCTRFMCTR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (bbeta != null)
        for (int i = 0; i < bbeta.length; i++) {
          bbeta[i] = bbetabbeta.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FX != null)
        for (int i = 0; i < FX.length; i++) {
          FX[i] = FXFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (RFX != null)
        for (int i = 0; i < RFX.length; i++) {
          RFX[i] = RFXRFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FLOAD1d != null)
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1d[i] = FLOAD1dFLOAD1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FFC != null)
        for (int i = 0; i < FFC.length; i++) {
          FFC[i] = FFCFFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (SSV != null)
        for (int i = 0; i < SSV.length; i++) {
          SSV[i] = SSVSSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void PropertiesCA(long n, int nfac, String[] stocknames, double[] w, double[] benchmark, double[] alpha,
      double[] rreturn, double[] areturn, double[] Rreturn, double[] breturn, double[] QMATRIX, double[] risk,
      double[] arisk, double[] Rrisk, double[] brisk, double[] srisk, double[] pbeta, double[] MCAR, double[] MCTR,
      double[] MCRR, double[] MCBR, double[] FMCRR, double[] FMCTR, double[] FMCAR, double[] FMCBR, double[] FMCSR,
      double[] beta, double[] FX, double[] RFX, double[] AFX, double[] BFX, double[] SFX, double[][] FLOAD,
      double[] FFC, double[] SSV, long ncomp, double[] Composite) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var PropertiesCAnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("PropertiesCA").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment rreturnrreturn;
      if (rreturn == null) {
        rreturnrreturn = MemorySegment.NULL;
      } else {
        rreturnrreturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, rreturn.length);
      }
      if (rreturn != null) {
        for (int i = 0; i < rreturn.length; i++) {
          rreturnrreturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, rreturn[i]);
        }
      }
      MemorySegment areturnareturn;
      if (areturn == null) {
        areturnareturn = MemorySegment.NULL;
      } else {
        areturnareturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, areturn.length);
      }
      if (areturn != null) {
        for (int i = 0; i < areturn.length; i++) {
          areturnareturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, areturn[i]);
        }
      }
      MemorySegment RreturnRreturn;
      if (Rreturn == null) {
        RreturnRreturn = MemorySegment.NULL;
      } else {
        RreturnRreturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Rreturn.length);
      }
      if (Rreturn != null) {
        for (int i = 0; i < Rreturn.length; i++) {
          RreturnRreturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Rreturn[i]);
        }
      }
      MemorySegment breturnbreturn;
      if (breturn == null) {
        breturnbreturn = MemorySegment.NULL;
      } else {
        breturnbreturn = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, breturn.length);
      }
      if (breturn != null) {
        for (int i = 0; i < breturn.length; i++) {
          breturnbreturn.setAtIndex(ValueLayout.JAVA_DOUBLE, i, breturn[i]);
        }
      }
      MemorySegment QMATRIXQMATRIX;
      if (QMATRIX == null) {
        QMATRIXQMATRIX = MemorySegment.NULL;
      } else {
        QMATRIXQMATRIX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, QMATRIX.length);
      }
      if (QMATRIX != null) {
        for (int i = 0; i < QMATRIX.length; i++) {
          QMATRIXQMATRIX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, QMATRIX[i]);
        }
      }
      MemorySegment riskrisk;
      if (risk == null) {
        riskrisk = MemorySegment.NULL;
      } else {
        riskrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, risk.length);
      }
      if (risk != null) {
        for (int i = 0; i < risk.length; i++) {
          riskrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, risk[i]);
        }
      }
      MemorySegment ariskarisk;
      if (arisk == null) {
        ariskarisk = MemorySegment.NULL;
      } else {
        ariskarisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, arisk.length);
      }
      if (arisk != null) {
        for (int i = 0; i < arisk.length; i++) {
          ariskarisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, arisk[i]);
        }
      }
      MemorySegment RriskRrisk;
      if (Rrisk == null) {
        RriskRrisk = MemorySegment.NULL;
      } else {
        RriskRrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Rrisk.length);
      }
      if (Rrisk != null) {
        for (int i = 0; i < Rrisk.length; i++) {
          RriskRrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Rrisk[i]);
        }
      }
      MemorySegment briskbrisk;
      if (brisk == null) {
        briskbrisk = MemorySegment.NULL;
      } else {
        briskbrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, brisk.length);
      }
      if (brisk != null) {
        for (int i = 0; i < brisk.length; i++) {
          briskbrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, brisk[i]);
        }
      }
      MemorySegment srisksrisk;
      if (srisk == null) {
        srisksrisk = MemorySegment.NULL;
      } else {
        srisksrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, srisk.length);
      }
      if (srisk != null) {
        for (int i = 0; i < srisk.length; i++) {
          srisksrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, srisk[i]);
        }
      }
      MemorySegment pbetapbeta;
      if (pbeta == null) {
        pbetapbeta = MemorySegment.NULL;
      } else {
        pbetapbeta = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pbeta.length);
      }
      if (pbeta != null) {
        for (int i = 0; i < pbeta.length; i++) {
          pbetapbeta.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pbeta[i]);
        }
      }
      MemorySegment MCARMCAR;
      if (MCAR == null) {
        MCARMCAR = MemorySegment.NULL;
      } else {
        MCARMCAR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCAR.length);
      }
      if (MCAR != null) {
        for (int i = 0; i < MCAR.length; i++) {
          MCARMCAR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCAR[i]);
        }
      }
      MemorySegment MCTRMCTR;
      if (MCTR == null) {
        MCTRMCTR = MemorySegment.NULL;
      } else {
        MCTRMCTR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCTR.length);
      }
      if (MCTR != null) {
        for (int i = 0; i < MCTR.length; i++) {
          MCTRMCTR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCTR[i]);
        }
      }
      MemorySegment MCRRMCRR;
      if (MCRR == null) {
        MCRRMCRR = MemorySegment.NULL;
      } else {
        MCRRMCRR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCRR.length);
      }
      if (MCRR != null) {
        for (int i = 0; i < MCRR.length; i++) {
          MCRRMCRR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCRR[i]);
        }
      }
      MemorySegment MCBRMCBR;
      if (MCBR == null) {
        MCBRMCBR = MemorySegment.NULL;
      } else {
        MCBRMCBR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, MCBR.length);
      }
      if (MCBR != null) {
        for (int i = 0; i < MCBR.length; i++) {
          MCBRMCBR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, MCBR[i]);
        }
      }
      MemorySegment FMCRRFMCRR;
      if (FMCRR == null) {
        FMCRRFMCRR = MemorySegment.NULL;
      } else {
        FMCRRFMCRR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCRR.length);
      }
      if (FMCRR != null) {
        for (int i = 0; i < FMCRR.length; i++) {
          FMCRRFMCRR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCRR[i]);
        }
      }
      MemorySegment FMCTRFMCTR;
      if (FMCTR == null) {
        FMCTRFMCTR = MemorySegment.NULL;
      } else {
        FMCTRFMCTR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCTR.length);
      }
      if (FMCTR != null) {
        for (int i = 0; i < FMCTR.length; i++) {
          FMCTRFMCTR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCTR[i]);
        }
      }
      MemorySegment FMCARFMCAR;
      if (FMCAR == null) {
        FMCARFMCAR = MemorySegment.NULL;
      } else {
        FMCARFMCAR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCAR.length);
      }
      if (FMCAR != null) {
        for (int i = 0; i < FMCAR.length; i++) {
          FMCARFMCAR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCAR[i]);
        }
      }
      MemorySegment FMCBRFMCBR;
      if (FMCBR == null) {
        FMCBRFMCBR = MemorySegment.NULL;
      } else {
        FMCBRFMCBR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCBR.length);
      }
      if (FMCBR != null) {
        for (int i = 0; i < FMCBR.length; i++) {
          FMCBRFMCBR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCBR[i]);
        }
      }
      MemorySegment FMCSRFMCSR;
      if (FMCSR == null) {
        FMCSRFMCSR = MemorySegment.NULL;
      } else {
        FMCSRFMCSR = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FMCSR.length);
      }
      if (FMCSR != null) {
        for (int i = 0; i < FMCSR.length; i++) {
          FMCSRFMCSR.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FMCSR[i]);
        }
      }
      MemorySegment betabeta;
      if (beta == null) {
        betabeta = MemorySegment.NULL;
      } else {
        betabeta = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, beta.length);
      }
      if (beta != null) {
        for (int i = 0; i < beta.length; i++) {
          betabeta.setAtIndex(ValueLayout.JAVA_DOUBLE, i, beta[i]);
        }
      }
      MemorySegment FXFX;
      if (FX == null) {
        FXFX = MemorySegment.NULL;
      } else {
        FXFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FX.length);
      }
      if (FX != null) {
        for (int i = 0; i < FX.length; i++) {
          FXFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FX[i]);
        }
      }
      MemorySegment RFXRFX;
      if (RFX == null) {
        RFXRFX = MemorySegment.NULL;
      } else {
        RFXRFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, RFX.length);
      }
      if (RFX != null) {
        for (int i = 0; i < RFX.length; i++) {
          RFXRFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, RFX[i]);
        }
      }
      MemorySegment AFXAFX;
      if (AFX == null) {
        AFXAFX = MemorySegment.NULL;
      } else {
        AFXAFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, AFX.length);
      }
      if (AFX != null) {
        for (int i = 0; i < AFX.length; i++) {
          AFXAFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, AFX[i]);
        }
      }
      MemorySegment BFXBFX;
      if (BFX == null) {
        BFXBFX = MemorySegment.NULL;
      } else {
        BFXBFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, BFX.length);
      }
      if (BFX != null) {
        for (int i = 0; i < BFX.length; i++) {
          BFXBFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, BFX[i]);
        }
      }
      MemorySegment SFXSFX;
      if (SFX == null) {
        SFXSFX = MemorySegment.NULL;
      } else {
        SFXSFX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SFX.length);
      }
      if (SFX != null) {
        for (int i = 0; i < SFX.length; i++) {
          SFXSFX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SFX[i]);
        }
      }
      double[] FLOAD1d = twoD2oneD((int) n, (int) nfac, FLOAD); // Get the integer arguments correct!
      MemorySegment FLOAD1dFLOAD1d;
      if (FLOAD1d == null) {
        FLOAD1dFLOAD1d = MemorySegment.NULL;
      } else {
        FLOAD1dFLOAD1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD1d.length);
      }
      if (FLOAD1d != null) {
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1dFLOAD1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD1d[i]);
        }
      }
      MemorySegment FFCFFC;
      if (FFC == null) {
        FFCFFC = MemorySegment.NULL;
      } else {
        FFCFFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FFC.length);
      }
      if (FFC != null) {
        for (int i = 0; i < FFC.length; i++) {
          FFCFFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FFC[i]);
        }
      }
      MemorySegment SSVSSV;
      if (SSV == null) {
        SSVSSV = MemorySegment.NULL;
      } else {
        SSVSSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SSV.length);
      }
      if (SSV != null) {
        for (int i = 0; i < SSV.length; i++) {
          SSVSSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SSV[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      PropertiesCAnative.invokeExact(
          n,
          nfac,
          stocknamesstocknames,
          ww,
          benchmarkbenchmark,
          alphaalpha,
          rreturnrreturn,
          areturnareturn,
          RreturnRreturn,
          breturnbreturn,
          QMATRIXQMATRIX,
          riskrisk,
          ariskarisk,
          RriskRrisk,
          briskbrisk,
          srisksrisk,
          pbetapbeta,
          MCARMCAR,
          MCTRMCTR,
          MCRRMCRR,
          MCBRMCBR,
          FMCRRFMCRR,
          FMCTRFMCTR,
          FMCARFMCAR,
          FMCBRFMCBR,
          FMCSRFMCSR,
          betabeta,
          FXFX,
          RFXRFX,
          AFXAFX,
          BFXBFX,
          SFXSFX,
          FLOAD1dFLOAD1d,
          FFCFFC,
          SSVSSV,
          ncomp,
          CompositeComposite);
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (rreturn != null)
        for (int i = 0; i < rreturn.length; i++) {
          rreturn[i] = rreturnrreturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (areturn != null)
        for (int i = 0; i < areturn.length; i++) {
          areturn[i] = areturnareturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Rreturn != null)
        for (int i = 0; i < Rreturn.length; i++) {
          Rreturn[i] = RreturnRreturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (breturn != null)
        for (int i = 0; i < breturn.length; i++) {
          breturn[i] = breturnbreturn.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (QMATRIX != null)
        for (int i = 0; i < QMATRIX.length; i++) {
          QMATRIX[i] = QMATRIXQMATRIX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (risk != null)
        for (int i = 0; i < risk.length; i++) {
          risk[i] = riskrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (arisk != null)
        for (int i = 0; i < arisk.length; i++) {
          arisk[i] = ariskarisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Rrisk != null)
        for (int i = 0; i < Rrisk.length; i++) {
          Rrisk[i] = RriskRrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (brisk != null)
        for (int i = 0; i < brisk.length; i++) {
          brisk[i] = briskbrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (srisk != null)
        for (int i = 0; i < srisk.length; i++) {
          srisk[i] = srisksrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (pbeta != null)
        for (int i = 0; i < pbeta.length; i++) {
          pbeta[i] = pbetapbeta.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCAR != null)
        for (int i = 0; i < MCAR.length; i++) {
          MCAR[i] = MCARMCAR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCTR != null)
        for (int i = 0; i < MCTR.length; i++) {
          MCTR[i] = MCTRMCTR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCRR != null)
        for (int i = 0; i < MCRR.length; i++) {
          MCRR[i] = MCRRMCRR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (MCBR != null)
        for (int i = 0; i < MCBR.length; i++) {
          MCBR[i] = MCBRMCBR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCRR != null)
        for (int i = 0; i < FMCRR.length; i++) {
          FMCRR[i] = FMCRRFMCRR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCTR != null)
        for (int i = 0; i < FMCTR.length; i++) {
          FMCTR[i] = FMCTRFMCTR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCAR != null)
        for (int i = 0; i < FMCAR.length; i++) {
          FMCAR[i] = FMCARFMCAR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCBR != null)
        for (int i = 0; i < FMCBR.length; i++) {
          FMCBR[i] = FMCBRFMCBR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FMCSR != null)
        for (int i = 0; i < FMCSR.length; i++) {
          FMCSR[i] = FMCSRFMCSR.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (beta != null)
        for (int i = 0; i < beta.length; i++) {
          beta[i] = betabeta.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FX != null)
        for (int i = 0; i < FX.length; i++) {
          FX[i] = FXFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (RFX != null)
        for (int i = 0; i < RFX.length; i++) {
          RFX[i] = RFXRFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (AFX != null)
        for (int i = 0; i < AFX.length; i++) {
          AFX[i] = AFXAFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (BFX != null)
        for (int i = 0; i < BFX.length; i++) {
          BFX[i] = BFXBFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (SFX != null)
        for (int i = 0; i < SFX.length; i++) {
          SFX[i] = SFXSFX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FLOAD1d != null)
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1d[i] = FLOAD1dFLOAD1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FFC != null)
        for (int i = 0; i < FFC.length; i++) {
          FFC[i] = FFCFFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (SSV != null)
        for (int i = 0; i < SSV.length; i++) {
          SSV[i] = SSVSSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void GetBeta(long n, int nfac, double[] benchmark, double[] Q, double[] beta, long ncomp,
      double[] Composite) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var GetBetanative = Linker.nativeLinker().downcallHandle(
          safeqp.find("GetBeta").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment betabeta;
      if (beta == null) {
        betabeta = MemorySegment.NULL;
      } else {
        betabeta = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, beta.length);
      }
      if (beta != null) {
        for (int i = 0; i < beta.length; i++) {
          betabeta.setAtIndex(ValueLayout.JAVA_DOUBLE, i, beta[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      GetBetanative.invokeExact(
          n,
          nfac,
          benchmarkbenchmark,
          QQ,
          betabeta,
          ncomp,
          CompositeComposite);
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (beta != null)
        for (int i = 0; i < beta.length; i++) {
          beta[i] = betabeta.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void Get_RisksC(long n, int nfac, double[] Q, double[] w, double[] benchmark, double[] arisk,
      double[] risk, double[] Rrisk, double[] brisk, double[] pbeta, long ncomp, double[] Composite) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var Get_RisksCnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("Get_RisksC").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment ariskarisk;
      if (arisk == null) {
        ariskarisk = MemorySegment.NULL;
      } else {
        ariskarisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, arisk.length);
      }
      if (arisk != null) {
        for (int i = 0; i < arisk.length; i++) {
          ariskarisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, arisk[i]);
        }
      }
      MemorySegment riskrisk;
      if (risk == null) {
        riskrisk = MemorySegment.NULL;
      } else {
        riskrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, risk.length);
      }
      if (risk != null) {
        for (int i = 0; i < risk.length; i++) {
          riskrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, risk[i]);
        }
      }
      MemorySegment RriskRrisk;
      if (Rrisk == null) {
        RriskRrisk = MemorySegment.NULL;
      } else {
        RriskRrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Rrisk.length);
      }
      if (Rrisk != null) {
        for (int i = 0; i < Rrisk.length; i++) {
          RriskRrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Rrisk[i]);
        }
      }
      MemorySegment briskbrisk;
      if (brisk == null) {
        briskbrisk = MemorySegment.NULL;
      } else {
        briskbrisk = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, brisk.length);
      }
      if (brisk != null) {
        for (int i = 0; i < brisk.length; i++) {
          briskbrisk.setAtIndex(ValueLayout.JAVA_DOUBLE, i, brisk[i]);
        }
      }
      MemorySegment pbetapbeta;
      if (pbeta == null) {
        pbetapbeta = MemorySegment.NULL;
      } else {
        pbetapbeta = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pbeta.length);
      }
      if (pbeta != null) {
        for (int i = 0; i < pbeta.length; i++) {
          pbetapbeta.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pbeta[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      Get_RisksCnative.invokeExact(
          n,
          nfac,
          QQ,
          ww,
          benchmarkbenchmark,
          ariskarisk,
          riskrisk,
          RriskRrisk,
          briskbrisk,
          pbetapbeta,
          ncomp,
          CompositeComposite);
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (arisk != null)
        for (int i = 0; i < arisk.length; i++) {
          arisk[i] = ariskarisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (risk != null)
        for (int i = 0; i < risk.length; i++) {
          risk[i] = riskrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Rrisk != null)
        for (int i = 0; i < Rrisk.length; i++) {
          Rrisk[i] = RriskRrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (brisk != null)
        for (int i = 0; i < brisk.length; i++) {
          brisk[i] = briskbrisk.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (pbeta != null)
        for (int i = 0; i < pbeta.length; i++) {
          pbeta[i] = pbetapbeta.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void MarginalUtility(long n, int nfac, String[] stocknames, double[] w, double[] benchmark,
      double[] Initial, double[] Q, double gamma, double kappa, long npiece, double[] hpiece, double[] pgrad,
      double[] buy, double[] sell, double[] alpha, double[] tcost, double[] utility, double[] gradutility,
      double[] utility_per_stock, double[] cost_per_stock, long ncomp, double[] Composite) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var MarginalUtilitynative = Linker.nativeLinker().downcallHandle(
          safeqp.find("MarginalUtility").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS));
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment InitialInitial;
      if (Initial == null) {
        InitialInitial = MemorySegment.NULL;
      } else {
        InitialInitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Initial.length);
      }
      if (Initial != null) {
        for (int i = 0; i < Initial.length; i++) {
          InitialInitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Initial[i]);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment hpiecehpiece;
      if (hpiece == null) {
        hpiecehpiece = MemorySegment.NULL;
      } else {
        hpiecehpiece = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, hpiece.length);
      }
      if (hpiece != null) {
        for (int i = 0; i < hpiece.length; i++) {
          hpiecehpiece.setAtIndex(ValueLayout.JAVA_DOUBLE, i, hpiece[i]);
        }
      }
      MemorySegment pgradpgrad;
      if (pgrad == null) {
        pgradpgrad = MemorySegment.NULL;
      } else {
        pgradpgrad = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pgrad.length);
      }
      if (pgrad != null) {
        for (int i = 0; i < pgrad.length; i++) {
          pgradpgrad.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pgrad[i]);
        }
      }
      MemorySegment buybuy;
      if (buy == null) {
        buybuy = MemorySegment.NULL;
      } else {
        buybuy = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, buy.length);
      }
      if (buy != null) {
        for (int i = 0; i < buy.length; i++) {
          buybuy.setAtIndex(ValueLayout.JAVA_DOUBLE, i, buy[i]);
        }
      }
      MemorySegment sellsell;
      if (sell == null) {
        sellsell = MemorySegment.NULL;
      } else {
        sellsell = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, sell.length);
      }
      if (sell != null) {
        for (int i = 0; i < sell.length; i++) {
          sellsell.setAtIndex(ValueLayout.JAVA_DOUBLE, i, sell[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment tcosttcost;
      if (tcost == null) {
        tcosttcost = MemorySegment.NULL;
      } else {
        tcosttcost = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, tcost.length);
      }
      if (tcost != null) {
        for (int i = 0; i < tcost.length; i++) {
          tcosttcost.setAtIndex(ValueLayout.JAVA_DOUBLE, i, tcost[i]);
        }
      }
      MemorySegment utilityutility;
      if (utility == null) {
        utilityutility = MemorySegment.NULL;
      } else {
        utilityutility = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, utility.length);
      }
      if (utility != null) {
        for (int i = 0; i < utility.length; i++) {
          utilityutility.setAtIndex(ValueLayout.JAVA_DOUBLE, i, utility[i]);
        }
      }
      MemorySegment gradutilitygradutility;
      if (gradutility == null) {
        gradutilitygradutility = MemorySegment.NULL;
      } else {
        gradutilitygradutility = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, gradutility.length);
      }
      if (gradutility != null) {
        for (int i = 0; i < gradutility.length; i++) {
          gradutilitygradutility.setAtIndex(ValueLayout.JAVA_DOUBLE, i, gradutility[i]);
        }
      }
      MemorySegment utility_per_stockutility_per_stock;
      if (utility_per_stock == null) {
        utility_per_stockutility_per_stock = MemorySegment.NULL;
      } else {
        utility_per_stockutility_per_stock = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, utility_per_stock.length);
      }
      if (utility_per_stock != null) {
        for (int i = 0; i < utility_per_stock.length; i++) {
          utility_per_stockutility_per_stock.setAtIndex(ValueLayout.JAVA_DOUBLE, i, utility_per_stock[i]);
        }
      }
      MemorySegment cost_per_stockcost_per_stock;
      if (cost_per_stock == null) {
        cost_per_stockcost_per_stock = MemorySegment.NULL;
      } else {
        cost_per_stockcost_per_stock = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, cost_per_stock.length);
      }
      if (cost_per_stock != null) {
        for (int i = 0; i < cost_per_stock.length; i++) {
          cost_per_stockcost_per_stock.setAtIndex(ValueLayout.JAVA_DOUBLE, i, cost_per_stock[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      MarginalUtilitynative.invokeExact(
          n,
          nfac,
          stocknamesstocknames,
          ww,
          benchmarkbenchmark,
          InitialInitial,
          QQ,
          gamma,
          kappa,
          npiece,
          hpiecehpiece,
          pgradpgrad,
          buybuy,
          sellsell,
          alphaalpha,
          tcosttcost,
          utilityutility,
          gradutilitygradutility,
          utility_per_stockutility_per_stock,
          cost_per_stockcost_per_stock,
          ncomp,
          CompositeComposite);
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Initial != null)
        for (int i = 0; i < Initial.length; i++) {
          Initial[i] = InitialInitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (hpiece != null)
        for (int i = 0; i < hpiece.length; i++) {
          hpiece[i] = hpiecehpiece.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (pgrad != null)
        for (int i = 0; i < pgrad.length; i++) {
          pgrad[i] = pgradpgrad.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (buy != null)
        for (int i = 0; i < buy.length; i++) {
          buy[i] = buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (sell != null)
        for (int i = 0; i < sell.length; i++) {
          sell[i] = sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (tcost != null)
        for (int i = 0; i < tcost.length; i++) {
          tcost[i] = tcosttcost.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (utility != null)
        for (int i = 0; i < utility.length; i++) {
          utility[i] = utilityutility.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (gradutility != null)
        for (int i = 0; i < gradutility.length; i++) {
          gradutility[i] = gradutilitygradutility.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (utility_per_stock != null)
        for (int i = 0; i < utility_per_stock.length; i++) {
          utility_per_stock[i] = utility_per_stockutility_per_stock.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (cost_per_stock != null)
        for (int i = 0; i < cost_per_stock.length; i++) {
          cost_per_stock[i] = cost_per_stockcost_per_stock.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void MarginalUtilitybSa(long n, int nfac, String[] stocknames, double[] w, double[] benchmark,
      double[] Initial, double[] Q, double gamma, double kappa, long npiece, double[] hpiece, double[] pgrad,
      double[] buy, double[] sell, double[] alpha, double[] tcost, double[] utility, double[] gradutility,
      double[] utility_per_stock, double[] cost_per_stock, long ncomp, double[] Composite, double ShortCostScale,
      double[] shortalphacost) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var MarginalUtilitybSanative = Linker.nativeLinker().downcallHandle(
          safeqp.find("MarginalUtilitybSa").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS));
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment InitialInitial;
      if (Initial == null) {
        InitialInitial = MemorySegment.NULL;
      } else {
        InitialInitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Initial.length);
      }
      if (Initial != null) {
        for (int i = 0; i < Initial.length; i++) {
          InitialInitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Initial[i]);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment hpiecehpiece;
      if (hpiece == null) {
        hpiecehpiece = MemorySegment.NULL;
      } else {
        hpiecehpiece = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, hpiece.length);
      }
      if (hpiece != null) {
        for (int i = 0; i < hpiece.length; i++) {
          hpiecehpiece.setAtIndex(ValueLayout.JAVA_DOUBLE, i, hpiece[i]);
        }
      }
      MemorySegment pgradpgrad;
      if (pgrad == null) {
        pgradpgrad = MemorySegment.NULL;
      } else {
        pgradpgrad = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pgrad.length);
      }
      if (pgrad != null) {
        for (int i = 0; i < pgrad.length; i++) {
          pgradpgrad.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pgrad[i]);
        }
      }
      MemorySegment buybuy;
      if (buy == null) {
        buybuy = MemorySegment.NULL;
      } else {
        buybuy = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, buy.length);
      }
      if (buy != null) {
        for (int i = 0; i < buy.length; i++) {
          buybuy.setAtIndex(ValueLayout.JAVA_DOUBLE, i, buy[i]);
        }
      }
      MemorySegment sellsell;
      if (sell == null) {
        sellsell = MemorySegment.NULL;
      } else {
        sellsell = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, sell.length);
      }
      if (sell != null) {
        for (int i = 0; i < sell.length; i++) {
          sellsell.setAtIndex(ValueLayout.JAVA_DOUBLE, i, sell[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment tcosttcost;
      if (tcost == null) {
        tcosttcost = MemorySegment.NULL;
      } else {
        tcosttcost = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, tcost.length);
      }
      if (tcost != null) {
        for (int i = 0; i < tcost.length; i++) {
          tcosttcost.setAtIndex(ValueLayout.JAVA_DOUBLE, i, tcost[i]);
        }
      }
      MemorySegment utilityutility;
      if (utility == null) {
        utilityutility = MemorySegment.NULL;
      } else {
        utilityutility = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, utility.length);
      }
      if (utility != null) {
        for (int i = 0; i < utility.length; i++) {
          utilityutility.setAtIndex(ValueLayout.JAVA_DOUBLE, i, utility[i]);
        }
      }
      MemorySegment gradutilitygradutility;
      if (gradutility == null) {
        gradutilitygradutility = MemorySegment.NULL;
      } else {
        gradutilitygradutility = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, gradutility.length);
      }
      if (gradutility != null) {
        for (int i = 0; i < gradutility.length; i++) {
          gradutilitygradutility.setAtIndex(ValueLayout.JAVA_DOUBLE, i, gradutility[i]);
        }
      }
      MemorySegment utility_per_stockutility_per_stock;
      if (utility_per_stock == null) {
        utility_per_stockutility_per_stock = MemorySegment.NULL;
      } else {
        utility_per_stockutility_per_stock = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, utility_per_stock.length);
      }
      if (utility_per_stock != null) {
        for (int i = 0; i < utility_per_stock.length; i++) {
          utility_per_stockutility_per_stock.setAtIndex(ValueLayout.JAVA_DOUBLE, i, utility_per_stock[i]);
        }
      }
      MemorySegment cost_per_stockcost_per_stock;
      if (cost_per_stock == null) {
        cost_per_stockcost_per_stock = MemorySegment.NULL;
      } else {
        cost_per_stockcost_per_stock = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, cost_per_stock.length);
      }
      if (cost_per_stock != null) {
        for (int i = 0; i < cost_per_stock.length; i++) {
          cost_per_stockcost_per_stock.setAtIndex(ValueLayout.JAVA_DOUBLE, i, cost_per_stock[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      MemorySegment shortalphacostshortalphacost;
      if (shortalphacost == null) {
        shortalphacostshortalphacost = MemorySegment.NULL;
      } else {
        shortalphacostshortalphacost = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, shortalphacost.length);
      }
      if (shortalphacost != null) {
        for (int i = 0; i < shortalphacost.length; i++) {
          shortalphacostshortalphacost.setAtIndex(ValueLayout.JAVA_DOUBLE, i, shortalphacost[i]);
        }
      }
      MarginalUtilitybSanative.invokeExact(
          n,
          nfac,
          stocknamesstocknames,
          ww,
          benchmarkbenchmark,
          InitialInitial,
          QQ,
          gamma,
          kappa,
          npiece,
          hpiecehpiece,
          pgradpgrad,
          buybuy,
          sellsell,
          alphaalpha,
          tcosttcost,
          utilityutility,
          gradutilitygradutility,
          utility_per_stockutility_per_stock,
          cost_per_stockcost_per_stock,
          ncomp,
          CompositeComposite,
          ShortCostScale,
          shortalphacostshortalphacost);
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Initial != null)
        for (int i = 0; i < Initial.length; i++) {
          Initial[i] = InitialInitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (hpiece != null)
        for (int i = 0; i < hpiece.length; i++) {
          hpiece[i] = hpiecehpiece.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (pgrad != null)
        for (int i = 0; i < pgrad.length; i++) {
          pgrad[i] = pgradpgrad.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (buy != null)
        for (int i = 0; i < buy.length; i++) {
          buy[i] = buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (sell != null)
        for (int i = 0; i < sell.length; i++) {
          sell[i] = sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (tcost != null)
        for (int i = 0; i < tcost.length; i++) {
          tcost[i] = tcosttcost.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (utility != null)
        for (int i = 0; i < utility.length; i++) {
          utility[i] = utilityutility.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (gradutility != null)
        for (int i = 0; i < gradutility.length; i++) {
          gradutility[i] = gradutilitygradutility.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (utility_per_stock != null)
        for (int i = 0; i < utility_per_stock.length; i++) {
          utility_per_stock[i] = utility_per_stockutility_per_stock.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (cost_per_stock != null)
        for (int i = 0; i < cost_per_stock.length; i++) {
          cost_per_stock[i] = cost_per_stockcost_per_stock.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (shortalphacost != null)
        for (int i = 0; i < shortalphacost.length; i++) {
          shortalphacost[i] = shortalphacostshortalphacost.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void MarginalUtilityb(long n, int nfac, String[] stocknames, double[] w, double[] benchmark,
      double[] Initial, double[] Q, double gamma, double kappa, long npiece, double[] hpiece, double[] pgrad,
      double[] buy, double[] sell, double[] alpha, double[] tcost, double[] utility, double[] gradutility,
      double[] utility_per_stock, double[] cost_per_stock, long ncomp, double[] Composite, double ShortCostScale) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var MarginalUtilitybnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("MarginalUtilityb").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_INT,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE));
      MemorySegment stocknamesstocknames;
      if (stocknames == null) {
        stocknamesstocknames = MemorySegment.NULL;
      } else {
        stocknamesstocknames = foreign.allocateArray(ValueLayout.ADDRESS, stocknames.length);
      }
      if (stocknames != null) {
        for (int i = 0; i < stocknames.length; i++) {
          MemorySegment k5 = foreign.allocateUtf8String(stocknames[i]);
          k5.setUtf8String(0, stocknames[i]);
          stocknamesstocknames.setAtIndex(ValueLayout.ADDRESS, i, k5);
        }
      }
      MemorySegment ww;
      if (w == null) {
        ww = MemorySegment.NULL;
      } else {
        ww = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, w.length);
      }
      if (w != null) {
        for (int i = 0; i < w.length; i++) {
          ww.setAtIndex(ValueLayout.JAVA_DOUBLE, i, w[i]);
        }
      }
      MemorySegment benchmarkbenchmark;
      if (benchmark == null) {
        benchmarkbenchmark = MemorySegment.NULL;
      } else {
        benchmarkbenchmark = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, benchmark.length);
      }
      if (benchmark != null) {
        for (int i = 0; i < benchmark.length; i++) {
          benchmarkbenchmark.setAtIndex(ValueLayout.JAVA_DOUBLE, i, benchmark[i]);
        }
      }
      MemorySegment InitialInitial;
      if (Initial == null) {
        InitialInitial = MemorySegment.NULL;
      } else {
        InitialInitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Initial.length);
      }
      if (Initial != null) {
        for (int i = 0; i < Initial.length; i++) {
          InitialInitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Initial[i]);
        }
      }
      MemorySegment QQ;
      if (Q == null) {
        QQ = MemorySegment.NULL;
      } else {
        QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
      }
      if (Q != null) {
        for (int i = 0; i < Q.length; i++) {
          QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);
        }
      }
      MemorySegment hpiecehpiece;
      if (hpiece == null) {
        hpiecehpiece = MemorySegment.NULL;
      } else {
        hpiecehpiece = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, hpiece.length);
      }
      if (hpiece != null) {
        for (int i = 0; i < hpiece.length; i++) {
          hpiecehpiece.setAtIndex(ValueLayout.JAVA_DOUBLE, i, hpiece[i]);
        }
      }
      MemorySegment pgradpgrad;
      if (pgrad == null) {
        pgradpgrad = MemorySegment.NULL;
      } else {
        pgradpgrad = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pgrad.length);
      }
      if (pgrad != null) {
        for (int i = 0; i < pgrad.length; i++) {
          pgradpgrad.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pgrad[i]);
        }
      }
      MemorySegment buybuy;
      if (buy == null) {
        buybuy = MemorySegment.NULL;
      } else {
        buybuy = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, buy.length);
      }
      if (buy != null) {
        for (int i = 0; i < buy.length; i++) {
          buybuy.setAtIndex(ValueLayout.JAVA_DOUBLE, i, buy[i]);
        }
      }
      MemorySegment sellsell;
      if (sell == null) {
        sellsell = MemorySegment.NULL;
      } else {
        sellsell = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, sell.length);
      }
      if (sell != null) {
        for (int i = 0; i < sell.length; i++) {
          sellsell.setAtIndex(ValueLayout.JAVA_DOUBLE, i, sell[i]);
        }
      }
      MemorySegment alphaalpha;
      if (alpha == null) {
        alphaalpha = MemorySegment.NULL;
      } else {
        alphaalpha = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, alpha.length);
      }
      if (alpha != null) {
        for (int i = 0; i < alpha.length; i++) {
          alphaalpha.setAtIndex(ValueLayout.JAVA_DOUBLE, i, alpha[i]);
        }
      }
      MemorySegment tcosttcost;
      if (tcost == null) {
        tcosttcost = MemorySegment.NULL;
      } else {
        tcosttcost = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, tcost.length);
      }
      if (tcost != null) {
        for (int i = 0; i < tcost.length; i++) {
          tcosttcost.setAtIndex(ValueLayout.JAVA_DOUBLE, i, tcost[i]);
        }
      }
      MemorySegment utilityutility;
      if (utility == null) {
        utilityutility = MemorySegment.NULL;
      } else {
        utilityutility = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, utility.length);
      }
      if (utility != null) {
        for (int i = 0; i < utility.length; i++) {
          utilityutility.setAtIndex(ValueLayout.JAVA_DOUBLE, i, utility[i]);
        }
      }
      MemorySegment gradutilitygradutility;
      if (gradutility == null) {
        gradutilitygradutility = MemorySegment.NULL;
      } else {
        gradutilitygradutility = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, gradutility.length);
      }
      if (gradutility != null) {
        for (int i = 0; i < gradutility.length; i++) {
          gradutilitygradutility.setAtIndex(ValueLayout.JAVA_DOUBLE, i, gradutility[i]);
        }
      }
      MemorySegment utility_per_stockutility_per_stock;
      if (utility_per_stock == null) {
        utility_per_stockutility_per_stock = MemorySegment.NULL;
      } else {
        utility_per_stockutility_per_stock = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, utility_per_stock.length);
      }
      if (utility_per_stock != null) {
        for (int i = 0; i < utility_per_stock.length; i++) {
          utility_per_stockutility_per_stock.setAtIndex(ValueLayout.JAVA_DOUBLE, i, utility_per_stock[i]);
        }
      }
      MemorySegment cost_per_stockcost_per_stock;
      if (cost_per_stock == null) {
        cost_per_stockcost_per_stock = MemorySegment.NULL;
      } else {
        cost_per_stockcost_per_stock = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, cost_per_stock.length);
      }
      if (cost_per_stock != null) {
        for (int i = 0; i < cost_per_stock.length; i++) {
          cost_per_stockcost_per_stock.setAtIndex(ValueLayout.JAVA_DOUBLE, i, cost_per_stock[i]);
        }
      }
      MemorySegment CompositeComposite;
      if (Composite == null) {
        CompositeComposite = MemorySegment.NULL;
      } else {
        CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);
      }
      if (Composite != null) {
        for (int i = 0; i < Composite.length; i++) {
          CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);
        }
      }
      MarginalUtilitybnative.invokeExact(
          n,
          nfac,
          stocknamesstocknames,
          ww,
          benchmarkbenchmark,
          InitialInitial,
          QQ,
          gamma,
          kappa,
          npiece,
          hpiecehpiece,
          pgradpgrad,
          buybuy,
          sellsell,
          alphaalpha,
          tcosttcost,
          utilityutility,
          gradutilitygradutility,
          utility_per_stockutility_per_stock,
          cost_per_stockcost_per_stock,
          ncomp,
          CompositeComposite,
          ShortCostScale);
      if (stocknames != null)
        for (int i = 0; i < stocknames.length; i++) {
          var k8 = stocknamesstocknames.getAtIndex(ValueLayout.ADDRESS, i);
          k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
          stocknames[i] = k8.getUtf8String(0);
        }
      if (w != null)
        for (int i = 0; i < w.length; i++) {
          w[i] = ww.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (benchmark != null)
        for (int i = 0; i < benchmark.length; i++) {
          benchmark[i] = benchmarkbenchmark.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Initial != null)
        for (int i = 0; i < Initial.length; i++) {
          Initial[i] = InitialInitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Q != null)
        for (int i = 0; i < Q.length; i++) {
          Q[i] = QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (hpiece != null)
        for (int i = 0; i < hpiece.length; i++) {
          hpiece[i] = hpiecehpiece.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (pgrad != null)
        for (int i = 0; i < pgrad.length; i++) {
          pgrad[i] = pgradpgrad.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (buy != null)
        for (int i = 0; i < buy.length; i++) {
          buy[i] = buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (sell != null)
        for (int i = 0; i < sell.length; i++) {
          sell[i] = sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (alpha != null)
        for (int i = 0; i < alpha.length; i++) {
          alpha[i] = alphaalpha.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (tcost != null)
        for (int i = 0; i < tcost.length; i++) {
          tcost[i] = tcosttcost.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (utility != null)
        for (int i = 0; i < utility.length; i++) {
          utility[i] = utilityutility.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (gradutility != null)
        for (int i = 0; i < gradutility.length; i++) {
          gradutility[i] = gradutilitygradutility.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (utility_per_stock != null)
        for (int i = 0; i < utility_per_stock.length; i++) {
          utility_per_stock[i] = utility_per_stockutility_per_stock.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (cost_per_stock != null)
        for (int i = 0; i < cost_per_stock.length; i++) {
          cost_per_stock[i] = cost_per_stockcost_per_stock.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Composite != null)
        for (int i = 0; i < Composite.length; i++) {
          Composite[i] = CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void Factor2Var(long n, long nfac, double[] FFC, double[][] FLOAD, double[] SSV, double[] Variance) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var Factor2Varnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("Factor2Var").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment FFCFFC;
      if (FFC == null) {
        FFCFFC = MemorySegment.NULL;
      } else {
        FFCFFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FFC.length);
      }
      if (FFC != null) {
        for (int i = 0; i < FFC.length; i++) {
          FFCFFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FFC[i]);
        }
      }
      double[] FLOAD1d = twoD2oneD((int) n, (int) nfac, FLOAD); // Get the integer arguments correct!
      MemorySegment FLOAD1dFLOAD1d;
      if (FLOAD1d == null) {
        FLOAD1dFLOAD1d = MemorySegment.NULL;
      } else {
        FLOAD1dFLOAD1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD1d.length);
      }
      if (FLOAD1d != null) {
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1dFLOAD1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD1d[i]);
        }
      }
      MemorySegment SSVSSV;
      if (SSV == null) {
        SSVSSV = MemorySegment.NULL;
      } else {
        SSVSSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SSV.length);
      }
      if (SSV != null) {
        for (int i = 0; i < SSV.length; i++) {
          SSVSSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SSV[i]);
        }
      }
      MemorySegment VarianceVariance;
      if (Variance == null) {
        VarianceVariance = MemorySegment.NULL;
      } else {
        VarianceVariance = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Variance.length);
      }
      if (Variance != null) {
        for (int i = 0; i < Variance.length; i++) {
          VarianceVariance.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Variance[i]);
        }
      }
      Factor2Varnative.invokeExact(
          n,
          nfac,
          FFCFFC,
          FLOAD1dFLOAD1d,
          SSVSSV,
          VarianceVariance);
      if (FFC != null)
        for (int i = 0; i < FFC.length; i++) {
          FFC[i] = FFCFFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FLOAD1d != null)
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1d[i] = FLOAD1dFLOAD1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (SSV != null)
        for (int i = 0; i < SSV.length; i++) {
          SSV[i] = SSVSSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (Variance != null)
        for (int i = 0; i < Variance.length; i++) {
          Variance[i] = VarianceVariance.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static void Factor2Cov(long n, long nfac, double[] FFC, double[][] FLOAD, double[] SSV, double[] QFIX) {

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var Factor2Covnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("Factor2Cov").orElseThrow(),
          FunctionDescriptor.ofVoid(
              ValueLayout.JAVA_LONG,
              ValueLayout.JAVA_LONG,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment FFCFFC;
      if (FFC == null) {
        FFCFFC = MemorySegment.NULL;
      } else {
        FFCFFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FFC.length);
      }
      if (FFC != null) {
        for (int i = 0; i < FFC.length; i++) {
          FFCFFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FFC[i]);
        }
      }
      double[] FLOAD1d = twoD2oneD((int) n, (int) nfac, FLOAD); // Get the integer arguments correct!
      MemorySegment FLOAD1dFLOAD1d;
      if (FLOAD1d == null) {
        FLOAD1dFLOAD1d = MemorySegment.NULL;
      } else {
        FLOAD1dFLOAD1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD1d.length);
      }
      if (FLOAD1d != null) {
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1dFLOAD1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD1d[i]);
        }
      }
      MemorySegment SSVSSV;
      if (SSV == null) {
        SSVSSV = MemorySegment.NULL;
      } else {
        SSVSSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SSV.length);
      }
      if (SSV != null) {
        for (int i = 0; i < SSV.length; i++) {
          SSVSSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SSV[i]);
        }
      }
      MemorySegment QFIXQFIX;
      if (QFIX == null) {
        QFIXQFIX = MemorySegment.NULL;
      } else {
        QFIXQFIX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, QFIX.length);
      }
      if (QFIX != null) {
        for (int i = 0; i < QFIX.length; i++) {
          QFIXQFIX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, QFIX[i]);
        }
      }
      Factor2Covnative.invokeExact(
          n,
          nfac,
          FFCFFC,
          FLOAD1dFLOAD1d,
          SSVSSV,
          QFIXQFIX);
      if (FFC != null)
        for (int i = 0; i < FFC.length; i++) {
          FFC[i] = FFCFFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (FLOAD1d != null)
        for (int i = 0; i < FLOAD1d.length; i++) {
          FLOAD1d[i] = FLOAD1dFLOAD1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (SSV != null)
        for (int i = 0; i < SSV.length; i++) {
          SSV[i] = SSVSSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
      if (QFIX != null)
        for (int i = 0; i < QFIX.length; i++) {
          QFIX[i] = QFIXQFIX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
        }
    } catch (Throwable e) {
      System.out.println(e);
    }
  }

  public static double TestInvoke(Object passer) {
    double back = -123;
    double r3 = 1.4422495703074083;
    double seeker = 3;
    MethodType mt;
    MethodHandle mh;
    MethodHandles.Lookup lookup = MethodHandles.publicLookup();
    mt = MethodType.methodType(double.class, double.class, Object.class);
    try {
      mh = lookup.findStatic(passer.getClass(), "passer", mt);
      back = (double) mh.invokeExact(r3, passer);
      mh = lookup.findStatic(passer.getClass(), "tester",
          MethodType.methodType(double.class, double.class, double.class));
      back = (double) mh.invokeExact(r3, seeker);
      mh = lookup.findStatic(passer.getClass(), "getseek", MethodType.methodType(double.class, Object.class));
      back = (double) mh.invokeExact(passer);
    } catch (Throwable d) {
      System.out.println(d);
    }
    return back;
  }

  public static double Solve1D(Object RiskE, double gammabot, double gammatop, double tol) {
    double back = -12345;

    MethodHandle mh = null;
    MemorySegment ms = null;
    FunctionDescriptor oned;
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    try {
      mh = lookup.findStatic(Info.class, "passerFunc",
          MethodType.methodType(double.class, double.class, MemorySegment.class));
      oned = FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_DOUBLE, ValueLayout.ADDRESS);
      ms = Linker.nativeLinker().upcallStub(mh, oned, Arena.ofAuto());
    } catch (Throwable u) {
      System.out.println(u);
    }

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      mh = MethodHandles.publicLookup().findStatic(RiskE.getClass(), "getseek",
          MethodType.methodType(double.class, Object.class));
      var risk = (double) mh.invokeExact(RiskE);
      var RiskERiskE = foreign.allocate(8);
      RiskERiskE.set(ValueLayout.JAVA_DOUBLE, 0, risk);
      var Solve1Dnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("Solve1D").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS));
      back = (double) Solve1Dnative.invokeExact(
          ms,
          gammabot,
          gammatop,
          tol,
          RiskERiskE);
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

  public static double PathMin(Object RiskE, double gammabot, double gammatop, double tol, int stopifpos) {
    double back = -12345;
    /*
     * If an argument is of type Object it will mean that
     * it is a function. The java generated here is not complete.
     * It is only 100% correct for Solve1D, otherwise some editing will be needed
     */

    MethodHandle mh = null;
    MemorySegment ms = null;
    FunctionDescriptor oned;
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    try {
      mh = lookup.findStatic(Info.class, "passerMinFunc",
          MethodType.methodType(double.class, double.class, MemorySegment.class));
      oned = FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_DOUBLE, ValueLayout.ADDRESS);
      ms = Linker.nativeLinker().upcallStub(mh, oned, Arena.ofAuto());
    } catch (Throwable u) {
      System.out.println(u);
    }

    try (Arena foreign = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
      var PathMinnative = Linker.nativeLinker().downcallHandle(
          safeqp.find("PathMin").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, // same no. of args as C function
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.JAVA_DOUBLE,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT));
      back = (double) PathMinnative.invokeExact(
          ms,
          gammabot,
          gammatop,
          tol,
          MemorySegment.NULL, // No extra data is needed via pointer
          stopifpos);
    } catch (Throwable e) {
      System.out.println(e);
      back = 0;
    }
    return back;
  }

}
