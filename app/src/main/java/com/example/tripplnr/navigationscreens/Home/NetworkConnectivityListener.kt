package com.example.tripplnr.navigationscreens.Home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NetworkConnectivityListener(private val context: Context) {
    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    @OptIn(DelicateCoroutinesApi::class)
    fun startListening(listener: OnNetworkConnectivityChangeListener) {
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {

                    listener.onNetworkConnected()


            }

            override fun onLost(network: Network) {
                listener.onNetworkDisconnected()
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager?.registerNetworkCallback(networkRequest,
            networkCallback as ConnectivityManager.NetworkCallback
        )
    }

    fun stopListening() {
        connectivityManager?.unregisterNetworkCallback(networkCallback!!)
    }

    interface OnNetworkConnectivityChangeListener {
        fun onNetworkConnected()
        fun onNetworkDisconnected()
    }
}
