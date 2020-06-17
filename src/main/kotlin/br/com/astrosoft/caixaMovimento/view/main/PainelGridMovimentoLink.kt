package br.com.astrosoft.caixaMovimento.view.main

import br.com.astrosoft.caixaMovimento.model.beans.CaixaMovimento
import br.com.astrosoft.caixaMovimento.viewmodel.CaixaMovimentacaoViewModel
import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnLocalTime
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.framework.view.updateItens
import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.provider.ListDataProvider

class PainelGridMovimentoLink(): VerticalLayout() {
  private var gridMovimentoLink: Grid<CaixaMovimento>
  private val dataProviderMovimentoLink = ListDataProvider<CaixaMovimento>(mutableListOf())
  
  init {
    this.setSizeFull()
    isMargin = false
    isPadding = false
    
    gridMovimentoLink = this.grid(dataProvider = dataProviderMovimentoLink) {
     // this.addColumnInt(CaixaMovimento::item)
      this.addColumnInt(CaixaMovimento::lj)
      this.addColumnInt(CaixaMovimento::pedido)
      this.addColumnInt(CaixaMovimento::pdv)
      this.addColumnLocalDate(CaixaMovimento::data)
      this.addColumnString(CaixaMovimento::notaFiscal)
      this.addColumnLocalTime(CaixaMovimento::hora)
      this.addColumnInt(CaixaMovimento::nro)
      this.addColumnInt(CaixaMovimento::tp)
      this.addColumnString(CaixaMovimento::tipos)
      this.addColumnString(CaixaMovimento::autoriz)
      this.addColumnDouble(CaixaMovimento::parcela)
      this.addColumnString(CaixaMovimento::obs)
    }
  }
  
  fun updateGrid(itens: List<CaixaMovimento>) {
    gridMovimentoLink.deselectAll()
    dataProviderMovimentoLink.updateItens(itens)
  }
}