package com.example.spring.myprojectTask1.repository;


import com.example.spring.myprojectTask1.model.Tutorial;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class JDBCTutorialRepository implements TutorialRepository {
    private final String CONNSTRING = "jdbc:postgresql://localhost:5432/postgres?user=" +
            "postgres&password=1234";

    @Override
    public void save(Tutorial tutorial) {
        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {
            PreparedStatement statement = connection.
                    prepareStatement("Insert into tutorials(title, description, published ) Values(?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, tutorial.getTitle());
            statement.setString(2, tutorial.getDescription());
            statement.setBoolean(3, tutorial.isPublished());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            tutorial.setId(resultSet.getInt(1));
        } catch (SQLException e) {

        }
    }

    @Override
    public int update(long id,Tutorial tutorial) {
        int updated=0;

        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {
            PreparedStatement statement = connection.
                    prepareStatement("update tutorials set title=?,description=?,published=? where id=?");
            statement.setString(1,tutorial.getTitle());
            statement.setString(2,tutorial.getDescription());
            statement.setBoolean(3,tutorial.isPublished());
            statement.setLong(4,id);
            updated=statement.executeUpdate();



        }catch (SQLException e){

        }

        return updated;
    }

    @Override
    public Tutorial findById(long id) {
        Tutorial tutorial = null;
        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM tutorials where id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            tutorial = getTutorialFromResultSet(resultSet);
        } catch (SQLException e) {

        }
        return tutorial;
    }

    @Override
    public int deleteById(long id) {
        int isDeleted=0;
        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {
            PreparedStatement statement = connection.
                    prepareStatement("DELETE FROM tutorials where id=?");
            statement.setLong(1, id);
            isDeleted = statement.executeUpdate();


        } catch (SQLException e) {

        }
        return isDeleted;
    }


    @Override
    public List<Tutorial> findAll() {

        List<Tutorial> tutorials = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {

            PreparedStatement preparedStatement = connection.prepareStatement("Select * from tutorials");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tutorials.add(getTutorialFromResultSet(resultSet));
            }

        } catch (SQLException e) {

        }

        return tutorials;
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {

        List<Tutorial> tutorials=new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM tutorials where published=?");
            statement.setBoolean(1,published);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tutorial tutorial = new Tutorial();
                tutorial=getTutorialFromResultSet(resultSet);
                tutorials.add(tutorial);
            }
        }catch (SQLException e){

        }
        return tutorials;
    }

    @Override
    public int deleteAll() {
        int isDeleted=0;
        try (Connection connection = DriverManager.getConnection(CONNSTRING)) {
            PreparedStatement statement = connection.
                    prepareStatement("DELETE FROM tutorials");
            isDeleted = statement.executeUpdate();


        } catch (SQLException e) {

        }
        return isDeleted;



    }



    private Tutorial getTutorialFromResultSet(ResultSet resultSet) throws SQLException {
        Tutorial tutorial = new Tutorial();
        tutorial.setId(resultSet.getInt("id"));
        tutorial.setTitle(resultSet.getString("title"));
        tutorial.setDescription(resultSet.getString("description"));
        tutorial.setPublished(resultSet.getBoolean("published"));
        return tutorial;
    }

}
