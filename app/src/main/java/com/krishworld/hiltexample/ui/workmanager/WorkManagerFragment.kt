package com.krishworld.hiltexample.ui.workmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentAudioRecorderBinding
import com.krishworld.hiltexample.databinding.FragmentWorkmanagerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class WorkManagerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var applicationContext: Context

    private var _binding: FragmentWorkmanagerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWorkmanagerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputData = Data.Builder()
            .putString(
                "imageUrl",
                "https://fastly.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU"
            )
            .build()

        val downloadImageRequest = OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setInputData(inputData)
            .build()


        WorkManager.getInstance(requireContext())
            .beginWith(downloadImageRequest)
            .enqueue()

        WorkManager.getInstance(requireContext())
            .getWorkInfoByIdLiveData(downloadImageRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val imagePath = workInfo.outputData.getString("imagePath")
                    Log.d("downloadImageRequest", "${downloadImageRequest.id}")
                    // Use the filtered image path for further processing or display
                    Toast.makeText(requireContext(), imagePath, Toast.LENGTH_LONG).show()
                } else if (workInfo != null && workInfo.state == WorkInfo.State.FAILED) {
                    // Handle the failure case
                    Toast.makeText(requireContext(), "Handle the failure case", Toast.LENGTH_LONG)
                        .show()
                }
            }

        val toastRequest = PeriodicWorkRequestBuilder<ToastWorker>(15, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiresCharging(true).build()).build()

        WorkManager.getInstance(requireContext())
            .getWorkInfoByIdLiveData(toastRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val toastMsg = workInfo.outputData.getString("toastMsg")
                    Log.d("toastRequest", "${toastRequest.id}")
                    // Use the filtered image path for further processing or display
                    Toast.makeText(requireContext(), toastMsg, Toast.LENGTH_LONG).show()
                } else if (workInfo != null && workInfo.state == WorkInfo.State.FAILED) {
                    // Handle the failure case
                    Toast.makeText(requireContext(), "Handle the failure case", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}