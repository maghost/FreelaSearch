package org.freelasearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.utils.RoundedCornersTransformation;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.MyViewHolder> {
    private List<DtoAnuncio> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context mContext;

    public AnuncioAdapter(Context c, List<DtoAnuncio> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_anuncio_card, viewGroup, false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(mContext).load(mList.get(position).getImagem()).placeholder(R.drawable.no_image).error(R.drawable.no_image).fit().
                transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_LEFT)).
                transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT)).
                into(holder.ivAnuncio);
        holder.tvTitulo.setText(mList.get(position).getTitulo());
        holder.tvDescricao.setText(mList.get(position).getDescricao());

        try {
            YoYo.with(Techniques.FadeIn).duration(200).playOn(holder.itemView);
        } catch (Exception e) {
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(DtoAnuncio dto, int position) {
        mList.add(dto);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivAnuncio;
        public TextView tvTitulo;
        public TextView tvDescricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivAnuncio = (ImageView) itemView.findViewById(R.id.iv_anuncio);
            tvTitulo = (TextView) itemView.findViewById(R.id.tv_titulo);
            tvDescricao = (TextView) itemView.findViewById(R.id.tv_descricao);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getLayoutPosition());
            }
        }
    }

}
