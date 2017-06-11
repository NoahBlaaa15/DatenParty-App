package datenparty.datenparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtikelAdapter extends ArrayAdapter<Artikel> {
    private final Context context;
    private final ArrayList<Artikel> values;

    public ArtikelAdapter (Context context, ArrayList<Artikel> values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.quelle);
        textView.setText(values.get(position).quelle);

        TextView textView1 = (TextView) rowView.findViewById(R.id.category);
        textView1.setText(values.get(position).category);

        TextView textView2 = (TextView) rowView.findViewById(R.id.heading);
        textView2.setText(values.get(position).ueberschrift);

        return rowView;
    }
}