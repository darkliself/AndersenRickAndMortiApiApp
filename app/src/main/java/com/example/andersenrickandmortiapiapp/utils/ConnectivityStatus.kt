package com.example.andersenrickandmortiapiapp.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

object ConnectivityStatus {
    var isConnected = true
    val networkCallback = object : ConnectivityManager.NetworkCallback() {

        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isConnected = true
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
//            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            isConnected = false
        }
    }

}



