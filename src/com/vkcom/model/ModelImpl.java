package com.vkcom.model;

/*
*
* Имплементер Модели
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 12.09.17
*
*/

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ModelImpl implements Model {

    // Путь к файлу Excel
    private String path = "C:\\Users\\Vladislav.Klochkov\\Desktop\\tmp66E8.tmp.xls";

    private Map<Integer, Object> map = new HashMap<>();

    private int tableSize;


    @Override
    public void loaderFile(String path) {
        setPath(path);
    }

    @Override
    public Map<Object, Object> parserXSLFile() {
        HSSFWorkbook workBook = null;
        // Получаем файл формата xlsx
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(this.path));
            workBook = new HSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            // Добавить логгер
            System.out.println("Ошибка загруки файла");
        }
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        int i = 0;
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            List<Object> tempList = new ArrayList<>();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                tempList.add(cell);
            }
            map.put(i, tempList);
            i++;
        }

        Map<Integer, Object> tempMap = new HashMap<>();
        int f = 0;
        for (Map.Entry<Integer, Object> m : map.entrySet()) {
            List<Object> l = (List<Object>) m.getValue();
            for (int j = 0; j < l.size(); j++) {
                if (String.valueOf(l.get(j)).equals("122.0") ||
                        String.valueOf(l.get(j)).equals("120.0")) {
                    List<Object> l1 = (List<Object>) m.getValue();
                    for (int k = 0; k < l1.size(); k++) {
                        if (String.valueOf(l1.get(k)).equals("ГРУЖ")) {
                            List<Object> l2 = (List<Object>) m.getValue();
                            for (int q = 0; q < l2.size(); q++) {
                                if (String.valueOf(l2.get(q)).equals("Альфа Транс Логистик, ООО") ||
                                        String.valueOf(l2.get(q)).equals("ВАГОНЫ НА СЛЕЖЕНИИ ДЛЯ ИНФОРМАЦИИ") ||
                                        String.valueOf(l2.get(q)).equals("ВЕСТКОМТРАНС ООО") ||
                                        String.valueOf(l2.get(q)).equals("Дженерал Лизинг") ||
                                        String.valueOf(l2.get(q)).equals("ИнтерТрансКарго ООО ") ||
                                        String.valueOf(l2.get(q)).equals("РЕАЛГРУПП") ||
                                        String.valueOf(l2.get(q)).equals("ТД АМК ООО") ||
                                        String.valueOf(l2.get(q)).equals("ФИРМА ТРАНСГАРАНТ ООО") ||
                                        String.valueOf(l2.get(q)).equals("УРАЛЬСКАЯ ТРАНСПОРТНАЯ КОМПАНИЯ")) {
                                    tempMap.put(f, m.getValue());
                                    f++;
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        }

        Map<Object, Object> totalMap = new HashMap<>();
        List<Object> header = (List<Object>) map.get(0);
        for (int j = 0; j < header.size(); j++) {
            List<Object> tempBody = new ArrayList<>();
            for (Map.Entry<Integer, Object> body : tempMap.entrySet()) {
                List<Object> temp = (List<Object>) body.getValue();
                tempBody.add(temp.get(j));
            }
            this.tableSize = tempBody.size();
            totalMap.put(header.get(j), tempBody);
        }

        return totalMap;
    }

    @Override
    public void writeToFileExcel() throws IOException {
        String[] nameHeader = {"Номер вагона", "Собственник", "Клиент Текущее задание", "Грузоотправитель наименование организации", "Грузополучатель наименование организации", "Дата и время отправления", "Станция отправления", "Дорога отправления, наименование", "Дорога назначения", "Станция назначения", "Накладная №", "Груж\\Порож", "Код груза ЕТСНГ", "Груз", "Вес груза, тонны", "Расстояние всего (от станции отправления)", "Итого начислено без НДС"};
        HSSFWorkbook workBook = new HSSFWorkbook();
        Sheet sheet = workBook.createSheet("sheet");

        Map<Object, Object> map = parserXSLFile();

        int rownum = 0;
        Cell cell;
        Row row;

        row = sheet.createRow(rownum);

        for (int i = 0; i < nameHeader.length; i++) {
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(nameHeader[i]);
        }

        for (int j = 0; j < this.tableSize; j++) {
            rownum++;
            row = sheet.createRow(rownum);
            for (int i = 0; i < nameHeader.length; i++) {
                for (Map.Entry<Object, Object> body : map.entrySet()) {
                    if (nameHeader[i].equals(String.valueOf(body.getKey()))) {
                        List<Object> temp = (List<Object>) body.getValue();
                        cell = row.createCell(i);
                        cell.setCellValue(String.valueOf(temp.get(j)));
                    }
                }
            }
        }



        File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\111.xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workBook.write(outFile);

    }

    @Override
    public void exportToFileExcel() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }
}
