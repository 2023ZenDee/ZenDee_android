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
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.databinding.DialogIssueBinding
import com.ggd.zendee.feature.login.LoginViewModel.Companion.TAG
import java.time.LocalDate
import kotlin.math.log

class IssueDialog(private val context : Context) {

    private lateinit var binding : DialogIssueBinding
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    private val issueDialogAdapter = IssueDialogPagerAdapter()

    fun setOnClicklistener(listener : OnDialogClickListener){
        onClickListener = listener
    }

    fun setLeftArrow(visible : Boolean){
        if(visible) binding.arrowLeftBtn.visibility = View.VISIBLE
        else binding.arrowLeftBtn.visibility = View.INVISIBLE
    }

    fun setRightArrow(visible : Boolean){
        if(visible) binding.arrowRightBtn.visibility = View.VISIBLE
        else binding.arrowRightBtn.visibility = View.INVISIBLE
    }
    fun showDialog(list : List<IssueModel>, startPosition : Int){

        Log.d("젠디", "showDialog - startPosition : $startPosition ")

        binding = DialogIssueBinding.inflate(LayoutInflater.from(context))

        if (0 >= startPosition) setLeftArrow(false)
        else setLeftArrow(true)

        if (startPosition >= list.size - 1) setRightArrow(false)
        else setRightArrow(true)

        dialog.setContentView(binding.root)
        dialog.show()

        binding.issueHorizontalPager.adapter = issueDialogAdapter
        binding.issueHorizontalPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        issueDialogAdapter.submitList(list)

        binding.issueHorizontalPager.setCurrentItem(startPosition,false)

        issueDialogAdapter.setOnDialogClickListener( object : IssueDialogPagerAdapter.OnItemClickListener {

            override fun onClick(issue: IssueModel) {

                onClickListener.onClicked(issue)
                dialog.dismiss()

            }

        })

        binding.issueHorizontalPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {

                onClickListener.onChanged(position, issueDialogAdapter.itemCount)

                super.onPageSelected(position)
            }

//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                setRightArrow(false)
//                setLeftArrow(false)
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d(TAG, "onPageScrollStateChanged: ${state}")
                if(state != 0){
                    setRightArrow(false)
                    setLeftArrow(false)
                }
                super.onPageScrollStateChanged(state)
            }

        })

        context.dialogResize(dialog,1F,0.15F)
        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        val params = dialog.window!!.attributes
        params.y = 200
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.setOnDismissListener {

            onClickListener.onDismissed()
        }

    }

    interface OnDialogClickListener{
        fun onClicked(issue: IssueModel)
        fun onDismissed()
        fun onChanged(index : Int, length : Int)
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