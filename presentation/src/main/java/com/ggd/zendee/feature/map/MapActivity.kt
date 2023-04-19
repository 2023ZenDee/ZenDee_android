package com.ggd.zendee.feature.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseActivity
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.databinding.ActivityMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : BaseActivity<ActivityMapBinding, BaseViewModel>(R.layout.activity_map) {

    override val viewModel: BaseViewModel by viewModels()

    override fun start() {
        TODO("Not yet implemented")
    }
}