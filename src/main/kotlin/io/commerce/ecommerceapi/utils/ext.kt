package io.commerce.ecommerceapi.utils

import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

/**
 * @author jedy
 */

fun Any?.notNull(f: ()->Unit){
    if (this != null) f()
}

fun Any?.isNull(): Boolean{
    return this == null
}

fun String.random(): String{
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val STRING_LENGTH = 10
    return  ThreadLocalRandom.current()
        .ints(STRING_LENGTH.toLong(), 0, charPool.size)
        .asSequence()
        .map(charPool::get)
        .joinToString("") + this
}