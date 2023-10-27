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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding,RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    private val rankingAdapter :RankingRecyclerviewAdapter by lazy{ RankingRecyclerviewAdapter(requireContext()) }
    private val rankingTagAdpater : RankingTagAdapter by lazy { RankingTagAdapter() }

    override fun start() {

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = rankingAdapter
        }

        binding.tagGridview.apply {
            adapter = rankingTagAdpater
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        rankingTagAdpater.submitList(viewModel.tagList)

        fun getTagStringList() : List<String>{

            val list = mutableListOf<String>()

            for (i in viewModel.tagList){
                if(i.isTagSelected) list.add(i.tagName)
            }

            return list
        }

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

        CoroutineScope(Dispatchers.IO).launch{
            if (viewModel.rankList != null) {
                withContext(Dispatchers.Main){
                    rankingAdapter.submitList(viewModel.rankList)
                }
            } else {
                rankingTagAdpater.submitList(viewModel.tagList)
                viewModel.getRank(viewModel.sortBy,getTagStringList())
            }
        }

//        viewModel.getRank()

        rankingAdapter.setDialogClickListener(object : RankingRecyclerviewAdapter.OnDialogClickListener{
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
                viewModel.getRank(viewModel.sortBy,getTagStringList())
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })



    }



    fun updateRank(list : List<IssueModel>?){
        viewModel.rankList = list
        rankingAdapter.submitList(list)

//        rankingAdapter.submitList(list)
    }

    private fun handleEvent(event: RankingViewModel.Event) =
        when (event) {

            is RankingViewModel.Event.SuccessGetRank -> {
                updateRank(event.issueModels)
            }
            is RankingViewModel.Event.UnknownException -> Log.d("젠디", "ERROR - ${event.error}")

        }



}