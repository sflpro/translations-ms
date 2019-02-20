package com.sfl.tms.core.service.translatable.entity.exception

/**
 * User: Vazgen Danielyan
 * Date: 12/5/18
 * Time: 6:11 PM
 */
class TranslatableEntityNotFoundException(uuid: String, label: String) : RuntimeException("Translatable entity not found by '$uuid' uuid and '$label' label.")