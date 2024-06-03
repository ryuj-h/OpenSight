package org.example.b104.domain.account.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.b104.domain.account.controller.record.*;
import org.example.b104.domain.account.controller.request.AccountRequestHeader;
import org.example.b104.domain.account.controller.request.InquireBankAccountTypesRequest;
import org.example.b104.domain.account.controller.response.*;
import org.example.b104.domain.account.service.command.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AccountService {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    private final String apiKey = "010218e760dd476db9bac3b474847947";


    AccountService() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public HttpResponse<String> SendHttpRequest(String url, String method, String body) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public MakeManagerKeyResponse makeManagerKey(String managerId) {
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST", "{\"managerId\": \"" + managerId + "\"}");

        if (httpResponse == null) return null;
        if (httpResponse.statusCode() != 200) return null;

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String resultManagerId = jsonNode.get("managerId").asText();
            String resultApiKey = jsonNode.get("apiKey").asText();
            return MakeManagerKeyResponse.builder()
                    .result("success")
                    .managerId(resultManagerId)
                    .apiKey(resultApiKey)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public MakeManagerKeyResponse remakeManagerKey(String managerId) {
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST", "{\"managerId\": \"" + managerId + "\"}");

        if (httpResponse == null) return null;
        if (httpResponse.statusCode() != 200) return null;

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String resultManagerId = jsonNode.get("managerId").asText();
            String resultApiKey = jsonNode.get("apiKey").asText();
            return MakeManagerKeyResponse.builder()
                    .result("success")
                    .managerId(resultManagerId)
                    .apiKey(resultApiKey)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public RegisterAccountMemberResponse registerAccountMember(RegisterAccountMemberCommand command) {
        String userId = command.getUserId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("apiKey", apiKey);
        jsonObject.put("userId", userId);

        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                jsonObject.toString());

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return RegisterAccountMemberResponse.builder()
                    .result("error")
                    .build();

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String result = jsonNode.get("code").asText();
            String resultUserId = jsonNode.get("payload").get("userId").asText();
            String userName = jsonNode.get("payload").get("userName").asText();
            String institutionCode = jsonNode.get("payload").get("institutionCode").asText();
            String userKey = jsonNode.get("payload").get("userKey").asText();
            String created = jsonNode.get("payload").get("created").asText();
            String modified = jsonNode.get("payload").get("modified").asText();
            String now = jsonNode.get("now").asText();

            return RegisterAccountMemberResponse.builder()
                    .result(result)
                    .userId(resultUserId)
                    .userName(userName)
                    .institutionCode(institutionCode)
                    .userKey(userKey)
                    .created(created)
                    .modified(modified)
                    .now(now)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SearchAccountMemberResponse searchAccountMember(SearchAccountMemberCommand command) {
        String userId = command.getUserId();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("apiKey", apiKey);
        jsonObject.put("userId", userId);

        System.out.println(jsonObject.toString());
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                jsonObject.toString());

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return SearchAccountMemberResponse.builder()
                    .result("error")
                    .build();

        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            String result = jsonNode.get("code").asText();
            String resultUserId = jsonNode.get("payload").get("userId").asText();
            String userName = jsonNode.get("payload").get("userName").asText();
            String institutionCode = jsonNode.get("payload").get("institutionCode").asText();
            String userKey = jsonNode.get("payload").get("userKey").asText();
            String created = jsonNode.get("payload").get("created").asText();
            String modified = jsonNode.get("payload").get("modified").asText();
            String now = jsonNode.get("now").asText();

            return SearchAccountMemberResponse.builder()
                    .result(result)
                    .userId(resultUserId)
                    .userName(userName)
                    .institutionCode(institutionCode)
                    .userKey(userKey)
                    .created(created)
                    .modified(modified)
                    .now(now)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquireBankAccountTypesResponse inquireBankAccountTypes() {
        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireBankAccountTypes")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireBankAccountTypes")
                .apiKey(apiKey)
                .build();

        accountRequestHeader.init();

        InquireBankAccountTypesRequest request = InquireBankAccountTypesRequest.builder()
                .Header(accountRequestHeader)
                .build();

        //아무리봐도 이렇게 하면 안되는 거 같은데 일단 작동함
        //추후 수정 필요
        //*********************  이 부 분 **************************
        JsonNode jsonNode = objectMapper.valueToTree(request);
        String jsonNodeString = jsonNode.toString();
        char [] jsonNodeStringArray = jsonNodeString.toCharArray();
        jsonNodeStringArray[2] = 'H'; // 지맘대로 H를 h로 바꿔서 일단 하드코딩
        String jsonNodeString2 = new String(jsonNodeStringArray);
        //*********************************************************

        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                jsonNodeString2);


        JSONObject jsonObject = new JSONObject(httpResponse.body());
        JSONObject resultHeader = jsonObject.getJSONObject("Header");
        JSONArray RECArray = jsonObject.getJSONArray("REC");
        try{
            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            REC recList[] = objectMapper.readValue(RECArray.toString(), REC[].class);
            return InquireBankAccountTypesResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(recList)
                    .build();
        } catch(Exception e){
            e.printStackTrace();
        }
        return InquireBankAccountTypesResponse.builder()
                .result("error")
                .build();
    }

    public OpenAccountResponse openAccount(OpenAccountCommand command) {

        String accountTypeUniqueNo = command.getAccountTypeUniqueNo();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("openAccount")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("openAccount")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"accountTypeUniqueNo\": \"" + accountTypeUniqueNo + "\"}");

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return OpenAccountResponse.builder()
                    .result("error")
                    .build();

        try {

            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");
            String resultBankCode = REC.getString("bankCode");
            String resultAccountNo = REC.getString("accountNo");

            AccountResponseHeader accountResp = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);

            return OpenAccountResponse.builder()
                    .result("success")
                    .Header(accountResp)
                    .bankCode(resultBankCode)
                    .accountNo(resultAccountNo)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquireAccountListResponse inquireAccountList(InquireAccountListCommand command) {
        String userKey = command.getUserKey();
        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireAccountList")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireAccountList")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + "}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());


        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireAccountListResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONArray RECArray = jsonObject.getJSONArray("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            InquireAccountListRecord recList[] = objectMapper.readValue(RECArray.toString(), InquireAccountListRecord[].class);

            return InquireAccountListResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(recList)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquireAccountInfoResponse inquireAccountInfo(InquireAccountInfoCommand command) {
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireAccountInfo")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireAccountInfo")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\"}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());


        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireAccountInfoResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            InquireAccountInfoRecord rec = objectMapper.readValue(REC.toString(), InquireAccountInfoRecord.class);

            return InquireAccountInfoResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquireDepositorAccountNumberResponse inquireDepositorAccountNumber(InquireDepositorAccountNumberCommand command){
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireDepositorAccountNumber")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireDepositorAccountNumber")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();
        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\"}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireDepositorAccountNumberResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            InquireDepositorAccountNumberRecord rec = objectMapper.readValue(REC.toString(), InquireDepositorAccountNumberRecord.class);

            return InquireDepositorAccountNumberResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public InquireAccountBalanceResponse inquireAccountBalance(InquireAccountBalanceCommand command) {
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireAccountBalance")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireAccountBalance")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\"}");

        if (httpResponse == null) return null;
        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireAccountBalanceResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            InquireAccountBalanceRecord rec = objectMapper.readValue(REC.toString(), InquireAccountBalanceRecord.class);

            return InquireAccountBalanceResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DrawingTransferResponse drawingTransfer(DrawingTransferCommand command) {
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        long transactionBalance = command.getTransactionBalance();
        String transactionSummary = command.getTransactionSummary();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("drawingTransfer")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("drawingTransfer")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                   "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\", \"transactionBalance\": " + transactionBalance + ", \"transactionSummary\": \"" + transactionSummary + "\"}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201)) {

            return DrawingTransferResponse.builder()
                    .result("error")
                    .build();
        }

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            DrawingTransferRecord rec = objectMapper.readValue(REC.toString(), DrawingTransferRecord.class);

            return DrawingTransferResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReceivedTransferAccountNumberResponse receivedTransferAccountNumber(ReceivedTransferAccountNumberCommand command) {
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        long transactionBalance = command.getTransactionBalance();
        String transactionSummary = command.getTransactionSummary();
        String userKey = command.getUserKey();


        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("receivedTransferAccountNumber")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("receivedTransferAccountNumber")
                .apiKey(apiKey)
                .userKey(userKey)//입금할때는 유저키가 필요가 없나?
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\", \"transactionBalance\": " + transactionBalance + ", \"transactionSummary\": \"" + transactionSummary + "\"}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201)) {

            return ReceivedTransferAccountNumberResponse.builder()
                    .result("error")
                    .build();
        }

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            ReceivedTransferAccountNumberRecord rec = objectMapper.readValue(REC.toString(), ReceivedTransferAccountNumberRecord.class);

            return ReceivedTransferAccountNumberResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AccountTransferResponse accountTransfer(AccountTransferCommand command) {
        String depositBankCode = command.getDepositBankCode();
        String depositAccountNo = command.getDepositAccountNo();
        long transactionBalance = command.getTransactionBalance();
        String withdrawalBankCode = command.getWithdrawalBankCode();
        String withdrawalAccountNo = command.getWithdrawalAccountNo();
        String depositTransactionSummary = command.getDepositTransactionSummary();
        String withdrawalTransactionSummary = command.getWithdrawalTransactionSummary();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("accountTransfer")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("accountTransfer")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();

        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", " +
                        "\"depositBankCode\": \"" + depositBankCode + "\", " +
                        "\"depositAccountNo\": \"" + depositAccountNo + "\", " +
                        "\"transactionBalance\": " + transactionBalance + ", " +
                        "\"withdrawalBankCode\": \"" + withdrawalBankCode + "\", " +
                        "\"withdrawalAccountNo\": \"" + withdrawalAccountNo + "\", " +
                        "\"depositTransactionSummary\": \"" + depositTransactionSummary + "\", " +
                        "\"withdrawalTransactionSummary\": \"" + withdrawalTransactionSummary + "\"}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return AccountTransferResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject2 = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject2.getJSONObject("Header");
            JSONArray RECArray = jsonObject2.getJSONArray("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            AccountTransferRecord[] rec = objectMapper.readValue(RECArray.toString(), AccountTransferRecord[].class);

            return AccountTransferResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

        public InquireAccountTransactionHistoryResponse inquireAccountTransactionHistory(InquireAccountHistoryTransactionCommand command) {
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        String startDate = command.getStartDate();
        String endDate = command.getEndDate();
        String transactionType = command.getTransactionType();
        String orderByType = command.getOrderByType();
        String userKey = command.getUserKey();


        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireAccountTransactionHistory")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireAccountTransactionHistory")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\", \"startDate\": \"" + startDate + "\", \"endDate\": \"" + endDate + "\", \"transactionType\": \"" + transactionType + "\", \"orderByType\": \"" + orderByType + "\"}");

        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireAccountTransactionHistoryResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);

            InquireAccountHistoryRecord rec = objectMapper.readValue(REC.toString(), InquireAccountHistoryRecord.class);


            return InquireAccountTransactionHistoryResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquireTransactionHistoryDetailResponse inquireTransactionHistoryDetail(InquireTransactionHistoryDetailCommand command) {
        String bankCode = command.getBankCode();
        String accountNo = command.getAccountNo();
        int transactionUniqueNo = command.getTransactionUniqueNo();
        String userKey = command.getUserKey();

        AccountRequestHeader accountRequestHeader = AccountRequestHeader.builder()
                .apiName("inquireTransactionHistoryDetail")
                .institutionCode("00100")
                .fintechAppNo("001")
                .apiServiceCode("inquireTransactionHistoryDetail")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        accountRequestHeader.init();
        HttpResponse<String> httpResponse = SendHttpRequest("https://openbankapi", "POST",
                "{\"Header\": " + objectMapper.valueToTree(accountRequestHeader).toString() + ", \"bankCode\": \"" + bankCode + "\", \"accountNo\": \"" + accountNo + "\", \"transactionUniqueNo\": \"" + transactionUniqueNo + "\"}");


        System.out.println("[+] Status Code : " + httpResponse.statusCode());
        System.out.println("[+] Body : " +httpResponse.body());

        if (httpResponse == null) return null;
        if (!(httpResponse.statusCode() == 200 || httpResponse.statusCode() == 201))
            return InquireTransactionHistoryDetailResponse.builder()
                    .result("error")
                    .build();

        try {
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            JSONObject resultHeader = jsonObject.getJSONObject("Header");
            JSONObject REC = jsonObject.getJSONObject("REC");

            AccountResponseHeader accountResponseHeader = objectMapper.readValue(resultHeader.toString(), AccountResponseHeader.class);
            InquireTransactionHistoryDetailRecord rec = objectMapper.readValue(REC.toString(), InquireTransactionHistoryDetailRecord.class);

            return InquireTransactionHistoryDetailResponse.builder()
                    .result("success")
                    .Header(accountResponseHeader)
                    .REC(rec)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
