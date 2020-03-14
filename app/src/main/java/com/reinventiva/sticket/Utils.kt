package com.reinventiva.sticket

class Utils {
    companion object {
        fun SerialNumber(number: Int): String {
            val serial = (number / 99 + 65).toChar()
            val numPart = number % 99 + 1
            return "$serial - ${numPart.format(2)}"
        }

        private fun Int.format(digits: Int) = "%0${digits}d".format(this)

        fun durationToString(seconds: Int): String {
            var sec = ((seconds + 5)/ 10) * 10
            var min = sec / 60
            sec -= min * 60
            val hour = min / 60
            min -= hour * 60
            var text = ""
            if (hour > 0)
                text = "${hour} hora${if (hour == 1) "" else "s"} "
            if (min > 0)
                text += "${min} min "
            if (sec > 0 && hour == 0)
                text += "${sec} seg"
            return text.trimEnd()
        }
    }
}