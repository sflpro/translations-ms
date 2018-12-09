package com.sfl.qup.tms.api.endpoints.translation.response.field

import com.sfl.qup.tms.api.common.model.response.AbstractApiResponseModel

/**
 * User: Vazgen Danielyan
 * Date: 12/5/18
 * Time: 5:13 PM
 */
data class TranslatableEntityFieldCreateResponseModel(val uuid: String, val name: String) : AbstractApiResponseModel