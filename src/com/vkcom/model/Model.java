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

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Model {

    // Метод загрузки файла из формы
    public void loaderFile(File file);

    // Метод парсинга файла Excel
    public Map<Object, Object> parserXSLFile(String typeWagon);

    // Метод зааписи в файлы
    public void writeToFileExcel() throws IOException;

    // Экспорт в файлы
    public void exportToFileExcel();

}
