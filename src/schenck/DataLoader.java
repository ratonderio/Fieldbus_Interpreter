package schenck;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DataLoader {

  //TODO Need Map for SchenckDataType for final translation; not sure if here or own class
  DataLoader() {
    List<String> translationTable = Collections.emptyList();
    //List<String> lines = Collections.emptyList();
    try {
      translationTable = Files
          .readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
      //lines = Files.readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Iterator<String> iterator = translationTable.iterator();

    String hexCode, rest, temp, currentName = null, prevName;
    List<String> test = new ArrayList<>();
    //HashMap<SchenckDataType, String> translated;

    while (iterator.hasNext()) {
      temp = iterator.next();
      hexCode = temp.substring(0, 4);
      rest = temp.substring(4).strip();

      if (hexCode.matches("\\d[a-fA-F0-9]{3}")) {
        test.add(hexCode + " " + rest);
        if (!iterator.hasNext()) {
          BitEncoded finalBitEncoded = new BitEncoded(currentName,test);
          System.out.println(test.size());
          System.out.println(finalBitEncoded.toString());
        }
      } else if (hexCode.matches("[CS][ot][ma].*")) {
        System.out.println(test.size());
        prevName = currentName;
        currentName = hexCode + rest;
        if (test.size() <= 2) {
          EncodedInteger encodedInteger = new EncodedInteger(prevName, test);
          System.out.println(encodedInteger.toString());
        } else if (test.size() == 8) {
          BitEncoded bitEncoded = new BitEncoded(prevName, test);
          System.out.println(bitEncoded.toString());
        } else {
          for (String floats : test) {
            IEEE754 ieee754 = new IEEE754(floats);
            System.out.println(ieee754.toString());
          }
        }
        test.clear();
      } else {
        System.out.println("Nothing");
      }
    }
    //System.out.println(toLittleEndian("8666B740"));
  }


  // TODO Return String of Little Endian, move decode logic to data type
  public @NotNull
  Number toLittleEndian(final String hex) {
    boolean test = false;
    StringBuilder hexLittleEndian = new StringBuilder();
    if (hex.length() % 2 != 0) {
      return 0f;
    }
    for (int i = hex.length() - 2; i >= 0; i -= 2) {
      hexLittleEndian.append(hex, i, i + 2);
    }
    if (test) {
      return Float.intBitsToFloat((int) Long.parseLong(hexLittleEndian.toString(), 16));
    } else {
      return new BigInteger(hexLittleEndian.toString(), 16);
    }
  }
}
