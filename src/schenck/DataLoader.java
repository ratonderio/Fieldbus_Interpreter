package schenck;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DataLoader {

  private HashMap<String, SchenckDataType> schenckDataTypeHashMap = new HashMap<>();

  DataLoader() {
    List<String> translationTable = new ArrayList<>(Collections.emptyList());
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream("translate.txt");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
          Objects.requireNonNull(inputStream)));
      while (bufferedReader.ready()) {
        translationTable.add(bufferedReader.readLine());
      }
    } catch (Exception e) {
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
        currentName = temp.strip();
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
              intEncodedTopLevel.setName(intEncodedTopLevel.getName() + "/" + encodedInteger.getName());
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
            String fullName = bitEncodedTopLevel.getName().substring(0, bitEncodedTopLevel.getName().length()-3);
            fullName = fullName.concat("/" + bitEncoded.getName().substring(0,bitEncoded.getName().length()-3));
            bitEncodedTopLevel.setName(fullName);
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
