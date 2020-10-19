package schenck;

//TODO Liberal with the getters to begin with;remove extraneous when done
public class IEEE754 implements SchenckDataType {

  private final String name;
  private final String value;

  IEEE754(String collatedName) {
    value = collatedName.substring(0, 4);
    name = collatedName.substring(4).strip();
  }

  @Override
  public String toString() {
    return "IEEE754{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}
