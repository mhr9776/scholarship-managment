package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.UseCase;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.RequestScholarshipByStudentUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class RequestScholarshipByStudentUseCaseImpl implements RequestScholarshipByStudentUseCase {
    public void request(Scholarship scholarship) {
        User user = AuthenticationService.getInstance().getLoginUser();
        if (user != null && user.getRole().equals("Student")){
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "insert into " +
                        "scholarship(status, name, family, nationalCode, lastUni, lastDegree, lastField" +
                        ", lastScore, applyUni, applyDegree, applyField, applyDate) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "RequestedByStudent");
                preparedStatement.setString(2, scholarship.getName());
                preparedStatement.setString(3, scholarship.getFamily());
                preparedStatement.setString(4, scholarship.getNationalCode());
                preparedStatement.setString(5, scholarship.getLastUni());
                preparedStatement.setString(6, scholarship.getLastDegree());
                preparedStatement.setString(7, scholarship.getLastField());
                preparedStatement.setFloat(8, scholarship.getLastScore());
                preparedStatement.setString(9, scholarship.getApplyUni());
                preparedStatement.setString(10, scholarship.getApplyDegree());
                preparedStatement.setString(11, scholarship.getApplyField());
                preparedStatement.setString(12, scholarship.getApplyDate());
                preparedStatement.execute();

//                List<Scholarship> allScholarships = new FindAllScholarshipsByApplicationUseCaseImpl().listAllScholarships();
//                Long scholarshipId = allScholarships.get(allScholarships.size()-1).getId();
//                new SaveLogByApplicationUseCaseImpl().save(scholarshipId, "RequestedByStudent");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
