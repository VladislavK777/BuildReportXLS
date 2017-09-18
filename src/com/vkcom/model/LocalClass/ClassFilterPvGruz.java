package com.vkcom.model.LocalClass;

/*
*
* Вспомогательный класс для фильтра ПВ ГРУЗ
*
* @project BuildReportXLS
* @author vladislavklockov
* @version 1.0
* @create 18.09.17
*
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFilterPvGruz {
    private int tableSize;

    // метод применения фильров
    private Map<Integer, Object> filters(Map<Integer, Object> map, String typeWagon, String dateYesterday) {
        Map<Integer, Object> tempMap = new HashMap<>();
        int f = 0;
        for (Map.Entry<Integer, Object> m : map.entrySet()) {
            List<Object> l = (List<Object>) m.getValue();
            if (String.valueOf(l.get(6)).equals("ПВ")) {
                List<Object> l1 = (List<Object>) m.getValue();
                    if (String.valueOf(l1.get(17)).equals(typeWagon)) {
                        List<Object> l2 = (List<Object>) m.getValue();
                        if (String.valueOf(l2.get(11)).contains(dateYesterday)) {
                            List<Object> l3 = (List<Object>) m.getValue();
                            if (String.valueOf(l3.get(7)).equals("Альфа Транс Логистик, ООО") ||
                                    String.valueOf(l3.get(7)).equals("ВТГ ООО") ||
                                    String.valueOf(l3.get(7)).equals("ОРЕНТРАНС АО") ||
                                    String.valueOf(l3.get(7)).equals("СЛК ООО")) {
                                tempMap.put(f, m.getValue());
                                f++;
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
