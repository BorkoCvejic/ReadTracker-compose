package com.bcoding.readtracker.core.data

import com.bcoding.readtracker.book.data.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object {
        private const val DEFAULT_SEARCH_LANG = "eng"
        private const val SEARCH_FIELDS = "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
    }

    @GET("/search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("lang") lang: String = DEFAULT_SEARCH_LANG,
        @Query("fields") fields: String = SEARCH_FIELDS
    ): SearchResponseDto
}
