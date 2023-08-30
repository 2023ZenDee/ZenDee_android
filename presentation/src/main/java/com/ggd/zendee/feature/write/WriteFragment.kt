package com.ggd.zendee.feature.write

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentWriteBinding
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.map.IssueTag
import com.ggd.zendee.feature.map.TagSelectorDialog
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class WriteFragment : BaseFragment<FragmentWriteBinding,WriteViewModel>(R.layout.fragment_write) {
    override val viewModel: WriteViewModel by viewModels()
    private val mainViewModel : MainViewModel by activityViewModels()
    val timeList = listOf<Int>(5,10,20,40,60,120,240,480,720,1080,1440,2880,4320,5760)

    var pictureUri : Uri? = null


    val requestImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){
        Log.d("젠디","Uri - ${it}")

//        val bitmap = it?.uriToBitmap(requireContext())
//        binding.contentImg.setImageBitmap(bitmap)
        binding.contentImg.setImageURI(it)
        binding.removeImgBtn.visibility = View.VISIBLE

    }

    val requestPicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){
        Log.d("젠디","Uri - ${it}")

        if (it){
            binding.contentImg.setImageURI(pictureUri)
        }

    }

    override fun start() {

        (activity as MainActivity).handleBottomNavigation(false)

        binding.tagBtn.background = getDrawableByTag(mainViewModel.selectedTag)

        binding.tagBtn.setOnClickListener {
            setDialog()
        }

        val TAG = "젠디"

        binding.deadtimeSeekbar.setOnSeekChangeListener(object : OnSeekChangeListener {

            override fun onSeeking(seekParams: SeekParams) {
                Log.d(TAG, "thumbPosition - ${seekParams.thumbPosition}");
                binding.deadtimeTxt.text = "이슈 잔류 시간 : ${getTimeString(timeList[seekParams.thumbPosition])}"
            }
            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {}
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

//        binding.writeBtn.setOnClickListener {
//
//            requestPicture.launch("")
//
//        }

        binding.pictureBtn.setOnClickListener {

//                request.launch("image/*")
            requestImage.launch("image/*")

        }

        binding.removeImgBtn.setOnClickListener {

            binding.contentImg.setImageBitmap(null)
            binding.removeImgBtn.visibility = View.GONE

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).handleBottomNavigation(true)

    }

    private fun Uri.uriToBitmap(context: Context): Bitmap {
        return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            true -> {
                val source = ImageDecoder.createSource(context.contentResolver, this)
                ImageDecoder.decodeBitmap(source)
            }
            else -> {
                MediaStore.Images.Media.getBitmap(context.contentResolver, this)
            }
        }
    }

    fun setDialog(){

        val dialog = TagSelectorDialog(requireContext())
        dialog.setDialog()

        dialog.setRecyclerview(viewModel.tagDataSet)

        dialog.setOnClicklistener(object : TagSelectorDialog.OnDialogClickListener{

            override fun onClicked(position: Int) {
                Log.d("젠디", "onClicked: tag - ${viewModel.tagDataSet[position]} ")
                mainViewModel.selectedTag = viewModel.tagDataSet[position]

                binding.tagBtn.background = getDrawableByTag(mainViewModel.selectedTag)

            }

        })

        dialog.showDialog()

    }

    fun getTimeString(time : Int) : String{

        if (time < 60) {
            return "${time}분"
        }
        else if(time < 1440){
            return "${time/60}시간"
        }
        else{
            return "${time/1440}일"
        }
    }



    private fun getDrawableByTag(issueTag: IssueTag) : Drawable? {

        when(issueTag){
            IssueTag.HOT -> return binding.root.context.getDrawable(R.drawable.hot_tag)
            IssueTag.ALERT -> return binding.root.context.getDrawable(R.drawable.alert_tag)
            IssueTag.HAPPY -> return binding.root.context.getDrawable(R.drawable.happy_tag)
            IssueTag.NOTICE -> return binding.root.context.getDrawable(R.drawable.notice_tag)
            IssueTag.ACTIVE -> return binding.root.context.getDrawable(R.drawable.active_tag)
            IssueTag.LOVE -> return binding.root.context.getDrawable(R.drawable.love_tag)
            IssueTag.LUCKY -> return binding.root.context.getDrawable(R.drawable.lucky_tag)
        }
    }

}