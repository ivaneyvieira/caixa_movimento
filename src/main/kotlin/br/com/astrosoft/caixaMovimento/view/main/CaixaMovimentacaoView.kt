package br.com.astrosoft.caixaMovimento.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.updateItens
import br.com.astrosoft.caixaMovimento.model.beans.CaixaMovimento
import br.com.astrosoft.caixaMovimento.model.beans.UserSaci
import br.com.astrosoft.caixaMovimento.view.layout.CaixaMovimentacaoLayout
import br.com.astrosoft.caixaMovimento.viewmodel.ICaixaMovimentacaoView
import br.com.astrosoft.caixaMovimento.viewmodel.CaixaMovimentacaoViewModel
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

@Route(layout = CaixaMovimentacaoLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class CaixaMovimentacaoView: ViewLayout<CaixaMovimentacaoViewModel>(), ICaixaMovimentacaoView {
  private lateinit var gridMovimentoLink: PainelGridMovimentoLink
  override val viewModel: CaixaMovimentacaoViewModel = CaixaMovimentacaoViewModel(this)
  
  override fun isAccept() = true
  
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
      
      gridMovimentoLink = painelGridMovimentoLink()

      
      viewModel.updateGridMovimentoLink()
    }
  }
  
  override fun updateGridMovimentoLink(itens: List<CaixaMovimento>) {
    gridMovimentoLink.updateGrid(itens)
  }
  
  companion object {
    const val TAB_MOVIMENTO_LINK: String = "Cartão Link"
  }
}

