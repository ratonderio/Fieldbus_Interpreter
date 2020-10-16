package schenck;

import java.util.List;

//TODO 32 Bits Encoded
public class BitEncoded implements SchenckDataType {

  String name;
  List<String> bitEncodedList;

  BitEncoded(String name, List<String> bitEncodedList) {
    this.name = name;
    this.bitEncodedList = bitEncodedList;
  }

  @Override
  public String toString() {
    return "BitEncoded{" +
        "name='" + name + '\'' +
        ", bitEncodedList=" + bitEncodedList +
        '}';
  }
}
