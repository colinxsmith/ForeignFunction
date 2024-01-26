import com.bitaplus.BitaModel.Optimisation.*;

//compile via;    javac  --enable-preview  --source 21 -Xlint:preview  testpick.java
//run via;        java  -ea --enable-preview --enable-native-access=ALL-UNNAMED --source 21 .\testpick.java
public class testpick {
    public static void main(String args[]) {
         OptimiserFunctions.libraryname = "/host/colin/safeqp/libsafeqp.so";
    String[]stocks={"aa","bb","ff"};
    String []universe={"aa","bb","cc","dd","ee","ff"};
    String[]Q=universe.clone();
    long[]order=new long[universe.length];
    int back=OptimiserFunctions.pickoutstrings(universe.length,universe,stocks.length,stocks,Q,order);
    }
}