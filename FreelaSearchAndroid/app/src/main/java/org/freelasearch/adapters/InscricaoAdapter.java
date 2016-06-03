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
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.utils.EstadoUtils;

import java.util.List;

public class InscricaoAdapter extends RecyclerView.Adapter<InscricaoAdapter.MyViewHolder> {
    private List<DtoInscricao> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context mContext;

    public InscricaoAdapter(Context c, List<DtoInscricao> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_inscricao, viewGroup, false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*if (holder.nhmAnunciante != null) {
            Picasso.with(mContext).load(mList.get(position).getAnunciante().getUsuario().getUrlFoto()).placeholder(R.drawable.no_image).error(R.drawable.no_image).fit().
                    transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_LEFT)).
                    transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT)).
                    into(holder.nhmAnunciante);
        }*/

        DtoAnuncio anuncio = mList.get(position).getAnuncio();

        if (holder.nhmAnunciante != null) {
            if (anuncio.getAnunciante().getUsuario().getUrlFoto() != null && !anuncio.getAnunciante().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(mContext).load(anuncio.getAnunciante().getUsuario().getUrlFoto())
                        .placeholder(R.drawable.default_profile).error(R.drawable.default_profile).fit().into(holder.nhmAnunciante);
            } else {
                holder.nhmAnunciante.setImageResource(R.drawable.default_profile);
            }
        }
        holder.tvTitulo.setText(anuncio.getTitulo());
        holder.tvAnunciante.setText(anuncio.getAnunciante().getUsuario().getNome());
        holder.tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));
        holder.tvCategoria.setText(anuncio.getCategoria() != null ? anuncio.getCategoria().getNome() : null);
        holder.tvDescricao.setText(anuncio.getDescricao());

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

    public void addListItem(DtoInscricao dto, int position) {
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
        public TextView tvTitulo;
        public ImageView nhmAnunciante;
        public TextView tvAnunciante;
        public TextView tvLocalizacao;
        public TextView tvCategoria;
        public TextView tvDescricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitulo = (TextView) itemView.findViewById(R.id.tv_titulo);
            nhmAnunciante = (ImageView) itemView.findViewById(R.id.nhm_anunciante);
            tvAnunciante = (TextView) itemView.findViewById(R.id.tv_anunciante);
            tvLocalizacao = (TextView) itemView.findViewById(R.id.tv_localizacao);
            tvCategoria = (TextView) itemView.findViewById(R.id.tv_categoria);
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
