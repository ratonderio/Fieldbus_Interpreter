package schenck;

import java.util.Scanner;

public class DataLoader {
  DataLoader(){
    Scanner scanner1 = new Scanner(System.in);
    String test = scanner1.nextLine();
    String[] test2 = test.split("\\s+");
    scanner1.close();

    SchenckDateTime currentTime = new SchenckDateTime(test2[0] + " " + test2[1] + " " + test2[2], test2[5] + test2[6]);
    System.out.println(currentTime.currentTime + currentTime.currentMillis);

    FBInput fbInput = new FBInput();
    System.out.println(fbInput.fieldbusInputs.get(test2[7]));

  }

}
