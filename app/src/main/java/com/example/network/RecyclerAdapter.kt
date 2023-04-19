import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.network.DataModel2
import com.example.network.R

class RecyclerAdapter(private val itemClicked: CodecItemClicked, private val list: ArrayList<DataModel2>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycleritem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.title.text = item.title
        holder.desc.text = item.desc
        if(item.title.lowercase().contains("decoder")) {
            holder.type.text = "Decoder"
        }else{
            holder.type.text = "Encoder"
        }
        holder.layout.setOnClickListener{
            itemClicked.onItemClick(list, position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val type: TextView = itemView.findViewById(R.id.type)
        var layout : ConstraintLayout = itemView.findViewById(R.id.itemlayout)
    }

    interface CodecItemClicked {
        fun onItemClick(list: List<DataModel2>, position: Int){
        }
    }
}

