package io.github.kongpf8848.rxhttp.typebuilder

import io.github.kongpf8848.rxhttp.typebuilder.exception.TypeException
import io.github.kongpf8848.rxhttp.typebuilder.typeimpl.ParameterizedTypeImpl
import java.lang.reflect.Type
import java.util.*

class TypeBuilder private constructor(val raw: Class<*>, val parent: TypeBuilder?) {

    private val args = ArrayList<Type>()

    companion object {
        fun newInstance(raw: Class<*>): TypeBuilder {
            return TypeBuilder(raw, null)
        }
        private fun newInstance(raw: Class<*>, parent: TypeBuilder): TypeBuilder {
            return TypeBuilder(raw, parent)
        }
    }

    fun beginSubType(raw: Class<*>): TypeBuilder {
        return newInstance(raw, this)
    }

    fun endSubType(): TypeBuilder {
        if (parent == null) {
            throw TypeException("expect beginSubType() before endSubType()")
        }
        parent.addTypeParam(type)
        return parent
    }

    fun addTypeParam(clazz: Class<*>?): TypeBuilder {
        return addTypeParam(clazz as Type?)
    }

    fun addTypeParam(type: Type?): TypeBuilder {
        if (type == null) {
            throw NullPointerException("addTypeParam expect not null Type")
        }
        args.add(type)
        return this
    }

    fun build(): Type {
        if (parent != null) {
            throw TypeException("expect endSubType() before build()")
        }
        return type
    }

    private val type: Type
        get() = if (args.isEmpty()) {
            raw
        } else ParameterizedTypeImpl(raw, args.toTypedArray(), null)


}