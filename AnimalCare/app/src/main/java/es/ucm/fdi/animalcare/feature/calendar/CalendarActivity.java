package es.ucm.fdi.animalcare.feature.calendar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class CalendarActivity extends ToolBarManagement implements CalendarView {
    private int TASK_LOADER_ID=50;
    private CalendarPresenter mCalendarPresenter;
    private List<EventDay> events = new ArrayList<>();
    private Calendar mCalendar;
    private com.applandeo.materialcalendarview.CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private TaskLoaderCallbacks mTaskLoaderCallbacks = new TaskLoaderCallbacks(this, this);
    private TasksListAdapter mAdapter;
    private List<Task> mTasks= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        bindViews();
        setUpToolbar();

        mCalendarPresenter = new CalendarPresenter(this);

        mCalendar = Calendar.getInstance();

        mCalendarPresenter.prepareEvents(App.getApp().getUser().getmId());

        mAdapter = new TasksListAdapter(this, mTasks);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(TASK_LOADER_ID) != null){
            loaderManager.initLoader(TASK_LOADER_ID,null, mTaskLoaderCallbacks);
        }

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar calendar = eventDay.getCalendar();
                searchEventsOfDay(calendar);
            }
        });

        //Get events of current day
        Calendar calendar = Calendar.getInstance();
        searchEventsOfDay(calendar);
    }

    @Override
    public void setEvents(List<EventDay> events){
        mCalendarView.setEvents(events);
    }

    public void searchEventsOfDay(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Bundle queryBundle = new Bundle();
        queryBundle.putString(TaskLoaderCallbacks.EXTRA_DATETIMESTRING, dateFormat.format(calendar.getTime()));
        queryBundle.putInt(TaskLoaderCallbacks.EXTRA_USERID, App.getApp().getUser().getmId());
        LoaderManager.getInstance(this)
                .restartLoader(TASK_LOADER_ID, queryBundle, mTaskLoaderCallbacks);
    }

    public void updateTasksList(List<Task> data){
        mAdapter.setTasksData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindViews() {
        mCalendarView = findViewById(R.id.calendarView);
        mRecyclerView = findViewById(R.id.recycler_view);
        TextView textView = findViewById(R.id.tareas);
        textView.setText(App.getApp().getResources().getString(R.string.calendar_tasks));
    }

    public void setUpToolbar() {
        super.setUpToolbar(R.id.button_toolbar_calendar);
    }
}