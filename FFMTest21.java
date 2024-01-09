import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
//run via  java  --enable-preview --enable-native-access=ALL-UNNAMED --source 21 FFMTest21.java
public class FFMTest21 {
  public static void main(String[] args) throws Throwable {
    // 1. Get a lookup object for commonly used libraries
    SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();

    // 2. Get a handle to the "strlen" function in the C standard library
    MethodHandle strlen = Linker.nativeLinker().downcallHandle(
        stdlib.find("strlen").orElseThrow(),
        FunctionDescriptor.of(ValueLayout.JAVA_LONG,ValueLayout. ADDRESS));

    // 3. Convert Java String to C string and store it in off-heap memory
    try (Arena offHeap = Arena.ofConfined()) {
      MemorySegment str = offHeap.allocateUtf8String("Colin Smith");

      // 4. Invoke the foreign function
      long len = (long) strlen.invoke(str);

      System.out.println("len = " + len);
    }
    // 5. Off-heap memory is deallocated at end of try-with-resources
  }
}