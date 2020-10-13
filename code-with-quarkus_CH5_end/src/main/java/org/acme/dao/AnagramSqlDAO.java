package org.acme.dao;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.util.StringUtil;
import org.acme.ExampleResource;
import org.acme.dto.AnagramResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;

@ApplicationScoped
public class AnagramSqlDAO {

    @DataSource("anagram")
    @Inject
    AgroalDataSource dataSource;

    @Inject
    ExampleResource exampleResource;

    public AnagramResponse generateAndSaveAnagram(String source) throws SQLException {
        Integer anagramSourceId = saveAnagramSource(source);
        return saveAnagram(anagramSourceId,source);
    }

    public Integer saveAnagramSource(String source) throws SQLException {
        Integer sourceId = null;
        if(source != null && !"".equals(source.trim())) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("insert into anagram_source (anagram_source_text) values(?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, source);
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    sourceId = resultSet.getInt(1);
                }
            }
        }
        return sourceId;
    }

    public AnagramResponse saveAnagram(Integer anagramSourceId,String source) throws SQLException {
        AnagramResponse response = null;
        if(anagramSourceId != null) {
            response = exampleResource.getAnagram(source);
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("insert into anagram (anagram_text,anagram_source_id) values(?,?)");
                statement.setString(1, response.getAnagram());
                statement.setInt(2, anagramSourceId);
                ResultSet results = statement.executeQuery();
            }
        }
        return response;
    }
}
