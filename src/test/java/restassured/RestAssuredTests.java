package restassured;

import io.restassured.common.mapper.ObjectDeserializationContext;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.json.mapping.JsonPathObjectDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import restassured.support.Greeting;
import restassured.support.Winner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests {

    private static final String EXPECTED_INTEGER = "15303030";

    private static final String EXPECTED_LONG = "13000000000";

    private static final String ORDER_NUMBER_JSON = """
            {
                 "orderNumber": 15303030
            }
             """;

    private static final String LIGHT_YEARS_TO_COSMIC_HORIZON_JSON = """
            {
                "lightYearsToCosmicHorizon": 13000000000
            }
            """;

    private static final String PRICE_JSON = """
            {
                "price": 12.1
            }
            """;


    private static final String GREETING = """
            {
                 "greeting" : {
                                 "firstName" : "John",
                                 "lastName" : "Doe"
                            }
             }
             """;

    private static final String LOTTO = """
            {
                "lotto":{
                    "lottoId":5,
                    "winning-numbers":[2, 45, 34, 23, 7, 5, 3],
                    "winners":[
                        {
                              "winnerId":23,
                              "numbers":[2, 45, 34, 23, 3, 5]
                        },{
                              "winnerId":54,
                              "numbers":[52, 3, 12, 11, 18, 22]
                        }
                    ]
                }
            }
            """;

    @Test
    public void json_path_numbers1() {
        final JsonPath jsonPath = new JsonPath(PRICE_JSON).using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        BigDecimal price = jsonPath.get("price");
        assertThat(price, equalTo(BigDecimal.valueOf(12.1)));
    }

    @Test
    public void json_path_numbers2() {
        final JsonPath jsonPath = new JsonPath(PRICE_JSON).using(new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.FLOAT_AND_DOUBLE));
        float price = jsonPath.get("price");
        assertThat(price, equalTo(12.1f));
    }

    @Test
    public void json_path_numbers3() {
        final JsonPath jsonPath = new JsonPath(ORDER_NUMBER_JSON).using(new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_INTEGER));
        BigInteger orderNumber = jsonPath.get("orderNumber");
        assertThat(orderNumber, equalTo(new BigInteger(EXPECTED_INTEGER)));
    }

    @Test
    public void json_path_numbers4() {
        final JsonPath jsonPath = new JsonPath(LIGHT_YEARS_TO_COSMIC_HORIZON_JSON).using(new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_INTEGER));
        BigInteger orderNumber = jsonPath.get("lightYearsToCosmicHorizon");
        assertThat(orderNumber, equalTo(new BigInteger(EXPECTED_LONG)));
    }

    // ----------------------------------------------------------------

    @Test
    public void
    json_path_supports_custom_deserializer() {
        final AtomicBoolean customDeserializerUsed = new AtomicBoolean(false);

        final JsonPath jsonPath = new JsonPath(GREETING).using(new JsonPathConfig().defaultObjectDeserializer(new JsonPathObjectDeserializer() {
            public <T> T deserialize(ObjectDeserializationContext ctx) {
                customDeserializerUsed.set(true);
                final String json = ctx.getDataToDeserialize().asString();
                final Greeting greeting = new Greeting();
                greeting.setFirstName(StringUtils.substringBetween(json, "\"firstName\":\"", "\""));
                greeting.setLastName(StringUtils.substringBetween(json, "\"lastName\":\"", "\""));
                return (T) greeting;
            }
        }));

        final Greeting greeting = jsonPath.getObject("", Greeting.class);
        
        assertThat(greeting.getFirstName(), equalTo("John"));
        assertThat(greeting.getLastName(), equalTo("Doe"));
        assertThat(customDeserializerUsed.get(), is(true));
    }

    @Test
    public void
    json_path_supports_custom_deserializer_with_static_configuration() {
        final AtomicBoolean customDeserializerUsed = new AtomicBoolean(false);
        JsonPath.config = new JsonPathConfig().defaultObjectDeserializer(new JsonPathObjectDeserializer() {
            public <T> T deserialize(ObjectDeserializationContext ctx) {
                customDeserializerUsed.set(true);
                final String json = ctx.getDataToDeserialize().asString();
                final Greeting greeting = new Greeting();
                greeting.setFirstName(StringUtils.substringBetween(json, "\"firstName\":\"", "\""));
                greeting.setLastName(StringUtils.substringBetween(json, "\"lastName\":\"", "\""));
                return (T) greeting;
            }
        });

        final JsonPath jsonPath = new JsonPath(GREETING);

        try {
            final Greeting greeting = jsonPath.getObject("", Greeting.class);

            assertThat(greeting.getFirstName(), equalTo("John"));
            assertThat(greeting.getLastName(), equalTo("Doe"));
            assertThat(customDeserializerUsed.get(), is(true));
        } finally {
            JsonPath.reset();
        }
    }

    @Test
    public void
    extracting_first_lotto_winner_to_java_object() {
        final Winner winner = JsonPath.from(LOTTO).getObject("lotto.winners[0]", Winner.class);
        assertThat(winner.getNumbers(), hasItems(2, 45, 34, 23, 3, 5));
        assertThat(winner.getWinnerId(), equalTo(23));
    }

    @Test
    public void
    getting_numbers_greater_than_ten_for_lotto_winner_with_id_equal_to_23() {
        List<Integer> numbers = JsonPath.from(LOTTO).getList("lotto.winners.find { it.winnerId == 23 }.numbers.findAll { it > 10 }", Integer.class);
        assertThat(numbers, hasItems(45, 34, 23));
        assertThat(numbers, hasSize(3));
    }
}
