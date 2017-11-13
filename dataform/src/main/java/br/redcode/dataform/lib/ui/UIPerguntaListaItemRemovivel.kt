package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterItemRemovivel
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.DuasLinhas
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta


/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaListaItemRemovivel(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario, val handlerInputPopup: HandlerInputPopup) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_lista_item_removivel, pergunta, configuracao), Perguntavel {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var buttonAdicionar: Button

    private val adapter = AdapterItemRemovivel(object : OnItemClickListener {
        override fun onItemClickListener(position: Int) {
            removerItemDaLista(position)
        }
    })

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        textViewAndamento = view.findViewById<TextView>(R.id.textViewAndamento)
        buttonAdicionar = view.findViewById<Button>(R.id.buttonAdicionar)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.setCustomAdapter(adapter)
        buttonAdicionar.setOnClickListener { handlerInputPopup.chamarPopup() }

//        if (pergunta.inputPopup == null) {
//            throw RuntimeException("Pergunta do tipo LISTA_ITEM_REMOVIVEL deve conter um inputPopup")
//        }

        // DEFININDO O QUE O CÓDIGO A SER EXECUTADO
        handlerInputPopup.function = { duasLinhas: DuasLinhas ->
            adapter.adicionar(duasLinhas)
            atualizarContador()
        }

        pergunta.resposta?.respostas?.let {
            val mutavel = it as MutableList<String>

            for (string in mutavel) {
                if (string.contains("#").not()) {
                    it.remove(string)
                }
            }

            val listaDuasLinhas: List<DuasLinhas> = mutavel.map { stringComSeparador ->
                object : DuasLinhas {
                    override fun getLinha1(): String {
                        return stringComSeparador.split("#")[0]
                    }

                    override fun getLinha2(): String {
                        return stringComSeparador.split("#")[1]
                    }
                }
            }

            adapter.setLista(listaDuasLinhas)
        }

        atualizarContador()
    }

    private fun removerItemDaLista(position: Int) {
        adapter.remover(position)
        atualizarContador()
    }

    private fun atualizarContador() {
        val tamanho = adapter.itemCount
        val maximo = pergunta.getLimiteMaximo()
        textViewAndamento.text = String.format(contextActivity.getString(R.string.x_barra_x), tamanho, maximo)

        buttonAdicionar.isEnabled = tamanho != maximo
        if (isPreenchidoCorretamente()) indicador.hide()
    }

    override fun getResposta(): Resposta {
        val listaString: List<String> = adapter.getLista().map { duasLinhas -> duasLinhas.getLinha1() + " # " + duasLinhas.getLinha2() }
        val resposta = Resposta(respostas = listaString)
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        val countMarcadas = getQuantidadeAlternativasMarcadas()
        return countMarcadas >= pergunta.getLimiteMinimo() && countMarcadas <= pergunta.getLimiteMaximo()
    }

    fun getQuantidadeAlternativasMarcadas(): Int {
        return getResposta().alternativas?.filter { it.selecionado }?.size ?: 0
    }

    override fun getMensagemErroPreenchimento(): String {
        return String.format(contextActivity.getString(R.string.faltam_x_itens), (pergunta.getLimiteMaximo() - getQuantidadeAlternativasMarcadas()))
    }

}