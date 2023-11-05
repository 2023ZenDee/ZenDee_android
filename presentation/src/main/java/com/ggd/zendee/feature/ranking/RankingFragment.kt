package com.ggd.zendee.feature.ranking

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentRankingBinding
import com.ggd.zendee.feature.login.LoginViewModel.Companion.TAG
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding,RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


//    private val rankingAdapter :RankingRecyclerviewAdapter by lazy{ RankingRecyclerviewAdapter(requireContext()) }
    private val rankingAdapter = RankingPagingAdapter()
    private val rankingTagAdpater : RankingTagAdapter by lazy { RankingTagAdapter() }

    override fun start() {

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = rankingAdapter
        }

        viewModel.pagingData.observe(this, Observer {
            rankingAdapter.submitData(this.lifecycle,it)
            Log.d(TAG, "list가 추가되었습니다")
        })

        binding.tagGridview.apply {
            adapter = rankingTagAdpater
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        rankingTagAdpater.submitList(viewModel.tagList)

        binding.sortRadioGroup.setOnCheckedChangeListener { radioGroup, i ->

            if (binding.sortViewsRadio.isChecked) viewModel.sortBy = "views"
            if (binding.sortLikesRadio.isChecked) viewModel.sortBy = "likes"
            if (binding.sortDislikesRadio.isChecked) viewModel.sortBy = "bads"

        }

        rankingTagAdpater.setAdapterListener(object : RankingTagAdapter.OnAdapterListener{

            override fun onItemClicked(data: RankingTagData, checked: Boolean) {
                viewModel.tagList[data.index].isTagSelected = checked
                rankingTagAdpater.submitList(viewModel.tagList)
            }

        })

        rankingAdapter.setDialogClickListener(object : RankingPagingAdapter.OnDialogClickListener{
            override fun onClick(issue: IssueModel) {

                mainViewModel.issue = issue
                findNavController().navigate(R.id.action_rankingFragment_to_issueFragment)
            }

        })

        binding.filterBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.filterLayout,true)
        }

        binding.drawerLayout.addDrawerListener(object : DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                viewModel.getPagingRank(viewModel.sortBy, viewModel.getTagStringList())
//                rankingAdapter.refresh()
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })

    }


    private fun handleEvent(event: RankingViewModel.Event) =
        when (event) {

            is RankingViewModel.Event.SuccessGetRank -> {
//                updateRank(event.issueModels)
            }
            is RankingViewModel.Event.UnknownException -> Log.d("젠디", "ERROR - ${event.error}")
            RankingViewModel.Event.Success -> {
                rankingAdapter.refresh()
            }
        }


}