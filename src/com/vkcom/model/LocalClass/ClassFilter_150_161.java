package com.vkcom.model.LocalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*
* Вспомогательный класс для фильтра 150-161
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 18.09.17
*
*/

public class ClassFilter_150_161 {
    private int tableSize;

    // метод применения фильров
    private Map<Integer, Object> filters(Map<Integer, Object> map, String typeWagon, String dateYesterday) {
        Map<Integer, Object> tempMap = new HashMap<>();
        int f = 0;
        for (Map.Entry<Integer, Object> m : map.entrySet()) {
            List<Object> l = (List<Object>) m.getValue();
            if (String.valueOf(l.get(6)).equals("КР")) {
                List<Object> l1 = (List<Object>) m.getValue();
                if (String.valueOf(l1.get(3)).equals("150.0") ||
                        String.valueOf(l1.get(3)).equals("151.0") ||
                        String.valueOf(l1.get(3)).equals("152.0") ||
                        String.valueOf(l1.get(3)).equals("153.0") ||
                        String.valueOf(l1.get(3)).equals("154.0") ||
                        String.valueOf(l1.get(3)).equals("155.0") ||
                        String.valueOf(l1.get(3)).equals("156.0") ||
                        String.valueOf(l1.get(3)).equals("157.0") ||
                        String.valueOf(l1.get(3)).equals("158.0") ||
                        String.valueOf(l1.get(3)).equals("159.0") ||
                        String.valueOf(l1.get(3)).equals("160.0") ||
                        String.valueOf(l1.get(3)).equals("161.0")) {
                    List<Object> l2 = (List<Object>) m.getValue();
                    if (String.valueOf(l2.get(17)).equals(typeWagon)) {
                        List<Object> l3 = (List<Object>) m.getValue();
                        if (String.valueOf(l3.get(11)).contains(dateYesterday)) {
                            List<Object> l4 = (List<Object>) m.getValue();
                            if (String.valueOf(l4.get(7)).equals("Альфа Транс Логистик, ООО") ||
                                    String.valueOf(l4.get(7)).equals("А-Система Транс ООО") ||
                                    String.valueOf(l4.get(7)).equals("ВАГОНЫ НА СЛЕЖЕНИИ ДЛЯ ИНФОРМАЦИИ") ||
                                    String.valueOf(l4.get(7)).equals("ВЕСТКОМТРАНС ООО") ||
                                    String.valueOf(l4.get(7)).equals("Дженерал Лизинг") ||
                                    String.valueOf(l4.get(7)).equals("ИнтерТрансКарго ООО ") ||
                                    String.valueOf(l4.get(7)).equals("РЕАЛГРУПП") ||
                                    String.valueOf(l4.get(7)).equals("РТХ-Логистик") ||
                                    String.valueOf(l4.get(7)).equals("ТД АМК ООО") ||
                                    String.valueOf(l4.get(7)).equals("ФИРМА ТРАНСГАРАНТ ООО") ||
                                    String.valueOf(l4.get(7)).equals("УРАЛЬСКАЯ ТРАНСПОРТНАЯ КОМПАНИЯ") ||
                                    String.valueOf(l4.get(7)).equals("АВС Лизинг") ||
                                    String.valueOf(l4.get(7)).equals("УГП ООО")) {
                                tempMap.put(f, m.getValue());
                                f++;
                            }
                        }
                    }
                }
            }
        }
        return tempMap;
    }

    // Применяем фильры
    public Map<Object, Object> applyFilters(Map<Integer, Object> map, String typeWagon, String dateYesterday) {
        List<Object> header = (List<Object>) map.get(0);
        Map<Object, Object> totalMap = new HashMap<>();
        for (int j = 0; j < header.size(); j++) {
            List<Object> tempBody = new ArrayList<>();
            for (Map.Entry<Integer, Object> body : filters(map, typeWagon, dateYesterday).entrySet()) {
                List<Object> temp = (List<Object>) body.getValue();
                tempBody.add(temp.get(j));
            }
            this.tableSize = tempBody.size();
            totalMap.put(header.get(j), tempBody);
        }
        return totalMap;
    }

    public int getTableSize() {
        return tableSize;
    }

}
