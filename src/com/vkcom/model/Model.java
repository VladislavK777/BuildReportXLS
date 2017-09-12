package com.vkcom.model;

/*
*
* Интерфес модели
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 12.09.17
*
*/

public interface Model {

    // Метод загрузки файла из формы
    public void loaderFile();

    // Метод парсинга файла Excel
    public void parserXSLFile();

    // Метод зааписи в файлы
    public void writeToFileExcel();
}
