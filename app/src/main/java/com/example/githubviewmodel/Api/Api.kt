package com.example.githubviewmodel.Api

import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import kotlin.properties.Delegates

open class ApiResponse<T:Any>{
    companion object{
        val LINK_PATTERN:Pattern = Pattern.compile("")
        val PAGE_PATTERN:Pattern = Pattern.compile("")
        val NEXT_LINK:String = "next"
        var code by Delegates.notNull<Int>()
        var body:Any? = null
        var errorMessage: String? = null
        lateinit var links:Map<String, String>
    }
    constructor(error:Throwable){
        code = 500
        errorMessage = error.message.orEmpty()
        links = Collections.emptyMap()
    }

    constructor(response:Response<T>){
        code = response.code()
        var message:String? = null
        message.isNullOrEmpty()
        when{
             response.isSuccessful -> {
                 body = response.body()!!
                 return
             }
             response.errorBody() != null  -> {
                 message = response.errorBody().toString()
             }
            message!!.isNullOrEmpty() -> {
                message = response.message()
            }
        }
        errorMessage = message
    }
}