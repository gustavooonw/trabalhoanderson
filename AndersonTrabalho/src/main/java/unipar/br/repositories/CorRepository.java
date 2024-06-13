/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unipar.br.repositories;
/**
 *
 * @author jjoao
 */
import unipar.br.domain.Cor;
import unipar.br.infrestructure.ConnectionFactory;
import unipar.br.exeptions.NegocioException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import unipar.br.domain.Cor;
import unipar.br.exeptions.NegocioException;
import unipar.br.infrestructure.ConnectionFactory;

public class CorRepository {

    private static final String INSERT =
            "INSERT INTO cor(ds_cor) VALUES (?)";

    private static final String FIND_ALL =
            "SELECT id, ds_cor FROM cor";

    public Cor insert(Cor cor) throws SQLException, NegocioException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cor.getDescricao());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            rs.next();

            cor.setId(rs.getInt(1));

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return cor;
    }

    public ArrayList<Cor> findAll() throws SQLException {
        ArrayList<Cor> cores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(FIND_ALL);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cor cor = new Cor();
                cor.setId(rs.getInt("id"));
                cor.setDescricao(rs.getString("ds_cor"));

                cores.add(cor);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return cores;
    }
}
