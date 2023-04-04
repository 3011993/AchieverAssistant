package com.example.achieverassistant.moments

import android.graphics.Bitmap
import android.view.ViewGroup
import android.view.LayoutInflater
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.achieverassistant.databinding.CardviewMomentsBinding
import com.example.achieverassistant.moments.data.TheMoment

class RecyclerAdapterForMoments(private val clickListener: OnMomentListener) :
    ListAdapter<TheMoment, RecyclerAdapterForMoments.ViewHolderMoment>(diffCallBack) {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolderMoment {
        return ViewHolderMoment.from(viewGroup)
    }

    override fun onBindViewHolder(viewHolderMoment: ViewHolderMoment, position: Int) {
        val theMoment = getItem(position)
        viewHolderMoment.bind(clickListener, theMoment)
    }


    class ViewHolderMoment(private val binding: CardviewMomentsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(clickListener: OnMomentListener, theMoment: TheMoment) {
            binding.textviewTitle.text = theMoment.title
            binding.textviewDate.text = theMoment.date
            binding.shortdescriptionMoment.text = theMoment.descripton
            val file = theMoment.image
            val myBitmap = BitmapFactory.decodeFile(theMoment.image)

            val rotatedBitmap = getRotatedBitmap(myBitmap, file)

            binding.imageviewMoment.setImageBitmap(rotatedBitmap)


            binding.moment = theMoment
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }


        companion object {
            fun from(viewGroup: ViewGroup): ViewHolderMoment {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = CardviewMomentsBinding.inflate(layoutInflater, viewGroup, false)
                return ViewHolderMoment(binding)
            }
        }


        private fun getRotatedBitmap(source: Bitmap, value: String): Bitmap {
            val intf = ExifInterface(value)
            val orientationInt = intf.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            val rotatedBitmap = when (orientationInt) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(source, 90F)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(source, 180F)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(source, 270F)
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

    }


    fun getItemAt(position: Int): TheMoment? {
        return getItem(position)
    }

    class OnMomentListener(val clickListener: (theMoment: TheMoment) -> Unit) {
        fun onClick(theMoment: TheMoment) = clickListener(theMoment)
    }

    companion object {
        private val diffCallBack: DiffUtil.ItemCallback<TheMoment> =
            object : DiffUtil.ItemCallback<TheMoment>() {
                override fun areItemsTheSame(oldmoment: TheMoment, newmoment: TheMoment): Boolean {
                    return oldmoment.id == newmoment.id
                }

                override fun areContentsTheSame(
                    oldmoment: TheMoment,
                    newmoment: TheMoment
                ): Boolean {
                    return oldmoment.image == newmoment.image && oldmoment.title == newmoment.title && oldmoment.descripton == newmoment.descripton && oldmoment.date == newmoment.date
                }
            }
    }

}