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
import com.ggd.model.Issue.PostIssueDto
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentWriteBinding
import com.ggd.zendee.feature.login.LoginViewModel.Companion.TAG
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.map.IssueTag
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.feature.map.TagSelectorDialog
import com.ggd.zendee.utils.repeatOnStarted
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
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
        pictureUri = it
        binding.removeImgBtn.visibility = View.VISIBLE
    }

    val cameraImage = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){

    }


    override fun start() {

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        (activity as MainActivity).handleBottomNavigation(false)

        binding.tagBtn.background = getDrawableByTag(mainViewModel.selectedTag)

        binding.tagBtn.setOnClickListener {
            setDialog()
        }

        val TAG = "젠디"

        binding.deadtimeSeekbar.setOnSeekChangeListener(object : OnSeekChangeListener {

            override fun onSeeking(seekParams: SeekParams) {
                Log.d(TAG, "thumbPosition - ${seekParams.thumbPosition}")

                binding.deadtimeTxt.text = "이슈 잔류 시간 : ${getTimeString(timeList[seekParams.thumbPosition])}"
                viewModel.minute = timeList[seekParams.thumbPosition]

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
            pictureUri = null

        }

        binding.writeBtn.setOnClickListener {

            Log.d(TAG, "WriteFragment - picture : ${pictureUri?.uriToBitmap(requireContext())?.bitmapToMultipart()} ")

            if (binding.titleEdittxt.text.isEmpty()){
                Toast.makeText(context,"제목을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if (binding.contentEdittxt.text.isEmpty()){
                Toast.makeText(context,"내용을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if (mainViewModel.currentLocation != null){

                viewModel.postIssue(
                    PostIssueDto(
                        title = binding.titleEdittxt.text.toString(),
                        content = binding.contentEdittxt.text.toString(),
                        lat = mainViewModel.currentLocation!!.latitude.toFloat(),
                        lng = mainViewModel.currentLocation!!.longitude.toFloat(),
                        postImg = pictureUri?.uriToBitmap(requireContext())?.bitmapToMultipart(),
                        tag = when(mainViewModel.selectedTag){
                            IssueTag.HOT -> "뜨거움"
                            IssueTag.ALERT -> "경고"
                            IssueTag.HAPPY -> "재미"
                            IssueTag.NOTICE -> "공지"
                            IssueTag.ACTIVE -> "활동"
                            IssueTag.LOVE -> "사랑"
                            IssueTag.LUCKY -> "행운"
                        },
                        deleted_at = viewModel.minute
                    )
                )
            }
            else {
                Log.d(TAG,"currentLocation - null")
            }

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

    private fun Bitmap.bitmapToMultipart(): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val requestFile =
            RequestBody.create("image/png".toMediaTypeOrNull(), byteArrayOutputStream.toByteArray())
        Log.d(TAG, "bitmapToMultipart: ${pictureUri?.path}")
        return MultipartBody.Part.createFormData("img", "image.png", requestFile)
    }

    private fun handleEvent(event: WriteViewModel.Event) =
        when (event) {
            WriteViewModel.Event.SuccessPostIssue -> { findNavController().popBackStack() }
            is WriteViewModel.Event.UnknownException -> {
                Log.d(TAG, "ERROR ERROR ERROR ERROR - ${event.error}")
                Toast.makeText(context,"이슈 작성 실패",Toast.LENGTH_SHORT)
            }
        }



}