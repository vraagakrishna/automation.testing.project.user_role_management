package utils;

import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.logging.Logger;

public class JwtUtils {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(JwtUtils.class.getName());

    private static final ObjectMapper mapper = new ObjectMapper();

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss z");
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public static JSONObject decodeJwt(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3)
            throw new IllegalArgumentException("Invalid JWT token format");

        String payloadJson = new String(Base64.getUrlDecoder()
                                              .decode(parts[1]));

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(payloadJson);
            logger.info("JWT token: " + jsonObject);

            LoggerManager.logToReport("Decoded JWT payload: " + prettyPrintJson(payloadJson));

            convertTimeToReadableFormat((Long) jsonObject.get("iat"), "Issued At");
            convertTimeToReadableFormat((Long) jsonObject.get("exp"), "Expiration");

            return jsonObject;
        } catch (ParseException ex) {
            throw new RuntimeException("Failed to parse JWT payload: " + ex.getMessage());
        }
    }

    public static String manipulateJwtRole(String jwtToken, String newUserRole) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String payloadJson = new String(Base64.getUrlDecoder()
                                              .decode(parts[1]));
        JSONObject jsonObject;

        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(payloadJson);
            logger.info("jwtToken: " + payloadJson);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT payload: " + e.getMessage());
        }

        // manipulate user role
        jsonObject.put("role", newUserRole.toLowerCase());

        // re-encode modified payload
        String modifiedPayload = Base64.getUrlEncoder()
                                       .withoutPadding()
                                       .encodeToString(jsonObject.toString()
                                                                 .getBytes());

        // keep the same header but break the payload intentionally
        return parts[0] + "." + modifiedPayload + "." + parts[2];
    }

    public static String expireToken(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String payloadJson = new String(Base64.getUrlDecoder()
                                              .decode(parts[1]));
        JSONObject jsonObject;

        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(payloadJson);
            logger.info("jwtToken: " + payloadJson);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT payload: " + e.getMessage());
        }

        // set exp to 1 hour ago
        long expiredTime = (System.currentTimeMillis() / 1000) - 3600;
        jsonObject.put("exp", expiredTime);

        // re-encode modified payload
        String modifiedPayload = Base64.getUrlEncoder()
                                       .withoutPadding()
                                       .encodeToString(jsonObject.toString()
                                                                 .getBytes());

        // keep the same header but break the payload intentionally
        return parts[0] + "." + modifiedPayload + "." + parts[2];
    }

    public static String removeSignature(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        // remove signature
        return parts[0] + "." + parts[1] + ".";
    }

    public static String manipulateTokenAlgorithm(String jwtToken, String mewAlgo) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String headerJson = new String(Base64.getUrlDecoder()
                                             .decode(parts[0]));
        JSONObject jsonObject;

        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(headerJson);
            logger.info("jwtToken: " + headerJson);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT header: " + e.getMessage());
        }

        // manipulate algorithm
        jsonObject.put("alg", mewAlgo.toLowerCase());

        // re-encode modified payload
        String modifiedHeader = Base64.getUrlEncoder()
                                      .withoutPadding()
                                      .encodeToString(jsonObject.toString()
                                                                .getBytes());

        // keep the same header but break the algorithm intentionally
        return modifiedHeader + "." + parts[1] + "." + parts[2];
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private static String prettyPrintJson(String json) {
        try {
            Object jsonObj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter()
                         .writeValueAsString(jsonObj);
        } catch (Exception e) {
            // if it's not valid JSON (e.g., HTML or plain text), just return it as-is
            return json;
        }
    }

    private static void convertTimeToReadableFormat(long time, String name) {
        // convert seconds → milliseconds
        Instant instant = Instant.ofEpochSecond(time);

        // format into readable UTC or local date
        ZonedDateTime utcTime = instant.atZone(ZoneOffset.UTC);
        ZonedDateTime localTime = instant.atZone(ZoneId.systemDefault());

        String readableUtcDate = utcTime.format(formatter);
        String readableLocalDate = localTime.format(formatter);

        logger.info(name + " UTC Time: " + readableUtcDate);
        logger.info(name + " Local Time: " + readableLocalDate);

        LoggerManager.logToReport(name + " UTC Time: " + readableUtcDate);
        LoggerManager.logToReport(name + " Local Time: " + readableLocalDate);
    }
    // </editor-fold>

}
