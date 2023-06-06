package com.example.todo.presentor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.Utils
import com.example.todo.data.Task
import com.example.todo.removeStrikeThrough
import com.example.todo.strikeThrough

class TaskAdapter(
    private val taskClickInterface: TaskCLickInterface
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val allTasks = ArrayList<Task>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTv = itemView.findViewById<TextView>(R.id.tv_task_name)
        val dateTV = itemView.findViewById<TextView>(R.id.tv_time)
        val deleteIV = itemView.findViewById<ImageView>(R.id.iv_delete)
        val cbTask = itemView.findViewById<CheckBox>(R.id.cb_task)
        val pendingTv = itemView.findViewById<TextView>(R.id.tv_task_pending)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo_list,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskTv.text = allTasks[position].taskTitle

        if (allTasks[position].isCompleted){
            holder.taskTv.strikeThrough()
        }else{
            holder.taskTv.removeStrikeThrough()
        }

        if (Utils.isTimePastCurrentTime(allTasks[position].timeStamp) && !allTasks[position].isCompleted){
            holder.taskTv.setTextColor(holder.itemView.context.getColor(R.color.text_error))
            holder.pendingTv.visibility = View.VISIBLE
        }else{
            holder.taskTv.setTextColor(holder.itemView.context.getColor(R.color.text_primary))
            holder.pendingTv.visibility = View.GONE
        }

        holder.cbTask.isChecked = allTasks[position].isCompleted

        holder.dateTV.text = allTasks[position].timeStamp
        holder.deleteIV.setOnClickListener {
            taskClickInterface.onDeleteIconClick(allTasks[position])
        }

        holder.itemView.setOnClickListener {
            taskClickInterface.onTaskClick(allTasks[position])
        }

        holder.cbTask.setOnClickListener {
            if (holder.cbTask.isChecked){
                val task = allTasks[position]
                task.isCompleted = true
                taskClickInterface.onTaskCompleted(task)
            }else{
                val task = allTasks[position]
                task.isCompleted = false
                taskClickInterface.onTaskCompleted(task)
            }
        }
    }

    override fun getItemCount(): Int {
        return allTasks.size
    }

    fun updateList(newList: List<Task>) {
        allTasks.clear()
        allTasks.addAll(newList)
        notifyDataSetChanged()
    }
}

interface TaskCLickInterface {
    fun onDeleteIconClick(task: Task)
    fun onTaskClick(task: Task)
    fun onTaskCompleted(task: Task)
}
