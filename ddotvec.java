import java.lang.foreign.*;
import java.lang.invoke.*;

//run via  java  --enable-preview --enable-native-access=ALL-UNNAMED --source 21 FFMTest21.java
public class ddotvec {
  public static void main(String[] args) throws Throwable {
    double[] a = { 1, 2, 3 };
    double[] b = { 1, 1, 1 };
    long n = a.length;

    try (Arena offHeap = Arena.ofConfined()) {
      final var safeqp = SymbolLookup.libraryLookup("safeqp", offHeap);

      MethodHandle dsumvec = Linker.nativeLinker().downcallHandle(
          safeqp.find("ddotvec").orElseThrow(),
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS,
              ValueLayout.ADDRESS));
      MemorySegment A = offHeap.allocateArray(ValueLayout.ADDRESS, a.length);
      for (int i = 0; i < a.length; i++) {
        MemorySegment cDouble = offHeap.allocate(ValueLayout.JAVA_DOUBLE, a[i]);
        A.setAtIndex(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE), i, cDouble);
      }
      MemorySegment B = offHeap.allocateArray(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE), b.length);
      for (int i = 0; i < b.length; i++) {
        MemorySegment cDouble = offHeap.allocate(ValueLayout.JAVA_DOUBLE, b[i]);
        B.setAtIndex(ValueLayout.ADDRESS, i, cDouble);
      }

      var back = dsumvec.invoke(n, A, B);
      System.out.println("sum of elements " + back);
    }
  }
}