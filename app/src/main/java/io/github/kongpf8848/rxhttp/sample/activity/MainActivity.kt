package io.github.kongpf8848.rxhttp.sample.activity

import android.content.Intent
import android.os.Bundle
import io.github.kongpf8848.rxhttp.sample.R
import io.github.kongpf8848.rxhttp.sample.databinding.ActivityMainBinding
import io.github.kongpf8848.rxhttp.sample.mvvm.BaseMvvmActivity
import io.github.kongpf8848.rxhttp.sample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

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

        button3.setOnClickListener {
            startActivity(Intent(this,ZhiHuActivity::class.java))
        }

        button4.setOnClickListener {
            startActivity(Intent(this,ZhiHuJavaActivity::class.java))
        }

        button5.setOnClickListener {
            startActivity(Intent(this,GlideActivity::class.java))
        }

    }


}





