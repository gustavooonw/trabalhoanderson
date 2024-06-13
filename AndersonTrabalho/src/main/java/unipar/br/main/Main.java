package unipar.br.main;

import unipar.br.domain.Cachorro;
import unipar.br.domain.Raca;
import unipar.br.domain.Pelagem;
import unipar.br.domain.Cor;
import unipar.br.services.CachorroService;
import unipar.br.services.RacaService;
import unipar.br.services.PelagemService;
import unipar.br.services.CorService;
import unipar.br.exeptions.NegocioException;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        RacaService racaService = new RacaService();
        PelagemService pelagemService = new PelagemService();
        CorService corService = new CorService();
        CachorroService cachorroService = new CachorroService();

        try {
            Raca raca = new Raca();
            raca.setDescricao("Husky");
            raca = racaService.insert(raca);

            Pelagem pelagem = new Pelagem();
            pelagem.setDescricao("Medio");
            pelagem = pelagemService.insert(pelagem);

            Cor cor = new Cor();
            cor.setDescricao("Preto");
            cor = corService.insert(cor);
            
            String nomeCachorro = "Cleiton";
            boolean stPerfume = true;
            String dtNascimentoStr = "10/04/2023";

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dtNascimento = dateFormat.parse(dtNascimentoStr);

            Cachorro cachorro = new Cachorro();
            cachorro.setNome(nomeCachorro);
            cachorro.setRaca(raca);
            cachorro.setPelagem(pelagem);
            cachorro.setCor(cor);
            cachorro.setStPerfume(stPerfume);
            cachorro.setDtNascimento(dtNascimento);

            cachorro = cachorroService.insert(cachorro);
            JOptionPane.showMessageDialog(null, "Cachorro adicionado: " + cachorro);

        } catch (SQLException | NegocioException | ParseException e) {
            e.printStackTrace();
        }
    }
}
