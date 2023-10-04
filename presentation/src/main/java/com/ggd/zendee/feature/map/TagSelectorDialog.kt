package com.ggd.zendee.feature.map

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.ggd.zendee.R
import com.ggd.zendee.databinding.DialogIssueBinding
import com.ggd.zendee.databinding.DialogTagSelectorBinding
import com.ggd.zendee.feature.login.LoginViewModel.Companion.TAG
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.time.LocalDate

class TagSelectorDialog(private val context : Context) {

    private lateinit var binding : DialogTagSelectorBinding
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    val tagAdapter: TagRecyclerviewAdapter by lazy { TagRecyclerviewAdapter() }

    var currentPosition = 10000

    fun setOnClicklistener(listener : OnDialogClickListener){
        onClickListener = listener
    }

    fun setDialog(){

        binding = DialogTagSelectorBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)

        dialog.window!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)

        binding.selectBtn.setOnClickListener {
            onClickListener.onClicked(currentPosition)
            dialog.dismiss()

        }

    }

    fun showDialog(){

        dialog.show()

    }

    fun setRecyclerview(tagDataSet : List<IssueTag> ){

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        binding.selectorRecyclerview.apply {

            layoutManager = linearLayoutManager
            adapter = tagAdapter
        }

        Log.d(TAG, "setDialog: smoothScrollToPosition")

        context.dialogResize(dialog,1F,0.3F)

        tagAdapter.submitList(tagDataSet)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.selectorRecyclerview)

        val zoomListener = ZoomCenterItemScrollListener(linearLayoutManager)

        binding.selectorRecyclerview.scrollToPosition(7773)
        binding.selectorRecyclerview.smoothScrollToPosition(7778)


        zoomListener.setChangedListener(object : ZoomCenterItemScrollListener.OnChangedListener{

            override fun onChanged(position: Int) {
                binding.guideTxt.setTextColor(binding.root.resources.getColorStateList(getColorByTag(tagDataSet[position%7])))
                binding.selectBtn.setTextColor(binding.root.resources.getColorStateList(getColorByTag(tagDataSet[position%7])))
                //Log.d("젠디","MapFragment - onChanged() - ${tagDataSet[position%7]}")
                currentPosition = position%7
                Log.d(TAG, "onChanged: ${position}")
            }

        })
//
//        binding.selectorRecyclerview.smoothScrollToPosition(7776)

        binding.selectorRecyclerview.addOnScrollListener(zoomListener)
    }


    private fun getColorByTag(issueTag: IssueTag) : Int {

        when(issueTag){
            IssueTag.HOT -> return R.color.hot
            IssueTag.ALERT -> return R.color.alert
            IssueTag.HAPPY -> return R.color.happy
            IssueTag.NOTICE -> return R.color.notice
            IssueTag.ACTIVE -> return R.color.active
            IssueTag.LOVE -> return R.color.love
            IssueTag.LUCKY -> return R.color.lucky
        }

    }

    interface OnDialogClickListener{
        fun onClicked(position : Int)

    }

    fun Context.dialogResize(dialog: Dialog, width: Float, height: Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

}