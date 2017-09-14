package com.vkcom.application;

import com.vkcom.controller.Controller;
import com.vkcom.model.ModelImpl;

import javax.swing.*;
import java.awt.*;
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
        Controller controller =new Controller(model);
        JFrame frame = new JFrame("BuildReport");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(260, 220));
        frame.setResizable(false);
        frame.setContentPane(controller.getView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
