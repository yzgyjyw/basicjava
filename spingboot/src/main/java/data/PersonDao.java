package data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class PersonDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insert() {
        jdbcTemplate.update("insert into Persons values (?,?,?,?,?)", 1, "gates", "bill", "xuewumen", "nanjing");

        HashMap<String, String> row = new HashMap<>();
        row.put("Id_P", "2");
        row.put("LastName", "bill1");
        row.put("FirstName", "bill2");

        Number number = simpleJdbcInsert.execute(row);
        System.out.println("number:" + number);
    }

    public void batchUpdate(){
        jdbcTemplate.batchUpdate("insert into Persons values(?,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1,i);
                preparedStatement.setString(2,"lastName-"+i);
                preparedStatement.setString(3,"firstName-"+i);
                preparedStatement.setString(4,"address-"+i);
                preparedStatement.setString(5,"city-"+i);
            }

            @Override
            public int getBatchSize() {
                return 10;
            }
        });

        HashMap[] mapArray  = new HashMap[2];


        List<Persons> list = new ArrayList<>();
        list.add(new Persons(1,"ln","","",""));
        list.add(new Persons(1,"ln","fn","",""));

        namedParameterJdbcTemplate.batchUpdate("insert into Persons(LastName,FirstName) values(:lastName,:firstName)", SqlParameterSourceUtils.createBatch(list));
    }

    public void listData() {
        System.out.println("Count:" + jdbcTemplate.queryForObject("select count(*) from Persons", Long.class));


        List<String> lastNames = jdbcTemplate.queryForList("select LastName from Persons", String.class);
        lastNames.forEach(System.out::println);

        jdbcTemplate.query("select * from Persons", new RowMapper<Persons>() {
            @Override
            public Persons mapRow(ResultSet resultSet, int i) throws SQLException {
                return null;
            }
        });

    }

}
