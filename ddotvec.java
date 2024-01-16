import java.lang.foreign.*;
import java.lang.invoke.*;

//run via  java  --enable-preview --enable-native-access=ALL-UNNAMED --source 21 ddotvec.java
public class ddotvec {
  public static void main(String[] args) throws Throwable {
    double[] a = { 1, 2, 3 };
    double[] b = { 1, 1, 1 };
    long n = a.length;

    try (Arena offHeap = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup("safeqp.dll", offHeap);

      MethodHandle dsumvec = Linker.nativeLinker().downcallHandle(
          safeqp.find("ddotvec").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment A = offHeap.allocateArray(ValueLayout.JAVA_DOUBLE, a.length);
      for (int i = 0; i < a.length; i++) {
        A.setAtIndex(ValueLayout.JAVA_DOUBLE, i, a[i]);
      }
      for (int i = 0; i < a.length; i++) {
        System.out.println("a[" + i + "] = " + A.getAtIndex(ValueLayout.JAVA_DOUBLE, i));
      }
      MemorySegment B = offHeap.allocateArray(ValueLayout.JAVA_DOUBLE, b.length);
      for (int i = 0; i < b.length; i++) {
        B.setAtIndex(ValueLayout.JAVA_DOUBLE, i, b[i]);
      }
      for (int i = 0; i < b.length; i++) {
        System.out.println("b[" + i + "] = " + B.getAtIndex(ValueLayout.JAVA_DOUBLE, i));
      }

      var back = (double) dsumvec.invokeExact(n, A, B);
      System.out.println("sum of elements in A (= scalar product A.B); " + back);
    }
  }
}