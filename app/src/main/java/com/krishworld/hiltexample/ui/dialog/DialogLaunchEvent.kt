package com.krishworld.hiltexample.ui.dialog

sealed class DialogLaunchEvent {
    object LaunchAlertDialog : DialogLaunchEvent()
    object LaunchCustomDialog : DialogLaunchEvent()
    object LaunchFullScreenDialog : DialogLaunchEvent()
    object LaunchBottomSheetDialog : DialogLaunchEvent()
}
