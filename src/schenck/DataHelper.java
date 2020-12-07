package schenck;

import java.math.BigInteger;
import org.jetbrains.annotations.NotNull;

public class DataHelper {

  public static @NotNull
  String toLittleEndian(SchenckDataType dataType) {

    StringBuilder hexLittleEndian = new StringBuilder();
    if (dataType.getValue().length() % 2 != 0) {
      return "Invalid";
    }
    for (int i = dataType.getValue().length() - 2; i >= 0; i -= 2) {
      hexLittleEndian.append(dataType.getValue(), i, i + 2);
    }
    if (dataType.getClass() == IEEE754.class) {
      float IEEE754Value = Float.intBitsToFloat((int) Long.parseLong(hexLittleEndian.toString(), 16));
      return Float.toString(IEEE754Value);
    } else if(dataType.getClass() == BitEncoded.class) {
      BigInteger bitEncodedValue = new BigInteger(hexLittleEndian.toString(), 16);
      String bitEncodedString = bitEncodedValue.toString(2);
      StringBuilder fullDwordString = new StringBuilder("0".repeat(Math.max(0, 32 - bitEncodedString.length()))
          + bitEncodedString);
      return fullDwordString.reverse().toString();
    } else {
      return hexLittleEndian.toString();
    }
  }
}
