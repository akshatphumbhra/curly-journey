package hu.ait.stopwatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.stopwatch.data.Time
import hu.ait.stopwatch.databinding.MarkTimeBinding

class StopwatchAdapter: RecyclerView.Adapter<StopwatchAdapter.ViewHolder> {

    val context: Context
    var timeList = mutableListOf<Time>()

    constructor(context: Context) : super() {
        this.context = context
    }

    inner class ViewHolder(val timeBinding: MarkTimeBinding) : RecyclerView.ViewHolder(timeBinding.root) {
        fun bind(time: Time) {
            timeBinding.time.text = time.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val markTimeBinding = MarkTimeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(markTimeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTime = timeList[holder.adapterPosition]
        holder.bind(currentTime)
//        holder.timeBinding.btnDel.setOnClickListener {
//            deleteTodo(holder.adapterPosition)
//        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    fun addTime(newTime: Time) {
        timeList.add(newTime)
        notifyItemInserted(timeList.lastIndex)
    }

    fun deleteTime(index: Int) {
        timeList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clearTime() {
        timeList.clear()
        notifyDataSetChanged()
    }
}