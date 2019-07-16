package com.example.to_do

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_add.*

//activity that gathers data for task entry and sends them back to main activity
class Add : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    fun addClicked(view : View){
        val taskDescription = getDesc.text.toString()
        val dueDate = getDate.text.toString()
        val category = spinner.selectedItem.toString()

        if (!taskDescription.isEmpty()) {
            val result = Intent()
            result.putExtra(TASK_DESC, taskDescription)
            result.putExtra(DUE_DATE, dueDate)
            result.putExtra(CATEGORY, category)
            setResult(Activity.RESULT_OK, result)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    companion object {
        val TASK_DESC = "todo"
        val DUE_DATE = "date"
        val CATEGORY = "category"
    }
}
