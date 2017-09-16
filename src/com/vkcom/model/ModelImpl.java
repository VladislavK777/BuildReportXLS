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

import com.vkcom.model.LocalClass.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModelImpl {

    private ClassFilter_120_122 classFilter_120_122 = new ClassFilter_120_122();
    private ClassFilter_138 classFilter_138 = new ClassFilter_138();
    private ClassFilter_150_161 classFilter_150_161 = new ClassFilter_150_161();

    // Путь к файлу Excel
    private File file;

    // Остнованя маппа с первоначальными данными
    private static Map<Integer, Object> map = new HashMap<>();

    // Размер таблицы. Вспомогательная переменная
    private List<Integer> tableSize = new ArrayList<>();

    // Массив заголовков дя формирования выходного файла(fix)
    private final String[] NAME_HEADERS = {"Станция текущей дислокации", "Признак 4", "Номер вагона", "Грузоподъемность, тн", "Тип вагона", "Собственник", "Клиент Текущее задание",
            "Грузоотправитель наименование организации", "Грузополучатель наименование организации", "Дата отправления", "Станция отправления",
            "Дорога отправления, наименование", "Дорога назначения", "Станция назначения", "Накладная №", "Груж\\Порож", "Код груза ЕТСНГ", "Груз",
            "Вес груза, тонны", "Расстояние всего (от станции отправления)", "Тариф ", "Итого начислено с НДС", "Итого начислено без НДС", "Признак 20"};

    // Тип вагона, для применения в фильтр
    private final String[] TYPE_WAGON = {"ГРУЖ", "ПОР"};

    private HSSFWorkbook workBook;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private Sheet sheet;

    private Calendar calendar = new GregorianCalendar();
    private String dateYesterday = dateYesterday(calendar);

    // Успешная выгрузка
    private boolean isOk = false;

    // Получение вчерашней даты
    private String dateYesterday(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return simpleDateFormat.format(calendar.getTime());
    }

    public void loaderFile(File file) {
        setFile(file);
        fillMap();
    }

    private void fillMap() {
        // Получаем файл формата xls
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
    }

    public List<Map<Object, Object>> parserXSLFile(String typeWagon) {
        List<Map<Object, Object>> listMaps = new ArrayList<>();

        listMaps.add(classFilter_120_122.applyFilters(map, typeWagon, dateYesterday));
        tableSize.add(classFilter_120_122.getTableSize());
        listMaps.add(classFilter_138.applyFilters(map, typeWagon, dateYesterday));
        tableSize.add(classFilter_138.getTableSize());
        listMaps.add(classFilter_150_161.applyFilters(map, typeWagon, dateYesterday));
        tableSize.add(classFilter_150_161.getTableSize());

        return listMaps;
    }

    public void writeToFileExcel() {
        for (int z = 0; z < TYPE_WAGON.length; z++) {

            List<Map<Object, Object>> map = parserXSLFile(TYPE_WAGON[z]);

            for (int a = 0; a < map.size(); a++) {
                HSSFWorkbook workBook = new HSSFWorkbook();
                Sheet sheet = workBook.createSheet(TYPE_WAGON[z]);

                int rownum = 0;
                Cell cell;
                Row row;

                row = sheet.createRow(rownum);

                for (int i = 0; i < this.NAME_HEADERS.length; i++) {
                    cell = row.createCell(i, CellType.STRING);
                    cell.setCellValue(this.NAME_HEADERS[i]);
                }

                for (int j = 0; j < this.tableSize.get(a); j++) {
                    rownum++;
                    row = sheet.createRow(rownum);
                    for (int i = 0; i < this.NAME_HEADERS.length; i++) {
                        for (Map.Entry<Object, Object> body : map.get(a).entrySet()) {
                            if (this.NAME_HEADERS[i].equals(String.valueOf(body.getKey()))) {
                                List<Object> temp = (List<Object>) body.getValue();
                                cell = row.createCell(i);
                                if (this.NAME_HEADERS[i].equals("Дата отправления")) {
                                    cell.setCellValue(dateNormal(String.valueOf(temp.get(j))));
                                } else {
                                    cell.setCellValue(String.valueOf(temp.get(j)));
                                }
                            }
                        }
                    }
                }

                //File file = new File("C:\\Users\\User93\\Desktop\\" + TYPE_WAGON[z] + ".xls");
                //File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\" + TYPE_WAGON[z] + a + ".xls");
                //File file = new File("/Users/vladislavklockov/Desktop/" + TYPE_WAGON[z] + a + ".xls");
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
    }

    // Вспомогательный метод возврата даты в нормальный вид
    private String dateNormal(String dateEng) {
        Date date = null;
        String dateNormal = null;
        try {
            date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(dateEng);
            dateNormal = new SimpleDateFormat("dd.MM.yyyy").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateNormal;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
