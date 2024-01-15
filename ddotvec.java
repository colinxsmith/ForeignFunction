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
          FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE),
              ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE)));
      MemorySegment A = offHeap.allocateArray(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE), a.length);
      for (int i = 0; i < a.length; i++) {
        A.setAtIndex(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE), i, offHeap.allocate(ValueLayout.JAVA_DOUBLE, a[i]));
      }
      MemorySegment B = offHeap.allocateArray(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE), b.length);
      for (int i = 0; i < b.length; i++) {
        B.setAtIndex(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_DOUBLE), i, offHeap.allocate(ValueLayout.JAVA_DOUBLE, b[i]));
      }

      var back = (double)dsumvec.invokeExact(n,A,B);
      System.out.println("sum of elements " + back);
    }
  }
}