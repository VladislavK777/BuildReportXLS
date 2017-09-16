package com.vkcom.model.LocalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFilter_138 {
    private int tableSize;

    // метод применения фильров
    private Map<Integer, Object> filters(Map<Integer, Object> map, String typeWagon, String dateYesterday) {
        Map<Integer, Object> tempMap = new HashMap<>();
        int f = 0;
        for (Map.Entry<Integer, Object> m : map.entrySet()) {
            List<Object> l = (List<Object>) m.getValue();
            for (int j = 0; j < l.size(); j++) {
                if (String.valueOf(l.get(j)).equals("КР")) {
                    List<Object> l1 = (List<Object>) m.getValue();
                    for (int k = 0; k < l1.size(); k++) {
                        if (String.valueOf(l1.get(k)).equals("138.0")) {
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
