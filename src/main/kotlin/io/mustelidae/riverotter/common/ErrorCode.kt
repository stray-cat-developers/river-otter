package io.mustelidae.riverotter.common

enum class ErrorCode(val desc: String) {
    // Human error
    H000("Data Not Found"),
    H001("Invalid Input"),

    // System error
    S000("Internal Server Error"),
    S001("develop mistake"),

    // Communication error
    C000("Communication error"),
    C100("Holiday api error"),
}
