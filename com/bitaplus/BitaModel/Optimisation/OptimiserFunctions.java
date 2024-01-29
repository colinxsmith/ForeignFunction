
package com.bitaplus.BitaModel.Optimisation;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
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
  public static double[][] oneD2twoD(int n, int m, double[] ONED){
    return oneD2twoD(n, m,  ONED,Boolean.FALSE);
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
  public static double[] twoD2oneD(int n, int nf, double[][] TWOD){
    return twoD2oneD(n, nf, TWOD,Boolean.FALSE);
  }

  public static double[] twoD2oneD(int n, int nf, double[][] TWOD, Boolean transpose) {
    if(TWOD==null){
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
  public static  String Return_Message(int ifail) {
    String back; 
   try (Arena foreign = Arena.ofConfined()) { 
   final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
   var Return_Messagenative = Linker.nativeLinker().downcallHandle(
   safeqp.find("Return_Message").orElseThrow(),
   FunctionDescriptor.of(ValueLayout.ADDRESS,
     ValueLayout.JAVA_INT ));
   MemorySegment bbb = (MemorySegment) Return_Messagenative.invokeExact(
     ifail );
 bbb = bbb.reinterpret(Long.MAX_VALUE);
 back = bbb.getUtf8String(0);
   }
   catch (Throwable e) {       System.out.println(e);       back = "";       }
   return back;}
 
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
  }public static  int days_left(String[] aversion) {
    int back; 
    aversion[0]="q";
   try (Arena foreign = Arena.ofConfined()) { 
   final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
   var days_leftnative = Linker.nativeLinker().downcallHandle(
   safeqp.find("days_left").orElseThrow(),
   FunctionDescriptor.of(ValueLayout.JAVA_INT,
     ValueLayout.ADDRESS ));
   var aversionaversion = foreign.allocateArray(ValueLayout.ADDRESS, aversion.length);
   for (int i = 0; i < aversion.length; i++) {
     MemorySegment k5=foreign.allocateUtf8String(aversion[i]);
     k5.setUtf8String(0,aversion[i]);
     aversionaversion.setAtIndex(ValueLayout.ADDRESS, i, k5);}
   back = (int) days_leftnative.invokeExact(
     aversionaversion );
   for (int i = 0; i < aversion.length; i++) {
     var k8=aversionaversion.getAtIndex(ValueLayout.ADDRESS, i);
     k8 = k8.reinterpret(Long.MAX_VALUE);// This is essential
     aversion[i] = k8.getUtf8String(0);}
   }
   catch (Throwable e) {       System.out.println(e);       back = 0;       }
   return back;}
 
  public static  void factor_model_process(long n, long nf, double[][] FL, double[] FC, double[] SV,double[] Q) {

    try (Arena foreign = Arena.ofConfined()) { 
    final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
    var factor_model_processnative = Linker.nativeLinker().downcallHandle(
    safeqp.find("factor_model_process").orElseThrow(),
    FunctionDescriptor.ofVoid(
      ValueLayout.JAVA_LONG ,
      ValueLayout.JAVA_LONG ,
      ValueLayout.ADDRESS ,
      ValueLayout.ADDRESS ,
      ValueLayout.ADDRESS ,
      ValueLayout.ADDRESS ));
    double[] FL1d = twoD2oneD((int) n, (int) nf, FL);	//Get the integer arguments correct!
    var FL1dFL1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FL1d.length);
    for (int i = 0; i < FL1d.length; i++) {
      FL1dFL1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FL1d[i]);}
    var FCFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FC.length);
    for (int i = 0; i < FC.length; i++) {
      FCFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FC[i]);}
    var SVSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SV.length);
    for (int i = 0; i < SV.length; i++) {
      SVSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SV[i]);}
    var QQ = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Q.length);
    for (int i = 0; i < Q.length; i++) {
      QQ.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Q[i]);}
    factor_model_processnative.invokeExact(
      n ,
      nf ,
      FL1dFL1d ,
      FCFC ,
      SVSV ,
      QQ );
    for (int i = 0; i < FL1d.length; i++) {
      FL1d[i]=FL1dFL1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
    for (int i = 0; i < FC.length; i++) {
      FC[i]=FCFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
    for (int i = 0; i < SV.length; i++) {
      SV[i]=SVSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
    for (int i = 0; i < Q.length; i++) {
      Q[i]=QQ.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
    }
    catch (Throwable e) {       System.out.println(e);             }}
   
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
   public static  short Optimise_internalCVPAFbl(long n, int nfac, String[] stocknames, double[] w_opt, long m, double[][] AAA, double[] L, double[] U, double[] alpha, double[] benchmark, double[] QMATRIX, double gamma, double[] Initial, double delta, double[] buy, double[] sell, double kappa, int basket, int trades, int revise, int costs, double min_holding, double min_trade, int m_LS, int Fully_Invested, double Rmin, double Rmax, int m_Round, double[] min_lot, double[] size_lot, int[] shake, long ncomp, double[] Composite, double LSValue, long npiece, double[] hpiece, double[] pgrad, long nabs, double[][] Abs_A, long mabs, long[] I_A, double[] Abs_U, double[] FFC, double[][] FLOAD, double[] SSV, double minRisk, double maxRisk, double[] ogamma, double[] mask, int log, String logfile, int downrisk, double downfactor, int longbasket, int shortbasket, int tradebuy, int tradesell, double zetaS, double zetaF, double ShortCostScale, double LSValuel, double[] Abs_L) {
    short back; 
   try (Arena foreign = Arena.ofConfined()) { 
   final var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);
   var Optimise_internalCVPAFblnative = Linker.nativeLinker().downcallHandle(
   safeqp.find("Optimise_internalCVPAFbl").orElseThrow(),
   FunctionDescriptor.of(ValueLayout.JAVA_SHORT,
     ValueLayout.JAVA_LONG ,
     ValueLayout.JAVA_INT ,
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
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_INT ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_LONG ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_LONG ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_LONG ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_LONG ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.ADDRESS ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_INT ,
     ValueLayout.ADDRESS ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_INT ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.JAVA_DOUBLE ,
     ValueLayout.ADDRESS ));
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
 MemorySegment QMATRIXQMATRIX;
 if(QMATRIX==null){
   QMATRIXQMATRIX = MemorySegment.NULL;}
 else{	QMATRIXQMATRIX = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, QMATRIX.length);}
 if(QMATRIX!=null){
   for (int i = 0; i < QMATRIX.length; i++) {
     QMATRIXQMATRIX.setAtIndex(ValueLayout.JAVA_DOUBLE, i, QMATRIX[i]);}}
 MemorySegment InitialInitial;
 if(Initial==null){
   InitialInitial = MemorySegment.NULL;}
 else{	InitialInitial = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Initial.length);}
 if(Initial!=null){
   for (int i = 0; i < Initial.length; i++) {
     InitialInitial.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Initial[i]);}}
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
 MemorySegment CompositeComposite;
 if(Composite==null){
   CompositeComposite = MemorySegment.NULL;}
 else{	CompositeComposite = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, Composite.length);}
 if(Composite!=null){
   for (int i = 0; i < Composite.length; i++) {
     CompositeComposite.setAtIndex(ValueLayout.JAVA_DOUBLE, i, Composite[i]);}}
 MemorySegment hpiecehpiece;
 if(hpiece==null){
   hpiecehpiece = MemorySegment.NULL;}
 else{	hpiecehpiece = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, hpiece.length);}
 if(hpiece!=null){
   for (int i = 0; i < hpiece.length; i++) {
     hpiecehpiece.setAtIndex(ValueLayout.JAVA_DOUBLE, i, hpiece[i]);}}
 MemorySegment pgradpgrad;
 if(pgrad==null){
   pgradpgrad = MemorySegment.NULL;}
 else{	pgradpgrad = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, pgrad.length);}
 if(pgrad!=null){
   for (int i = 0; i < pgrad.length; i++) {
     pgradpgrad.setAtIndex(ValueLayout.JAVA_DOUBLE, i, pgrad[i]);}}
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
 MemorySegment FFCFFC;
 if(FFC==null){
   FFCFFC = MemorySegment.NULL;}
 else{	FFCFFC = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FFC.length);}
 if(FFC!=null){
   for (int i = 0; i < FFC.length; i++) {
     FFCFFC.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FFC[i]);}}
   double[] FLOAD1d = twoD2oneD((int) n, (int) nfac, FLOAD);	//Get the integer arguments correct!
 MemorySegment FLOAD1dFLOAD1d;
 if(FLOAD1d==null){
   FLOAD1dFLOAD1d = MemorySegment.NULL;}
 else{	FLOAD1dFLOAD1d = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, FLOAD1d.length);}
 if(FLOAD1d!=null){
   for (int i = 0; i < FLOAD1d.length; i++) {
     FLOAD1dFLOAD1d.setAtIndex(ValueLayout.JAVA_DOUBLE, i, FLOAD1d[i]);}}
 MemorySegment SSVSSV;
 if(SSV==null){
   SSVSSV = MemorySegment.NULL;}
 else{	SSVSSV = foreign.allocateArray(ValueLayout.JAVA_DOUBLE, SSV.length);}
 if(SSV!=null){
   for (int i = 0; i < SSV.length; i++) {
     SSVSSV.setAtIndex(ValueLayout.JAVA_DOUBLE, i, SSV[i]);}}
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
   back = (short) Optimise_internalCVPAFblnative.invokeExact(
     n ,
     nfac ,
     stocknamesstocknames ,
     w_optw_opt ,
     m ,
     AAA1dAAA1d ,
     LL ,
     UU ,
     alphaalpha ,
     benchmarkbenchmark ,
     QMATRIXQMATRIX ,
     gamma ,
     InitialInitial ,
     delta ,
     buybuy ,
     sellsell ,
     kappa ,
     basket ,
     trades ,
     revise ,
     costs ,
     min_holding ,
     min_trade ,
     m_LS ,
     Fully_Invested ,
     Rmin ,
     Rmax ,
     m_Round ,
     min_lotmin_lot ,
     size_lotsize_lot ,
     shakeshake ,
     ncomp ,
     CompositeComposite ,
     LSValue ,
     npiece ,
     hpiecehpiece ,
     pgradpgrad ,
     nabs ,
     Abs_A1dAbs_A1d ,
     mabs ,
     I_AI_A ,
     Abs_UAbs_U ,
     FFCFFC ,
     FLOAD1dFLOAD1d ,
     SSVSSV ,
     minRisk ,
     maxRisk ,
     ogammaogamma ,
     maskmask ,
     log ,
     logfilelogfile ,
     downrisk ,
     downfactor ,
     longbasket ,
     shortbasket ,
     tradebuy ,
     tradesell ,
     zetaS ,
     zetaF ,
     ShortCostScale ,
     LSValuel ,
     Abs_LAbs_L );
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
   if(QMATRIX!=null) for (int i = 0; i < QMATRIX.length; i++) {
     QMATRIX[i]=QMATRIXQMATRIX.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(Initial!=null) for (int i = 0; i < Initial.length; i++) {
     Initial[i]=InitialInitial.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(buy!=null) for (int i = 0; i < buy.length; i++) {
     buy[i]=buybuy.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(sell!=null) for (int i = 0; i < sell.length; i++) {
     sell[i]=sellsell.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(min_lot!=null) for (int i = 0; i < min_lot.length; i++) {
     min_lot[i]=min_lotmin_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(size_lot!=null) for (int i = 0; i < size_lot.length; i++) {
     size_lot[i]=size_lotsize_lot.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(shake!=null) for (int i = 0; i < shake.length; i++) {
     shake[i]=shakeshake.getAtIndex(ValueLayout.JAVA_INT, i);}
   if(Composite!=null) for (int i = 0; i < Composite.length; i++) {
     Composite[i]=CompositeComposite.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(hpiece!=null) for (int i = 0; i < hpiece.length; i++) {
     hpiece[i]=hpiecehpiece.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(pgrad!=null) for (int i = 0; i < pgrad.length; i++) {
     pgrad[i]=pgradpgrad.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(Abs_A1d!=null) for (int i = 0; i < Abs_A1d.length; i++) {
     Abs_A1d[i]=Abs_A1dAbs_A1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(I_A!=null) for (int i = 0; i < I_A.length; i++) {
     I_A[i]=I_AI_A.getAtIndex(ValueLayout.JAVA_LONG, i);}
   if(Abs_U!=null) for (int i = 0; i < Abs_U.length; i++) {
     Abs_U[i]=Abs_UAbs_U.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(FFC!=null) for (int i = 0; i < FFC.length; i++) {
     FFC[i]=FFCFFC.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(FLOAD1d!=null) for (int i = 0; i < FLOAD1d.length; i++) {
     FLOAD1d[i]=FLOAD1dFLOAD1d.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(SSV!=null) for (int i = 0; i < SSV.length; i++) {
     SSV[i]=SSVSSV.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(ogamma!=null) for (int i = 0; i < ogamma.length; i++) {
     ogamma[i]=ogammaogamma.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   if(mask!=null) for (int i = 0; i < mask.length; i++) {
     mask[i]=maskmask.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
 logfile=logfilelogfile.getUtf8String(0);
   if(Abs_L!=null) for (int i = 0; i < Abs_L.length; i++) {
     Abs_L[i]=Abs_LAbs_L.getAtIndex(ValueLayout.JAVA_DOUBLE, i);}
   }
   catch (Throwable e) {       System.out.println(e);       back = 0;       }
   return back;}
 
}