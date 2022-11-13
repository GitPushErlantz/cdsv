package com.gperlantz.cdsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gperlantz.cdsv.models.Playlist;

import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;

public class CreateList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Spinner typeSelector = (Spinner) findViewById(R.id.cbTypeSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelector.setAdapter(adapter);
        typeSelector.setOnItemSelectedListener(this);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        //TODO: fix the editText colors (and all the colors in general)
        RadioGroup rg = (RadioGroup) findViewById(R.id.rgColor);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText edtListName = (EditText) findViewById(R.id.edtListName);
                EditText edtListAuthor = (EditText) findViewById(R.id.edtListAuthor);
                if (checkedId == R.id.rbBlackWhite) {
                    setTheme(R.style.bw);
                } else if (checkedId == R.id.rbBlue) {
                    setTheme(R.style.blue);
                } else if (checkedId == R.id.rbPurple) {
                    setTheme(R.style.purple);
                } else if (checkedId == R.id.rbOrange) {
                    setTheme(R.style.orange);
                } else if (checkedId == R.id.rbTeal) {
                    setTheme(R.style.teal);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //grab variable elements
        ImageView iv = (ImageView) findViewById(R.id.ivLogo);
        TextView title = (TextView) findViewById(R.id.tvTitlebarTitle);
        String text = adapterView.getItemAtPosition(i).toString();
        //grab the type, and set the title and icon at the top depending on it
        if (text.equals("Album") || text.equals("Álbum") || text.equals("Albuma")) {
            iv.setImageResource(R.drawable.album49);
            title.setText(getResources().getString(R.string.newAlbum));
        } else if (text.equals("Mix") || text.equals("Mix-a")) {
            iv.setImageResource(R.drawable.mix49);
            title.setText(getResources().getString(R.string.newMix));
        } else { // if the unknown type has been selected, just use the normal logo and call it a list
            iv.setImageResource(R.drawable.logo49);
            title.setText(getResources().getString(R.string.newList));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void save() { // will save the list to a csv file, stored in the app's data folder
        EditText listName = (EditText) findViewById(R.id.edtListName);
        EditText listAuthor = (EditText) findViewById(R.id.edtListAuthor);
        Spinner typeSelector = (Spinner) findViewById(R.id.cbTypeSelector);
        String name = listName.getText().toString();
        String author = listAuthor.getText().toString();
        String type = typeSelector.getSelectedItem().toString();
        RadioGroup colorSelector = (RadioGroup) findViewById(R.id.rgColor);
        //grab the color from the radio group
        String color = "Teal";
        if (colorSelector.getCheckedRadioButtonId() == R.id.rbTeal) {
            color = "Teal";
        } else if (colorSelector.getCheckedRadioButtonId() == R.id.rbBlue) {
            color = "Blue";
        } else if (colorSelector.getCheckedRadioButtonId() == R.id.rbBlackWhite) {
            color = "BlackWhite";
        } else if (colorSelector.getCheckedRadioButtonId() == R.id.rbPurple) {
            color = "Purple";
        } else if (colorSelector.getCheckedRadioButtonId() == R.id.rbOrange) {
            color = "Orange";
        }
        if (name.isEmpty() || author.isEmpty()) { // if the name or author fields are empty, show a toast and don't save
            Toast noEmpty = Toast.makeText(getApplicationContext(), getResources().getString(R.string.noEmpty), Toast.LENGTH_SHORT);
            noEmpty.show();
        } else { // if the name or author have a semicolon, which would break the csv, show a toast and don't save
            if (name.contains(";") || author.contains(";")) {
                Toast errToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.noSemicolon), Toast.LENGTH_SHORT);
                errToast.show();
            } else { // if everything is correct though, save the list and let the user know
                String fileName = author + "-" + name + ".csv";
                fileName = fileName.replace(" ", "_");
                int repeatCounter = 1;
                while (new File(getFilesDir(), fileName).exists()) { // if the file already exists, add a number to the end of the file name, so it doesn't get overwritten
                    fileName = author + "-" + name + " (" + repeatCounter + ").csv";
                    repeatCounter++;
                }

                File file = new File(this.getFilesDir(), fileName);
                /*
                 * The list's desired format:
                 * version, type, color, title, author
                 * song title, artist, minutes, seconds, link
                 * song title, artist, minutes, seconds, link
                 * song title, artist, minutes, seconds, link
                 * etc...
                 */
                Playlist list = new Playlist("cdsv1", type, color, name, author, fileName);
                try (PrintWriter pw = new PrintWriter(file)) { // write the first line of the format
                    pw.println(list.toCsv()); // the version is hardcoded for now, that will eventually change
                    Toast successToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.listCreated), Toast.LENGTH_SHORT);
                    successToast.show();
                    finish();
                } catch (Exception e) { // this should never happen
                    e.printStackTrace();
                    Toast errToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorCreatingList), Toast.LENGTH_SHORT);
                    errToast.show();
                }
            }
        }
    }
}
