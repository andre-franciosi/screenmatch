package br.com.andre.screenmatch;

import br.com.andre.screenmatch.model.DadosEpisodio;
import br.com.andre.screenmatch.model.DadosSerie;
import br.com.andre.screenmatch.model.DadosTemporada;
import br.com.andre.screenmatch.service.APIKeyReader;
import br.com.andre.screenmatch.service.ConverteDados;
import br.com.andre.screenmatch.service.consumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Lendo a apikey de outro arquivo
		APIKeyReader apiKeyReader = new APIKeyReader();
		String apiKey = apiKeyReader.lerAPIKey("./apikey.txt");

		//Criando o consumoAPI e Fazendo primeiro consumo da API, com apenas nome da série
		var consumoAPI = new consumoAPI();
		var json = consumoAPI.obterDados("http://www.omdbapi.com/?t=breaking-bad&apikey=" + apiKey);

		System.out.println(json);

		//Convertendo dados para uma melhor apresentação
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		//Consumindo API para buscar dados de um episódio específico
		json = consumoAPI.obterDados("http://www.omdbapi.com/?t=breaking-bad&Season=1&Episode=1&apikey=" + apiKey);
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		//Criando lista para armazenar episódios
		List<DadosTemporada> temporadas = new ArrayList<>();

		//Iterando pelo número de temporadas e imprimindo todas as temporadas com sua lista de episódios
		for(int i=1;i<= dados.numeroTemporadas();i++){
			json = consumoAPI.obterDados("http://www.omdbapi.com/?t=breaking-bad&Season="+ i +"&apikey=" + apiKey);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
