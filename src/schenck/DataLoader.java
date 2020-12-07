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
    List<String> tempList = new ArrayList<>();

    BitEncoded bitEncodedTopLevel = null;
    EncodedInteger intEncodedTopLevel = null;
    int bitEncodedByte = 0, intEncodedByte = 0;

    while (iterator.hasNext()) {
      temp = iterator.next();

      if (temp.substring(0, 4).matches("\\d[a-fA-F0-9]{3}")) {
        tempList.add(temp);
        if (!iterator.hasNext()) {
          BitEncoded finalBitEncoded = new BitEncoded(currentName, tempList);
          assert bitEncodedTopLevel != null;
          bitEncodedTopLevel.getByteList().add(finalBitEncoded);
          schenckDataTypeHashMap.put(bitEncodedTopLevel.getValue(), bitEncodedTopLevel);
          schenckDataTypeHashMap.put(finalBitEncoded.getValue(), finalBitEncoded);
        }
      } else if (temp.substring(0, 4).matches("[CS][ot][ma].*")) {
        prevName = currentName;
        currentName = temp;
        if (tempList.size() <= 2) {
          EncodedInteger encodedInteger = new EncodedInteger(prevName, tempList);
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
        } else if (tempList.size() == 8) {
          BitEncoded bitEncoded = new BitEncoded(prevName, tempList);

          if (bitEncodedByte == 0) {
            bitEncodedTopLevel = bitEncoded;
            bitEncodedByte++;
            tempList.clear();
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
          for (String floats : tempList) {
            IEEE754 ieee754 = new IEEE754(floats);
            schenckDataTypeHashMap.put(ieee754.getValue(), ieee754);
          }
        }
        tempList.clear();
      }
    }
    schenckDataTypeHashMap.put("----", new IEEE754("---- Not Used"));
  }

  public HashMap<String, SchenckDataType> getSchenckDataTypeHashMap() {
    return schenckDataTypeHashMap;
  }
}
