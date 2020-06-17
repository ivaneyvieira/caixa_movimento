package br.com.astrosoft.caixaMovimento.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.caixaMovimento.model.beans.CaixaMovimento

class CaixaMovimentacaoViewModel(view: ICaixaMovimentacaoView): ViewModel<ICaixaMovimentacaoView>(view) {
  fun updateGridMovimentoLink() {
    view.updateGridMovimentoLink(listMovimentoLink())
  }
  
  private fun listMovimentoLink(): List<CaixaMovimento> {
    return CaixaMovimento.listaCaixaMovimento()
  }
}

interface ICaixaMovimentacaoView: IView {
  fun updateGridMovimentoLink(itens: List<CaixaMovimento>)
}