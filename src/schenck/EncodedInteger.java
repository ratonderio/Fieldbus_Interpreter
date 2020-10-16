package schenck;

import java.util.List;

//TODO Special case for Encoded Integer Type for Command00/01, 02/03, 11 LO, Status 00/01, 54/55, 56/57, may be more
public class EncodedInteger implements SchenckDataType {

  String name;
  List<String> encodedIntegerList;

  EncodedInteger(String name, List<String> encodedIntegerList) {
    this.name = name;
    this.encodedIntegerList = encodedIntegerList;
  }

  @Override
  public String toString() {
    return "EncodedInteger{" +
        "name='" + name + '\'' +
        ", encodedIntegerList=" + encodedIntegerList +
        '}';
  }
}
