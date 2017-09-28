package service.impl;

import java.text.Collator;
import java.util.Arrays;

import service.SortService;
import service.dto.Sortable;

public class SortByAlphaImpl implements SortService {

    @Override
    public void serve(Sortable[] array) {
        Arrays.sort(array, (s1, s2) -> {
            String name1 = s1.getLocalName();
            String name2 = s2.getLocalName();
            return  Collator.getInstance(java.util.Locale.CHINA).compare(name1, name2);
        });
    }

}
