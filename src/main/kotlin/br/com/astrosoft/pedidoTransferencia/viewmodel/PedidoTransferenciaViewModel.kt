package br.com.astrosoft.pedidoTransferencia.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.pedidoTransferencia.model.beans.CaixaMovimento

class PedidoTransferenciaViewModel(view: IPedidoTransferenciaView): ViewModel<IPedidoTransferenciaView>(view) {
  fun updateGridMovimentoLink() {
    view.updateGridMovimentoLink(listMovimentoLink())
  }
  
  private fun listMovimentoLink(): List<CaixaMovimento> {
    return CaixaMovimento.listaCaixaMovimento()
  }
}

interface IPedidoTransferenciaView: IView {
  fun updateGridMovimentoLink(itens: List<CaixaMovimento>)
}