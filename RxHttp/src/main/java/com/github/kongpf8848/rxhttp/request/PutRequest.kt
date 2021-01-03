package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

class PutRequest<T> : PostRequest<T> {
    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)
}