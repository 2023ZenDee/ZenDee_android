package com.ggd.zendee.feature.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentMapBinding

class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(R.layout.fragment_map){

    override val viewModel: MapViewModel by viewModels()

    override fun start() {
        TODO("Not yet implemented")
    }

}