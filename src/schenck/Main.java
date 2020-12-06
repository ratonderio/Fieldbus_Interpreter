package schenck;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {

  public static void main(String[] args) {
    //System.out.println(dataLoader.schenckDataTypeHashMap.get("0140"));
    FlatDarculaLaf.install();
    new GUI();
    //new FieldbusOutput("poop");
  }
}

