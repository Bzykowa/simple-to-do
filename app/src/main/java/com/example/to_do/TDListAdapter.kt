package com.example.to_do

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.to_do_list_item.view.*

//adapter for RecyclerView (scrollable list)
class TDListAdapter(
    private var tasks: ArrayList<Tasks>,
    val clickListener: (Tasks) -> Unit,
    val doneCheck: (Tasks, Boolean) -> Unit
) : RecyclerView.Adapter<TDListAdapter.ItemViewholder>() {
    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.to_do_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(tasks.get(position), clickListener, doneCheck)
    }

    fun setTasks(words: ArrayList<Tasks>) {
        this.tasks = words
        notifyDataSetChanged()
    }

    //inner class that deals with binding data to single item view in a list
    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var commands: Array<String> = arrayOf(
            "Contact someone",
            "Fix something",
            "Go somewhere",
            "Travel preparations",
            "Work related",
            "Look-up something",
            "Practice a skill"
        )

        fun bind(item: Tasks, clickListener: (Tasks) -> Unit, doneCheck: (Tasks, Boolean) -> Unit) {
            setIsRecyclable(false)
            when (item.taskCategory) {
                commands[0] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_call,
                        Resources.getSystem().newTheme()
                    )
                )
                commands[1] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_preferences,
                        Resources.getSystem().newTheme()
                    )
                )
                commands[2] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_directions,
                        Resources.getSystem().newTheme()
                    )
                )
                commands[3] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_dialog_map,
                        Resources.getSystem().newTheme()
                    )
                )
                commands[4] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_agenda,
                        Resources.getSystem().newTheme()
                    )
                )
                commands[5] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_search,
                        Resources.getSystem().newTheme()
                    )
                )
                commands[6] -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_edit,
                        Resources.getSystem().newTheme()
                    )
                )
                else -> itemView.imageView.setImageDrawable(
                    Resources.getSystem().getDrawable(
                        android.R.drawable.ic_menu_help,
                        Resources.getSystem().newTheme()
                    )
                )
            }
            itemView.todoText.text = item.taskDescription
            itemView.dateText.text = item.taskDue
            itemView.checkBox.isChecked = item.taskChecked
            itemView.checkBox.setOnCheckedChangeListener { checkBox, isChecked ->
                doneCheck(item, isChecked)
            }
            itemView.setOnLongClickListener {
                clickListener(item)
                true
            }
        }
    }
}

