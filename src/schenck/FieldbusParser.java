package schenck;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FieldbusParser {

  final static String[] defaultCommandsVBW = {"(0140)", "(0160)", "(0180)", "(0260)", "(0252)",
      "(0262)", "(----)", "(----)"};
  final static String[] defaultCommandsVDB = {"(0140)", "(0160)", "(0180)", "(0250)", "(0252)",
      "(0262)", "(----)", "(----)"};
  final static String[] defaultCommandsVDD = {"(0140)", "(0160)", "(0180)", "(0250)", "(0252)",
      "(0262)", "(----)", "(----)"};
  final static String[] defaultCommandsVKD = {"(0140)", "(0160)", "(0180)", "(0250)", "(0252)",
      "(0100)", "(----)", "(----)"};
  final static String[] defaultCommandsVMD = {"(0140)", "(0160)", "(0180)", "(0250)", "(0252)",
      "(0262)", "(----)", "(----)"};

  final static String[] defaultInputsVBW = {"(02F0)", "(0310)", "(0610)", "(0750)", "(0752)", "(0758)",
      "(075C)", "(0762)", "(0330)", "(0754)", "(0768)", "(076A)", "(0772)", "(0774)",
      "(0776)", "(0778)"};
  final static String[] defaultInputsVDB = {"(02F0)", "(0310)", "(0610)", "(0750)", "(0752)", "(0758)",
      "(0766)", "(076A)", "(0330)", "(0754)", "(075C)", "(07A8)", "(0768)", "(076A)",
      "(076E)", "(0770)"};
  final static String[] defaultInputsVDD = {"(02F0)", "(0310)", "(0610)", "(0750)", "(0752)", "(075A)",
      "(075C)", "(0766)", "(0330)", "(0754)", "(0772)", "(07A8)", "(0768)", "(076A)",
      "(076E)", "(0770)"};
  final static String[] defaultInputsVKD = {"(02F0)", "(0310)", "(0610)", "(0750)", "(0752)", "(0760)",
      "(0762)", "(0766)", "(0330)", "(0754)", "(075E)", "(07A8)", "(0768)", "(076A)",
      "(0650)", "(0770)"};
  final static String[] defaultInputsVMD = {"(02F0)", "(0310)", "(0610)", "(0750)", "(0752)", "(075A)",
      "(075C)", "(0766)", "(0330)", "(0754)", "(075E)", "(07A8)", "(0768)", "(076A)",
      "(076E)", "(0770)"};


  public static FieldbusObject parseDisocontVSE(String scanline, DataLoader dataLoader,String softwareType) {
    String parsedScanline;
    if (scanline.contains("==>")) {
      parsedScanline = convertObjectString(scanline, true,softwareType);
      return parseDisocontVCU(parsedScanline, dataLoader);
    } else if (scanline.contains("<==")) {
      parsedScanline = convertObjectString(scanline, false,softwareType);
      return parseDisocontVCU(parsedScanline, dataLoader);
    } else {
      return null;
    }
  }

  public static FieldbusObject parseDisocontVCU(String scanline, DataLoader dataLoader) {
    if (scanline.contains("{")) {
      FieldbusInput input = new FieldbusInput(scanline, dataLoader);
      FieldbusDataPanel fieldbusDataPanel = new FieldbusDataPanel(input.getFieldbusData(),
          true);
      input.setDataPanel(fieldbusDataPanel);
      return input;
    } else if (scanline.contains("}")) {
      FieldbusOutput output = new FieldbusOutput(scanline, dataLoader);
      FieldbusDataPanel fieldbusDataPanel = new FieldbusDataPanel(output.getFieldbusData(),
          false);
      output.setDataPanel(fieldbusDataPanel);
      return output;
    } else {
      return null;
    }
  }


  private static String convertObjectString(String scanline, boolean isCommand, String softwareType) {
    StringBuilder convertedScanline = new StringBuilder();
    String dataSubstring;
    String[] defaultSoftwareOutput, defaultSoftwareInput;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String currentTime = dateTimeFormatter.format(now);

    convertedScanline.append(currentTime);
    if (isCommand) {
      convertedScanline.append("   }  H  ").append(now.getNano() / 10000)
          .append(" ms  ");
    } else {
      convertedScanline.append("   {  H  ").append(now.getNano() / 10000)
          .append(" ms  ");
    }

    if (scanline.contains("SEC")) {
      dataSubstring = scanline.substring(scanline.indexOf("SEC") + 3).strip();
    } else {
      if (isCommand) {
        dataSubstring = scanline.substring(scanline.indexOf("==>") + 3).strip();
      } else {
        dataSubstring = scanline.substring(scanline.indexOf("<==") + 3).strip();
      }
    }
    dataSubstring = dataSubstring.substring(0, 63);

    List<String> dataStrings = new ArrayList<>();
    int index = 0;
    while (index < dataSubstring.length()) {
      dataStrings.add(dataSubstring.substring(index, Math.min(index + 8, dataSubstring.length())));
      index += 8;
    }

    switch (softwareType) {
      case "VBW" -> {
        defaultSoftwareInput = defaultInputsVBW;
        defaultSoftwareOutput = defaultCommandsVBW;
      }
      case "VDB" -> {
        defaultSoftwareInput = defaultInputsVDB;
        defaultSoftwareOutput = defaultCommandsVDB;
      }
      case "VDD" -> {
        defaultSoftwareInput = defaultInputsVDD;
        defaultSoftwareOutput = defaultCommandsVDD;
      }
      case "VMD" -> {
        defaultSoftwareInput = defaultInputsVMD;
        defaultSoftwareOutput = defaultCommandsVMD;
      }
      default -> {
        defaultSoftwareInput = defaultInputsVKD;
        defaultSoftwareOutput = defaultCommandsVKD;
      }
    }


    for (int i = 0; i <= 7; i++) {
      if (isCommand) {
        convertedScanline.append(defaultSoftwareOutput[i]).append(" ");
      } else {
        convertedScanline.append(defaultSoftwareInput[i]).append(" ");
      }
      convertedScanline.append(dataStrings.get(i)).append(" ");
    }
    return convertedScanline.toString();
  }
}



