package com.uothackk.app.watson;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WatsonTone {

    private double score;
    private String toneName;

}
