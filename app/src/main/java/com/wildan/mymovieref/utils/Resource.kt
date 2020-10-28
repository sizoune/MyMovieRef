package com.wildan.mymovieref.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> errorServer(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR_SERVER, data = data, message = message)

        fun <T> errorNetwork(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR_NETWORK, data = data, message = message)

        fun <T> errorUnknown(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR_UNKNOWN, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR_SERVER,
    ERROR_NETWORK,
    ERROR_UNKNOWN,
    LOADING
}