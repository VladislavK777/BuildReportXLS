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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModelImpl implements Model {

    // Путь к файлу Excel
    private File file;

    // Остнованя маппа с первоначальными данными
    private Map<Integer, Object> map = new HashMap<>();

    // Размер таблицы. Вспомогательная переменная
    private int tableSize;

    // Массив заголовков дя формирования выходного файла(fix)
    private final String[] NAME_HEADERS = {"Номер вагона", "Собственник", "Клиент Текущее задание",
            "Грузоотправитель наименование организации", "Грузополучатель наименование организации",
            "Дата и время отправления", "Станция отправления", "Дорога отправления, наименование",
            "Дорога назначения", "Станция назначения", "Накладная №", "Груж\\Порож", "Код груза ЕТСНГ",
            "Груз", "Вес груза, тонны", "Расстояние всего (от станции отправления)", "Итого начислено без НДС"};

    // Тип вагона, для применения в фильтр
    private final String[] TYPE_WAGON = {"ГРУЖ", "ПОР"};

    private HSSFWorkbook workBook;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private Sheet sheet;
    private Calendar calendar = new GregorianCalendar();
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private String dateYesterday = dateYesterday(calendar);

    // Успешная выгрузка
    private boolean isOk = false;

    // Получение вчерашней даты
    private String dateYesterday(Calendar calendar) {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String dateYesterday = format.format(calendar.getTime());
        return dateYesterday;
    }

    @Override
    public void loaderFile(File file) {
        setFile(file);
    }

    @Override
    public Map<Object, Object> parserXSLFile(String typeWagon) {
        // Получаем файл формата xlsx
        try {
            fileInputStream = new FileInputStream(this.file);
            workBook = new HSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            // Добавить логгер
            System.out.println("Ошибка загруки файла");
        }

        // Заполняем маппу данными
        sheet = workBook.getSheetAt(0);
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

        // Применяем фильры
        Map<Object, Object> totalMap = new HashMap<>();
        List<Object> header = (List<Object>) map.get(0);
        for (int j = 0; j < header.size(); j++) {
            List<Object> tempBody = new ArrayList<>();
            for (Map.Entry<Integer, Object> body : applyFilters(typeWagon, this.dateYesterday).entrySet()) {
                List<Object> temp = (List<Object>) body.getValue();
                tempBody.add(temp.get(j));
            }
            this.tableSize = tempBody.size();
            totalMap.put(header.get(j), tempBody);
        }
        return totalMap;
    }

    // метод применения фильров
    private Map<Integer, Object> applyFilters(String typeWagon, String dateYesterday) {
        Map<Integer, Object> tempMap = new HashMap<>();
        int f = 0;
        for (Map.Entry<Integer, Object> m : map.entrySet()) {
            List<Object> l = (List<Object>) m.getValue();
            for (int j = 0; j < l.size(); j++) {
                if (String.valueOf(l.get(j)).equals("КР")) {
                    List<Object> l1 = (List<Object>) m.getValue();
                    for (int k = 0; k < l1.size(); k++) {
                        if (String.valueOf(l1.get(k)).equals("120.0") ||
                                String.valueOf(l1.get(k)).equals("121.0") ||
                                String.valueOf(l1.get(k)).equals("122.0")) {
                            List<Object> l2 = (List<Object>) m.getValue();
                            for (int w = 0; w < l2.size(); w++) {
                                if (String.valueOf(l2.get(w)).equals(typeWagon)) {
                                    List<Object> l3 = (List<Object>) m.getValue();
                                    for (int z = 0; z < l3.size(); z++) {
                                        if (String.valueOf(l3.get(z)).contains(dateYesterday)) {
                                            List<Object> l4 = (List<Object>) m.getValue();
                                            for (int q = 0; q < l4.size(); q++) {
                                                if (String.valueOf(l4.get(q)).equals("Альфа Транс Логистик, ООО") ||
                                                        String.valueOf(l4.get(q)).equals("А-Система Транс ООО") ||
                                                        String.valueOf(l4.get(q)).equals("ВАГОНЫ НА СЛЕЖЕНИИ ДЛЯ ИНФОРМАЦИИ") ||
                                                        String.valueOf(l4.get(q)).equals("ВЕСТКОМТРАНС ООО") ||
                                                        String.valueOf(l4.get(q)).equals("Дженерал Лизинг") ||
                                                        String.valueOf(l4.get(q)).equals("ИнтерТрансКарго ООО ") ||
                                                        String.valueOf(l4.get(q)).equals("РЕАЛГРУПП") ||
                                                        String.valueOf(l4.get(q)).equals("РТХ-Логистик") ||
                                                        String.valueOf(l4.get(q)).equals("ТД АМК ООО") ||
                                                        String.valueOf(l4.get(q)).equals("ФИРМА ТРАНСГАРАНТ ООО") ||
                                                        String.valueOf(l4.get(q)).equals("УРАЛЬСКАЯ ТРАНСПОРТНАЯ КОМПАНИЯ")) {
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
                    }
                }
            }
        }
        return tempMap;
    }

    @Override
    public void writeToFileExcel() {
        for (int z = 0; z < TYPE_WAGON.length; z++) {
            HSSFWorkbook workBook = new HSSFWorkbook();
            Sheet sheet = workBook.createSheet("sheet_" + TYPE_WAGON[z]);
            Map<Object, Object> map = parserXSLFile(TYPE_WAGON[z]);

            int rownum = 0;
            Cell cell;
            Row row;

            row = sheet.createRow(rownum);

            for (int i = 0; i < this.NAME_HEADERS.length; i++) {
                cell = row.createCell(i, CellType.STRING);
                cell.setCellValue(this.NAME_HEADERS[i]);
            }

            for (int j = 0; j < this.tableSize; j++) {
                rownum++;
                row = sheet.createRow(rownum);
                for (int i = 0; i < this.NAME_HEADERS.length; i++) {
                    for (Map.Entry<Object, Object> body : map.entrySet()) {
                        if (this.NAME_HEADERS[i].equals(String.valueOf(body.getKey()))) {
                            List<Object> temp = (List<Object>) body.getValue();
                            cell = row.createCell(i);
                            cell.setCellValue(String.valueOf(temp.get(j)));
                        }
                    }
                }
            }

            //File file = new File("C:\\Users\\User93\\Desktop\\" + TYPE_WAGON[z] + ".xls");
            File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\" + TYPE_WAGON[z] + ".xls");
            file.getParentFile().mkdirs();


            try {
                fileOutputStream = new FileOutputStream(file);
                workBook.write(fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                isOk = true;
            } catch (IOException e) {
                System.out.println("Ошибка записи в файлы!");
                isOk = false;
            }
        }
    }

    @Override
    public void exportToFileExcel() {

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
