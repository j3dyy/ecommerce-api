package io.commerce.ecommerceapi.utils

/**
 * @author jedy
 */

fun Any?.notNull(f: ()->Unit){
    if (this != null) f()
}

fun Any?.isNull(): Boolean{
    return this == null
}
