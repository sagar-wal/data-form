package br.redcode.dataform.lib.ui

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.EditText
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaTextual(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_textual, pergunta, configuracao), Perguntavel {

    private lateinit var editText: EditText

    override fun initView(view: View) {
        super.initView(view)
        editText = view.findViewById<EditText>(R.id.editText)
    }

    override fun populateView() {
        super.populateView()
        editText.setTag("ui_pergunta_" + pergunta.id + "_edittext")
        pergunta.resposta?.resposta?.let { editText.setText(it) }
        editText.isEnabled = configuracao.editavel
        if (configuracao.editavel.not()) {
            editText.setTextAppearance(contextActivity, android.R.style.TextAppearance_Widget_TextView);
            editText.setTypeface(null, Typeface.ITALIC);
        }
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta(resposta = editText.text.toString().trim())
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return editText.text.toString().trim().isNotEmpty()
    }

    override fun getMensagemErroPreenchimento(): String {
        return contextActivity.getString(R.string.o_campo_de_texto_nao_foi_preenchido)
    }

}