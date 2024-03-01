package com.krishworld.hiltexample.ui.coroutine

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentCoroutinesBinding
import com.krishworld.hiltexample.ui.audio.viewmodel.AudioRecorderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoroutinesFragment : Fragment() {

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var coroutinesViewModel: CoroutinesViewModel
    private var _binding: FragmentCoroutinesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoroutinesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutinesViewModel =
            ViewModelProvider(this, viewModelFactory)[CoroutinesViewModel::class.java]
        binding.viewmodel = coroutinesViewModel
        binding.imageUrl =
            "https://fastly.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU"

        coroutinesViewModel.imgBitmapLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "${it.height}", Toast.LENGTH_LONG).show()
            binding.imageView.setImageBitmap(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}