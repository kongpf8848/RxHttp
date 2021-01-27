package com.github.kongpf8848.rxhttp.sample.mvvm

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface IBaseMvvm{

    fun findType(type: Type): Type?{
        return when(type){
            is ParameterizedType -> type
            is Class<*> ->{
                findType(type.genericSuperclass)
            }
            else ->{
                null
            }
        }
    }

}