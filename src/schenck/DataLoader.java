package schenck;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DataLoader {

  //TODO Need Map for SchenckDataType for final translation; not sure if here or own class
  DataLoader() {
    List<String> translationTable = Collections.emptyList();
    List<String> lines = Collections.emptyList();
    try {
      translationTable = Files
          .readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
      //lines = Files.readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String hexCode, rest, temp;
    HashMap<String, String> test = new HashMap<>();
    for (String translation : translationTable) {
      hexCode = translation.substring(0, 4);
      rest = translation.substring(4).strip();
      if (hexCode.matches("\\d[a-fA-F0-9]{3}")) {
        test.put(hexCode, rest);
      } else if (hexCode.matches("[CS][ot][ma].*")) {
        System.out.println(hexCode + rest);
      } else {
        System.out.println("Nothing");
      }
    }
    System.out.println(toLittleEndian("8666B740"));
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
