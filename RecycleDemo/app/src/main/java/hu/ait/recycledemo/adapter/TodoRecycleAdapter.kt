package hu.ait.recycledemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ait.recycledemo.R
import hu.ait.recycledemo.ScrollingActivity
import hu.ait.recycledemo.data.AppDatabase
import hu.ait.recycledemo.data.Todo
import hu.ait.recycledemo.databinding.TodoRowBinding
//import hu.ait.recycledemo.touch.TodoTouchHelperCallback
import java.util.*
import kotlin.concurrent.thread

class TodoRecyclerAdapter : ListAdapter<Todo, TodoRecyclerAdapter.ViewHolder>{ //}, TodoTouchHelperCallback {

    val context: Context

    constructor(context: Context) : super(TodoDiffCallback()) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val todoRowBinding = TodoRowBinding.inflate(LayoutInflater.from(context),
            parent, false)
        return ViewHolder(todoRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTodo = getItem(holder.adapterPosition)
        holder.bind(currentTodo)

        holder.todoRowBinding.btnDel.setOnClickListener {
            deleteTodo(holder.adapterPosition)
        }
    }


    fun deleteTodo(index: Int) {
        thread {
            AppDatabase.getInstance(context).todoDao().deleteTodo(getItem(index))
        }
    }

//    override fun onDismissed(position: Int) {
//        deleteTodo(position)
//    }
//
//    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
//        notifyItemMoved(fromPosition, toPosition)
//    }

    inner class ViewHolder(val todoRowBinding: TodoRowBinding) : RecyclerView.ViewHolder(todoRowBinding.root) {
        fun bind(todo: Todo) {
            todoRowBinding.tvDate.text = todo.createDate
            todoRowBinding.cbDone.text = todo.title
            todoRowBinding.cbDone.isChecked = todo.done
        }
    }
}


class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}