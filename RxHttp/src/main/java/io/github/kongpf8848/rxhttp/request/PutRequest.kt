package io.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

class PutRequest : PostRequest {
    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)
}