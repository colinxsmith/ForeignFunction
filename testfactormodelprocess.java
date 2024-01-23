import com.bitaplus.BitaModel.Optimisation.*;
//compile via;    javac  --enable-preview  --source 21 -Xlint:preview  testfactormodelprocess.java
//run via;         java  --enable-preview --enable-native-access=ALL-UNNAMED --source 21 .\testfactormodelprocess.java
public class testfactormodelprocess {
    public static void main(String args[]) {
        double[][] FL = { { 1, 1 }, { 2, -1}, { 3, 1 }, { 4, -1 } };
        int n = FL.length;
        int nf = FL[0].length;
        double[] SV = { 4, 5, 6, 7, 8 };
        double[] Q = new double[(nf + 1) * n];
        double[] FC = { 1, 2, 3 };
        OptimiserController.factor_model_process(n, nf, FL, FC, SV, Q);
        double[] Q1 = new double[(nf + 1) * n];
        OptimiserFunctions.factor_model_process(n, nf, FL, FC, SV, Q1);
        OptimiserFunctions.dsubvec((nf + 1) * n, Q, Q1, Q1);
        double diff = OptimiserFunctions.ddotvec((nf + 1) * n, Q1, Q1);
        assert diff < OptimiserFunctions.lm_eps;
        System.out.println("Agreement difference "+diff);
        OptimiserFunctions.daddvec((nf + 1) * n, Q, Q1, Q1);
    }
}