package com.sfl.qup.tms.api.endpoints.translation

import com.sfl.qup.tms.api.common.annotations.ValidateActionRequest
import com.sfl.qup.tms.api.common.model.AbstractApiModel
import com.sfl.qup.tms.api.common.model.ResultModel
import com.sfl.qup.tms.api.common.model.error.type.EntityExistsErrorModel
import com.sfl.qup.tms.api.endpoints.AbstractBaseController.Companion.created
import com.sfl.qup.tms.api.endpoints.AbstractBaseController.Companion.internal
import com.sfl.qup.tms.api.endpoints.AbstractBaseController.Companion.ok
import com.sfl.qup.tms.api.endpoints.translation.request.entity.TranslatableEntityCreateRequestModel
import com.sfl.qup.tms.api.endpoints.translation.request.entity.TranslatableEntityTranslationsCreateRequestModel
import com.sfl.qup.tms.api.endpoints.translation.request.field.TranslatableEntityFieldCreateRequestModel
import com.sfl.qup.tms.api.endpoints.translation.request.statics.TranslatableStaticCreateRequestModel
import com.sfl.qup.tms.api.endpoints.translation.request.statics.TranslatableStaticUpdateRequestModel
import com.sfl.qup.tms.api.endpoints.translation.response.entity.TranslatableEntityCreateResponseModel
import com.sfl.qup.tms.api.endpoints.translation.response.entity.TranslatableEntityTranslationCreateResponseModel
import com.sfl.qup.tms.api.endpoints.translation.response.entity.TranslatableEntityTranslationsCreateResponseModel
import com.sfl.qup.tms.api.endpoints.translation.response.field.TranslatableEntityFieldCreateResponseModel
import com.sfl.qup.tms.api.endpoints.translation.response.statics.TranslatableStaticResponseModel
import com.sfl.qup.tms.api.endpoints.translation.response.statics.TranslatableStaticsPageResponseModel
import com.sfl.qup.tms.service.language.exception.LanguageNotFoundByIdException
import com.sfl.qup.tms.service.language.exception.LanguageNotFoundByLangException
import com.sfl.qup.tms.service.translatable.entity.TranslatableEntityService
import com.sfl.qup.tms.service.translatable.entity.TranslatableEntityTranslationService
import com.sfl.qup.tms.service.translatable.entity.dto.TranslatableEntityDto
import com.sfl.qup.tms.service.translatable.entity.dto.TranslatableEntityTranslationDto
import com.sfl.qup.tms.service.translatable.entity.exception.TranslatableEntityExistsByUuidException
import com.sfl.qup.tms.service.translatable.entity.exception.TranslatableEntityNotFoundByUuidException
import com.sfl.qup.tms.service.translatable.entity.exception.TranslatableEntityTranslationExistException
import com.sfl.qup.tms.service.translatable.field.TranslatableEntityFieldService
import com.sfl.qup.tms.service.translatable.field.dto.TranslatableEntityFieldDto
import com.sfl.qup.tms.service.translatable.field.exception.TranslatableEntityFieldExistsForTranslatableEntityException
import com.sfl.qup.tms.service.translatablestatic.TranslatableStaticService
import com.sfl.qup.tms.service.translatablestatic.dto.TranslatableStaticDto
import com.sfl.qup.tms.service.translatablestatic.exception.TranslatableStaticNotFoundByKeyAndLanguageIdException
import com.sfl.qup.tms.service.translatablestatic.exception.TranslatableStaticNotFoundByKeyException
import com.sfl.qup.tms.service.translatablestatic.exception.TranslatableStaticExistException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * User: Vazgen Danielyan
 * Date: 12/5/18
 * Time: 2:05 PM
 */
@RestController
@RequestMapping("/translation")
class TranslationController {

    //region Injection

    @Autowired
    private lateinit var translatableEntityService: TranslatableEntityService

    @Autowired
    private lateinit var translatableEntityTranslationService: TranslatableEntityTranslationService

    @Autowired
    private lateinit var translatableEntityFieldService: TranslatableEntityFieldService

    @Autowired
    private lateinit var translatableStaticService: TranslatableStaticService

    //endregion

    //region Entity with translations

    @ValidateActionRequest
    @RequestMapping(value = ["/entity"], method = [RequestMethod.POST])
    fun createTranslatableEntity(@RequestBody request: TranslatableEntityCreateRequestModel): ResponseEntity<ResultModel<out AbstractApiModel>> = try {
        request
                .also { logger.trace("Creating new TranslatableEntity for provided request - {} ", it) }
                .let { translatableEntityService.create(TranslatableEntityDto(it.uuid, it.name)) }
                .let { created(TranslatableEntityCreateResponseModel(it.uuid, it.name)) }
                .also { logger.debug("Successfully created TranslatableEntity for provided request - {} ", request) }
    } catch (e: TranslatableEntityExistsByUuidException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    }

    @ValidateActionRequest
    @RequestMapping(value = ["/entity/translations"], method = [RequestMethod.POST])
    fun createTranslatableEntityWithTranslations(@RequestBody request: TranslatableEntityTranslationsCreateRequestModel): ResponseEntity<ResultModel<out AbstractApiModel>> = try {
        request
                .also { logger.trace("Creating new TranslatableEntityTranslations for provided request - {} ", it) }
                .let {
                    it.translations
                            .map { translatableEntityTranslationService.create(TranslatableEntityTranslationDto(request.uuid, it.lang, it.text)) }
                            .let { created(TranslatableEntityTranslationsCreateResponseModel(request.uuid, it.map { TranslatableEntityTranslationCreateResponseModel(it.text, it.language.lang) })) }
                }
                .also { logger.debug("Successfully created TranslatableEntityTranslations for provided request - {} ", request) }
    } catch (e: LanguageNotFoundByLangException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    } catch (e: TranslatableEntityNotFoundByUuidException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    } catch (e: TranslatableEntityTranslationExistException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    }

    //endregion

    //region Field with translations

    @ValidateActionRequest
    @RequestMapping(value = ["/entity/field"], method = [RequestMethod.POST])
    fun createTranslatableEntityField(@RequestBody request: TranslatableEntityFieldCreateRequestModel): ResponseEntity<ResultModel<out AbstractApiModel>> = try {
        request
                .also { logger.trace("Creating new translatable entity field for provided request - {} ", it) }
                .let { translatableEntityFieldService.create(TranslatableEntityFieldDto(it.entityUuid, it.fieldName)) }
                .let { created(TranslatableEntityFieldCreateResponseModel(it.entity.uuid, it.name)) }
                .also { logger.debug("Successfully created translatable entity field for provided request - {} ", request) }
    } catch (e: TranslatableEntityFieldExistsForTranslatableEntityException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    }

    //endregion

    //region Static translations

    @ValidateActionRequest
    @RequestMapping(value = ["/static"], method = [RequestMethod.POST])
    fun createTranslatableStatic(@RequestBody request: TranslatableStaticCreateRequestModel): ResponseEntity<ResultModel<out AbstractApiModel>> = try {
        request
                .also { logger.trace("Creating new TranslatableStatic for provided request - {} ", it) }
                .let { translatableStaticService.create(TranslatableStaticDto(it.key, it.value, it.languageId)) }
                .let { created(TranslatableStaticResponseModel(it.key, it.value, it.language.lang)) }
                .also { logger.debug("Successfully created TranslatableStatic for provided request - {} ", request) }
    } catch (e: LanguageNotFoundByIdException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    } catch (e: TranslatableStaticExistException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    }

    @ValidateActionRequest
    @RequestMapping(value = ["/static"], method = [RequestMethod.PATCH])
    fun updateTranslatableStatic(@RequestBody request: TranslatableStaticUpdateRequestModel): ResponseEntity<ResultModel<out AbstractApiModel>> = try {
        request
                .also { logger.trace("Updating TranslatableStatic for provided request - {} ", it) }
                .let { translatableStaticService.update(TranslatableStaticDto(it.key, it.value, it.languageId)) }
                .let { ok(TranslatableStaticResponseModel(it.key, it.value, it.language.lang)) }
                .also { logger.debug("Successfully updated TranslatableStatic for provided request - {} ", request) }
    } catch (e: LanguageNotFoundByIdException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    } catch (e: TranslatableStaticNotFoundByKeyAndLanguageIdException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    }

    @ValidateActionRequest
    @RequestMapping(value = ["/static"], method = [RequestMethod.GET])
    fun getStaticTranslation(@RequestParam("key", required = true) key: String, @RequestParam("languageId", required = false) languageId: Long?) = try {
        key
                .also { logger.trace("Retrieving TranslatableStatic for provided key - {}, languageId - {} ", it, languageId) }
                .let {
                    if (languageId == null) {
                        translatableStaticService.getByKey(it)
                                .map { TranslatableStaticResponseModel(it.key, it.value, it.language.lang) }
                                .groupBy { it.lang }
                                .let { ok(TranslatableStaticsPageResponseModel(it)) }
                                .also { logger.debug("Retrieved TranslatableStatic for provided key - {}, languageId - {} ", key, languageId) }

                    } else {
                        translatableStaticService.getByKeyAndLanguageId(it, languageId)
                                .let { ok(TranslatableStaticResponseModel(it.key, it.value, it.language.lang)) }
                                .also { logger.debug("Retrieved TranslatableStatic for provided key - {}", key) }
                    }
                }
    } catch (e: TranslatableStaticNotFoundByKeyException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    } catch (e: LanguageNotFoundByIdException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    } catch (e: TranslatableStaticNotFoundByKeyAndLanguageIdException) {
        internal(EntityExistsErrorModel(e.localizedMessage))
    }

    @ValidateActionRequest
    @RequestMapping(value = ["/static/list"], method = [RequestMethod.GET])
    fun getStaticTranslations(@RequestParam("term", required = false) term: String?, @RequestParam("page", required = false) page: Int?) = term
            .also { logger.trace("Retrieving TranslatableStatic list for provided term - {}, page - {} ", it, page) }
            .let { translatableStaticService.search(it, page) }
            .map { TranslatableStaticResponseModel(it.key, it.value, it.language.lang) }
            .groupBy { it.lang }
            .let { ok(TranslatableStaticsPageResponseModel(it)) }
            .also { logger.debug("Retrieved TranslatableStatic list for provided term - {}, page - {} ", term, page) }

    //endregion

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(TranslationController::class.java)
    }

}