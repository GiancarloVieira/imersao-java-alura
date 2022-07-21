import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://imdb-api.com/en/API/Top250Movies/k_p70m0c4i";
        URI  endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // pegar só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        var geradora = new GeradoraDeFigurinhas();
        for (Map<String,String> filme : listaDeFilmes) {
            
            String urlImagem = filme.get("iumage");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "saida/" + titulo + ".png";
            
            geradora.cria(inputStream,  nomeArquivo);
            
            System.out.println(filme.get(titulo));            
            System.out.println();
        }
    }
}
