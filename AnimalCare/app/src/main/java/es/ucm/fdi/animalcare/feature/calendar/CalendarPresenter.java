package es.ucm.fdi.animalcare.feature.calendar;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class CalendarPresenter extends BasePresenter {
    private CalendarView mCalendarView;
    private CalendarModel mCalendarModel;

    CalendarPresenter(CalendarView view){
        mCalendarModel = new CalendarModel();
        mCalendarView = view;
    }
}
