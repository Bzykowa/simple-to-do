package com.example.to_do

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var tasks: ArrayList<Tasks>
    private val ADD_TASK_REQUEST = 1
    private lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //set up view model class for storing data during lifetime of app
        viewModel = ViewModelProviders.of(this).get(TaskListViewModel::class.java)
        //get existing tasks or set up empty array for them
        tasks =
            if (viewModel.getList().value != null) (viewModel.getList().value as ArrayList<Tasks>?)!! else ArrayList()
        //making RecyclerView like a list
        viewManager = LinearLayoutManager(this)
        //passing listeners for items in a list to adapter
        viewAdapter = TDListAdapter(
            tasks,
            { item: Tasks -> itemDelete(item) },
            { item: Tasks, done: Boolean -> taskChecker(item, done) })
        recyclerView = findViewById<RecyclerView>(R.id.todoList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        //set up listener for starting activity responsible for adding tasks
        fab.setOnClickListener { view ->
            val intent = Intent(this, Add::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
        //observer for data changes in RecyclerView
        viewModel.tasks.observe(this, Observer<List<Tasks>> { tasks ->
            tasks?.let { (viewAdapter as TDListAdapter).setTasks((it as ArrayList<Tasks>?)!!) }
        })
    }
    //accepting new entry to tasks
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val taskDesc = data?.getStringExtra(Add.TASK_DESC)
                val dueDateString = data?.getStringExtra(Add.DUE_DATE)
                try {
                    val dateCheck : LocalDate = LocalDate.parse(dueDateString)
                    val category = data?.getStringExtra(Add.CATEGORY)
                    val newTask = taskDesc?.let {
                        dueDateString?.let { it1 ->
                            category?.let { it2 ->
                                Tasks(
                                    taskDescription = it,
                                    taskDue = it1,
                                    taskCategory = it2,
                                    taskChecked = false
                                )
                            }
                        }
                    }
                    tasks.add(newTask!!)
                    viewModel.insert(newTask)
                    viewAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(this, "Not added due to wrong date", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    //add options menu that consists of sorting functions
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //set up functions in menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_date -> {
                sortDate(recyclerView)
                true
            }
            R.id.action_sort_todo -> {
                sortDesc(recyclerView)
                true
            }
            R.id.action_group_cat -> {
                sortCat()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //listener function that deals with task deletion
    private fun itemDelete(task: Tasks) {
        tasks.remove(task)
        viewModel.delete(task)
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Removed the clicked item", Toast.LENGTH_LONG).show()
    }

    //listener function for checking tasks
    private fun taskChecker(task: Tasks, result: Boolean) {
        val ind = tasks.indexOf(task)
        task.taskChecked = result
        tasks[ind] = task
        viewModel.update(task)
    }

    //sorting functions
    fun sortDate(view: View) {
        viewModel.sort(1)
        tasks.sortBy { it.taskDue }
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Sorted by date", Toast.LENGTH_LONG).show()
    }

    fun sortDesc(view: View) {
        viewModel.sort(0)
        tasks.sortBy { it.taskDescription }
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Sorted by description", Toast.LENGTH_LONG).show()
    }

    fun sortCat() {
        viewModel.sort(2)
        tasks.sortBy { it.taskCategory }
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Sorted by category", Toast.LENGTH_LONG).show()
    }

}

