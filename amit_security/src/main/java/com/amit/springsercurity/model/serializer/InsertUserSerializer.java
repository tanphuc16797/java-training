package com.amit.springsercurity.model.serializer;
import com.amit.springsercurity.database.entity.User;
import com.amit.springsercurity.domain.PasswordStore;
import com.amit.springsercurity.redis.entity.PasswordDTO;
import com.amit.springsercurity.util.ICheckBCryptPasswordEncoder;
import com.amit.springsercurity.util.ExcelTemplate;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
public class InsertUserSerializer {
    private String insertHeader;
    private String insertBody;
    private String insertFooter;
    private String tableName;
    private String parameters;
    private Enum fixedCell;

    public InsertUserSerializer(String tableName, List<String> parameters){
        this.tableName = tableName;
        this.insertHeader = this.buildInsertHeader(parameters);
        this.insertFooter = ";";
    }

    private String buildInsertHeader(List<String> parameters){
        this.parameters = String.join(", ", parameters);
        return String.format("INSERT INTO %s(%s) VALUES ", this.tableName, this.parameters);
    }

    public String makeInsertBody(User user){
        return String.format("('%s','%s','%s','%s')", user.getUserName(), user.getPassword(), user.getName(), user.getAge());
    }

    public String makeInsertBody(Row row, String uniqueCode, int nowYear, ICheckBCryptPasswordEncoder passwordEncoder, PasswordStore passwordStore){
        return this.makeInsertBody(rowDataToUser(row, uniqueCode, nowYear, passwordEncoder, passwordStore));
    }

    public String buildInsertSql(){
        return this.insertHeader + this.insertBody + this.insertFooter;
    }

    public User rowDataToUser(Row row, String uniqueCode, int nowYear, ICheckBCryptPasswordEncoder passwordEncoder, PasswordStore passwordStore){
        User user = new User();

        String fullName = row.getCell(ExcelTemplate.ImportUserTemplate.CellIndex.FULLNAME).toString();
        String birthday = row.getCell(ExcelTemplate.ImportUserTemplate.CellIndex.BIRTHDAY).toString();
        String password = row.getCell(ExcelTemplate.ImportUserTemplate.CellIndex.PASSWORD).toString();

        int year = Integer.parseInt(birthday.split(
                ExcelTemplate.ImportUserTemplate.DateFormat.separate
        )[ExcelTemplate.ImportUserTemplate.DateFormat.YEAR]);

        int age = nowYear - year;
        String username = fullName.toLowerCase().replaceAll("\\s", "") + uniqueCode;
        String encodedPassword = "";

        Optional<PasswordDTO> passwordDTO = passwordStore.getEncodedPassword(password);
        if (passwordDTO.isPresent()) {
            encodedPassword = passwordDTO.get().getEncodedPassword();
        } else {
            encodedPassword = passwordEncoder.encode(password);
            passwordStore.insertPassword(password, encodedPassword);
        }
        user.setUserName(username);
        user.setName(fullName);
        user.setPassword(encodedPassword);
        user.setAge(age);
        return user;
    }

}
