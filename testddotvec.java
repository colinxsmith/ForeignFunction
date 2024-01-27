import com.bitaplus.BitaModel.Optimisation.*;

//compile with;   javac  --enable-preview  --source 21 -Xlint:preview  testddotvec.java
//run with;       java  --enable-preview --enable-native-access=ALL-UNNAMED --source 21 testddotvec.java
public class testddotvec {
    public static void main(String args[]) {
        OptimiserFunctions.libraryname = "/host/colin/safeqp/libsafeqp.so";
        double[] a = { 1, 2, 3, 4, 5, 6, 7 };
        double[] b = { 1, 1, 1, 1, 1, 1, 1 };
        long n = a.length;
        double adotb_JNI = OptimiserController.ddotvec(n, a, b);
        double adotb = OptimiserFunctions.ddotvec(n, a, b);
        assert adotb_JNI == adotb;
        double actualsum = OptimiserFunctions.dsumvec(n, a);
        assert actualsum == adotb;
        System.out.println("a.b = " + adotb + " (from JNI " + adotb_JNI + ")" + " (using dsumvec " + actualsum + ")");
        String back = OptimiserFunctions.version();
        System.out.println(back);
        String backs = OptimiserController.version();
        String cve = OptimiserFunctions.cversion();
        System.out.println("cversion " + cve);
        assert back == backs;
    }
}