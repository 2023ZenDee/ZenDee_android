package com.ggd.zendee.feature.ranking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentRankingBinding

class RankingFragment : BaseFragment<FragmentRankingBinding,RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()

    private val rankingAdapter :RankingRecyclerviewAdapter by lazy{ RankingRecyclerviewAdapter() }

    val dummies = mutableListOf<IssueModel>().apply {
        add(IssueModel(0,"후후ㅜ후후후후후ㅜ훟","하하ㅏ하하하하하하ㅏㅎ","","",0.0F,0.0F,"",0,"",6,"경고","지미",5,6,6,"대구광역시 수성구 지산동"))
        add(IssueModel(0,"미쳐따리","무쳐따","","",0.0F,0.0F,"",0,"",6,"공지","지미",5,6,6,"대구광역시 수성구 지산동"))
        add(IssueModel(0,"이거시 게시물?!","이거슨 게시무리영","","",0.0F,0.0F,"",0,"",6,"사랑","지미",5,6,6,"대구광역시 수성구 지산동"))
        add(IssueModel(0,"헤ㅔㅎ 이제야 되네","크아아아ㅏㅇㅇ","","",0.0F,0.0F,"",0,"",6,"활동","지미",5,6,6,"대구광역시 수성구 지산동"))
    }



    override fun start() {

        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = rankingAdapter
        }

        rankingAdapter.submitList(dummies)

    }

}