package demo.service;

import java.util.HashMap;

public interface GeneratorService {
    
    public byte[] createPDF(String templateName, HashMap<String, Object> data);
    
}
