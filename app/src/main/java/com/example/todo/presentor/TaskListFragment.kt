package com.example.todo.presentor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.Task
import com.example.todo.databinding.FragmentToListBinding
import com.example.todo.presentor.adapter.TaskAdapter
import com.example.todo.presentor.adapter.TaskCLickInterface
import com.example.todo.presentor.viewmodel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TaskListFragment : Fragment() {

    private var _binding: FragmentToListBinding? = null
    private var viewModel:TaskViewModel?=null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentToListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        attachObserver()
        setupAdapter()
    }

    private fun attachObserver() {
        viewModel?.allTasks?.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()){
                    binding.rvTasks.visibility = VISIBLE
                    binding.layoutNoData.root.visibility = GONE
                }else{
                    binding.rvTasks.visibility = GONE
                    binding.layoutNoData.root.visibility = VISIBLE
                }
                (binding.rvTasks.adapter as TaskAdapter).updateList(it)
            }
        })
    }

    private fun init() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[TaskViewModel::class.java]


    }

    private fun setupAdapter() {
        binding.rvTasks.adapter = TaskAdapter(object :TaskCLickInterface{
            override fun onDeleteIconClick(task: Task) {
                showDeleteDialog(task)
            }

            override fun onTaskClick(task: Task) {

            }

            override fun onTaskCompleted(task: Task) {
                viewModel?.updateTask(task)
            }

        })
    }

    private fun showDeleteDialog(task: Task) {
       val dialog =  MaterialAlertDialogBuilder(requireContext())
            .setTitle("Warning")
            .setMessage("Do you want to delete ${task.taskTitle}, this action can't be undone.")
            .setPositiveButton("OK") { p0, p1 -> deleteTask(task)}
            .setNegativeButton("Cancel"
            ) { p0, p1 ->  p0.dismiss()}
           .show()
    }

    private fun deleteTask(task: Task) {
        viewModel?.deleteTask(task)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as TodoActivity).showFab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}