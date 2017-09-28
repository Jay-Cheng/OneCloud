package service.factory;

import service.SortService;
import service.impl.SortByAlphaImpl;
import service.impl.SortByTimeImpl;

public class SortServiceFactory {
    private static final SortByAlphaImpl ALPHA_IMPL = new SortByAlphaImpl();
    private static final SortByTimeImpl TIME_IMPL = new SortByTimeImpl();
    
    public static SortService getService(int sortType) {
        switch(sortType) {
//        case 0:return ALPHA_IMPL;
        case 1:return TIME_IMPL;
        default:return ALPHA_IMPL;
        }
    }
}
