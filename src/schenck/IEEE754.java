package schenck;

//TODO Floats
public class IEEE754 implements SchenckDataType{
  String name,translatedName;

  IEEE754(String collatedName){
    name = collatedName.substring(0,4);
    translatedName = collatedName.substring(5).strip();
  }

  @Override
  public String toString() {
    return "IEEE754{" +
        "name='" + name + '\'' +
        ", translatedName='" + translatedName + '\'' +
        '}';
  }
}
