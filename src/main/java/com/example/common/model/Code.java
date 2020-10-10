package com.example.common.model;

import java.io.Serializable;

import lombok.Data;




@Data
public class Code implements Serializable {
   /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int codeSeq;

    private String codeId;

    private String codeName;

    private String codeType;

    private String codeTypeName;

}
