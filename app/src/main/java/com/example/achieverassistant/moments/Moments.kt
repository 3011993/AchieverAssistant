package com.example.achieverassistant.moments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.achieverassistant.R
import com.example.achieverassistant.databinding.ActivityMomentsBinding
import com.example.achieverassistant.moments.data.TheMoment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*


@AndroidEntryPoint
class Moments : AppCompatActivity() {

    lateinit var currentImagePath: String
    lateinit var pathToFile: String
    lateinit var recyclerAdapterForMoments: RecyclerAdapterForMoments
    lateinit var binding: ActivityMomentsBinding

    val momentsViewModel by viewModels<MomentsViewModel>()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moments)





        recyclerAdapterForMoments =
            RecyclerAdapterForMoments(RecyclerAdapterForMoments.OnMomentListener { theMoment ->
                val data = Intent(this, ADDEDITMoment::class.java)
                data.putExtra(EXTRA_EDIT_PICTURE_PATH, theMoment.image)
                data.putExtra(ADDEDITMoment.EXTRA_DATA_TITLE, theMoment.title)
                data.putExtra(ADDEDITMoment.EXTRA_DATA_DATE, theMoment.date)
                data.putExtra(
                    ADDEDITMoment.EXTRA_DATA_SHORT_DESCRIPTION_MOMENT, theMoment.descripton
                )

                editActivityLauncher.launch(data)
            })

        binding.recyclerviewMoments.adapter = recyclerAdapterForMoments
        binding.viewModel = momentsViewModel
        binding.lifecycleOwner = this


        momentsViewModel.allMoments.observe(this) { theMoments ->
            recyclerAdapterForMoments.submitList(
                theMoments
            )
        }
        binding.captureMoment.setOnClickListener { captureMoment() }


        val itemTouchHelper = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                momentsViewModel.deleteMoment(recyclerAdapterForMoments.getItemAt(viewHolder.absoluteAdapterPosition)!!)
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerviewMoments)


    }


    //this responsible for take shot and save everything
    @RequiresApi(Build.VERSION_CODES.N)
    private fun captureMoment() {

        requestPermissionsForCamera()

        val photoFile = createPhotoFile()

        if (photoFile != null) {
            pathToFile = photoFile.absolutePath
            val photoURL = FileProvider.getUriForFile(
                this, "com.example.camera_pictures.fileprovider", photoFile
            )
            //takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURL)
            getCameraImage.launch(photoURL)
        }

    }

    //this method for save picture
    @RequiresApi(Build.VERSION_CODES.N)
    private fun createPhotoFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

        val imageDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var file: File? = null
        try {
            file = File.createTempFile(timestamp, ".jpg", imageDirectory)
            currentImagePath = file.absolutePath
        } catch (e: Exception) {
            Log.e(TAG_MOMENTS, e.message.toString())
        }

        return file!!
    }


    companion object {

        const val EXTRA_PICTURE_PATH = "com.mooth.takepicture"
        const val EXTRA_EDIT_PICTURE_PATH = "com.mooth.edittakenpicture"

        const val CODE_REQUEST_PERMISSION_CAMERA = 55
        const val TAG_MOMENTS = "Moments Tag"


    }


    private val getCameraImage =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success == true) {
                val intent = Intent(this, ADDEDITMoment::class.java)
                intent.putExtra(EXTRA_PICTURE_PATH, pathToFile)
                saveMomentDetails.launch(intent)

                Log.i("intent image", "handle work")
            } else {
                Log.i("intent image", "can't handle work")
            }

        }


    private val saveMomentDetails =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.data != null) {
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageOfMoment =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_IMAGE_MOMENT)
                            .toString()
                    val titleOfMoment =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_TITLE).toString()
                    val dateOfMoment =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_DATE).toString()
                    val shortDescription =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_SHORT_DESCRIPTION_MOMENT)
                            .toString()
                    momentsViewModel.insertMoment(
                        TheMoment(
                            imageOfMoment, titleOfMoment, dateOfMoment, shortDescription
                        )
                    )
                    Toast.makeText(this, "Your Moment is Great", Toast.LENGTH_SHORT).show()
                    Log.i("intent image", "save intent worked")

                } else {
                    Toast.makeText(this, "Your Moment wasn't added", Toast.LENGTH_SHORT).show()
                    Log.i("intent image", "save intent can't work")

                }

            }

        }


    private val editActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                if (result.resultCode == Activity.RESULT_OK) {
                    val id = result.data!!.getIntExtra(ADDEDITMoment.EXTRA_DATA_ID_MOMENT, -1)
                    if (id == -1) {
                        Toast.makeText(this, "Moment Not Edited", Toast.LENGTH_SHORT).show()
                    }
                    val imageOfMoment =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_IMAGE_MOMENT)
                            .toString()
                    val titleOfMoment =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_TITLE).toString()
                    val dateOfMoment =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_DATE).toString()
                    val shortDescription =
                        result.data!!.getStringExtra(ADDEDITMoment.EXTRA_DATA_SHORT_DESCRIPTION_MOMENT)
                            .toString()
                    val theMoment =
                        TheMoment(imageOfMoment, titleOfMoment, dateOfMoment, shortDescription)
                    theMoment.id = id
                    momentsViewModel.updateMoment(theMoment)
                    Toast.makeText(this, "Moment Edited", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Moment Not Edited", Toast.LENGTH_SHORT).show()
                }
            }

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.moments_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_moments -> {
                momentsViewModel.deleteAllMoments()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//if (Build.VERSION.SDK_INT >= 23) {
//    requestPermissions(
//        arrayOf(
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        ), Moments.CODE_REQUEST_PERMISSION_CAMERA
//    )
//}

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermissionsForCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission For Camera Granted", Toast.LENGTH_SHORT).show()
        } else if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), CODE_REQUEST_PERMISSION_CAMERA
            )
            //preferred change it with snackBar with action
            Toast.makeText(this, "Permission for Camera was denied", Toast.LENGTH_SHORT).show()
        }
    }


}