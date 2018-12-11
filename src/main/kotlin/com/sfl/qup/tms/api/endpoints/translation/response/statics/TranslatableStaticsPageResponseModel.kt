package com.sfl.qup.tms.api.endpoints.translation.response.statics

import com.sfl.qup.tms.api.common.model.response.AbstractApiResponseModel

/**
 * User: Vazgen Danielyan
 * Date: 12/11/18
 * Time: 6:20 PM
 */
data class TranslatableStaticsPageResponseModel(val statics: Map<String, List<TranslatableStaticResponseModel>>) : AbstractApiResponseModel