package com.sfl.tms.core.service.language.impl

import com.sfl.tms.core.domain.language.Language
import com.sfl.tms.core.persistence.language.LanguageRepository
import com.sfl.tms.core.service.language.LanguageService
import com.sfl.tms.core.service.language.exception.LanguageExistByLangException
import com.sfl.tms.core.service.language.exception.LanguageNotFoundByLangException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * User: Vazgen Danielyan
 * Date: 12/4/18
 * Time: 6:13 PM
 */
@Service
class LanguageServiceImpl : LanguageService {

    //region Injection

    @Autowired
    private lateinit var languageRepository: LanguageRepository

    //endregion

    @Transactional(readOnly = true)
    override fun findByLang(lang: String): Language? = lang
        .also { logger.trace("Retrieving language for provided lang - {} ", lang) }
        .let { languageRepository.findByLang(it) }
        .also { logger.debug("Retrieved language for provided lang - {} ", lang) }

    @Throws(LanguageNotFoundByLangException::class)
    @Transactional(readOnly = true)
    override fun getByLang(lang: String): Language = lang
        .also { logger.trace("Retrieving language for provided lang - {} ", lang) }
        .let {
            findByLang(lang).let {
                if (it == null) {
                    logger.error("Can't find language for lang - {}", lang)
                    throw LanguageNotFoundByLangException(lang)
                }
                logger.debug("Retrieved language for provided lang - {} ", lang)
                it
            }
        }

    @Transactional(readOnly = true)
    override fun getAll(): List<Language> {
        logger.trace("Retrieving all languages")
        return languageRepository.findAll().also { logger.debug("Retrieved all languages") }
    }

    @Throws(LanguageExistByLangException::class)
    @Transactional
    override fun create(lang: String): Language = lang
        .also { logger.trace("Creating new language for provided lang - {} ", lang) }
        .let {
            findByLang(it).let {
                if (it == null) {
                    Language()
                        .apply { this.lang = lang }
                        .let { languageRepository.save(it) }
                        .also { logger.debug("Successfully created new Language for provided lang - {}", lang) }
                } else {
                    logger.error("Language with {} lang already exists. Skipping creation.", lang)
                    throw LanguageExistByLangException(lang)
                }
            }
        }

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(LanguageServiceImpl::class.java)
    }
}