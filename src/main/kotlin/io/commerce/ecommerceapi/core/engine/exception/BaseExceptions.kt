package io.commerce.ecommerceapi.core.engine.exception



open class StorageException : RuntimeException {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}

class StorageFileNotFoundException : StorageException {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}