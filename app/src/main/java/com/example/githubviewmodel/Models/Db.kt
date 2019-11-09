package com.example.githubviewmodel.Models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Repos::class, Contributors::class, RepoSearchResult::class], version = 1)
abstract class GitHubDb:RoomDatabase(){
    abstract fun userDao():UserDao
    abstract fun reposDao():RepoDao
}