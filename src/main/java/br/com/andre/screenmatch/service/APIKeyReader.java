package br.com.andre.screenmatch.service;

import java.io.BufferedReader;
import java.io.FileReader;

public class APIKeyReader {
    public String lerAPIKey(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

