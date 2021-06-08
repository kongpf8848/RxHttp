package io.github.kongpf8848.rxhttp.sample.activity

import android.os.Bundle
import android.os.Looper
import io.github.kongpf8848.rxhttp.sample.R
import io.github.kongpf8848.rxhttp.sample.base.BaseActivity
import io.github.kongpf8848.rxhttp.sample.image.ImageLoader
import kotlinx.android.synthetic.main.activity_glide.*

class GlideActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        Looper.myQueue().addIdleHandler {
            loadImage()
            false
        }
    }

    private fun loadImage(){

        ImageLoader.getInstance().load(this,"http://t8.baidu.com/it/u=198337120,441348595&fm=79&app=86&f=JPEG?w=1280&h=732",iv_pic_1)
        ImageLoader.getInstance().load(this,"http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853",iv_pic_2)
    }


}