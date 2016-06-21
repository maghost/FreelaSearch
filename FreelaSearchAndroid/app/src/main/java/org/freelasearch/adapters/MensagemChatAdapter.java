package org.freelasearch.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.freelasearch.R;
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.utils.DataUtils;
import org.freelasearch.utils.RoundedCornersTransformation;

import java.util.List;

public class MensagemChatAdapter extends RecyclerView.Adapter<MensagemChatAdapter.MyViewHolder> {
    private List<DtoMensagem> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context mContext;
    private Integer idUsuario;

    public MensagemChatAdapter(Context c, List<DtoMensagem> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_chat_mensagem, viewGroup, false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DtoMensagem mensagem = mList.get(position);

        holder.cvItemMensagem.setCardBackgroundColor(Color.TRANSPARENT);
        holder.cvItemMensagem.setCardElevation(0);
        holder.cvItemMensagem.setMaxCardElevation(0);

        if (mensagem.getUsuarioRemetente() == null || mensagem.getUsuarioRemetente().getId() == null || getIdUsuario().equals(mensagem.getUsuarioRemetente().getId())) {
            if (mensagem.getUsuarioRemetente().getId() == null) {
                // SISTEMA
                holder.rlContraparte.setVisibility(View.VISIBLE);
                holder.rlUsuario.setVisibility(View.GONE);

                Picasso.with(mContext).load(R.drawable.logo_square_launcher).fit().
                        into(holder.ivFotoContraparte);

                holder.tvMensagemContraparte.setText(mensagem.getConteudo());

                holder.tvDataContraparte.setText(DataUtils.formatChat(mensagem.getDataEnvio()));
            } else {
                // USUÁRIO LOGADO
                holder.rlUsuario.setVisibility(View.VISIBLE);
                holder.rlContraparte.setVisibility(View.GONE);

                if (mensagem.getUsuarioRemetente() != null && mensagem.getUsuarioRemetente().getUrlFoto() != null) {
                    Picasso.with(mContext).load(mensagem.getUsuarioRemetente().getUrlFoto()).placeholder(R.drawable.default_profile_bigger).error(R.drawable.default_profile_bigger).fit().
                            into(holder.ivFoto);
                } else {
                    Picasso.with(mContext).load(R.drawable.default_profile_bigger).fit().
                            into(holder.ivFotoContraparte);
                }

                holder.tvMensagem.setText(mensagem.getConteudo());

                holder.tvData.setText(DataUtils.formatChat(mensagem.getDataEnvio()));
            }
        } else {
            // CONTRAPARTE
            holder.rlContraparte.setVisibility(View.VISIBLE);
            holder.rlUsuario.setVisibility(View.GONE);

            if (mensagem.getUsuarioRemetente() != null && mensagem.getUsuarioRemetente().getUrlFoto() != null) {
                Picasso.with(mContext).load(mensagem.getUsuarioRemetente().getUrlFoto()).placeholder(R.drawable.default_profile_bigger).error(R.drawable.default_profile_bigger).fit().
                        into(holder.ivFotoContraparte);
            } else {
                Picasso.with(mContext).load(R.drawable.default_profile_bigger).fit().
                        into(holder.ivFotoContraparte);
            }

            holder.tvMensagemContraparte.setText(mensagem.getConteudo());

            holder.tvDataContraparte.setText(DataUtils.formatChat(mensagem.getDataEnvio()));
        }

        try

        {
            YoYo.with(Techniques.FadeIn).duration(120).playOn(holder.itemView);
        } catch (
                Exception e
                )

        {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void addListItem(DtoMensagem dto, int position, boolean adicionaInicio) {
        if (adicionaInicio) {
            mList.add(0, dto);
            notifyItemInserted(0);
        } else {
            mList.add(dto);
            notifyItemInserted(position);
        }
    }

    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cvItemMensagem;

        public RelativeLayout rlUsuario;
        public ImageView ivFoto;
        public TextView tvMensagem;
        public TextView tvData;

        public RelativeLayout rlContraparte;
        public ImageView ivFotoContraparte;
        public TextView tvMensagemContraparte;
        public TextView tvDataContraparte;

        public MyViewHolder(View itemView) {
            super(itemView);

            cvItemMensagem = (CardView) itemView.findViewById(R.id.cv_item_mensagem);

            rlUsuario = (RelativeLayout) itemView.findViewById(R.id.rl_usuario);
            ivFoto = (ImageView) itemView.findViewById(R.id.iv_foto);
            tvMensagem = (TextView) itemView.findViewById(R.id.tv_mensagem);
            tvData = (TextView) itemView.findViewById(R.id.tv_data);

            rlContraparte = (RelativeLayout) itemView.findViewById(R.id.rl_contraparte);
            ivFotoContraparte = (ImageView) itemView.findViewById(R.id.iv_foto_contraparte);
            tvMensagemContraparte = (TextView) itemView.findViewById(R.id.tv_mensagem_contraparte);
            tvDataContraparte = (TextView) itemView.findViewById(R.id.tv_data_contraparte);

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