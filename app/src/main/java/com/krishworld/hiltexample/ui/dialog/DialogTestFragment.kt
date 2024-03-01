package com.krishworld.hiltexample.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.BottomSheetLayoutBinding
import com.krishworld.hiltexample.databinding.CustomDialogLayoutBinding
import com.krishworld.hiltexample.databinding.FragmentDialogTestBinding
import com.krishworld.hiltexample.ui.dialog.viewmodel.DialogTestViewModel
import com.krishworld.hiltexample.utils.Utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogTestFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appContext: Context
    private lateinit var dialogTestViewModel: DialogTestViewModel
    private var _binding: FragmentDialogTestBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogTestViewModel =
            ViewModelProvider(this, viewModelFactory)[DialogTestViewModel::class.java]
        binding.viewmodel = dialogTestViewModel

        //-----------Handle DialogLaunchEvent here----------
        dialogTestViewModel.launchEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is DialogLaunchEvent.LaunchAlertDialog -> showAlertDialog()
                    is DialogLaunchEvent.LaunchCustomDialog -> showCustomDialog()
                    is DialogLaunchEvent.LaunchFullScreenDialog -> showFullScreenDialog()
                    is DialogLaunchEvent.LaunchBottomSheetDialog -> showBottomSheetDialog()
                }.exhaustive
            }
        }
    }

    private fun showAlertDialog() {
        //NotificationScheduler.scheduleNotification(appContext,10000)
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme)
        builder.setTitle("Alert Dialog")
        builder.setMessage("Alert Dialog Content")
        builder.setPositiveButton("OK") { dialog, _ ->
            // Handle positive button click
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Handle negative button click
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showCustomDialog() {
        val dialog = Dialog(requireActivity(), R.style.CustomDialogTheme)
        dialog.setCancelable(false)
        val dialogView =
            LayoutInflater.from(appContext).inflate(R.layout.custom_dialog_layout, null)
        val customDialogLayoutBinding = CustomDialogLayoutBinding.bind(dialogView)
        dialog.setContentView(customDialogLayoutBinding.root)
        customDialogLayoutBinding.txtClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showFullScreenDialog() {
        FullScreenDialog().show(childFragmentManager, "FullScreenDialog")
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog =
            BottomSheetDialog(requireActivity(), R.style.ThemeOverlay_App_BottomSheetDialog)
        val bottomSheetDialogView =
            LayoutInflater.from(appContext).inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialogLayoutBinding = BottomSheetLayoutBinding.bind(bottomSheetDialogView)
        bottomSheetDialog.setContentView(bottomSheetDialogLayoutBinding.root)
        bottomSheetDialogLayoutBinding.copyText.setOnClickListener {
            Toast.makeText(appContext, "Copy is Clicked ", Toast.LENGTH_LONG).show()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}