package com.example.achieverassistant.dailyPlan


import android.app.*
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.achieverassistant.*
import com.example.achieverassistant.dailyPlan.dialogs.DialogForDeleteAllTasks
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import com.example.achieverassistant.databinding.DailyTasksLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date


@AndroidEntryPoint
class DailyTasksFragment : Fragment() {

    private lateinit var binding: DailyTasksLayoutBinding
    private lateinit var recyclerAdapterForDailyTask: RecyclerViewForDailyPlan


    private val dailyTasksLiveModel by activityViewModels<DailyTasksLiveModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.daily_tasks_layout, container, false)


        //click listener to edit and delete items from recycler view
        val clickListener = RecyclerViewForDailyPlan.OnDailyTasksListener({ dailyTask ->
            val data = Intent(activity, ADDEDITDailyTasks::class.java)
            data.putExtra(ADDEDITDailyTasks.EXTRA_DATA_ID_CURRENT_TASK, dailyTask.id)
            data.putExtra(ADDEDITDailyTasks.EXTRA_DATA_CURRENT_TEXT, dailyTask.currentTask)
            data.putExtra(ADDEDITDailyTasks.EXTRA_DATA_TIME_CURRENT_TEXT, dailyTask.currentTextTime)
            editActivityResultLauncher.launch(data)
        }, { id ->
            val alert = AlertDialog.Builder(activity)
            alert.setMessage("are you sure to delete this task?")
            alert.setCancelable(true)
            alert.setTitle("Delete your task")
            alert.setPositiveButton("yes") { dialog, which ->
                dailyTasksLiveModel.deleteDailyTask(id)
                Toast.makeText(activity, "Task is Deleted", Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("No") { dialog, which ->
                dialog.cancel()
                Toast.makeText(activity, "Task is Not Deleted", Toast.LENGTH_SHORT).show()
                //here to observe daily tasks
            }
            alert.show()
        })

        recyclerAdapterForDailyTask = RecyclerViewForDailyPlan(clickListener)




        binding.recyclerviewforDailytasks.adapter = recyclerAdapterForDailyTask
        binding.lifecycleOwner = this
        binding.dailyViewModel = dailyTasksLiveModel

        dailyTasksLiveModel.allDailyTasks.observe(viewLifecycleOwner) { dailyTasks ->
            recyclerAdapterForDailyTask.submitList(dailyTasks)
        }

        binding.buttonAddNewTask.setOnClickListener {
          it.findNavController().navigate(DailyTasksFragmentDirections.actionDailyTasksFragmentToAddEditDailyTaskScreen())
        }


        //request notifications new apis
        requestNotificationsAPI()
        createNotificationRight()


        //new menu apis
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.daily_tasks_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.deleteAllTasks -> deleteAllTasksWithDialog()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)



        return binding.root
    }

    //Delete All Tasks With AlertDialog
    private fun deleteAllTasksWithDialog() {
        val alertDialog = DialogForDeleteAllTasks()
        alertDialog.show(childFragmentManager, "Alert Dialog")
    }


    //Activity Result Launcher Apis for add new task
    private val addActivityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            if (result.resultCode == Activity.RESULT_OK) {
                val currentTask =
                    result.data!!.getStringExtra(ADDEDITDailyTasks.EXTRA_DATA_CURRENT_TEXT)
                        .toString()
                val timeTask =
                    result.data!!.getSerializableExtra(ADDEDITDailyTasks.EXTRA_DATA_TIME_CURRENT_TEXT) as Date

                val dailyTasks = DailyTasks(currentTask, timeTask)
                dailyTasksLiveModel.insertDailyTask(dailyTasks)
                Toast.makeText(requireActivity(), "Task Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "DailyTask Not Added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Activity Result Launcher Apis for edit task
    private val editActivityResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.data != null) {
                if (result.resultCode == Activity.RESULT_OK) {

                    val id =
                        result.data!!.getIntExtra(ADDEDITDailyTasks.EXTRA_DATA_ID_CURRENT_TASK, -1)
                    if (id == -1) {
                        Toast.makeText(activity, "Task not edited", Toast.LENGTH_SHORT).show()
                    }
                    val currentTask =
                        result.data!!.getStringExtra(ADDEDITDailyTasks.EXTRA_DATA_CURRENT_TEXT)
                            .toString()
                    val timeOfTask =
                        result.data!!.getSerializableExtra(ADDEDITDailyTasks.EXTRA_DATA_TIME_CURRENT_TEXT) as Date
                    val dailyTasks = DailyTasks(currentTask, timeOfTask)
                    dailyTasks.id = id
                    dailyTasksLiveModel.updateDailyTask(dailyTasks)
                    Toast.makeText(activity, "Task Edited", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "DailyTask Not Edited", Toast.LENGTH_SHORT).show()
                }
            }
        }


    //create notification channel
    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel =
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply { setShowBadge(false) }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time For Do your task!"
            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    // handle permission with new apis
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { hasGranted ->
            hasNotificationPermissionGranted = hasGranted
            if (!hasGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                        showNotificationPermissionRationale()
                    } else {
                        showSettingDialog()
                    }
                }
            }
        }

    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Notification Permission")
            .setMessage("Notification permission is required, Please allow notification permission from setting")
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showNotificationPermissionRationale() {

        MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Alert")
            .setMessage("Notification permission is required, to show notification")
            .setPositiveButton("Ok") { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    var hasNotificationPermissionGranted = false

    private fun requestNotificationsAPI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            hasNotificationPermissionGranted = true
        }

    }

    private fun createNotificationRight() {
        if (checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PermissionChecker.PERMISSION_GRANTED
        ) {
            createChannel(channelDailyNotificationID, channelDailyNotificationName)
        }
    }

}