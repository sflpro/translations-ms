package com.sfl.qup.tms.service.translatable.exception

/**
 * User: Vazgen Danielyan
 * Date: 12/5/18
 * Time: 6:11 PM
 */
class TranslatableEntityExistsByUuidException(val uuid: String) : RuntimeException("Translatable entity already exists by $uuid uuid.")