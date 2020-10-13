package org.acme;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;

@Path("/hello")
public class JWTIssuerResource {

    final String KEY = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCr1KPUhWTDKcO4\n" +
            "vE4CLA/HTH1ozzKxShCFwj19pmJK5tgGXgVLZ6p/eVjLpvnsemJ0ZNuw+x8rdtnT\n" +
            "hGIFC44pClNCHvGFA43b0FUHF2Bxbdpt+MHSUOh81b8CSsoeXUMWUZV8//fTmmLN\n" +
            "FraQNQWdYGckVQ1jXT+RgKSKBTbjb5XIAWwSbjeE4Db7L3jIaibfaTa5ZyxiYftf\n" +
            "wv6xonMVEs2CcHpatcfq1kaoAYgM4zSce+gtBLDi4fm3H3x5kV8yfAAw4PmFB8+S\n" +
            "UJ9WgKTWDR3lEq0EuowSS7XPYOaOWqnYOoGsAKhSD+saEi6LygMrUY+GhJ9KNK8b\n" +
            "LPRNtjB1AgMBAAECggEAdLv6LQgOg1PdRqSO7oTWl1QMouaYjr89aFGkirc9cZtQ\n" +
            "SUsYl7iuqPlcbMZcZd5jAqX+ZkbETzfoFf2Kxdiq1JDSLUpuVCos9pebHD0yZPz3\n" +
            "dtyXeHVE7IIdyMXceDJtwDzerNQMnnH1C1WR43hR/y+eAp3RgZ647/WQUr/mIS3x\n" +
            "WgbJIbSwIn9k3ocYihpKgGUyyqUFiS1bZCeT9UgPPAp91NPGY6MJQCCcfUcDEaO2\n" +
            "w25kyEVk0qmGhlewupHLvb/XBI1qtn0fEBuDltg2/q6KhOOCoQQOawhb9VlB9nru\n" +
            "fQSS6WC+mkO/h2vcQGvxKdICAu6O44BrmnJAvG5mhQKBgQDYj55dRrNk0kcJZt2d\n" +
            "fY/fmZKh7zD18ByTL37grc8KzsbTYEoee9Dxrze5qyCTJcbTEGi1TtH0/2rCkaZe\n" +
            "Dg+baqKt0C+kNRakpqXE44jaAgEZPu2iQch2+YWDB6G1iPvwDag2CZQXtpvxcmcK\n" +
            "IxZ9UDHyLQXXXAuHLb4f6K4LdwKBgQDLH6CaZ6BLUREK9E1O393MsZ7hp5Th+XBc\n" +
            "WjkK23e0uU59kSz7P4AN6dKs2qK0+OtbCZrC6jD6zY0RyOHbgfILuaklLoXbceyv\n" +
            "c/g/dFTvUT8fHmh6mTt88yeAvnWpKNpV/1ZdhEkQw+sZ/bZs32B1FcVWYTPWsJb4\n" +
            "tG+Ewt/GcwKBgCBIuMkbAx9ioahrJ1qVH1+sG0n8yIXBd+ERUY7ZGmZ4EPfuAN6u\n" +
            "gqWlA1+wfTiyhwNsDNAKog4uDZJX+D9JRVZb/UiAIroOGAQPu+KwMPSfG97RnMd1\n" +
            "mxbxd8/i0V6ovzY4Mv1rTCKE3JaQAYBnXrDrxaZew+IpHKbYUOGc5GVdAoGAEuJs\n" +
            "3w4VVAtEZ11RNq0PhPFakSYwbFXxgVSOCxleZrn3tyrhILbFpAWCJbnp+KNP1lXt\n" +
            "QoPda+PiYq3nea36H+bx99f8b+uGksVYZiXdo6ZNFQqRshw+8J32pTyP2dtHeUlU\n" +
            "8TrURC/C0tbIuBSIdjpA74f39tTSJGs6FwC7J0sCgYBbIMZd7HFqHqNDgEIPVdey\n" +
            "O0WTo3ee4fh6C2x2QVMr/rXMEA7C8JN9zoBfkW+TRNgZqoTyNHKePHvZ5jsdQuui\n" +
            "/I4EJ87eKrClD5ttE+tFwk77QpKcxo8ZKP/ONxyjIxE/yFg40k9fTs8VgAOb5uPF\n" +
            "GXis/WAqLJYS/3XuNAN49Q==\n" +
            "-----END PRIVATE KEY-----\n";

    @Context
    SecurityContext securityContext;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    @ConfigProperty(name="mp.jwt.verify.issuer",defaultValue ="my-issuer-name" )
    String jwtIssuer;

    @Inject
    @ConfigProperty(name="mp.jwt.verify.issuer",defaultValue ="my-issuer-name" )
    Provider<String> liveJwtIssuer;

    final Logger logger = Logger.getLogger(JWTIssuerResource.class.getName());
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{firstName}/{lastName}/generate-token-for")
    public Response getJWT(@PathParam("firstName")String firstName, @PathParam("lastName")String lastName){
        String JWT = null;
		try {
            Map<String,Object> rolesMap = new HashMap<>();
            rolesMap.put("role 1","VIP");
            rolesMap.put("role 2","VVIP");
            rolesMap.put("role 3","visitor");
            List<String> groups = new ArrayList<>();
            groups.add("Group 1");

            JWT = generateAndSignJWT(firstName, lastName,groups,rolesMap);
           	} catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Response.ok(JWT).build();
    }


    String generateAndSignJWT(String firstName, String lastName,List groups, Map roles) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException{
        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("firstName", firstName);
        claimsMap.put("lastName", lastName);
        logger.info("Current issuer: "+liveJwtIssuer.get());
        JwtClaimsBuilder claims = Jwt.claims(claimsMap)
                                    .subject(firstName+" "+lastName)
                                    .claim("roleMappings", roles)
                                    .claim("groups", groups)
                                    .issuer(jwtIssuer)
                                    .issuedAt(Instant.now().toEpochMilli())
                                    .expiresAt(Instant.now().plus(2, ChronoUnit.DAYS).toEpochMilli());
        PrivateKey privateKey = loadPrivateKey();
        return claims.jws().sign(privateKey);
    }

    PrivateKey loadPrivateKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException{
        byte[] keyFileBytes = KEY.getBytes("utf-8");
        //strip marker text from the raw text
        String cleanKey = new String(keyFileBytes, 0, keyFileBytes.length).replaceAll("-----BEGIN (.*)-----", "")
                                                      .replaceAll("-----END (.*)----", "")
                                                      .replaceAll("\r\n", "")
                                                      .replaceAll("\n", "")
                                                      .trim();
        logger.info("Clean key: "+cleanKey);
        //convert text to PKCS8/RSA key
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(cleanKey)));
    }

}