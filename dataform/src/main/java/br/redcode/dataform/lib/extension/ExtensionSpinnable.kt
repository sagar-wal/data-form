package br.redcode.dataform.lib.extension

import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.model.payloads.PayloadOption

/**
 * Created by pedrofsn on 31/10/2017.
 */
fun Spinnable.toDTO(): PayloadOption {
    return PayloadOption(id.toLong(), selected)
}