package unipar.br.services;
import unipar.br.domain.Pelagem;
import  unipar.br.repositories.PelagemRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class PelagemService {

    private PelagemRepository pelagemRepository;

    public PelagemService() {
        this.pelagemRepository = new PelagemRepository();
    }

    public Pelagem insert(Pelagem pelagem) throws SQLException {
        return pelagemRepository.insert(pelagem);
    }

    public ArrayList<Pelagem> findAll() throws SQLException {
        return pelagemRepository.findAll();
    }
}