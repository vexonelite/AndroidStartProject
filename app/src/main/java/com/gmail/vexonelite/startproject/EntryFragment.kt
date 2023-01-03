package com.gmail.vexonelite.startproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.vexonelite.startproject.databinding.FragmentEntryBinding


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
//        uiInitilization(rootView)
//        setupViewModel()
    }


}