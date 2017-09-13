package com.vkcom.application;

import com.vkcom.model.ModelImpl;

import java.io.IOException;

/*
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 12.09.17
*
*/public class Gotoapp {
    public static void main(String[] args) throws IOException {
        ModelImpl model = new ModelImpl();
        model.writeToFileExcel();
    }
}
