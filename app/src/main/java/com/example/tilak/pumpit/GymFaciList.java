package com.example.tilak.pumpit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GymFaciList {
    public static HashMap<String, List<String>> getInfo(){
        HashMap<String, List<String>> gymFaci = new HashMap<String, List<String>>();

        List<String> CardioVascular = new ArrayList<>();
        CardioVascular.add("TREADMILLS");
        CardioVascular.add("ELLIPTICAL TRAINERS");
        CardioVascular.add("CROSS TRAINERS");
        CardioVascular.add("STAIR CLIMBERS");
        CardioVascular.add("ROWERS");
        CardioVascular.add("UPRIGHT BIKES");
        CardioVascular.add("RECUMBENT BIKES");
        CardioVascular.add("CARDIO ENTERTAINMENT");

        List<String> Strength = new ArrayList<>();
        Strength.add("CIRCUIT MACHINES");
        Strength.add("SELECTORIZED MACHINES");
        Strength.add("ISO-LATERAL MACHINES");
        Strength.add("PLATE LOAD MACHINES");
        Strength.add("FUNCTIONAL TRAINING MACHINES");
        Strength.add("FREE WEIGHTS");

        List<String> GroupActivities = new ArrayList<>();
        GroupActivities.add("YOGA");
        GroupActivities.add("ZUMBA");
        GroupActivities.add("AEROBICS");
        GroupActivities.add("PILATES");
        GroupActivities.add("HOOP FITNESS");

        List<String> AddAmenities = new ArrayList<>();
        AddAmenities.add("SMALL GROUP TRAINING");
        AddAmenities.add("PERSONAL TRAINING");
        AddAmenities.add("SPECIALIZED TRAINING");
        AddAmenities.add("LOCKERS ROOMS");
        AddAmenities.add("SHOWERS");
        AddAmenities.add("SAUNA");

        gymFaci.put("CardioVascular", CardioVascular);
        gymFaci.put("Strength", Strength);
        gymFaci.put("Group Activities", GroupActivities);
        gymFaci.put("Additional Amenities", AddAmenities);

        return gymFaci;

    }
}
