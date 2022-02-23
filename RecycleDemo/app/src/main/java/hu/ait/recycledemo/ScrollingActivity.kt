package hu.ait.recycledemo

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import hu.ait.recycledemo.adapter.TodoRecyclerAdapter
import hu.ait.recycledemo.data.AppDatabase
import hu.ait.recycledemo.data.Todo
import hu.ait.recycledemo.databinding.ActivityScrollingBinding
import hu.ait.recycledemo.dialog.TodoDialog
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity(), TodoDialog.TodoHandler {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: TodoRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            TodoDialog().show(supportFragmentManager, "TODO_DIALOG")
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {

        val todoList = AppDatabase.getInstance(this).todoDao().getAllTodo()

        adapter = TodoRecyclerAdapter(this)
        binding.recylerTodo.adapter = adapter

//        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        binding.recylerTodo.addItemDecoration(divider)

//        binding.recylerTodo.layoutManager = GridLayoutManager(this, 2)

        val todoLiveData = AppDatabase.getInstance(this).todoDao().getAllTodo()
        todoLiveData.observe(this, {items ->
            adapter.submitList(items)
        })
        binding.recylerTodo.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        //val touchCallbackList = TodoRecyclerTouchCallback(adapter)
        //val itemTouchHelper = ItemTouchHelper(touchCallbackList)
        //itemTouchHelper.attachToRecyclerView(binding.recyclerTodo)
    }



override fun todoCreated(todo: Todo) {

    thread {
        AppDatabase.getInstance(this).todoDao().addTodo(todo)
    }
}
}