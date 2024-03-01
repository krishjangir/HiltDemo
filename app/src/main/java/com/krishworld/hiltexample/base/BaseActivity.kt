package com.krishworld.hiltexample.base

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krishworld.hiltexample.HiltExampleApp
import com.krishworld.hiltexample.ui.localization.LocaleUtil
import com.krishworld.hiltexample.ui.localization.Storage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var context: Context
    lateinit var connectivityManager: ConnectivityManager

    /*---------------For network checks -----------*/
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Toast.makeText(context, "Network is available for use", Toast.LENGTH_LONG)
                .show()
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unMetered =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            Toast.makeText(context, "Lost network connection", Toast.LENGTH_LONG).show()
        }
    }

    /*---------------For localization -----------*/
    private lateinit var oldPrefLocaleCode: String
    protected val storage: Storage by lazy {
        (application as HiltExampleApp).storage
    }

    /**
     * updates the toolbar text locale if it set from the android:label property of Manifest
     */
    private fun resetTitle() {
        try {
            val label =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    packageManager.getActivityInfo(
                        componentName, PackageManager.ComponentInfoFlags.of(
                            PackageManager.GET_META_DATA.toLong()
                        )
                    ).labelRes
                } else {
                    packageManager.getActivityInfo(
                        componentName,
                        PackageManager.GET_META_DATA
                    ).labelRes
                }
            if (label != 0) {
                setTitle(label)
            }
        } catch (_: PackageManager.NameNotFoundException) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetTitle()
    }

    override fun onResume() {
        super.onResume()
        /*---------------For localization -----------*/
        val currentLocaleCode = Storage(this).getPreferredLocale()
        if (oldPrefLocaleCode != currentLocaleCode) {
            recreate() //locale is changed, restart the activity to update
            oldPrefLocaleCode = currentLocaleCode
        }
        /*---------------For network checks -----------*/
        connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    override fun attachBaseContext(newBase: Context?) {
        /*---------------For localization -----------*/
        if (newBase != null) {
            oldPrefLocaleCode = Storage(newBase).getPreferredLocale()
            applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode))
        }
        super.attachBaseContext(newBase)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}