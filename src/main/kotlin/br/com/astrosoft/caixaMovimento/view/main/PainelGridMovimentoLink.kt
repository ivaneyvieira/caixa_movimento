package br.com.astrosoft.caixaMovimento.view.main

import br.com.astrosoft.caixaMovimento.model.beans.CaixaMovimento
import br.com.astrosoft.caixaMovimento.viewmodel.CaixaMovimentacaoViewModel
import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnLocalTime
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.framework.view.list
import br.com.astrosoft.framework.view.updateItens
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.init
import com.github.mvysny.karibudsl.v10.label
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.provider.ListDataProvider
import com.vaadin.flow.component.grid.ColumnTextAlign.END

class PainelGridMovimentoLink(): VerticalLayout() {
  private var gridMovimentoLink: Grid<CaixaMovimento>
  private val dataProviderMovimentoLink = ListDataProvider<CaixaMovimento>(mutableListOf())
  
  init {
    this.setSizeFull()
    isMargin = false
    isPadding = false
    
    gridMovimentoLink = this.grid(dataProvider = dataProviderMovimentoLink) {
      isMultiSort = true
      addColumnSeq("Num")
      this.addColumnInt(CaixaMovimento::lj) {
        this.width = "40px"
      }
      this.addColumnInt(CaixaMovimento::pedido) {
        this.width = "80px"
      }
      this.addColumnInt(CaixaMovimento::pdv) {
        this.width = "40px"
      }
      this.addColumnLocalDate(CaixaMovimento::data) {
        this.width = "100px"
      }
      this.addColumnString(CaixaMovimento::notaFiscal) {
        this.width = "100px"
      }
      this.addColumnLocalTime(CaixaMovimento::hora) {
        this.width = "40px"
      }
      this.addColumnInt(CaixaMovimento::nro) {
        this.width = "30px"
      }
      this.addColumnString(CaixaMovimento::tipos) {
        this.width = "100px"
      }
      this.addColumnString(CaixaMovimento::autoriz) {
        this.width = "80px"
      }
      this.addColumnDouble(CaixaMovimento::parcela) {
        this.width = "100px"
      }
      this.addColumnString(CaixaMovimento::obs) {
        this.flexGrow = 8
      }
    }
  }
  
  fun updateGrid(itens: List<CaixaMovimento>) {
    gridMovimentoLink.deselectAll()
    dataProviderMovimentoLink.updateItens(itens)
  }
  
  private fun @VaadinDsl Grid<CaixaMovimento>.addColumnSeq(label: String) {
    addColumn {
      val lista = list(this)
      lista.indexOf(it) + 1
    }.apply {
      this.textAlign = END
      isAutoWidth = true
      setHeader(label)
      width = "30px"
    }
  }
}

fun (@VaadinDsl HasComponents).painelGridMovimentoLink(block: (@VaadinDsl PainelGridMovimentoLink).() -> Unit = {}) =
  init(PainelGridMovimentoLink(), block)