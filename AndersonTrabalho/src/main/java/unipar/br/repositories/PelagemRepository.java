package unipar.br.repositories;
import  unipar.br.domain.Pelagem;
import unipar.br.infrestructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PelagemRepository {

    private static final String INSERT =
            "INSERT INTO pelagem(ds_pelagem) VALUES (?)";

    private static final String FIND_ALL =
            "SELECT id, ds_pelagem FROM pelagem";

    public Pelagem insert(Pelagem pelagem) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pelagem.getDescricao());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            rs.next();

            pelagem.setId(rs.getInt(1));

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return pelagem;
    }

    public ArrayList<Pelagem> findAll() throws SQLException {
        ArrayList<Pelagem> pelagens = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(FIND_ALL);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Pelagem pelagem = new Pelagem();
                pelagem.setId(rs.getInt("id"));
                pelagem.setDescricao(rs.getString("ds_pelagem"));

                pelagens.add(pelagem);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return pelagens;
    }
}
