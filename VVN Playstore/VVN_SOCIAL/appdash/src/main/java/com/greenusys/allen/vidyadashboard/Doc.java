package com.greenusys.allen.vidyadashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Doc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        String data[]={"Document","Pdf","Image"};
        int images[]={R.drawable.doc2,R.drawable.pdf1,R.drawable.img1};

        ListView listView;

        listView=(ListView)findViewById(R.id.Document_list);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        next("docx",1);
                        break;

                    case 1:
                        next("pdf",2);
                        break;
                    case 2:
                        next("jpg",3);
                        break;

                }
            }
        });

        Myadapter myAdapter=new Myadapter(getApplicationContext(),data ,images);
        listView.setAdapter(myAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent uu2=new Intent(Doc.this,MainActivity_Dash.class);
        startActivity(uu2);

    }

    private void next(String str, int im) {

        Intent n=new Intent(Doc.this,Doc_Next.class);
        n.putExtra("type", str);
        n.putExtra("image", im);
        startActivity(n);
    }

    class Myadapter extends ArrayAdapter {

        int []imagear;
        String []namear;
        public Myadapter(Context context, String[] name, int image[])
        {
            super(context,R.layout.customdocumentlist,R.id.titlelists,name);
            this.imagear=image;
            this.namear=name;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView , ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.customdocumentlist,parent,false);
            ImageView im=(ImageView) row.findViewById(R.id.image);
            TextView t=(TextView)row.findViewById(R.id.titlelists);

            im.setImageResource(imagear[position]);
            t.setText(namear[position]);


            return row;

        }

    }


}
