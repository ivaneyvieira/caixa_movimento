package br.com.astrosoft.caixaMovimento.model.beans

import br.com.astrosoft.AppConfig
import br.com.astrosoft.caixaMovimento.model.saci
import java.time.LocalDate
import java.time.LocalTime

data class CaixaMovimento(val item: Int?,
                               val lj: Int,
                               val pedido: Int,
                               val pdv: Int,
                               val data: LocalDate?,
                               val nf: Int,
                               val sr: String,
                               val hora: LocalTime?,
                               val nro: Int,
                               val tp: Int,
                               val tipos: String?,
                               val autoriz: String,
                               val parcela: Double?,
                               val obs: String?) {
  val notaFiscal: String
    get() = numeroNota(nf.toString(), sr)
  
  private fun numeroNota(nfno: String, nfse: String): String {
    return when {
      nfno == "" -> ""
      nfse == "" -> nfno
      else       -> "$nfno/$nfse"
    }
  }
  
  companion object {
    fun listaCaixaMovimento(): List<CaixaMovimento> {
      return saci.listaCaixaMovimentoLink()
    }
  }
}