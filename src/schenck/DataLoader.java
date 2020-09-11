package schenck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataLoader {
  DataLoader() {
    List<String> translate = Collections.emptyList();
    List<String> lines = Collections.emptyList();
    try{
      translate = Files.readAllLines(Path.of("C:\\Users\\J.Purdon\\Documents\\Documents\\Engineering\\PROGRAMMING\\DATA\\translate.txt"));
      lines = Files.readAllLines(Path.of("C:\\Users\\J.Purdon\\Desktop\\fieldbus test.txt"), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Scanner scanner;
    HashMap<String, String> test = new HashMap<>();
    for (String translation : translate) {
      scanner = new Scanner(translation);
      if(scanner.hasNext("[A-fa-f0-9]+")){
        String intermediate = scanner.next();
      } else {
        System.out.println(scanner.next());
      }
      test.put(scanner.next(), scanner.nextLine());
      System.out.println(translation);
    }

    System.out.println(test.get("0100"));


/*
    SchenckDateTime currentTime = new SchenckDateTime(test2[0] + " " + test2[1] + " " + test2[2], test2[5] + test2[6]);
    System.out.println(currentTime.currentTime + currentTime.currentMillis);

    FBInput fbInput = new FBInput();
    System.out.println(fbInput.fieldbusInputs.get(test2[7]));*/
  }
}
