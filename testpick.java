import com.bitaplus.BitaModel.Optimisation.*;

//compile via;    javac  --enable-preview  --source 21 -Xlint:preview  testpick.java
//run via;        java  -ea --enable-preview --enable-native-access=ALL-UNNAMED --source 21 .\testpick.java
public class testpick {
    public static void main(String args[]) {
         OptimiserFunctions.libraryname = "/host/colin/safeqp/libsafeqp.so";
    String[]stocks={"aa","bb","ff","cc","ee","dd"};
    String []universe={"aa","bb","cc","dd","ee","ff"};
    String[]Q=
    {"11",
    "21","22",
    "31","32","33",
    "41","42","43","44",
    "51","52","53","54","55",
    "61","62","63","64","65","66"};
    var dd=OptimiserFunctions.cversion();
    long[]order=new long[universe.length];
    int back=OptimiserFunctions.pickoutstrings(universe.length,universe,stocks.length,stocks,Q,order);
    }
}