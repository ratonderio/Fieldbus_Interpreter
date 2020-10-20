package schenck;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DataLoader {

  HashMap<String, SchenckDataType> schenckDataTypeHashMap = new HashMap<>();

  //TODO HashMap created - determine if passed elsewhere (is dataloader even a good name at this point??)
  DataLoader() {
    List<String> translationTable = Collections.emptyList();
    try {
      translationTable = Files
          .readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Iterator<String> iterator = translationTable.iterator();

    String temp, currentName = null, prevName;
    List<String> test = new ArrayList<>();

    while (iterator.hasNext()) {
      temp = iterator.next();

      if (temp.substring(0, 4).matches("\\d[a-fA-F0-9]{3}")) {
        test.add(temp);
        if (!iterator.hasNext()) {
          BitEncoded finalBitEncoded = new BitEncoded(currentName, test);
          schenckDataTypeHashMap.put(finalBitEncoded.getValue(), finalBitEncoded);
        }
      } else if (temp.substring(0, 4).matches("[CS][ot][ma].*")) {
        prevName = currentName;
        currentName = temp;
        if (test.size() <= 2) {
          EncodedInteger encodedInteger = new EncodedInteger(prevName, test);
          schenckDataTypeHashMap.put(encodedInteger.getValue(), encodedInteger);
        } else if (test.size() == 8) {
          BitEncoded bitEncoded = new BitEncoded(prevName, test);
          schenckDataTypeHashMap.put(bitEncoded.getValue(), bitEncoded);
        } else {
          for (String floats : test) {
            IEEE754 ieee754 = new IEEE754(floats);
            schenckDataTypeHashMap.put(ieee754.getValue(), ieee754);
          }
        }
        test.clear();
      } else {
        System.out.println("Nothing");
      }
    }
    //System.out.println(schenckDataTypeHashMap.entrySet());
    //System.out.println(toLittleEndian("8666B740"));
  }


  // TODO This is floating here for a bit until it finds a final home
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
