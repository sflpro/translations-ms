package com.sfl.qup.tms.service.translatablestatic

import com.sfl.qup.tms.domain.translatablestastic.TranslatableStatic
import com.sfl.qup.tms.service.translatablestatic.dto.TranslatableStaticDto

/**
 * User: Vazgen Danielyan
 * Date: 12/11/18
 * Time: 1:51 AM
 */
interface TranslatableStaticService {

    fun findByKeyAndLanguageLang(key: String, lang: String): TranslatableStatic?

    fun getByKeyAndLanguageLang(key: String, lang: String): TranslatableStatic

    fun getByKey(key: String): List<TranslatableStatic>

    fun create(dto: TranslatableStaticDto): TranslatableStatic

    fun update(dto: TranslatableStaticDto): TranslatableStatic

    fun search(term: String?, page: Int?): List<TranslatableStatic>

}