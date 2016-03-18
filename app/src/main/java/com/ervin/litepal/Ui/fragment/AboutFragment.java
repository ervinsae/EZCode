package com.ervin.litepal.Ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ervin.litepal.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2016/3/18.
 */
public class AboutFragment extends Fragment {
    @Bind(R.id.lv)
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        listView.setAdapter(new AboutAdapter());
    }

    class AboutAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 18;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.layout_about_item,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv.setTextColor(getResources().getColor(R.color.material_cyan_300));
            return convertView;
        }

        class ViewHolder{
            TextView tv;
            ViewHolder(View view){
                tv = (TextView) view.findViewById(R.id.about_tv);
            }
        }
    }
}
