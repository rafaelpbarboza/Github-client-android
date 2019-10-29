package com.example.githubviewmodel.Models

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["login"])
data class User(val login:String, @SerializedName("avatar_url") val avatar:String, val name:String,
                val company:String, val url:String, val blog:String)

@Entity(indices = [Index("id"), Index("owner_login")], primaryKeys = ["name", "owner_login"])
data class Repos(@Embedded(prefix = "owner_")val owner:User, val id:Int, val name:String, val full_name:String,
                 val description:String, @SerializedName("stargazers_count")val start:Int)

@Entity(foreignKeys = (
    [ForeignKey(
        entity = Repos::class,
        parentColumns = ["name"],
        childColumns = ["repo"],
        onUpdate = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["owner"],
        childColumns = ["login"],
        onUpdate = ForeignKey.CASCADE
    )
    ]
))
data class Contributors(@SerializedName("login") @PrimaryKey val login: String,
                        @SerializedName("contributions") val contributions: Int,
                        val avatar_url:String,@PrimaryKey val repo:String,@PrimaryKey val owner:String)

data class SearchDataResponse(@SerializedName("total_count")val count:Int,
                              @SerializedName("Items")val repo:MutableList<Repos>,
                              val nextPage:Int){

    val listRespoId:MutableList<Int>
        get() {
            val repoId = mutableListOf<Int>()
            repo.forEach { repoId.add(it.id) }
            return repoId
        }
}