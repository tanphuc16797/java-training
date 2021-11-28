package com.amit.springsercurity.service;

import com.amit.springsercurity.database.entity.User;
import com.amit.springsercurity.domain.PasswordStore;
import com.amit.springsercurity.domain.TokenStore;
import com.amit.springsercurity.domain.UserDomain;
import com.amit.springsercurity.model.ApiException;
import com.amit.springsercurity.model.ERROR;
import com.amit.springsercurity.model.MainResponse;
import com.amit.springsercurity.model.request.AddMutilUserRequest;
import com.amit.springsercurity.model.request.AddUserRequest;
import com.amit.springsercurity.model.request.ImportUserByExcelRequest;
import com.amit.springsercurity.model.serializer.InsertUserSerializer;
import com.amit.springsercurity.util.ExcelTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class UserService extends BaseService{
    private static Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    @Qualifier("fixPool")
    ExecutorService fixPool;

    @Autowired
    PasswordStore passwordStore;

    @Autowired
    private UserDomain userDomain;

    public MainResponse<String> createdUser(AddUserRequest request) throws ApiException {

        this.validateRequestCreatedUser(request);

        User user = new User(request);
        String passwordEncode = passwordEncoder.encode(request.getPassword());
        user.setPassword(passwordEncode);

        userDomain.saveUser(user);
        return new MainResponse<>();
    }

    protected void validateRequestCreatedUser(AddUserRequest request) throws ApiException {
        if (StringUtils.isBlank(request.getUserName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "username không được để trống");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApiException(ERROR.INVALID_PARAM, "mật khẩu không được để trống");
        }

        if (StringUtils.isBlank(request.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Họ tên không được để trống");
        }

        if (request.getPassword().length() < 6) {
            throw new ApiException(ERROR.INVALID_PARAM, "mật khẩu không được dưới 6 kí tự");
        }

        User userByUserName = userDomain.getUserByUserName(request.getUserName());

        if (userByUserName != null) {
            throw new ApiException(ERROR.INVALID_REQUEST, "Tài khoản này đã có người sử dụng, xin vui lòng sử dụng tên đăng nhập khác !");
        }
    }


    public MainResponse<Long> createdMutilUser(AddMutilUserRequest request) throws ApiException {
        long t1 = System.currentTimeMillis();
        for (AddUserRequest data : request.getData()){
            this.createdUser(data);
        }
        long t2 = System.currentTimeMillis();

        long totalTime = t2- t1;

        MainResponse<Long> response = new MainResponse<>();
        response.setData(totalTime);
        return response;
    }

//    public MainResponse<Long> createdMutilUserAsync(AddMutilUserRequest request) throws ApiException {
//
//        long t1 = System.currentTimeMillis();
//        List<Future<MainResponse>> result = new ArrayList<>();
//        for (AddUserRequest data : request.getData()){
//            Future<MainResponse> rs = fixPool.submit(() -> createdUser(data));
//
//            result.add(rs);
//        }
//
//        for (Future<MainResponse> task : result ){
//            try {
//                MainResponse rrr = task.get();
//            }catch (Exception e){
//
//            }
//
//        }
//
//        long t2 = System.currentTimeMillis();
//
//        long totalTime = t2- t1;
//
//        MainResponse<Long> response = new MainResponse<>();
//        response.setData(totalTime);
//        return response;
//    }

    public MainResponse<Long> createdMutilUserAsync(AddMutilUserRequest request) throws ApiException {

        long t1 = System.currentTimeMillis();

        for (AddUserRequest data : request.getData()){
            fixPool.execute(()->createdUser(data));

            Executors.newFixedThreadPool(15).execute(()->createdUser(data));

        }

        long t2 = System.currentTimeMillis();

        long totalTime = t2- t1;

        MainResponse<Long> response = new MainResponse<>();
        response.setData(totalTime);
        return response;
    }

    public MainResponse<Long> importedUsersByExcel(MultipartFile file) throws ApiException {
        long t1 = System.currentTimeMillis();
        long totalTime = 0;

        InsertUserSerializer insertUserSerializer = new InsertUserSerializer(
                ExcelTemplate.ImportUserTemplate.tableName,
                ExcelTemplate.ImportUserTemplate.parameters);

        List<String> errors = new ArrayList<>();
        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String insertBody = "";
        Iterator<Row> rowIterator = null;
        OPCPackage opcPackage = null;
        InputStream uploadStream;
        XSSFWorkbook workbook;
        XSSFSheet sheet;

        try {
            uploadStream = file.getInputStream();
            opcPackage = OPCPackage.open(uploadStream);
            workbook = new XSSFWorkbook(opcPackage);
            sheet = workbook.getSheetAt(0);
            rowIterator = sheet.iterator();
        } catch (Exception e) {
            throw new ApiException(ERROR.SYSTEM_ERROR, e.getMessage());
        } finally {
            try {opcPackage.close();} catch (IOException e) {}
        }

        List<Future<String>> tasks = new ArrayList<>();
        String uniqueCode = simpleDateFormat.format(today);
        int itemCount = 0;
        for (Iterator<Row> it = rowIterator; it.hasNext(); ) {
            itemCount += 1;
            String finalUniqueCode = uniqueCode + "_" + itemCount;
            Future<String> task = fixPool.submit(
                    () -> insertUserSerializer.makeInsertBody(it.next(), finalUniqueCode, localDate.getYear(), passwordEncoder, passwordStore)
                );
            tasks.add(task);
        }

        int count = 0;
        for (Future<String> task : tasks ){
            try {
                insertBody += task.get() + ',';
                count += 1;
            }catch (Exception e){
            }
        }

        if (!insertBody.isEmpty()){
            insertBody = insertBody.substring(0, insertBody.length() - 1);
        }

        userDomain.insertUsers(insertBody);
        totalTime = System.currentTimeMillis() - t1;
        MainResponse<Long> response = new MainResponse<>();
        response.setData(totalTime);
        response.setMessage(errors.toString());
        return response;
    }
}
