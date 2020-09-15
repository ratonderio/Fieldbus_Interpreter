package schenck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DataLoader {

  DataLoader() {
    List<String> translate = Collections.emptyList();
    List<String> lines = Collections.emptyList();
    try {
      translate = Files
          .readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
      //lines = Files.readAllLines(Path.of("src/resources/translate.txt"), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String hexCode, rest, temp;
    HashMap<String, String> test = new HashMap<>();
    for (String translation : translate) {
      hexCode = translation.substring(0, 4);
      rest = translation.substring(4).strip();
      if (hexCode.matches("\\d[a-f0-9]{3}")) {
        test.put(hexCode, rest);
      } else {
        System.out.println(hexCode + rest);
      }
    }
    System.out.println(toLittleEndian("8666B740"));

  }

  public static @NotNull
  Float toLittleEndian(final String hex) {
    long ret;
    String hexLittleEndian = "";
    if (hex.length() % 2 != 0) {
      return 0f;
    }
    for (int i = hex.length() - 2; i >= 0; i -= 2) {
      hexLittleEndian += hex.substring(i, i + 2);
    }
    ret = Long.parseLong(hexLittleEndian, 16);
    return Float.intBitsToFloat((int) ret);
  }

}
