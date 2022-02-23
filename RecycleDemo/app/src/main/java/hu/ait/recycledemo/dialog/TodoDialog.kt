package hu.ait.recycledemo.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.ait.recycledemo.data.Todo
import hu.ait.recycledemo.databinding.TodoDialogBinding
import java.lang.RuntimeException
import java.util.*

class TodoDialog : DialogFragment(){

    public interface TodoHandler {
        fun todoCreated(todo: Todo)
    }

    lateinit var todoHandler: TodoHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TodoHandler) {
            todoHandler = context
        } else {
            throw RuntimeException("The Activity is not implementing the TodoHandler interface")
        }
    }

    lateinit var todoDialogBinding: TodoDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New Todo")

        todoDialogBinding = TodoDialogBinding.inflate(layoutInflater)
        builder.setView(todoDialogBinding.root)

        builder.setPositiveButton("Save") {
                dialog, which ->

            val newTodo = Todo(
                null,
                todoDialogBinding.etTodoText.text.toString(),
                Date(System.currentTimeMillis()).toString(),
                todoDialogBinding.cbTodoDone.isChecked
            )
            todoHandler.todoCreated(newTodo)
        }

        builder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return builder.create()
    }

}