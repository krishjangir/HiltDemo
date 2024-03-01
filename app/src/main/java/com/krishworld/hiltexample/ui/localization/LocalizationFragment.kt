package com.krishworld.hiltexample.ui.localization

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.krishworld.hiltexample.HiltExampleApp
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentLocalizationBinding
import com.krishworld.hiltexample.utils.Utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalizationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appContext: Context

    private lateinit var localizationViewModel: LocalizationViewModel
    private var _binding: FragmentLocalizationBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalizationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localizationViewModel =
            ViewModelProvider(this, viewModelFactory)[LocalizationViewModel::class.java]
        binding.viewmodel = localizationViewModel

        //-----------Handle localization event here----------
        localizationViewModel.localizationEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { param ->
                when (param) {
                    is LocalizationEvent.ChangeLanguageEvent -> changeLanguage(param.language)
                }.exhaustive
            }
        }
    }

    private fun changeLanguage(language: String) {
        (appContext as HiltExampleApp).storage.setPreferredLocale(language)
        LocaleUtil.applyLocalizedContext(appContext, language)
        activity?.recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}