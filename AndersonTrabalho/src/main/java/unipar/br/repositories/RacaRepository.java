package unipar.br.repositories;
import unipar.br.domain.Raca;
import unipar.br.infrestructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RacaRepository {

    private static final String INSERT =
            "INSERT INTO raca(ds_raca) VALUES (?)";

    private static final String FIND_ALL =
            "SELECT id, ds_raca FROM raca";

    public Raca insert(Raca raca) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, raca.getDescricao());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            rs.next();

            raca.setId(rs.getInt(1));

        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return raca;
    }

    public ArrayList<Raca> findAll() throws SQLException {
        ArrayList<Raca> racas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(FIND_ALL);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Raca raca = new Raca();
                raca.setId(rs.getInt("id"));
                raca.setDescricao(rs.getString("ds_raca"));
                racas.add(raca);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        }

        return racas;
    }
}
