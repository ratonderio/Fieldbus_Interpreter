package schenck;

import java.math.BigInteger;
import org.jetbrains.annotations.NotNull;

public class DataHelper {

  public static @NotNull
  Number toLittleEndian(SchenckDataType dataType) {

    StringBuilder hexLittleEndian = new StringBuilder();
    if (dataType.getValue().length() % 2 != 0) {
      return 0f;
    }
    for (int i = dataType.getValue().length() - 2; i >= 0; i -= 2) {
      hexLittleEndian.append(dataType.getValue(), i, i + 2);
    }
    if (dataType.getClass() == IEEE754.class) {
      return Float.intBitsToFloat((int) Long.parseLong(hexLittleEndian.toString(), 16));
    } else {
      return new BigInteger(hexLittleEndian.toString(), 16);
    }
  }


}
