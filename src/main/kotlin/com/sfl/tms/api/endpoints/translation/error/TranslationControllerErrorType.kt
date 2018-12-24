package com.sfl.tms.api.endpoints.translation.error

import com.sfl.tms.api.common.model.error.type.ErrorType

/**
 * User: Vazgen Danielyan
 * Date: 12/5/18
 * Time: 4:26 PM
 */
enum class TranslationControllerErrorType : ErrorType {
    TRANSLATABLE_ENTITY_UUID_MISSING,
    TRANSLATABLE_ENTITY_NAME_MISSING,
    TRANSLATABLE_ENTITY_FIELD_NAME_MISSING,

    TRANSLATABLE_ENTITY_FIELD_TRANSLATIONS_MISSING,
    TRANSLATABLE_ENTITY_FIELD_TRANSLATION_TEXT_MISSING,
    TRANSLATABLE_ENTITY_FIELD_TRANSLATION_LANG_MISSING,

    TRANSLATABLE_STATIC_KEY_MISSING,
    TRANSLATABLE_STATIC_VALUE_MISSING,
    TRANSLATABLE_STATIC_LANGUAGE_MISSING,
}