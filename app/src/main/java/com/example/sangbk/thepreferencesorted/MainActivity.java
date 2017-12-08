package com.example.sangbk.thepreferencesorted;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity {
    EditText name=null;
    EditText address=null;
    EditText notes=null;
    RadioGroup types=null;
    RadioGroup sales=null;
    RestaurantAdapter adapter=null;
    ListView list=null;
    MySQLHelper helper=null;
    Cursor model=null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.addr);
        types=(RadioGroup)findViewById(R.id.types);
        sales=(RadioGroup)findViewById(R.id.sales);
        notes=(EditText)findViewById(R.id.notes);
        Button save=(Button)findViewById(R.id.save);
        save.setOnClickListener(onSave);
        list=(ListView)findViewById(R.id.restaurants);
        helper=new MySQLHelper(this);
        model=helper.getAll();
        startManagingCursor(model);
        adapter=new RestaurantAdapter(model);
        list.setAdapter(adapter);

        TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");

        spec.setContent(R.id.restaurants);
        spec.setIndicator("", getResources().getDrawable(R.drawable.list));
        getTabHost().addTab(spec);

        spec=getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("", getResources()
                .getDrawable(R.drawable.restaurant));
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0);
        list.setOnItemClickListener(onListClick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }
    public boolean onOptionsItemSelectec(MenuItem item){
        if(item.getItemId()==R.id.add){
            Toast.makeText(this,"Sorry The funtion not ready",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==R.id.sort){
            startActivity(new Intent(this, Preference.class));
            return  true;
        }
        return (super.onOptionsItemSelected(item));
    }


    private View.OnClickListener onSave=new View.OnClickListener() {
        public void onClick(View v) {
            String typesTxt=null;
            String saleTxt=null;
            switch (types.getCheckedRadioButtonId()) {
                case R.id.sit_down:
                    typesTxt="Sit_down";
                    break;

                case R.id.take_out:
                    typesTxt="Take_out";
                    break;

                case R.id.delivery:
                    typesTxt="Delivery";
                    break;
            }
            switch (sales.getCheckedRadioButtonId()) {
                case R.id.no:
                    saleTxt="No_discount";
                    break;

                case R.id.giam20:
                    saleTxt="Discount 20%";
                    break;

                case R.id.giam50:
                    saleTxt="Discount 50%";
                    break;
            }
            helper.insert(name.getText().toString(),address.getText().toString(),typesTxt,saleTxt,notes.getText().toString());
            model.requery();


        }
    };



    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent,
                                View view, int position,
                                long id) {
            model.moveToPosition(position);
            name.setText(helper.getName(model));
            address.setText(helper.getAddress(model));
            notes.setText(helper.getNotes(model));
            if (helper.getType(model).equals("Sit_down")) {
                types.check(R.id.sit_down);
            }
            else if (helper.getType(model).equals("Take_out")) {
                types.check(R.id.take_out);
            }
            else {
                types.check(R.id.delivery);
            }
            if (helper.getDiscount(model).equals("Discount 20%")) {
                sales.check(R.id.giam20);
            }
            else if (helper.getDiscount(model).equals("Discount 50%")) {
                sales.check(R.id.giam50);
            }
            else {
                sales.check(R.id.no);
            }
            getTabHost().setCurrentTab(1);
        }
    };




    class RestaurantAdapter extends CursorAdapter {


        public RestaurantAdapter( Cursor c) {
            super(MainActivity.this, c);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row,parent,false);
            RestaurantHolder holder=new RestaurantHolder(row);
            row.setTag(holder);
            return row;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            RestaurantHolder holder=(RestaurantHolder)view.getTag();
            holder.populaterFrom(cursor,helper);
        }
    }


    static class RestaurantHolder {
        private TextView name = null;
        private TextView address = null;
        private TextView salse=null;
        private ImageView icon = null;
        private ImageView icon2=  null;

        RestaurantHolder(View row) {
            name = (TextView) row.findViewById(R.id.title);
            address = (TextView) row.findViewById(R.id.address);
            salse =(TextView)row.findViewById(R.id.dis);
            icon = (ImageView) row.findViewById(R.id.icon);
            icon2 = (ImageView)row.findViewById(R.id.icon2);
        }


        void populaterFrom(Cursor c, MySQLHelper helper) {
            name.setText(helper.getName(c));
            address.setText(helper.getAddress(c));
            salse.setText(helper.getDiscount(c));

            if (helper.getType(c).equals("Sit_down")) {
                icon.setImageResource(R.drawable.ball_red);
            } else if (helper.getType(c).equals("Take_out")) {
                icon.setImageResource(R.drawable.ball_yellow);

            } else {
                icon.setImageResource(R.drawable.ball_green);
            }
            if(helper.getDiscount(c).equals("Discount 20%")){
                icon2.setImageResource(R.drawable.giam20);
            }else if(helper.getDiscount(c).equals("Discount 50%")) {
                icon2.setImageResource(R.drawable.giam50);
            }
        }
    }
}