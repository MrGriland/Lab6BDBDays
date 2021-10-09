package bgv.fit.bstu.lab6bdbdays;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bgv.fit.bstu.lab6bdbdays.Classes.Contact;
import bgv.fit.bstu.lab6bdbdays.Classes.DataItems;

public class MainActivity extends AppCompatActivity {

    List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = Read();
        DatePicker datePicker = findViewById(R.id.datedp);
        Calendar today = Calendar.getInstance();
        datePicker.setMaxDate(new Date().getTime());
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(), String.valueOf(datePicker.getDayOfMonth())+"."+String.valueOf(datePicker.getMonth())+"."+String.valueOf(datePicker.getYear()), Toast.LENGTH_SHORT).show();
                TextView info = findViewById(R.id.outtv);
                for (Contact c:
                     contacts) {
                    if (c.date.equals(String.valueOf(datePicker.getDayOfMonth())+"."+String.valueOf(datePicker.getMonth())+"."+String.valueOf(datePicker.getYear())))
                    {
                        info.setText(c.name+" "+c.surname+" "+c.phone);
                    }
                }
            }
        });
    }

    public List<Contact> Read()
    {
        List<Contact> contacts = new ArrayList<Contact>();
        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + "6LabExt.json");
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * Класс для создания строк из последовательностей символов
             */
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                /*
                 * Производим построчное считывание данных из файла в конструктор строки,
                 * Псоле того, как данные закончились, производим вывод текста в TextView
                 */
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                Gson gson = new Gson();
                DataItems dataItems = gson.fromJson(stringBuilder.toString(), DataItems.class);
                for (Contact contact:
                        dataItems.getContacts()) {
                    contacts.add(contact);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}