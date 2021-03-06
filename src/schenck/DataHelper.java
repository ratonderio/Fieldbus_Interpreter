package schenck;

import java.math.BigInteger;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

//TODO Little Endian does byte swap only.  IEEE SWP swaps words.  Words for bit encoded get read in
//from left to right.  Ex. (AABBCCDD -> BBAADDCC) So Little endian would first do (AABBCCDD -> BBAADDCC)
//then it gets read in word wise (BBAADDCC -> AABBCCDD) [3,2,1,0][7,6,5,4]


public class DataHelper {

  public static @NotNull
  String formatData(SchenckDataType dataType, boolean littleEndian, boolean wordSwapped) {
    if (dataType.getValue().length() % 2 != 0) {
      return "Invalid";
    }

    StringBuilder hexLittleEndian = getLittleEndian(dataType.getValue());
    StringBuilder hexWordSwapped = wordSwap(dataType.getValue());
    StringBuilder hexBoth = wordSwap(hexLittleEndian.toString());

    if (dataType.getClass() == IEEE754.class) {
      float IEEE754Value;
      if (littleEndian && wordSwapped) {
        IEEE754Value = Float
            .intBitsToFloat((int) Long.parseLong(hexBoth.toString(), 16));
      } else if (littleEndian) {
        IEEE754Value = Float
            .intBitsToFloat((int) Long.parseLong(hexLittleEndian.toString(), 16));
      } else if (wordSwapped) {
        IEEE754Value = Float
            .intBitsToFloat((int) Long.parseLong(hexWordSwapped.toString(), 16));
      } else {
        IEEE754Value = Float
            .intBitsToFloat((int) Long.parseLong(dataType.getValue(), 16));
      }
      return Float.toString(IEEE754Value);
    } else if (dataType.getClass() == BitEncoded.class) {
      StringBuilder fullDwordString;
      if (littleEndian) {
        fullDwordString = hexBoth;
      } else {
        fullDwordString = hexWordSwapped;
      }

      fullDwordString = getBitEncoded(fullDwordString.toString());

      return fullDwordString.reverse().toString();
    } else {
      return hexLittleEndian.toString();
    }
  }

  public static StringBuilder getBitEncoded(String hexValue) {
    BigInteger bitEncodedValue = new BigInteger(hexValue, 16);
    String bitEncodedString = bitEncodedValue.toString(2);
    return new StringBuilder("0".repeat(Math.max(0, 32 - bitEncodedString.length()))
        + bitEncodedString);
  }

  private static StringBuilder getLittleEndian(String hexValue) {
    StringBuilder stringBuilder = new StringBuilder();
    String[] hexValueSplit = hexValue.split("");
    stringBuilder.append(hexValueSplit[2]).append(hexValueSplit[3]).append(hexValueSplit[0])
        .append(hexValueSplit[1]);
    stringBuilder.append(hexValueSplit[6]).append(hexValueSplit[7]).append(hexValueSplit[4])
        .append(hexValueSplit[5]);
//    for (int i = hexValue.length() - 2; i >= 0; i -= 2) {
//      stringBuilder.append(hexValue, i, i + 2);
//    }
    return stringBuilder;
  }

  private static StringBuilder wordSwap(String hexValue) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(hexValue, 4, 8).append(hexValue, 0, 4);
    return stringBuilder;
  }

  public static ArrayList<String> toStatus5253(SchenckDataType dataType, boolean littleEndian) {
    String statusValue = dataType.getValue();
    if (littleEndian) {
      statusValue = String.valueOf(getLittleEndian(statusValue));
    }
    statusValue = String.valueOf(wordSwap(statusValue));
    statusValue = String.valueOf(getBitEncoded(statusValue));

    ArrayList<String> values = new ArrayList<>();

    values.add(String
        .format("%02d", Integer.parseInt(statusValue.substring(0, 8), 2)));    // Parameter No.
    values.add(String
        .valueOf(Integer.parseInt(statusValue.substring(8, 16), 2)));    // Parameter Block No.
    values.add(statusValue.substring(16, 17));    // Offset (+16)
    values.add(statusValue.substring(17, 19));    // Unused
    values.add(statusValue.substring(19, 20));    // Acknowledged
    values
        .add(String.valueOf(Integer.parseInt(statusValue.substring(20, 24), 2)));    // Event Class
    values
        .add(String.valueOf(Integer.parseInt(statusValue.substring(24, 28), 2)));    // Event Group
    values.add(String
        .format("%02d%n", Integer.parseInt(statusValue.substring(28), 2) + 1));    // Event No.

    values.set(5, getClass(values.get(5)));
    values.set(6, getGroup(values.get(6)));

    return values;

  }

  private static String getClass(String classNumber) {
    return switch (classNumber) {
      case "1" -> "A";
      case "2" -> "W1";
      case "3" -> "W2";
      case "4" -> "IG";
      default -> "N/A";
    };
  }

  private static String getGroup(String groupNumber) {
    return switch (groupNumber) {
      case "1" -> "SY";
      case "2" -> "SC";
      case "3" -> "WE";
      case "4" -> "WM";
      case "5" -> "MF";
      case "6" -> "IL";
      case "7" -> "CO";
      case "8" -> "CH";
      case "9" -> "CA";
      case "10" -> "HI";
      case "11" -> "LO";
      default -> "N/A";
    };
  }
}
