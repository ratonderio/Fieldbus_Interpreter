package schenck;

import java.util.HashMap;

public class FBOutput {

  HashMap<String,String> fieldbusOutputs;
  FBOutput(){
    fieldbusOutputs = new HashMap<>();
    fieldbusOutputs.put("(0140)","Command 04+05");
    fieldbusOutputs.put("(0160)","Command 06+07");
    fieldbusOutputs.put("(0180)","Command 08+09");
    fieldbusOutputs.put("(0250)","Serial Setpoint");
    fieldbusOutputs.put("(0252)","Serial Batch Setpoint");
    fieldbusOutputs.put("(0100)","Command 00+01");
    fieldbusOutputs.put("(----)","-------------");
  }

}
