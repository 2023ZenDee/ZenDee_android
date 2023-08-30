package com.ggd.zendee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.ggd.zendee.R

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    protected lateinit var binding: B
    protected lateinit var mViewModel: VM
    protected abstract val viewModel: VM
//    protected open val hasBottomNavigation: Boolean = false

    private val transaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()

    fun changeFragment(fragment : Fragment) {
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.commit()
    }


    protected abstract fun start()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDataBinding()
//        (activity as? MainActivity)?.setNavVisible(hasBottomNavigation)
        start()
    }

    private fun prepareDataBinding() {
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
//        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }
}