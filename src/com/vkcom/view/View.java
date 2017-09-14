package com.vkcom.view;

/*
*
* Вьюха
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 14.09.17
*
*/

import com.vkcom.controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JPanel {
    private Controller controller;

    JFileChooser jFileChooser = new JFileChooser();
    JLabel label = new JLabel("Выбранный файл");
    public JLabel labelOk = new JLabel("");
    JButton button = new JButton("Открыть файл");
    JButton buttonSave = new JButton("Выгрузить отчеты");
    FileFilter filter = new FileNameExtensionFilter("XLS file", "xls");


    public View(Controller controller) {
        this.controller = controller;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label.setAlignmentX(CENTER_ALIGNMENT);
        button.setAlignmentX(CENTER_ALIGNMENT);
        buttonSave.setAlignmentX(CENTER_ALIGNMENT);
        labelOk.setAlignmentX(CENTER_ALIGNMENT);
        buttonSave.setEnabled(false);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFileChooser.setFileFilter(filter);
                jFileChooser.addChoosableFileFilter(filter);
                int ret = jFileChooser.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    controller.setFile(jFileChooser.getSelectedFile());
                    label.setText(jFileChooser.getSelectedFile().getName());
                    buttonSave.setEnabled(true);
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.writeToExcel();
            }
        });

        this.add(Box.createVerticalGlue());
        this.add(label);
        this.add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(button);
        this.add(buttonSave);
        this.add(labelOk);
        this.add(Box.createVerticalGlue());
    }

}
