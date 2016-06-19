package org.freelasearch.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
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
import org.freelasearch.activities.InscricoesAnuncioActivity;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.interfaces.RecyclerViewOnClickListenerHack;
import org.freelasearch.utils.EstadoUtils;
import org.freelasearch.utils.RoundedCornersTransformation;

import java.util.List;

public class InscricoesAnuncioAdapter extends RecyclerView.Adapter<InscricoesAnuncioAdapter.MyViewHolder> {
    private List<DtoInscricao> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context mContext;

    public InscricoesAnuncioAdapter(Context c, List<DtoInscricao> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_inscricoes_anuncio_card, viewGroup, false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DtoInscricao inscricao = mList.get(position);
        DtoAnuncio anuncio = inscricao.getAnuncio();

        holder.tvTitulo.setText(anuncio.getTitulo());
        if (holder.ivFreelancer != null) {
            if (inscricao.getFreelancer().getUsuario().getUrlFoto() != null && !inscricao.getFreelancer().getUsuario().getUrlFoto().trim().isEmpty()) {
                Picasso.with(mContext).load(inscricao.getFreelancer().getUsuario().getUrlFoto()).placeholder(R.drawable.default_profile_bigger).error(R.drawable.default_profile_bigger).fit().
                        transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_LEFT)).
                        transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT)).
                        into(holder.ivFreelancer);
            } else {
                Picasso.with(mContext).load(R.drawable.default_profile_bigger).fit().
                        transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.TOP_LEFT)).
                        transform(new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT)).
                        into(holder.ivFreelancer);
            }
        }
        holder.tvFreelancer.setText(inscricao.getFreelancer().getUsuario().getNome());
        //TODO: Pegar a localização do Usuário, não do Anúncio
        holder.tvLocalizacao.setText(anuncio.getLocalizacao().getCidade() + ", " + new EstadoUtils().getDescriptionByUf(anuncio.getLocalizacao().getEstado()));

        holder.btnMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Redirecionar para Mensagem, já selecionado o usuário
            }
        });

        holder.btnContratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogContratacao = new AlertDialog.Builder(mContext);
                alertDialogContratacao.setTitle("Contratar Freelancer");
                alertDialogContratacao.setMessage("Ao contratar um freelancer o sistema automaticamente atualizará o status do anúncio para finalizado e não será mais possível editar ou inscrever-se para esse anúncio. Além disso o freelancer que se inscreveu receberá uma notificação que foi aceito para a vaga. Essas ações não poderão ser desfeitas.");
                alertDialogContratacao.setPositiveButton("Confirmar Contratação", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((InscricoesAnuncioActivity) mContext).contratar(inscricao.getFreelancer());
                    }
                });
                alertDialogContratacao.setNegativeButton("Cancelar", null);
                alertDialogContratacao.show();
            }
        });

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
        public ImageView ivFreelancer;
        public TextView tvFreelancer;
        public TextView tvLocalizacao;
        public AppCompatButton btnMensagem;
        public AppCompatButton btnContratar;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitulo = (TextView) itemView.findViewById(R.id.tv_titulo);
            ivFreelancer = (ImageView) itemView.findViewById(R.id.iv_freelancer);
            tvFreelancer = (TextView) itemView.findViewById(R.id.tv_freelancer);
            tvLocalizacao = (TextView) itemView.findViewById(R.id.tv_localizacao);
            btnMensagem = (AppCompatButton) itemView.findViewById(R.id.btn_mensagem);
            btnContratar = (AppCompatButton) itemView.findViewById(R.id.btn_contratar);

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
