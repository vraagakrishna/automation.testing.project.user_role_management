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

            LoggerManager.logToReport("Decoded JWT payload: " + prettyPrintJson(jwtToken));

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
            System.out.println("jwtToken: " + payloadJson);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT payload: " + e.getMessage());
        }

        // manipulate usr role
        jsonObject.put("role", newUserRole.toLowerCase());

        // re-encode modified payload
        String modifiedPayload = Base64.getUrlEncoder()
                                       .withoutPadding()
                                       .encodeToString(jsonObject.toString()
                                                                 .getBytes());

        // keep the same header but break the signature intentionally
        String fakeToken = parts[0] + "." + modifiedPayload + "." + parts[2];

        return fakeToken;
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

        System.out.println(name + " UTC Time: " + readableUtcDate);
        System.out.println(name + " Local Time: " + readableLocalDate);

        LoggerManager.logToReport(name + " UTC Time: " + readableUtcDate);
        LoggerManager.logToReport(name + " Local Time: " + readableLocalDate);
    }
    // </editor-fold>

}
