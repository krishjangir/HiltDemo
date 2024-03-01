package com.krishworld.hiltexample.ui.networapikcall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentNetworkApiBinding
import com.krishworld.hiltexample.ui.networapikcall.adapter.PostAdapter
import com.krishworld.hiltexample.ui.networapikcall.viewmodel.NetworkApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkApiFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var networkApiViewModel: NetworkApiViewModel
    private var _binding: FragmentNetworkApiBinding? = null
    private lateinit var postAdapter: PostAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNetworkApiBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkApiViewModel =
            ViewModelProvider(this, viewModelFactory)[NetworkApiViewModel::class.java]
        binding.viewmodel = networkApiViewModel
        postAdapter = PostAdapter(networkApiViewModel)
        binding.mainContentRv.adapter = postAdapter
        //------------Handle UI Content here------------
        networkApiViewModel.mainContentLiveData.observe(viewLifecycleOwner) {
            postAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}