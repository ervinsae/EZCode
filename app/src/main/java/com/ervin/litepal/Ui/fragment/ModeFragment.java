package com.ervin.litepal.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ervin.litepal.R;
import com.ervin.litepal.ui.HomeActivity;
import com.ervin.litepal.ui.widget.DragCardsView;
import com.ervin.litepal.ui.widget.HalfRoundedDrawable;
import com.ervin.litepal.ui.widget.MyImageView;
import com.ervin.litepal.Utils.Tools;

import java.util.ArrayList;

/**
 * Created by Ervin on 2015/12/18.
 */
public class ModeFragment extends Fragment implements View.OnClickListener{

    private ImageView menuIcon;
    private TextView tvTitle;
    private FragmentCallback mCallback;

    private DragCardsView mDragCardsView;
    private CardsAdapter mCardAdapter;
    private ArrayList<Integer> cardList = new ArrayList<>();
    private int[] imgRes=new int[]{
            R.mipmap.ic_google_logo1,
            R.mipmap.ic_google_logo2,
            R.mipmap.ic_google_logo3,
            R.mipmap.ic_google_logo4,
            R.mipmap.new_google_logo,
    };


    public ModeFragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (HomeActivity)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_mode,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        menuIcon = (ImageView) rootView.findViewById(R.id.show_drawer);
        tvTitle = (TextView) rootView.findViewById(R.id.toolbar_center_title);
        mDragCardsView = (DragCardsView) rootView.findViewById(R.id.dragCardsView);
        tvTitle.setText(R.string.menu_Mode);
        menuIcon.setVisibility(View.VISIBLE);
        menuIcon.setOnClickListener(this);
        initData();
    }

    private void initData() {

        for (int i = 0; i < imgRes.length; i++) {
            cardList.add(imgRes[i]);
        }
        mCardAdapter = new CardsAdapter();
        mDragCardsView.setAdapter(mCardAdapter);
        mDragCardsView.setFlingListener(new DragCardsView.onDragListener() {
            @Override
            public void removeFirstObjectInAdapter(boolean isLeft) {

                if (isLeft) {
                    Toast.makeText(getActivity(), "向左划了一张牌",
                            Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getActivity(), "向右划了一张牌",
                            Toast.LENGTH_SHORT).show();
                }
                if (cardList.size() > 0) {
                    cardList.remove(0);
                }
                mCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSelectLeft(double distance) {

            }

            @Override
            public void onSelectRight(double distance) {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onCardMoveDistance(double distance) {

            }

            @Override
            public void onCardReturn() {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_drawer:
                mCallback.callbackMenuSetting(null);
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    class CardsAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return cardList.size();
        }


        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return cardList.get(position);
        }


        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View cardView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.layout_card, parent, false);
            Bitmap bitmap = Tools.readBitMap(getActivity(),cardList.get(position));//BitmapFactory.decodeResource(getResources(), cardList.get(position));
            MyImageView iv_image = (MyImageView) cardView
                    .findViewById(R.id.iv_image);
            iv_image.setImageDrawable(new HalfRoundedDrawable(bitmap, 15, 0));
            return cardView;
        }


    }

}
