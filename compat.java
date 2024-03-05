import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

import com.bitaplus.BitaModel.Optimisation.*;

/*
Either 
javac   --enable-preview  -Xlint:preview -source 21 compat.java
java --enable-preview  --enable-native-access=ALL-UNNAMED compat

                    OR

java --enable-preview --source 21 --enable-native-access=ALL-UNNAMED compat.java
*/
public class compat {

    /*
     * static { // Need this if it is not part of class OptimiserController
     * try {
     * System.loadLibrary("OptimiserController");
     * } catch (UnsatisfiedLinkError e) {
     * System.err.
     * println("Native code library OptimiserController failed to load.\n" + e);
     * System.err.
     * println("Native code library OptimiserController failed to load.\n" + e);
     * System.err.
     * println("Native code library OptimiserController failed to load.\n" + e);
     * System.err.
     * println("Native code library OptimiserController failed to load.\n" + e);
     * System.err.
     * println("Native code library OptimiserController failed to load.\n" + e);
     * System.err.
     * println("Native code library OptimiserController failed to load.\n" + e);
     * // System.exit(1);
     * }
     * }
     */

    public static void main(String args[]) {
        System.out.println(OptimiserController.version());

        System.out.println(OptimiserController.expire_date());

        String file = "prob20june.log";
        if (args.length == 1)
            file = args[0];
        String[] keys = "n nfac names m A L soft_m soft_A soft_L soft_U soft_b soft_l U alpha shortalphacost bench Q gamma initial delta buy sell qbuy qsell kappa basket longbasket downrisk downfactor shortbasket tradebuy tradesell tradenum revise costs min_holding min_trade ls full minRisk maxRisk rmin rmax round min_lot size_lot ncomp Composites value valuel npiece hpiece pgrad nabs A_abs mabs I_A Abs_U Abs_L ShortCostScale mask"
                .split(" ");
        String delimiter = " ";
        String line = "";
        String lastKey = "";
        HashMap<String, String[]> DATA = new HashMap<String, String[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                if (line.contains("--------"))
                    break;
                String[] values;
                if (Arrays.asList(keys).contains(line)) {
                    lastKey = line;
                    line = br.readLine();
                    if (line == null)
                        break;
                    values = line.split(delimiter);
                    DATA.put(lastKey, values);
                } else {// We get here if array data goes over one line or unrecognised key word
                    if (!lastKey.matches("names") && line.matches("^[A-Z,a-z].*")) {
                        lastKey = line;
                        continue;
                    }
                    values = line.split(delimiter);
                    String[] here = DATA.get(lastKey);
                    if (here != null) {// Add the values in line to values in lastKey
                        int newLength = values.length + here.length;
                        String[] newValues = new String[newLength];
                        System.arraycopy(here, 0, newValues, 0, here.length);
                        System.arraycopy(values, 0, newValues, here.length, values.length);
                        DATA.put(lastKey, newValues);
                    } else {
                        DATA.put(lastKey, values);// Parts of the data file have what look like unknown keys
                    }
                }
            }
            // System.out.println((DATA));
        } catch (Exception e) {
            System.out.println(lastKey + " " + line + ":" + e);
        }
        int n = Arrays.stream(DATA.get("n")).mapToInt(Integer::parseInt).toArray()[0];
        int m = Arrays.stream(DATA.get("m")).mapToInt(Integer::parseInt).toArray()[0];
        int nfac = Arrays.stream(DATA.get("nfac")).mapToInt(Integer::parseInt).toArray()[0];
        int basket = Arrays.stream(DATA.get("basket")).mapToInt(Integer::parseInt).toArray()[0];
        int trades = Arrays.stream(DATA.get("tradenum")).mapToInt(Integer::parseInt).toArray()[0];
        int revise = Arrays.stream(DATA.get("revise")).mapToInt(Integer::parseInt).toArray()[0];
        int costs = Arrays.stream(DATA.get("costs")).mapToInt(Integer::parseInt).toArray()[0];
        int ls = Arrays.stream(DATA.get("ls")).mapToInt(Integer::parseInt).toArray()[0];
        int full = Arrays.stream(DATA.get("full")).mapToInt(Integer::parseInt).toArray()[0];
        double[] A = Arrays.stream(DATA.get("A")).mapToDouble(Double::parseDouble).toArray();
        double[] A_abs = null;
        try {
            A_abs = Arrays.stream(DATA.get("A_abs")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception jj) {
            ;
        }
        int[] I_Ai = null;
        long[] I_A = null;
        try {
            I_Ai = Arrays.stream(DATA.get("I_A")).mapToInt(Integer::parseInt).toArray();
            I_A = new long[I_Ai.length];
            for (int i = 0; i < I_Ai.length; ++i) {
                I_A[i] = (long) I_Ai[i];
            }
        } catch (Exception e) {
            ;
        }
        int round = Arrays.stream(DATA.get("round")).mapToInt(Integer::parseInt).toArray()[0];
        int ncomp = Arrays.stream(DATA.get("ncomp")).mapToInt(Integer::parseInt).toArray()[0];
        int npiece = Arrays.stream(DATA.get("npiece")).mapToInt(Integer::parseInt).toArray()[0];
        int longbasket = Arrays.stream(DATA.get("longbasket")).mapToInt(Integer::parseInt).toArray()[0];
        int shortbasket = Arrays.stream(DATA.get("shortbasket")).mapToInt(Integer::parseInt).toArray()[0];
        int tradebuy = Arrays.stream(DATA.get("tradebuy")).mapToInt(Integer::parseInt).toArray()[0];
        int tradesell = Arrays.stream(DATA.get("tradesell")).mapToInt(Integer::parseInt).toArray()[0];
        int nabs = Arrays.stream(DATA.get("nabs")).mapToInt(Integer::parseInt).toArray()[0];
        int mabs = Arrays.stream(DATA.get("mabs")).mapToInt(Integer::parseInt).toArray()[0];
        double[][] AA = OptimiserController.single2double(m, n, A);
        double[][] Abs_A = OptimiserController.single2double(nabs, n, A_abs);
        double[] L = Arrays.stream(DATA.get("L")).mapToDouble(Double::parseDouble).toArray();
        double[] U = Arrays.stream(DATA.get("U")).mapToDouble(Double::parseDouble).toArray();
        double[] Abs_L = null;
        try {
            Abs_L = Arrays.stream(DATA.get("Abs_L")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            ;
        }
        double[] Abs_U = null;
        try {
            Abs_U = Arrays.stream(DATA.get("Abs_U")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            ;
        }
        double[] initial = Arrays.stream(DATA.get("initial")).mapToDouble(Double::parseDouble).toArray();
        double[] alpha = Arrays.stream(DATA.get("alpha")).mapToDouble(Double::parseDouble).toArray();
        double[] bench = Arrays.stream(DATA.get("bench")).mapToDouble(Double::parseDouble).toArray();
        double[] Q = Arrays.stream(DATA.get("Q")).mapToDouble(Double::parseDouble).toArray();
        double[] buy = null;
        try {
            buy = Arrays.stream(DATA.get("buy")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] sell = null;
        try {
            sell = Arrays.stream(DATA.get("sell")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] min_lot = null;
        try {
            min_lot = Arrays.stream(DATA.get("min_lot")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] size_lot = null;
        try {
            size_lot = Arrays.stream(DATA.get("size_lot")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] Composites = null;
        try {
            Composites = Arrays.stream(DATA.get("Composites")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] hpiece = null;
        try {
            hpiece = Arrays.stream(DATA.get("hpiece")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] pgrad = null;
        try {
            pgrad = Arrays.stream(DATA.get("pgrad")).mapToDouble(Double::parseDouble).toArray();
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] mask = null;
        double gamma = Arrays.stream(DATA.get("gamma")).mapToDouble(Double::parseDouble).toArray()[0];
        double delta = Arrays.stream(DATA.get("delta")).mapToDouble(Double::parseDouble).toArray()[0];
        double kappa = Arrays.stream(DATA.get("kappa")).mapToDouble(Double::parseDouble).toArray()[0];
        double min_hold = Arrays.stream(DATA.get("min_holding")).mapToDouble(Double::parseDouble).toArray()[0];
        double min_trade = Arrays.stream(DATA.get("min_trade")).mapToDouble(Double::parseDouble).toArray()[0];
        double minRisk = Arrays.stream(DATA.get("minRisk")).mapToDouble(Double::parseDouble).toArray()[0];
        double maxRisk = Arrays.stream(DATA.get("maxRisk")).mapToDouble(Double::parseDouble).toArray()[0];
        double rmin = Arrays.stream(DATA.get("rmin")).mapToDouble(Double::parseDouble).toArray()[0];
        double rmax = Arrays.stream(DATA.get("rmax")).mapToDouble(Double::parseDouble).toArray()[0];
        int downrisk = Arrays.stream(DATA.get("downrisk")).mapToInt(Integer::parseInt).toArray()[0];
        double downfactor = Arrays.stream(DATA.get("downfactor")).mapToDouble(Double::parseDouble).toArray()[0];
        double value = Arrays.stream(DATA.get("value")).mapToDouble(Double::parseDouble).toArray()[0];
        double valuel = Arrays.stream(DATA.get("valuel")).mapToDouble(Double::parseDouble).toArray()[0];
        double ShortCostScale = Arrays.stream(DATA.get("ShortCostScale")).mapToDouble(Double::parseDouble).toArray()[0];
        double zetaF = 1;
        double zetaS = 1;

        double[] wJNI = new double[n];
        int[] shake = new int[n];
        double[] ogamma = new double[1];
        short back = OptimiserController.Optimise_internalCVPAFbl((long) n, nfac, DATA.get("names"), wJNI, (long) m, AA,
                L, U,
                alpha, bench, Q, gamma, initial, delta, buy, sell, kappa, basket, trades, revise, costs, min_hold,
                min_trade, ls, full, rmin, rmax, round, min_lot, size_lot, shake, (long) ncomp, Composites, value,
                (long) npiece, hpiece, pgrad, (long) nabs, Abs_A, (long) mabs, I_A, Abs_U, null, null, null,
                minRisk, maxRisk, ogamma, mask, 2, "OptJava.log", downrisk, downfactor, longbasket, shortbasket,
                tradebuy, tradesell, zetaS, zetaF, ShortCostScale, valuel, Abs_L);
        // OptimiserFunctions.libraryname="C:\\Users\\colin\\COM64\\safeqp\\x64\\Debug\\safeqp.dll";
        // OptimiserFunctions.libraryname="/home/colin/safeqp/libsafeqp.so";
        double[] wFFI = new double[n];
        back = OptimiserFunctions.Optimise_internalCVPAFbl((long) n, nfac, DATA.get("names"), wFFI, (long) m, AA, L, U,
                alpha, bench, Q, gamma, initial, delta, buy, sell, kappa, basket, trades, revise, costs, min_hold,
                min_trade, ls, full, rmin, rmax, round, min_lot, size_lot, shake, (long) ncomp, Composites, value,
                (long) npiece, hpiece, pgrad, (long) nabs, Abs_A, (long) mabs, I_A, Abs_U, null, null, null,
                minRisk, maxRisk, ogamma, mask, 2, "OptJava.log", downrisk, downfactor, longbasket, shortbasket,
                tradebuy, tradesell, zetaS, zetaF, ShortCostScale, valuel, Abs_L);

        System.out.println(back);
        System.out.println(OptimiserFunctions.Return_Message(back));
        double[] diff = new double[n];
        OptimiserFunctions.dsubvec(n, wJNI, wFFI, diff);
        assert OptimiserFunctions.ddotvec(n, diff, diff) < OptimiserFunctions.lm_eps;
        // Show how do a goal seek to find cube root of 3
        Info solvetest = new Info();
        solvetest.seek = 3;
        double ggg = OptimiserController.Solve1D(solvetest, 0, 10, 0);
        double result = solvetest.f1d(ggg);
        System.out.println("\ng=" + ggg + "\tg*g*g=" + ggg * ggg * ggg + "\tresult=" + result);
        System.out.println("Seek the cube root of " + solvetest.seek + " (" + Math.pow(3, 1.0 / 3.0) + ")");
        double gg = OptimiserFunctions.TestInvoke(solvetest);
        System.out.println("Seek " + gg);
        double g = OptimiserFunctions.Solve1D(solvetest, 0, 10, 0);
        result = solvetest.f1d(g);
        System.out.println("\ng=" + g + "\tg*g*g=" + g * g * g + "\tresult=" + result);
        g = OptimiserFunctions.PathMin(solvetest, 0, 10, 1e-9, 0);
        System.out.println("Minimum at;" + g + " Mininimum value is;" + Info.passerMinFunc(g, null));// Ok because
                                                                                                     // passer is null,
                                                                                                     // no extra data
    }

}
