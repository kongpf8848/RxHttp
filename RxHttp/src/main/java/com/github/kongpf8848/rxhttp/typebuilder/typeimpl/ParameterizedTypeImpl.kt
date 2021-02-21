package com.github.kongpf8848.rxhttp.typebuilder.typeimpl

import com.github.kongpf8848.rxhttp.typebuilder.exception.TypeException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.util.*

class ParameterizedTypeImpl(private val raw: Class<*>, private var args: Array<Type>?, private val owner: Type?) : ParameterizedType {

    private fun checkArgs() {

        val typeParameters: Array<out TypeVariable<out Class<out Any>>> = raw.typeParameters
        if (args!!.isNotEmpty() && typeParameters.size != args!!.size) {
            throw TypeException(raw.name + " expect " + typeParameters.size + " arg(s), got " + args!!.size)
        }
    }

    init {
        if (args == null){
            args=emptyArray()
        }
        checkArgs()
    }

    override fun getActualTypeArguments(): Array<Type>? {
        return args
    }

    override fun getRawType(): Type? {
        return raw
    }

    override fun getOwnerType(): Type? {
        return owner
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(raw.name)
        if (args!!.isNotEmpty()) {
            sb.append('<')
            for (i in args!!.indices) {
                if (i != 0) {
                    sb.append(", ")
                }
                val type = args!![i]
                if (type is Class<*>) {
                    var clazz = type as Class<*>
                    if (clazz.isArray) {
                        var count = 0
                        do {
                            count++
                            clazz = clazz.componentType!!
                        } while (clazz.isArray)
                        sb.append(clazz.name)
                        for (j in count downTo 1) {
                            sb.append("[]")
                        }
                    } else {
                        sb.append(clazz.name)
                    }
                } else {
                    sb.append(args!![i].toString())
                }
            }
            sb.append('>')
        }
        return sb.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ParameterizedTypeImpl
        if (raw != that.raw) return false
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(args, that.args)) return false
        return if (owner != null) owner == that.owner else that.owner == null
    }

    override fun hashCode(): Int {
        var result = raw.hashCode()
        result = 31 * result + Arrays.hashCode(args)
        result = 31 * result + (owner?.hashCode() ?: 0)
        return result
    }


}