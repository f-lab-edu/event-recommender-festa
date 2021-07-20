package com.festa.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConvertDataTypeKT {

    /**
     * companion object : static 키워드가 없는 Kotlin에서 사용, 대신 .java에서는 사용 불가능
     * @JvmStatic java에서도 사용할 수 있도록 설정
     */
    companion object {
        @JvmStatic val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        @JvmStatic fun dateFormatter(todayDate: LocalDate): String {
            return todayDate.format(format)
        }

        @JvmStatic fun longToString(target: Long): String {
            return target.toString()
        }
    }

}