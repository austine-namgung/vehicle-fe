package com.example.vehiclefe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class Code {

    private int codeSeq;

    private String codeId;

    private String codeName;

    private String codeType;

    private String codeTypeName;

}
