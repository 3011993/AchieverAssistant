package com.example.achieverassistant.moments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.achieverassistant.R
import com.example.achieverassistant.databinding.ActivityAddeditmomentBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ADDEDITMoment : AppCompatActivity() {


    lateinit var file: String
    lateinit var intf : ExifInterface

    lateinit var binding : ActivityAddeditmomentBinding
     private var orientationInt : Int? = null

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_addeditmoment)



        val data = intent
        if (data.hasExtra(Moments.EXTRA_PICTURE_PATH)) {
            title = "Add your Moment"
            file = data.getStringExtra(Moments.EXTRA_PICTURE_PATH).toString()
            ///date of the moment
//            var intf: ExifInterface?
            try {
                intf = ExifInterface(file)
                val formattedDate = SimpleDateFormat("EEE, MMM d, ''yyyy", Locale.US).format(Date())
                binding.edittextDateMoment.setText(formattedDate)
                binding.edittextDateMoment.isEnabled = false
            } catch (e: IOException) {

                e.printStackTrace()
            }




            val bitmap = BitmapFactory.decodeFile(data.getStringExtra(Moments.EXTRA_PICTURE_PATH))
            val rotatedBitmap = getRotatedBitmap(bitmap)
            binding.imageviewMoment.setImageBitmap(rotatedBitmap)

        } else if (data.hasExtra(Moments.EXTRA_EDIT_PICTURE_PATH)){
            title = "Edit Your Moment"
            file = data.getStringExtra(Moments.EXTRA_EDIT_PICTURE_PATH).toString()
            ///date of the moment
//            var intf: ExifInterface?
            try {
                intf = ExifInterface(file)
                //Dispaly dateString. You can do/use it your own way
                val formattedDate = SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(Date())
                binding.edittextDateMoment.setText(formattedDate)
                binding.edittextDateMoment.isEnabled= false

            } catch (e: IOException) {

                e.printStackTrace()
            }

            val bitmap = BitmapFactory.decodeFile(data.getStringExtra(Moments.EXTRA_EDIT_PICTURE_PATH))
            val rotatedBitmap = getRotatedBitmap(bitmap)
            binding.imageviewMoment.setImageBitmap(rotatedBitmap)
        }
        binding.setMoment.setOnClickListener {  recordMoment() }
    }


    private fun recordMoment() {
        val nameOfMoment = binding.edittextYourmomentName.text.toString()
        val dateOfMoment = binding.edittextDateMoment.text.toString()
        val shortDescription = binding.edittextShortDescription.text.toString()
        if (nameOfMoment.isEmpty()) {
            Toast.makeText(applicationContext, "Please Add your Moment name", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val saveMomentIntent = Intent()
        saveMomentIntent.putExtra(EXTRA_DATA_IMAGE_MOMENT, file)
        saveMomentIntent.putExtra(EXTRA_DATA_TITLE, nameOfMoment)
        saveMomentIntent.putExtra(EXTRA_DATA_DATE, dateOfMoment)
        saveMomentIntent.putExtra(EXTRA_DATA_SHORT_DESCRIPTION_MOMENT, shortDescription)
        val id = intent.getIntExtra(EXTRA_DATA_ID_MOMENT, -1)
        if (id != -1) {
            saveMomentIntent.putExtra(EXTRA_DATA_ID_MOMENT, id)
        }
        setResult(RESULT_OK, saveMomentIntent)
        finish()
    }


    private fun getRotatedBitmap(source: Bitmap) : Bitmap {
        orientationInt = intf.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        val rotatedBitmap = when(orientationInt){
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(source, 90F)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(source,180F)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(source,270F)
            ExifInterface.ORIENTATION_NORMAL -> source
            else -> source
        }
        return rotatedBitmap!!
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }


    companion object {

        const val EXTRA_DATA_ID_MOMENT = "com.mooth.achieverassistant.moments.idmoment"
        const val EXTRA_DATA_TITLE = "com.mooth.achieverassistant.moments.titlemoment"
        const val EXTRA_DATA_DATE = "com.mooth.achieverassistant.moments.datemoment"
        const val EXTRA_DATA_IMAGE_MOMENT = "com.mooth.achieverassistant.moments.imagemoment"
        const val EXTRA_DATA_SHORT_DESCRIPTION_MOMENT =
            "com.mooth.achieverassistant.moments.shortdesciption"
    }
}

