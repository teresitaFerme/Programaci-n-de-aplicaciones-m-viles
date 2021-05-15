package es.ucm.fdi.animalcare.feature.calendar;

import com.applandeo.materialcalendarview.EventDay;

import java.util.List;

import es.ucm.fdi.animalcare.base.BaseView;

public interface CalendarView extends BaseView {
    void setEvents(List<EventDay> events);
}
