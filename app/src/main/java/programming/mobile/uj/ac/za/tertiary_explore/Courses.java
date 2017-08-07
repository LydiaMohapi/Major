package programming.mobile.uj.ac.za.tertiary_explore;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Courses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //gather the courses available
        final String courses [] = {"Computer Science",
                "Maths","Informatics",
                "Mobile Programming","Graphics","Information Management","Life orientation"};
        ListView listCourses = (ListView)findViewById(R.id.listCourses);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(getBaseContext(),R.layout.list_courses_view,courses);
        //listCourses.setAdapter( new Ad);
        listCourses.setAdapter(coursesAdapter);
        listCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //popup
                AlertDialog.Builder alert = new AlertDialog.Builder(Courses.this);
                alert.setTitle(courses[position].toString());
                alert.setMessage("Course info here ...");
                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
}
