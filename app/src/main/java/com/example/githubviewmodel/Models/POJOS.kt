package com.example.githubviewmodel.Models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["login"])
data class User(val login:String, @SerializedName("avatar_url") val avatar:String, val name:String,
                val company:String, val url:String, val blog:String)

@Entity(indices = [Index("id"), Index("owner_login")], primaryKeys = ["name", "owner_login"])
data class Repos(@Embedded(prefix = "owner_")val owner:User, val id:Int, val name:String, val full_name:String,
                 val description:String, @SerializedName("stargazers_count")val start:Int)