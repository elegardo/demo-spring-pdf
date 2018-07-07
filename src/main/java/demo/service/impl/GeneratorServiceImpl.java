package demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import demo.service.GeneratorService;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    private static final String FONT_FAMILY = "Roboto";

    private static final String[] FONTS = { 
                                            "/fonts/Roboto-Regular-webfont.ttf",
                                            "/fonts/Roboto-Italic-webfont.ttf",
                                            "/fonts/Roboto-Bold-webfont.ttf",
                                            "/fonts/Roboto-BoldItalic-webfont.ttf",
                                            "/fonts/Roboto-Thin-webfont.ttf",
                                            "/fonts/Roboto-ThinItalic-webfont.ttf",
                                            "/fonts/Roboto-Light-webfont.ttf",
                                            "/fonts/Roboto-LightItalic-webfont.ttf",
                                            "/fonts/Roboto-Medium-webfont.ttf",
                                            "/fonts/Roboto-MediumItalic-webfont.ttf"
                                            };

    @Autowired
    private TemplateEngine templateEngine;

    private ITextRenderer renderer;

    public GeneratorServiceImpl() {
        renderer = new ITextRenderer();
        loadFonts(renderer);
    }

    private void loadFonts(ITextRenderer renderer) {
        try {
            for (String font : FONTS) {
                renderer.getFontResolver().addFont(font, FONT_FAMILY, BaseFont.CP1252, BaseFont.EMBEDDED, null);
            }
        } catch (DocumentException | IOException e) {
            System.out.println("No se pudo cargar el font");
        }

    }

    public byte[] createPDF(String templateName, HashMap<String, Object> data) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String processedHtml;
        String baseUrl;
        
        try {
            baseUrl = FileSystems
                                .getDefault()
                                .getPath("src", "main", "resources", "templates")
                                .toUri()
                                .toURL()
                                .toString();
            processedHtml = templateEngine.process(templateName, new Context(new Locale("es", "CL"), data));

            renderer.setDocumentFromString(processedHtml, baseUrl);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
            
            return outputStream.toByteArray();

        } catch (Exception e) {
            System.out.println("No se pudo generar el PDF");
        }
        
        return null;
    }

}
