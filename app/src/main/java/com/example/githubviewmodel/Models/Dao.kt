package com.example.githubviewmodel.Models

import android.util.SparseIntArray
import androidx.arch.core.util.Function
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:User)

    @Query("SELECT * FROM user WHERE login = :login")
    fun getUser(login:User):LiveData<User>
}

@Dao
abstract class RepoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(repo: List<Repos>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContributors(contributors: List<Contributors>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repos>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(repo: Repos):Long

    @Query("SELECT * FROM repos WHERE  owner_login = :login AND name = :name")
    abstract fun load(login:String, name:String):LiveData<Repos>

    @Query("SELECT login, avatar_url, repo, owner FROM contributors WHERE repo = :name AND owner = :owner ORDER BY contributions DESC")
    abstract fun getContributor(name:String, owner: String):LiveData<Contributors>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String):LiveData<RepoSearchResult>

    @Query("SELECT * FROM Repos WHERE id in (:reposId)")
    protected abstract fun loadById(reposId:List<Int>):LiveData<List<Repos>>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun repoSearchResult(query: String):RepoSearchResult

    fun loadOrdered(reposId: List<Int>):LiveData<List<Repos>> {
        val order = SparseIntArray()
        var index = 0
        reposId.forEach {
            order.put(it, index++)
        }
        return Transformations.map(loadById(reposId), Function {
            Collections.sort(it, kotlin.Comparator { o1, o2 ->  order.get(o1.id) - order.get(o2.id) })
            return@Function it
        })
    }
}