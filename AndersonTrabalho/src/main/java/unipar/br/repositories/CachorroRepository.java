/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unipar.br.repositories;
/**
 *
 * @author jjoao
 */


import unipar.br.domain.Cachorro;
import unipar.br.infrestructure.ConnectionFactory;
import unipar.br.exeptions.NegocioException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import unipar.br.domain.Cor;
import unipar.br.domain.Pelagem;
import unipar.br.domain.Raca;

public class CachorroRepository {

    private static final String INSERT =
            "INSERT INTO cachorro(ds_nome, id_raca, id_cor, id_pelagem, st_perfume, ds_dt_nascimento) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL =
            "SELECT c.id, c.ds_nome, r.id AS id_raca, r.ds_raca, co.id AS id_cor, co.ds_cor, p.id AS id_pelagem, " +
            "p.ds_pelagem, c.st_perfume, c.ds_dt_nascimento " +
            "FROM cachorro c " +
            "JOIN raca r ON c.id_raca = r.id " +
            "JOIN cor co ON c.id_cor = co.id " +
            "JOIN pelagem p ON c.id_pelagem = p.id";

    public Cachorro insert(Cachorro cachorro) throws SQLException, NegocioException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cachorro.getNome());
            pstmt.setInt(2, cachorro.getRaca().getId());
            pstmt.setInt(3, cachorro.getCor().getId());
            pstmt.setInt(4, cachorro.getPelagem().getId());
            pstmt.setBoolean(5, cachorro.isStPerfume());
            pstmt.setDate(6, new java.sql.Date(cachorro.getDtNascimento().getTime()));

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            rs.next();

            cachorro.setId(rs.getInt(1));

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return cachorro;
    }

    public ArrayList<Cachorro> findAll() throws SQLException {
        ArrayList<Cachorro> cachorros = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(FIND_ALL);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cachorro cachorro = new Cachorro();
                cachorro.setId(rs.getInt("id"));
                cachorro.setNome(rs.getString("ds_nome"));

                Raca raca = new Raca();
                raca.setId(rs.getInt("id_raca"));
                raca.setDescricao(rs.getString("ds_raca"));
                cachorro.setRaca(raca);

                Cor cor = new Cor();
                cor.setId(rs.getInt("id_cor"));
                cor.setDescricao(rs.getString("ds_cor"));
                cachorro.setCor(cor);

                Pelagem pelagem = new Pelagem();
                pelagem.setId(rs.getInt("id_pelagem"));
                pelagem.setDescricao(rs.getString("ds_pelagem"));
                cachorro.setPelagem(pelagem);

                cachorro.setStPerfume(rs.getBoolean("st_perfume"));
                cachorro.setDtNascimento(rs.getDate("ds_dt_nascimento"));

                cachorros.add(cachorro);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return cachorros;
    }
}
