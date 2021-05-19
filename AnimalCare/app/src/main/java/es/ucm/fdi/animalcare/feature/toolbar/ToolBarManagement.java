package es.ucm.fdi.animalcare.feature.toolbar;

import android.content.Intent;
import android.view.View;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class ToolBarManagement extends BaseActivity {
     public void launchFromToolbar(View view){
               Intent intent;
               switch (view.getId()){
                    case R.id.button_toolbar_upcoming:
                         intent = new Intent(view.getContext(), UpcomingActivity.class);
                         break;
                    case R.id.button_toolbar_user:
                         intent = new Intent(view.getContext(), UserActivity.class);
                         break;
                    case R.id.button_toolbar_calendar:
                         intent = new Intent(view.getContext(), CalendarActivity.class);
                         break;
                    case R.id.button_toolbar_pets:
                         intent = new Intent(view.getContext(), PetsActivity.class);
                         break;
                    default:
                         intent = new Intent(view.getContext(), SettingsActivity.class);
                         break;
               }
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
     }

     public void setUpToolbar(int id){
         switch (id){
             case R.id.button_toolbar_upcoming:
                 findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
                 break;
             case R.id.button_toolbar_user:
                 findViewById(R.id.button_toolbar_user).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
                 break;
             case R.id.button_toolbar_calendar:
                 findViewById(R.id.button_toolbar_calendar).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
                 break;
             case R.id.button_toolbar_pets:
                 findViewById(R.id.button_toolbar_pets).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
                 break;
             default:
                 findViewById(R.id.button_toolbar_settings).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
                 break;
         }
         if(id!=R.id.button_toolbar_pets){
             findViewById(R.id.button_toolbar_pets).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
         }
         if(id!=R.id.button_toolbar_settings){
             findViewById(R.id.button_toolbar_settings).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
         }
         if(id!=R.id.button_toolbar_calendar){
             findViewById(R.id.button_toolbar_calendar).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
         }
         if(id!=R.id.button_toolbar_upcoming){
             findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
         }
         if(id!=R.id.button_toolbar_user){
             findViewById(R.id.button_toolbar_user).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
         }
     }

}
