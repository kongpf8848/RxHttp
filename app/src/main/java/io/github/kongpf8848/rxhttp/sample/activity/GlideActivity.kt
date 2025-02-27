package io.github.kongpf8848.rxhttp.sample.activity

import android.os.Bundle
import android.os.Looper
import androidx.databinding.DataBindingUtil
import io.github.kongpf8848.rxhttp.sample.R
import io.github.kongpf8848.rxhttp.sample.base.BaseActivity
import io.github.kongpf8848.rxhttp.sample.databinding.ActivityGlideBinding
import io.github.kongpf8848.rxhttp.sample.image.ImageLoader


class GlideActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGlideBinding>(this, R.layout.activity_glide)
        binding.lifecycleOwner = this
        Looper.myQueue().addIdleHandler {
            ImageLoader.getInstance().load(this@GlideActivity,"http://t8.baidu.com/it/u=198337120,441348595&fm=79&app=86&f=JPEG?w=1280&h=732",binding.ivPic1 )
            ImageLoader.getInstance().load(this@GlideActivity,"http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853",binding.ivPic2)
            false
        }
    }

}