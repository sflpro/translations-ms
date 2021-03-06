package com.sfl.tms.rest.common.communicator.translation.request.aggregation.multiple

import com.sfl.tms.rest.common.communicator.translation.error.TranslationControllerErrorType
import com.sfl.tms.rest.common.model.error.ErrorType
import com.sfl.tms.rest.common.model.request.AbstractApiRequestModel
import org.apache.commons.lang3.StringUtils

/**
 * User: Vazgen Danielyan
 * Date: 1/18/19
 * Time: 12:39 PM
 */
data class TranslationAggregationByEntityRequestModel(var uuid: String, val label: String, var name: String?, var languages: List<TranslationAggregationByLanguage>) : AbstractApiRequestModel() {
    override fun validateRequiredFields(): List<ErrorType> = ArrayList<ErrorType>()
            .apply {
                if (StringUtils.isEmpty(uuid)) {
                    add(TranslationControllerErrorType.TRANSLATABLE_ENTITY_UUID_MISSING)
                }
            }.apply {
                if (StringUtils.isEmpty(label)) {
                    add(TranslationControllerErrorType.TRANSLATABLE_ENTITY_LABEL_MISSING)
                }
            }.apply {
                languages.map { it.validateRequiredFields() }.flatten().distinct().let { addAll(it) }
            }
}