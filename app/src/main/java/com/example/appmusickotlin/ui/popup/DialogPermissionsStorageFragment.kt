package com.example.appmusickotlin.ui.popup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.appmusickotlin.databinding.FragmentDialogPermissionsStorageBinding

class DialogPermissionsStorageFragment : DialogFragment() {
    private val REQUEST_CODE_STORAGE_PERMISSION = 1


    private lateinit var binding: FragmentDialogPermissionsStorageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogPermissionsStorageBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCreate.setOnClickListener {
            requestStoragePermission()
            dismiss()
        }
        binding.tvCancel.setOnClickListener {
            dismiss()

        }
    }
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_STORAGE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Quyền đã được cấp, truy cập vào bộ nhớ lưu trữ
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                dismiss()
                // Thực hiện hành động cần thiết khi quyền đã được cấp
            } else {
                // Quyền bị từ chối, thông báo cho người dùng
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


}