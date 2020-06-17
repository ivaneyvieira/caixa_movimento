package br.com.astrosoft.pedidoTransferencia.view

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.updateItens
import br.com.astrosoft.pedidoTransferencia.model.beans.CaixaMovimento
import br.com.astrosoft.pedidoTransferencia.model.beans.UserSaci
import br.com.astrosoft.pedidoTransferencia.viewmodel.IPedidoTransferenciaView
import br.com.astrosoft.pedidoTransferencia.viewmodel.PedidoTransferenciaViewModel
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.tabSheet
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.provider.ListDataProvider
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(layout = PedidoTransferenciaLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoTransferenciaView: ViewLayout<PedidoTransferenciaViewModel>(), IPedidoTransferenciaView {
  private lateinit var gridMovimentoLink: Grid<CaixaMovimento>
  //
  override val viewModel: PedidoTransferenciaViewModel = PedidoTransferenciaViewModel(this)
  private val dataProviderMovimentoLink = ListDataProvider<CaixaMovimento>(mutableListOf())
  
  override fun isAccept(user: UserSaci) = true
  
  init {
    tabSheet {
      setSizeFull()
      tab {
        painelMovimentoLink()
      }.apply {
        val button = Button(TAB_MOVIMENTO_LINK) {
          viewModel.updateGridMovimentoLink()
        }
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        this.addComponentAsFirst(button)
      }
    }
  }
  
  fun HasComponents.painelMovimentoLink(): VerticalLayout {
    return verticalLayout {
      this.setSizeFull()
      isMargin = false
      isPadding = false
      
      gridMovimentoLink = this.grid(dataProvider = dataProviderMovimentoLink)
      
      viewModel.updateGridMovimentoLink()
    }
  }
  
  override fun updateGridMovimentoLink(itens: List<CaixaMovimento>) {
    gridMovimentoLink.deselectAll()
    dataProviderMovimentoLink.updateItens(itens)
  }
  
  companion object {
    const val TAB_MOVIMENTO_LINK: String = "Cartão Link"
  }
}

