/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unipar.br.services;
/**
 *
 * @author jjoao
 */
import unipar.br.domain.Cor;
import unipar.br.repositories.CorRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import unipar.br.exeptions.NegocioException;

public class CorService {

    private CorRepository corRepository;

    public CorService() {
        this.corRepository = new CorRepository();
    }

    public Cor insert(Cor cor) throws SQLException, NegocioException {
        validarCor(cor);
        return corRepository.insert(cor);
    }

    public ArrayList<Cor> findAll() throws SQLException {
        return corRepository.findAll();
    }

    private void validarCor(Cor cor) throws NegocioException {
        if (cor.getDescricao() == null || cor.getDescricao().trim().isEmpty()) {
            throw new NegocioException("A descrição da cor não pode ser vazia.");
        }
    }
}
