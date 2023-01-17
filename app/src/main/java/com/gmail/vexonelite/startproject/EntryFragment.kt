package com.gmail.vexonelite.startproject

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gmail.vexonelite.startproject.databinding.FragmentEntryBinding
import getLogTag
import hasFineLocationPermissionBeenGranted
import hasWriteExternalStoragePermissionBeenGranted
import showLog
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class EntryFragment : Fragment() {
    // R.layout.fragment_entry
    private var _viewBinding: FragmentEntryBinding? = null
    // This property is only valid between onCreateView() and onDestroyView().
    private val viewBinding get() = _viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentEntryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        rootView.setOnClickListener { view -> {} }
        uiInitilization(rootView)
//        setupViewModel()
    }

    private fun uiInitilization(rootView: View) {
        val viewList = listOf<View>(
            viewBinding.button1,
            viewBinding.button2,
            viewBinding.button3,
            viewBinding.button4,
            viewBinding.button5,
            viewBinding.button6,
            viewBinding.button7,
            viewBinding.button8,
            viewBinding.button9,
            viewBinding.button10,
            viewBinding.button11,
            viewBinding.button12,
            viewBinding.button13,
            viewBinding.button14,
            viewBinding.button15,
            viewBinding.button16,
            viewBinding.button17,
            viewBinding.button18,
        )
        for (view in viewList) {
            view.setOnClickListener(myClickerLambda)
        }
    }

    private val myClickerLambda: (View) -> Unit = { view ->
        when (view.id) {
            R.id.button1 -> {
                enableWiFiIfNeeded()
            }
            R.id.button2 -> {
                requestLocationPermissions()
            }
            R.id.button3 -> {
                requestWriteExternalStoragePermission()
            }
            R.id.button4 -> {
                runRxTask1()
            }
            R.id.button5 -> {
            }
            R.id.button6 -> {
            }
            R.id.button7 -> {
            }
            R.id.button8 -> {
            }
            R.id.button9 -> {
            }
            R.id.button10 -> {
            }
            R.id.button11 -> {
            }
            R.id.button12 -> {
            }
            R.id.button13 -> {
            }
            R.id.button14 -> {
            }
            R.id.button15 -> {
            }
            R.id.button16 -> {
            }
            R.id.button17 -> {
            }
            R.id.button18 -> {
            }
            else -> {
            }
        }
    }

    private val locationPermissionsResultCallback: (Map<String, Boolean>) -> Unit = { resultsMap ->
        val result: Boolean = resultsMap.contains(Manifest.permission.ACCESS_FINE_LOCATION) && resultsMap.contains(Manifest.permission.ACCESS_COARSE_LOCATION)
        showLog(Log.INFO, getLogTag(), "locationPermissionsResultCallback - result: [$result]")
    }

    private val locationPermissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(), locationPermissionsResultCallback)

    private fun requestLocationPermissions() {
        val hostActivity = activity as AppCompatActivity
        if (hostActivity.hasFineLocationPermissionBeenGranted()) {
            showLog(Log.INFO, getLogTag(), "requestLocationPermissions - Permission Granted")
        }
        else {
            locationPermissionRequestLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    ///

    private val writeExternalStoragePermissionResultCallback: (Boolean) -> Unit = { result ->
        showLog(Log.INFO, getLogTag(), "writeExternalStoragePermissionResultCallback - result: [$result]")
    }

    private val writeExternalStoragePermissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(), writeExternalStoragePermissionResultCallback)

    private fun requestWriteExternalStoragePermission() {
        val hostActivity = activity as AppCompatActivity
        if(hostActivity.hasWriteExternalStoragePermissionBeenGranted()) {
            showLog(Log.INFO, getLogTag(), "requestWriteExternalStoragePermission - Permission Granted")
        }
        else {
            writeExternalStoragePermissionRequestLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    ///

    private val wiFiEnableActivityResultCallback : (ActivityResult) -> Unit = { result ->
        val isResultCodeOk = result.resultCode == Activity.RESULT_OK
        showLog(Log.INFO, getLogTag(), "requestWiFiFeatureResultCallback - isResultCodeOk: [$isResultCodeOk]")
    }

    private val requestWiFiFeatureLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), wiFiEnableActivityResultCallback)

    private fun enableWiFiIfNeeded() {
        val wifiManager =
            requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (wifiManager.isWifiEnabled) {
            showLog(Log.INFO, getLogTag(), "enableWiFiIfNeeded - WiFi enabled already")
        } else {
            if (Build.VERSION.SDK_INT < 29) {
                wifiManager.isWifiEnabled = true
            } else {
                requestWiFiFeatureLauncher.launch(Intent(Settings.Panel.ACTION_WIFI))
            }
        }
    }

    ///

    private fun runRxTask1() {
        val disposable: Disposable? = Observable.just("long", "longer", "longest")
            //.observeOn(AndroidSchedulers.mainThread()) // cannot involve ``observeOn`` here
            .map(stringToLength)
            .filter(stringLengthFilter)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            //.subscribeOn(Schedulers.io())
            //.observeOn(Schedulers.io())
            .subscribe(integerSubscriber)
    }
    private val stringToLength: (String) -> Int = { inputStr ->
        showLog(Log.INFO, getLogTag(), "stringToLength - inputStr: [$inputStr], ${Thread.currentThread().name}" )
        inputStr.length
    }

    private val stringLengthFilter: (Int) -> Boolean = { intValue ->
        showLog(Log.INFO, getLogTag(), "stringLengthFilter - intValue: [$intValue], ${Thread.currentThread().name}" )
        intValue > 4
    }

    private val integerSubscriber: (Int) -> Unit = { result ->
        showLog(Log.INFO, getLogTag(), "integerSubscriber - result: [$result], ${Thread.currentThread().name}" )
    }

}