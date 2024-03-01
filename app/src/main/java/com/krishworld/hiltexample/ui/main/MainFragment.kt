package com.krishworld.hiltexample.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentMainBinding
import com.krishworld.hiltexample.ui.main.adapter.MainContentAdapter
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel
import com.krishworld.hiltexample.utils.Utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private lateinit var mainContentAdapter: MainContentAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewmodel = mainViewModel
        mainContentAdapter = MainContentAdapter(mainViewModel)
        binding.mainContentRv.adapter = mainContentAdapter
        //------------Handle UI Content here------------
        mainViewModel.mainContentLiveData.observe(viewLifecycleOwner) {
            mainContentAdapter.submitList(it)
        }
        //-----------Handle Navigation here----------
        mainViewModel.launchEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is LaunchEvent.LaunchWebEvent -> launchWeb(navigation.webUrl)
                    is LaunchEvent.LaunchAppointments -> launchAppointments()
                }.exhaustive
            }
        }
    }

    /*
  * Launch Web from here
  * */
    private fun launchWeb(webUrl: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        startActivity(browserIntent)
    }

    /*
    * Launch Appointments from here
    * */
    private fun launchAppointments() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}