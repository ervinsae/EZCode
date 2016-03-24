package com.ervin.litepal.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ervin.litepal.R;
import com.ervin.litepal.table.Meizhis;
import com.ervin.litepal.ui.widget.RatioImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2016/3/24.
 */
public class MeizhiListAdapter extends RecyclerView.Adapter<MeizhiListAdapter.ViewHolder> {

    private List<Meizhis> mList;
    private Context mContext;

    public MeizhiListAdapter(Context context, List<Meizhis> meizhiList) {
        mList = meizhiList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.framgent_meizhi_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meizhis meizhi = mList.get(position);
        int limit = 48;
        String text = meizhi.desc.length() > limit ? meizhi.desc.substring(0, limit) +
                "..." : meizhi.desc;

        holder.meizhi = meizhi;
        holder.titleView.setText(text);
        holder.card.setTag(meizhi.desc);

        //Picasso.with(mContext).load(meizhi.url).resize(holder.meizhiView.getWidth(),holder.meizhiView.getHeight()).centerCrop().into(holder.meizhiView);

        Glide.with(mContext)
                .load(meizhi.url)
                .centerCrop()
                .into(holder.meizhiView)
                .getSize((width, height) -> {
                    if (!holder.card.isShown()) {
                        holder.card.setVisibility(View.VISIBLE);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.iv_meizhi)
        RatioImageView meizhiView;
        @Bind(R.id.tv_title)
        TextView titleView;

        View card;
        Meizhis meizhi;
        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
            meizhiView.setOnClickListener(this);
            card.setOnClickListener(this);
            meizhiView.setOriginalSize(50, 50);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
