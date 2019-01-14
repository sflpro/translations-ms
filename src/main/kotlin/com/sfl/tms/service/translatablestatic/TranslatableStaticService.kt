package com.sfl.tms.service.translatablestatic

import com.sfl.tms.domain.translatablestastic.TranslatableStatic
import com.sfl.tms.service.translatablestatic.dto.TranslatableStaticDto
import com.sfl.tms.service.translatablestatic.dto.TranslatableStaticTemplateDto

/**
 * User: Vazgen Danielyan
 * Date: 12/11/18
 * Time: 1:51 AM
 */
interface TranslatableStaticService {

    fun findByKeyAndEntityUuidAndLanguageLang(key: String, entityUuid: String, lang: String): TranslatableStatic?

    fun getByKeyAndEntityUuid(key: String, uuid: String): List<TranslatableStatic>

    fun getByKeyAndEntityUuidAndLanguageLang(key: String, uuid: String, lang: String): TranslatableStatic

    fun create(dto: TranslatableStaticDto): TranslatableStatic

    fun createTemplate(dto: TranslatableStaticTemplateDto): TranslatableStatic

    fun updateValue(dto: TranslatableStaticDto): TranslatableStatic

    fun search(uuid: String, term: String?, lang: String?, page: Int?): List<TranslatableStatic>

    fun copy(uuid: String): List<TranslatableStatic>

}