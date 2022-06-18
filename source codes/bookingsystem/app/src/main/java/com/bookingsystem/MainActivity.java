package com.bookingsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    String selectedDate;
    String Time;
    Button selectedBtn;
    List<Button> btnList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Content view*/
        String languageToLoad  = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_main);

        LinearLayout relativeLayout = findViewById(R.id.mainLinear);
        Button bckButton = findViewById(R.id.button11);
        Button reserveBtn = findViewById(R.id.reserveBtn);
        Button prevButton = null;

        Button time10 = findViewById(R.id.button2);
        Button time11 = findViewById(R.id.button3);
        Button time12 = findViewById(R.id.button4);
        Button time13 = findViewById(R.id.button5);
        Button time14 = findViewById(R.id.button6);
        Button time15 = findViewById(R.id.button7);
        Button time16 = findViewById(R.id.button8);
        Button time17 = findViewById(R.id.button9);
        Button time18 = findViewById(R.id.button10);

        EditText name = findViewById(R.id.nameTxtBox);
        EditText phone = findViewById(R.id.phoneTxtBox);
        EditText pet = findViewById(R.id.petTypeTxtBox);
        EditText operation = findViewById(R.id.operationTxtBox);
        TextView dateTxt = findViewById(R.id.dateLabel);

        Database db = new Database(MainActivity.this);


        List<Button> allBtnList = new ArrayList<Button>();      //list holding all buttons

        allBtnList.add(time10);
        allBtnList.add(time11);
        allBtnList.add(time12);
        allBtnList.add(time13);
        allBtnList.add(time14);
        allBtnList.add(time15);
        allBtnList.add(time16);
        allBtnList.add(time17);
        allBtnList.add(time18);


        List<Button> reservedBtnList = new ArrayList<Button>(); //list holding reserved buttons

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                /* when clicked day on calendar, selected day informations assign to selectedDate variable */
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault());
                String currentDate = sdf.format(c);
                selectedDate = String.valueOf(dayOfMonth+"/"+(month+1)+"/"+year);


                btnList.clear();            //clearing btnList every click to day because that list hold not selected hours and these hours are different every day
                reservedBtnList.clear();    //clearing reservedBtnList, this list hold reserved hours
                btnList.addAll(allBtnList); //add all buttons to btnList, we delete buttons in next
                selectedBtn = null; //clicked button
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()); //data format

                Date selectedDateD = null;
                Date currentDateD = null;

                try {
                    //converting selectedDate and currentDate to sdf2 format
                    selectedDateD = sdf2.parse(selectedDate);
                    currentDateD = sdf2.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if(selectedDateD.compareTo(currentDateD) <= 0 ) //Checking if the selected day is a future date
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage("You can only make an appointment for a future date.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    List<String> timeList = new ArrayList<>();
                    timeList = db.getData(selectedDate); //The occupied hours on the selected date are pulled from the database
                    try {
                        for(Button b : allBtnList)
                        {
                            for(String L: timeList)
                            {
                                if(b.getText().equals(L))
                                {
                                    b.setBackgroundColor(Color.parseColor("#C46B777E"));
                                    reservedBtnList.add(b);
                                    btnList.remove(b);
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    calendarView.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);

                }

            }
        });

        time10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isThere(reservedBtnList, time10)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time10.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                            "Phone: "+String.valueOf(userList.get(1))+"\n" +
                            "Pet: "+String.valueOf(userList.get(2))+"\n" +
                            "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                            "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time10.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time10.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time10;
                }
            }
        });

        time11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time11)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time11.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time11.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time11.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time11;
                }
            }
        });

        time12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time12)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time12.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time12.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time12.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time12;
                }
            }
        });

        time13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time13)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time13.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time13.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time13.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time13;
                }
            }
        });

        time14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time14)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time14.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time14.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time14.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time14;
                }
            }
        });

        time15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time15)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time15.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time15.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time15.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time15;
                }
            }
        });

        time16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time16)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time16.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time16.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time16.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time16;
                }
            }
        });

        time17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time17)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time17.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time17.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time17.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time17;
                }
            }
        });

        time18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThere(reservedBtnList, time18)) //if button is there reservedBtnList this mean is
                {
                    List<String> userList = new ArrayList<>();
                    String time = String.valueOf(time18.getText());
                    userList = db.getData(selectedDate, time); //the user's data at the selected time is being pulled from the database
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage(
                            "Name: "+String.valueOf(userList.get(0))+"\n" +
                                    "Phone: "+String.valueOf(userList.get(1))+"\n" +
                                    "Pet: "+String.valueOf(userList.get(2))+"\n" +
                                    "Process To Do: "+String.valueOf(userList.get(3))+"\n" +
                                    "Date: "+String.valueOf(userList.get(4)));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
                else
                {
                    Time = String.valueOf(time18.getText());
                    dateTxt.setText(selectedDate + " - "+Time); //time printing to textview
                    setDefaultColor(selectedBtn);
                    time18.setBackgroundColor(Color.parseColor("#C4184763"));
                    selectedBtn = time18;
                }

            }
        });

        bckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllEnabled(allBtnList);
                relativeLayout.setVisibility(View.INVISIBLE);
                calendarView.setVisibility(View.VISIBLE);
                dateTxt.setText(""); //on booking information
            }
        });

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = String.valueOf(name.getText());
                String phoneStr = String.valueOf(phone.getText());
                String petStr = String.valueOf(pet.getText());
                String operationStr = String.valueOf(operation.getText());
                String dateStr = String.valueOf(selectedDate);
                String timeStr = String.valueOf(Time);

                String dateTxtStr = String.valueOf(dateTxt.getText());

                /* checking all textviews and if there are no empty values adding to database */
                if(!nameStr.trim().equals("") && !phoneStr.trim().equals("") && !petStr.trim().equals("") && !operationStr.trim().equals("") && !dateStr.equals("") && !dateTxtStr.equals(""))
                {

                    db.setData(nameStr, phoneStr, petStr, operationStr, dateStr, timeStr);
                    btnList = deleteFromList(btnList, selectedBtn);
                    if(selectedBtn != null)
                        selectedBtn.setBackgroundColor(Color.parseColor("#C46B777E"));
                    reservedBtnList.add(selectedBtn);
                    selectedBtn = null;
                }
                else //if there is empty textview
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage("Please Check Your Entries.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.show();
                }
            }
        });


    }


    public void setDefaultColor(Button btn)
    {
        if(btn != null)
            btn.setBackgroundColor(Color.parseColor("#C45D9FC5"));

    }

    public void setAllEnabled(List<Button> allBtnList)
    {
        for(Button b: allBtnList)
        {
            b.setEnabled(true);
            b.setBackgroundColor(Color.parseColor("#C45D9FC5"));

        }
    }

    public List<Button> deleteFromList(List<Button> btnList, Button btn)
    {
        List<Button> tempBtnList = new ArrayList<Button>();

        for(Button b: btnList)
            if(b != btn)
                tempBtnList.add(b);

        return tempBtnList;
    }

    public boolean isThere(List <Button> btnList, Button btn)
    {
        for(Button b: btnList)
            if(b == btn)
                return true;

        return false;
    }

}