package com.astalavista.pscquestionpapers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astalavista.pscquestionpapers.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_ex.view.*

class BottomSheetEx : BottomSheetDialogFragment() {

    private var mBottomSheetListener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bottom_sheet_ex, container, false)

        //handle clicks
        v.btnOk.setOnClickListener {
            mBottomSheetListener!!.onOkClicked()
            dismiss() //dismiss bottom sheet when item click
        }
        v.btncancel.setOnClickListener {
            mBottomSheetListener!!.onCancelClicked()
            dismiss()
        }

        return v
    }

    interface BottomSheetListener {
        fun onOkClicked()
        fun onCancelClicked()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mBottomSheetListener = context as BottomSheetListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString())
        }

    }


}