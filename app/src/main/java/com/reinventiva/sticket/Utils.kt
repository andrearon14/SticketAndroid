package com.reinventiva.sticket

class Utils {
    companion object {
        fun SerialNumber(number: Int): String {
            val serial = (number / 99 + 65).toChar()
            val numPart = number % 99 + 1
            return "$serial - ${numPart.format(2)}"
        }

        private fun Int.format(digits: Int) = "%0${digits}d".format(this)
    }
}