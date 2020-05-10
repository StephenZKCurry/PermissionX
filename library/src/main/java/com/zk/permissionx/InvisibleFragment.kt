package com.zk.permissionx

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

/**
 *    desc   : 权限申请Fragment
 *    author : zhukai
 *    date   : 2020/5/10
 */

// 定义权限申请回调函数类型
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE = 1
    }

    private var callback: PermissionCallback? = null

    fun requestNow(vararg permissions: String, callback: PermissionCallback) {
        this.callback = callback
        requestPermissions(permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}