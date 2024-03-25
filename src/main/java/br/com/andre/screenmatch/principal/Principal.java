package br.com.andre.screenmatch.principal;

import br.com.andre.screenmatch.model.DadosEpisodio;
import br.com.andre.screenmatch.model.DadosSerie;
import br.com.andre.screenmatch.model.DadosTemporada;
import br.com.andre.screenmatch.service.APIKeyReader;
import br.com.andre.screenmatch.service.ConverteDados;
import br.com.andre.screenmatch.service.consumoAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    APIKeyReader apiKeyReader = new APIKeyReader();
    private Scanner leitura = new Scanner(System.in);
    private consumoAPI consumo = new consumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=" + apiKeyReader.lerAPIKey("./apikey.txt");
    public void exibeMenu(){
        System.out.println("Digite o nome da série: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);


        List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i=1;i<= dados.numeroTemporadas();i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + APIKEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for(int i = 0; i < dados.numeroTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).listaEpisodios();
//            for(int j = 0; j< episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.listaEpisodios().forEach(e -> System.out.println(e.titulo())));
    }

}
