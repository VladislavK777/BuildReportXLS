package com.vkcom.controller;

/*
*
* Контроллер
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 14.09.17
*
*/

import com.vkcom.model.ModelImpl;
import com.vkcom.view.View;

import java.io.File;

public class Controller {
    private ModelImpl model;
    private View view;

    // Загружаемый файл
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Controller(ModelImpl model) {
        this.model = model;
        this.view = new View(this);
    }

    public void writeToExcel() {
        model.loaderFile(this.file);
        model.writeToFileExcel();
        if (model.isOk()) {
            view.labelOk.setText("Выгрузка завершена успешно.");
        } else {
            view.labelOk.setText("Ошибка выгрузки.");
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}
