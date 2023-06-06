package com.example.todo.presentor

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.presentor.viewmodel.TaskViewModel
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null

    private val binding get() = _binding!!

    private var viewModel: TaskViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as TodoActivity).hideFab()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.btnAdd.setOnClickListener {
            verifyAndAddTask()
        }

        binding.etTaskTime.onFocusChangeListener = object :OnFocusChangeListener{
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1){
                    showTimePicker()
                }
            }
        }


    }

    private fun showTimePicker() {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireContext(),
            { view, hourOfDay, minute ->

                var am_pm = ""
                var hourDay = hourOfDay
                when {hourDay == 0 -> { hourDay += 12
                    am_pm = "AM"
                }
                    hourDay == 12 -> am_pm = "PM"
                    hourDay > 12 -> { hourDay -= 12
                        am_pm = "PM"
                    }
                    else -> am_pm = "AM"
                }

                binding.etTaskTime.setText(
                    String.format(
                        "%02d : %02d",
                        hourDay,
                        minute
                    )
                )

                if (am_pm=="AM"){
                    binding.spinnerTime.setSelection(0)
                }else{
                    binding.spinnerTime.setSelection(1)
                }
            }, hour, minute, false
        )


        mTimePicker.show()
    }

    private fun verifyAndAddTask() {
        if (binding.etTaskTitle.text.isNullOrBlank()) {
            binding.tilTaskTitle.error = "Please enter task name"
            return
        }

        if (binding.etTaskTime.text.isNullOrBlank()) {
            binding.tilTaskTime.error = "Please select task due time."
            return
        }
        viewModel?.addTask(
            Task(
                taskTitle = binding.etTaskTitle.text.toString(),
                timeStamp = binding.etTaskTime.text.toString() + " " + binding.spinnerTime.selectedItem,
                isCompleted = false
            )
        )

        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }

    private fun init() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[TaskViewModel::class.java]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}