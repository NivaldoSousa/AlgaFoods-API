package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

/*
 * Responsavel por emitir o relatorio
 * */
@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

        try {
            // espeficicar o caminho do relatorio compilado .jasper
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            //definindo os parametros do relatorio
            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
            parametros.put("TITULO", "Vendas Diárias");

            //buscando os dados do banco
            var VendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(VendasDiarias); //construindo o Bean

            //representa um relatorio preenchido com o caminho do arquivo do relatorio, parametros e dados do banco
            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint); //retorna o relatorio
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
