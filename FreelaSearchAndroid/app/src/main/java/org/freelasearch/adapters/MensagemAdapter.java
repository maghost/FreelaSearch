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
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.utils.DataUtils;

import java.util.List;

public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.MyViewHolder> {
    private List<DtoMensagem> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context mContext;

    public MensagemAdapter(Context c, List<DtoMensagem> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_mensagem, viewGroup, false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DtoMensagem mensagem = mList.get(position);
        boolean ehSistema = mensagem.getUsuarioRemetente() == null || mensagem.getUsuarioRemetente().getId() == null;

        if (holder.ivRemetente != null) {
            if (ehSistema) {
                Picasso.with(mContext).load(R.drawable.logo_square).fit().
                        into(holder.ivRemetente);
            } else if (mensagem.getUsuarioRemetente().getUrlFoto() != null && !mensagem.getUsuarioRemetente().getUrlFoto().trim().isEmpty()) {
                Picasso.with(mContext).load(mensagem.getUsuarioRemetente().getUrlFoto()).placeholder(R.drawable.default_profile_bigger).error(R.drawable.default_profile_bigger).fit().
                        into(holder.ivRemetente);
            } else {
                Picasso.with(mContext).load(R.drawable.default_profile_bigger).fit().
                        into(holder.ivRemetente);
            }
        }
        if (ehSistema) {
            holder.tvRemetente.setVisibility(View.INVISIBLE);
            holder.tvSistema.setVisibility(View.VISIBLE);
        } else {
            holder.tvRemetente.setText(mensagem.getUsuarioRemetente().getNome());
        }
        holder.tvMensagem.setText(mensagem.getConteudo());
        holder.tvData.setText(DataUtils.format(mensagem.getDataEnvio()));

        try {
            YoYo.with(Techniques.FadeIn).duration(120).playOn(holder.itemView);
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

    public void addListItem(DtoMensagem dto, int position) {
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
        public ImageView ivRemetente;
        public TextView tvRemetente;
        public TextView tvSistema;
        public TextView tvMensagem;
        public TextView tvData;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivRemetente = (ImageView) itemView.findViewById(R.id.nhm_remetente);
            tvRemetente = (TextView) itemView.findViewById(R.id.tv_remetente);
            tvSistema = (TextView) itemView.findViewById(R.id.tv_sistema);
            tvMensagem = (TextView) itemView.findViewById(R.id.tv_mensagem);
            tvData = (TextView) itemView.findViewById(R.id.tv_data);

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
