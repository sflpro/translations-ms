package com.sfl.qup.tms.persistence.translatablestastics

import com.sfl.qup.tms.domain.translatablestastic.TranslatableStatic
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * User: Vazgen Danielyan
 * Date: 12/11/18
 * Time: 1:51 AM
 */
@Repository
interface TranslatableStaticRepository : JpaRepository<TranslatableStatic, Long> {

    fun findByKey(key: String): List<TranslatableStatic>

    fun findByKeyAndLanguage_Id(key: String, languageId: Long): TranslatableStatic?

    fun findByKeyAndLanguage_Lang(key: String, lang: String): TranslatableStatic?

    fun findByOrderByKeyAsc(pageable: Pageable): List<TranslatableStatic>

    @Query("from TranslatableStatic ts where lower(ts.key) like lower(:term) order by ts.key asc")
    fun findByKeyLikeOrderByKeyAsc(@Param("term") term: String, pageable: Pageable): List<TranslatableStatic>

}