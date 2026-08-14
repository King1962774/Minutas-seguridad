package com.minutas.service;

import com.minutas.model.InformeTurno;
import com.minutas.model.Turno;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;

public class ReporteService {

    public File generarPdfInformeTurno(Turno turno, InformeTurno informe, String rutaDestino) {
        File file = new File(rutaDestino);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("INFORME DE FIN DE TURNO - MINUTAS SEGURIDAD");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 710);
                contentStream.showText("Turno ID: " + turno.getId() + " | Puesto: " + turno.getPuesto() + " | Tipo: " + turno.getTipo());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Hora Inicio: " + turno.getHoraInicio() + " | Hora Fin: " + turno.getHoraFin());
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Resumen Visitantes: " + (informe != null ? informe.getResumenVisitantes() : 0));
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Resumen Vehículos: " + (informe != null ? informe.getResumenVehiculos() : 0));
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("Resumen Paquetes: " + (informe != null ? informe.getResumenPaquetes() : 0));
                contentStream.newLineAtOffset(0, -35);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.showText("Pendientes y Novedades:");
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(informe != null && informe.getPendientes() != null ? informe.getPendientes() : "Sin pendientes.");
                contentStream.endText();
            }

            document.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
