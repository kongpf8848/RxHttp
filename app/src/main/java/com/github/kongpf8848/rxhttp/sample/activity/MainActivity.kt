package com.github.kongpf8848.rxhttp.sample.activity

import android.content.Intent
import android.os.Bundle
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.databinding.ActivityMainBinding
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseMvvmActivity
import com.github.kongpf8848.rxhttp.sample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MVVM架构使用RxHttp示例
 */
class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)

        button1.setOnClickListener {
          startActivity(Intent(this,MVVMActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this,MVCActivity::class.java))
        }

    }


}





