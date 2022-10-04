package br.com.eduardo.exerciciometas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardo.exerciciometas.databinding.MetaItemBinding

class MetasAdapter (val delegate:IUpdateMeta) :
    RecyclerView.Adapter<MetasAdapter.LineViewHolder>() {

    val metasList: MutableList<MetasModel> = mutableListOf()

    class LineViewHolder(val itemHolder: MetaItemBinding) :
        RecyclerView.ViewHolder(itemHolder.root) {
        fun bind(item: MetasModel) {
            itemHolder.metaPrioridade.text = "- ${item.prioridade} - "
            itemHolder.metaTitulo.text = item.titulo
            itemHolder.metaDescricao.text = item.descricao
            if (item.concluida) {
                itemHolder.metaIsOk.setImageResource(R.drawable.ic_check_ok)
            } else {
                itemHolder.metaIsOk.setImageResource(R.drawable.ic_check_pending)
            }
            itemHolder.metaDescricaoView.visibility =
                if (item.isDetailVisible) View.VISIBLE else View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        return LineViewHolder(
            MetaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        holder.bind(metasList[position])
        holder.itemHolder.metaIsOk.setOnClickListener {
            metasList[position].concluida = !metasList[position].concluida
            notifyItemChanged(position)
            delegate.UpdateMeta()
        }
        holder.itemHolder.metaCardView.setOnClickListener {
            metasList[position].isDetailVisible = !metasList[position].isDetailVisible
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return metasList.size
    }

    fun setList(newMetas: List<MetasModel>) {
        metasList.clear()
        metasList.addAll(newMetas)
        notifyDataSetChanged()
    }

    fun addListItem(newMeta: MetasModel) {
        metasList.add(newMeta)
        notifyItemInserted(metasList.size)
    }
}