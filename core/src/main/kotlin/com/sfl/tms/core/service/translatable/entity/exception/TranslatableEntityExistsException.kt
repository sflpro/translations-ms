package com.sfl.tms.core.service.translatable.entity.exception

/**
 * User: Vazgen Danielyan
 * Date: 12/5/18
 * Time: 6:11 PM
 */
class TranslatableEntityExistsException(val uuid: String, val label: String) : RuntimeException("Translatable entity already exists by '$uuid' uuid and '$label' label.")