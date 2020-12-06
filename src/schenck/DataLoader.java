package schenck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataLoader {

  private HashMap<String, SchenckDataType> schenckDataTypeHashMap = new HashMap<>();

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

    BitEncoded bitEncodedTopLevel = null;
    EncodedInteger intEncodedTopLevel = null;
    int bitEncodedByte = 0, intEncodedByte = 0;

    while (iterator.hasNext()) {
      temp = iterator.next();

      if (temp.substring(0, 4).matches("\\d[a-fA-F0-9]{3}")) {
        test.add(temp);
        if (!iterator.hasNext()) {
          BitEncoded finalBitEncoded = new BitEncoded(currentName, test);
          assert bitEncodedTopLevel != null;
          bitEncodedTopLevel.getByteList().add(finalBitEncoded);
          schenckDataTypeHashMap.put(bitEncodedTopLevel.getValue(), bitEncodedTopLevel);
          schenckDataTypeHashMap.put(finalBitEncoded.getValue(), finalBitEncoded);
        }
      } else if (temp.substring(0, 4).matches("[CS][ot][ma].*")) {
        prevName = currentName;
        currentName = temp;
        if (test.size() <= 2) {
          EncodedInteger encodedInteger = new EncodedInteger(prevName, test);
          switch (intEncodedByte) {
            case 0:
              intEncodedTopLevel = encodedInteger;
              intEncodedByte++;
              break;
            case 1:
              intEncodedTopLevel.getByteList().add(encodedInteger);
              schenckDataTypeHashMap.put(encodedInteger.getValue(), encodedInteger);
              schenckDataTypeHashMap.put(intEncodedTopLevel.getValue(), intEncodedTopLevel);
              intEncodedByte = 0;
              break;
            default:
              break;
          }
        } else if (test.size() == 8) {
          BitEncoded bitEncoded = new BitEncoded(prevName, test);

          if (bitEncodedByte == 0) {
            bitEncodedTopLevel = bitEncoded;
            bitEncodedByte++;
            test.clear();
            continue;
          } else if (bitEncodedByte < 3 && bitEncodedByte > 0) {
            bitEncodedTopLevel.getByteList().add(bitEncoded);
            bitEncodedByte++;
          } else if (bitEncodedByte == 3) {
            bitEncodedTopLevel.getByteList().add(bitEncoded);
            bitEncodedByte = 0;
            schenckDataTypeHashMap.put(bitEncodedTopLevel.getValue(), bitEncodedTopLevel);
          }

          schenckDataTypeHashMap.put(bitEncoded.getValue(), bitEncoded);
        } else {
          for (String floats : test) {
            IEEE754 ieee754 = new IEEE754(floats);
            schenckDataTypeHashMap.put(ieee754.getValue(), ieee754);
          }
        }
        test.clear();
      }
    }
    schenckDataTypeHashMap.put("----", new IEEE754("---- Not Used"));
    //System.out.println(schenckDataTypeHashMap.entrySet());
    //System.out.println(toLittleEndian("8666B740"));
  }

  public HashMap<String, SchenckDataType> getSchenckDataTypeHashMap() {
    return schenckDataTypeHashMap;
  }
}
