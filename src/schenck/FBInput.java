package schenck;

import java.util.HashMap;

public class FBInput {
  HashMap<String,String> fieldbusInputs;
  FBInput(){
    fieldbusInputs = new HashMap<>();
    fieldbusInputs.put("(02F0)","Status 2+3");
    fieldbusInputs.put("(0310)","Status 4+5");
    fieldbusInputs.put("(0610)","Status 52+53");
    fieldbusInputs.put("(0750)","Feedrate");
    fieldbusInputs.put("(0752)","Totalizer 1");
    fieldbusInputs.put("(0760)","Fill Weight");
    fieldbusInputs.put("(0762)","BIN: Fill Weight");
    fieldbusInputs.put("(0766)","Setpoint");
    fieldbusInputs.put("(0330)","Status 6+7");
    fieldbusInputs.put("(0754)","Totalizer 2");
    fieldbusInputs.put("(075E)","Speed");
    fieldbusInputs.put("(07A8)","Controller Magnitude Y");
    fieldbusInputs.put("(0768)","Batch Actual Value");
    fieldbusInputs.put("(076A)","Batch Resid. Amount");
    fieldbusInputs.put("(0650)","Status 56+57");
    fieldbusInputs.put("(0770)","Deviation");
  }

}
