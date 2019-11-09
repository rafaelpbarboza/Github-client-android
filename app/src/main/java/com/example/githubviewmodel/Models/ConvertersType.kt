package com.example.githubviewmodel.Models

import androidx.room.TypeConverter
import androidx.room.util.StringUtil
import java.util.*


class GithubTypwConverter{
    companion object {
        fun stringToListConverter(data: String): List<Int>{
            if(data == null){
                return Collections.emptyList()
            }
            return StringUtil.splitToIntList(data) as List<Int>
        }

        fun listToStringConverter(list: List<Int>): String? =  StringUtil.joinIntoString(list)

    }
}