package restassured;

import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.json.exception.JsonPathException;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import restassured.support.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonPathTest {

    private final String JSON = """
            { "store": {
                "book": [
                  {
                      "category": "reference",
                      "author": "Nigel Rees",
                      "title": "Sayings of the Century",
                      "price": 8.95
                  },
                  {
                      "category": "fiction",
                      "author": "Evelyn Waugh",
                      "title": "Sword of Honour",
                      "price": 12
                   },
                        
                   {
                      "category": "fiction",
                      "author": "Herman Melville",
                      "title": "Moby Dick",
                      "isbn": "0-553-21311-3",
                      "price": 8.99
                    },
                        
                  {
                      "category": "fiction",
                      "author": "J. R. R. Tolkien",
                      "title": "The Lord of the Rings",
                      "isbn": "0-395-19395-8",
                      "price": 22.99
                    }
                ],
                "bicycle":
                    {
                        "color": "red",
                        "price": 19.95,
                        "atoms": %d                    }
                  }
            }""".formatted(Long.MAX_VALUE);

    private final String JSON2 = """
            [
                {
                    "email" : "name1@mail.com",
                    "alias" : "name one",
                    "phone" : "3456789"
                },
                {
                    "email" : "name2@mail.com",
                    "alias" : "name two",
                    "phone" : "1234567"
                },
                {
                    "email" : "name3@mail.com",
                    "alias" : "name three",
                    "phone" : "2345678"
                }
            ]
            """;


    private final String JSON_MAP = """
            {
                "price1" : 12.3,
                "price2": 15.0
            }
            """;

    private final String JSON_PATH_STARTING_WITH_NUMBER = """
            {
                "0" : 12.3,
                "1": 15.0
            }
            """;

    private final String JSON_PATH_WITH_BOOLEAN = """
            { "map" :
                {
                    "true" : 12.3,
                    "false": 15.0
                }
            }
            """;

    @Test
    public void getList() {
        final List<String> categories = new JsonPath(JSON).get("store.book.category");
        assertThat(categories.size(), equalTo(4));
        assertThat(categories, hasItems("reference", "fiction"));
    }

    @Test
    public void firstBookCategory() {
        final String category = JsonPath.with(JSON).get("store.book[0].category");
        assertThat(category, equalTo("reference"));
    }

    @Test
    public void lastBookTitle() {
        final String title = JsonPath.with(JSON).get("store.book[-1].title");
        assertThat(title, equalTo("The Lord of the Rings"));
    }

    @Test
    public void booksWithArgAuthor() {
        String author = "Herman Melville";
        final List<Map<String, ?>> books = JsonPath.with(JSON).param("author", author).get("store.book.findAll { book -> book.author == author }");
        assertThat(books.size(), equalTo(1));

        final String authorActual = (String) books.get(0).get("author");
        assertThat(authorActual, equalTo(author));
    }

    @Test
    public void booksBetween5And15() {
        final List<Map<String, ?>> books = JsonPath.with(JSON).get("store.book.findAll { book -> book.price >= 5 && book.price <= 15 }");
        assertThat(books.size(), equalTo(3));

        final String author = (String) books.get(0).get("author");
        assertThat(author, equalTo("Nigel Rees"));

        final int price = (Integer) books.get(1).get("price");
        assertThat(price, equalTo(12));
    }

    @Test
    public void sizeInPath() {
        final Integer size = JsonPath.with(JSON).get("store.book.size()");
        assertThat(size, equalTo(4));
    }

    @Test
    public void getRootObjectAsMap() {
        final Map<String, Map<String, Object>> store = JsonPath.given(JSON).get("store");
        assertThat(store.size(), equalTo(2));

        final Map<String, Object> bicycle = store.get("bicycle");
        final String color = (String) bicycle.get("color");
        final float price = (Float) bicycle.get("price");
        assertThat(color, equalTo("red"));
        assertThat(price, equalTo(19.95f));
    }

    @Test
    public void getFloatAndDoublesAsBigDecimal() {
        final JsonPath using = JsonPath.with(JSON).using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        assertThat(using.<Map<String, Map<String, Object>>>get("store").size(), equalTo(2));

        final Map<String, Object> bicycle = using.<Map<String, Map<String, Object>>>get("store").get("bicycle");
        final String color = (String) bicycle.get("color");
        final BigDecimal price = (BigDecimal) bicycle.get("price");
        assertThat(color, equalTo("red"));
        assertThat(price, equalTo(new BigDecimal("19.95")));
    }

    @Test
    public void getFloatAndDoublesAsBigDecimalUsingStaticConfiguration() {
        JsonPath.config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
        try {
            final Map<String, Map<String, Object>> store = JsonPath.with(JSON).get("store");
            assertThat(store.size(), equalTo(2));

            final Map<String, Object> bicycle = store.get("bicycle");
            final String color = (String) bicycle.get("color");
            final BigDecimal price = (BigDecimal) bicycle.get("price");
            assertThat(color, equalTo("red"));
            assertThat(price, equalTo(new BigDecimal("19.95")));
        } finally {
            JsonPath.config = null;
        }
    }

    @Test
    public void nonStaticJsonPathConfigHasPrecedenceOverStaticConfiguration() {
        JsonPath.config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.FLOAT_AND_DOUBLE);
        try {
            final Map<String, Map<String, Object>> store = JsonPath.with(JSON).using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL)).get("store");
            assertThat(store.size(), equalTo(2));

            final Map<String, Object> bicycle = store.get("bicycle");
            final String color = (String) bicycle.get("color");
            final BigDecimal price = (BigDecimal) bicycle.get("price");
            assertThat(color, equalTo("red"));
            assertThat(price, equalTo(new BigDecimal("19.95")));
        } finally {
            JsonPath.config = null;
        }
    }

    @Test
    public void getRootObjectAsMap2() {
        final Map<String, Object> store = JsonPath.from(JSON).get("store.book[0]");
        for (Map.Entry<String, Object> stringObjectEntry : store.entrySet()) {
            System.out.println(stringObjectEntry.getKey() + stringObjectEntry.getValue());
        }
    }

    @Test
    public void rootPath() {
        final JsonPath jsonPath = new JsonPath(JSON).setRootPath("store.book");
        assertThat(jsonPath.getInt("size()"), equalTo(4));
        assertThat(jsonPath.getList("author", String.class), hasItem("J. R. R. Tolkien"));
    }

    @Test
    public void rootPathFollowedByArrayIndexing() {
        final JsonPath jsonPath = new JsonPath(JSON).setRootPath("store.book");
        assertThat(jsonPath.getString("[0].author"), equalTo("Nigel Rees"));
    }

    @Test
    public void supportsGettingEntireObjectGraphUsingEmptyString() {
        final List<Map<String, String>> object = JsonPath.from(JSON2).get("");
        assertThat(object.get(0).get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void supportsGettingEntireObjectGraphUsing$() {
        final List<Map<String, String>> object = JsonPath.from(JSON2).get("$");
        assertThat(object.get(0).get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void supportsGettingEntireObjectGraphUsingNoArgumentGet() {
        final List<Map<String, String>> object = JsonPath.from(JSON2).get();
        assertThat(object.get(0).get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void getValueFromUnnamedRootObject() {
        final Map<String, String> object = JsonPath.from(JSON2).get("get(0)");
        assertThat(object.get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void getValueFromUnnamedRootObjectUsingBrackets() {
        final Map<String, String> object = JsonPath.from(JSON2).get("[0]");
        assertThat(object.get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void getSubValueFromUnnamedRootObjectUsingBrackets() {
        final String object = JsonPath.from(JSON2).getString("[0].email");
        assertThat(object, equalTo("name1@mail.com"));
    }

    @Test
    public void getNumericalValues() {
        assertThat(JsonPath.with(JSON).getDouble("store.book[0].price"), equalTo(8.95D));
        assertThat(JsonPath.with(JSON).getFloat("store.book[0].price"), equalTo(8.95F));

        // The price is stored as an integer
        assertThat(JsonPath.with(JSON).getByte("store.book[1].price"), equalTo((byte) 12));
        assertThat(JsonPath.with(JSON).getShort("store.book[1].price"), equalTo((short) 12));
        assertThat(JsonPath.with(JSON).getInt("store.book[1].price"), equalTo(12));
        assertThat(JsonPath.with(JSON).getLong("store.book[1].price"), equalTo(12L));

        // The atoms are stored as a long
        assertThat(JsonPath.with(JSON).getByte("store.bicycle.atoms"), equalTo((byte) Long.MAX_VALUE));
        assertThat(JsonPath.with(JSON).getShort("store.bicycle.atoms"), equalTo((short) Long.MAX_VALUE));
        assertThat(JsonPath.with(JSON).getInt("store.bicycle.atoms"), equalTo((int) Long.MAX_VALUE));
        assertThat(JsonPath.with(JSON).getLong("store.bicycle.atoms"), equalTo(Long.MAX_VALUE));
    }

    @Test
    public void convertsValueToStringWhenExplicitlyRequested() {
        String phoneNumber = JsonPath.from(JSON2).getString("phone[0]");
        assertThat(phoneNumber, equalTo("3456789"));
    }

    @Test
    public void convertsValueToIntWhenExplicitlyRequested() {
        int phoneNumber = JsonPath.from(JSON2).getInt("phone[0]");
        assertThat(phoneNumber, equalTo(3456789));
    }

    @Test
    public void convertsValueToDoubleWhenExplicitlyRequested() {
        double phoneNumber = JsonPath.from(JSON2).getDouble("phone[0]");
        assertThat(phoneNumber, equalTo(3456789d));
    }

    @Test
    public void convertsValueToFloatWhenExplicitlyRequested() {
        float phoneNumber = JsonPath.from(JSON2).getFloat("phone[0]");
        assertThat(phoneNumber, equalTo(3456789f));
    }

    @Test
    public void convertsValueToUUIDWhenExplicitlyRequested() {
        String JSON3 = "{\"id\":\"db24eeeb-7fe5-41d3-8f06-986b793ecc91\"}";
        UUID uuid = JsonPath.from(JSON3).getUUID("id");
        assertThat(uuid, equalTo(UUID.fromString("db24eeeb-7fe5-41d3-8f06-986b793ecc91")));
    }

    @Test
    public void convertsListMembersToDefinedTypeIfPossible() {
        final List<Integer> phoneNumbers = JsonPath.with(JSON2).getList("phone", int.class);
        assertThat(phoneNumbers, hasItems(3456789, 1234567, 2345678));
    }

    @Test
    public void getMapWithGenericType() {
        final Map<String, String> map = JsonPath.with(JSON_MAP).getMap("$", String.class, String.class);
        assertThat(map, allOf(hasEntry("price1", "12.3"), hasEntry("price2", "15.0")));
    }

    @Test
    public void getMapWithAnotherGenericType() {
        final Map<String, Float> map = JsonPath.with(JSON_MAP).getMap("$", String.class, float.class);
        assertThat(map, allOf(hasEntry("price1", 12.3f), hasEntry("price2", 15.0f)));
    }

    @Test
    public void getStringConvertsTheResultToAString() {
        final String priceAsString = JsonPath.with(JSON).getString("store.book.price[0]");
        assertThat(priceAsString, is("8.95"));
    }

    @Test(expectedExceptions = JsonPathException.class)
    public void malformedJson() {
        String MALFORMED_JSON = """
                {
                    "a": 123456
                    "b":"string"
                }""";
        JsonPath.from(MALFORMED_JSON).get("a");
    }

    @Test
    public void getObjectWorksWhenPathPointsToAJsonObject() {
        final Book book = JsonPath.from(JSON).getObject("store.book[2]", Book.class);

        assertThat(book, equalTo(new Book("fiction", "Herman Melville", "Moby Dick", "0-553-21311-3", 8.99f)));
    }

    @Test
    public void getObjectWorksWhenPathPointsToATypeRefMap() {
        final Map<String, Object> book = JsonPath.from(JSON).getObject("store.book[2]", new TypeRef<Map<String, Object>>() {
        });
        assertThat(book.get("category"), Matchers.equalTo("fiction"));
        assertThat(book.get("author"), Matchers.equalTo("Herman Melville"));
        assertThat(book.get("price"), Matchers.equalTo(8.99));
    }

    @Test
    public void getObjectWorksWhenPathPointsToATypeRefList() {
        final List<Float> prices = JsonPath.from(JSON).getObject("store.book.price", new TypeRef<List<Float>>() {
        });
        assertThat(prices, containsInAnyOrder(8.95, 12, 8.99, 22.99));
    }

    @Test
    public void getObjectWorksWhenPathPointsToAJsonObject2() {
        final List<Book> books = JsonPath.from(JSON).getList("store.book", Book.class);
        assertThat(books, hasSize(4));
        assertThat(books.get(0).getAuthor(), equalTo("Nigel Rees"));
    }

    @Test
    public void getObjectAsMapWorksWhenPathPointsToAJsonObject() {
        final Map<String, String> book = JsonPath.from(JSON).getObject("store.book[2]", Map.class);
        assertThat(book, hasEntry("category", "fiction"));
        assertThat(book, hasEntry("author", "Herman Melville"));
    }

    @Test
    public void getObjectWorksWhenPathPointsToAList() {
        final List<String> categories = JsonPath.from(JSON).getObject("store.book.category", List.class);
        assertThat(categories, hasItems("reference", "fiction"));
    }

    @Test
    public void getObjectAsFloatWorksWhenPathPointsToAFloat() {
        final Float price = JsonPath.from(JSON).getObject("store.book.price[0]", Float.class);
        assertThat(price, equalTo(8.95f));
    }

    @Test
    public void getObjectAsStringWorksWhenPathPointsToAString() {
        final String category = JsonPath.from(JSON).getObject("store.book.category[0]", String.class);
        assertThat(category, equalTo("reference"));
    }

    @Test
    public void jsonPathSupportsPrettifiyingJson() {
        final String prettyJson = JsonPath.with(JSON2).prettify();
        assertThat(prettyJson, equalTo("""
                [
                    {
                        "email": "name1@mail.com",
                        "alias": "name one",
                        "phone": "3456789"
                    },
                    {
                        "email": "name2@mail.com",
                        "alias": "name two",
                        "phone": "1234567"
                    },
                    {
                        "email": "name3@mail.com",
                        "alias": "name three",
                        "phone": "2345678"
                    }
                ]"""));

    }

    @Test
    public void jsonPathSupportsPrettyPrintingJson() {
        final String prettyJson = JsonPath.with(JSON2).prettyPrint();
        assertThat(prettyJson, equalTo("""
                [
                    {
                        "email": "name1@mail.com",
                        "alias": "name one",
                        "phone": "3456789"
                    },
                    {
                        "email": "name2@mail.com",
                        "alias": "name two",
                        "phone": "1234567"
                    },
                    {
                        "email": "name3@mail.com",
                        "alias": "name three",
                        "phone": "2345678"
                    }
                ]"""));
    }

    @Test
    public void jsonPathSupportsPrettyPeekingJson() {
        final String phone = JsonPath.with(JSON2).prettyPeek().getString("phone[0]");
        assertThat(phone, equalTo("3456789"));
    }

    @Test
    public void jsonPathSupportsPeekingAtTheJson() {
        final String phone = JsonPath.with(JSON2).peek().getString("phone[0]");
        assertThat(phone, equalTo("3456789"));
    }

    @Test
    public void canParseJsonDocumentWhenFirstKeyIsIntegerUsingManualEscaping() {
        final float number = JsonPath.from(JSON_PATH_STARTING_WITH_NUMBER).getFloat("'0'");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenFirstKeyThatIsAIntegerUsingNoEscaping() {
        final float number = JsonPath.from(JSON_PATH_STARTING_WITH_NUMBER).getFloat("0");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesKeyThatIsAIntegerUsingNoEscaping() {
        String JSON_PATH_WITH_NUMBER = """
                {
                    "map" :
                        {
                            "0" : 12.3,
                            "1": 15.0
                        }
                }
                """;
        final float number = JsonPath.from(JSON_PATH_WITH_NUMBER).getFloat("map.0");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesKeyThatIsABooleanUsingEscaping() {
        final float number = JsonPath.from(JSON_PATH_WITH_BOOLEAN).getFloat("map.'false'");
        assertThat(number, equalTo(15.0f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesKeyThatIsABooleanUsingNoEscaping() {
        final float number = JsonPath.from(JSON_PATH_WITH_BOOLEAN).getFloat("map.true");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesMinusInsideEscaped() {
        JsonPath path = new JsonPath("""
                {
                    "a-b" : "minus",
                    "a.b" : "dot",
                    "a.b-c" : "both"
                }
                """);

        assertThat(path.getString("'a.b-c'"), equalTo("both"));
    }

    @Test
    public void canParseJsonDocumentWithMultipleConsecutiveIntegersInsidePath() {
        String json = """
                {
                    "foo.bar.baz":
                        {
                            "0.2.0": "test"
                        }
                }""";

        final String string = JsonPath.from(json).getString("'foo.bar.baz'.'0.2.0'");

        assertThat(string, equalTo("test"));
    }

    @Test
    public void can_parse_multiple_values() {
        final JsonPath jsonPath = new JsonPath(JSON);

        final String category1 = jsonPath.getString("store.book.category[0]");
        final String category2 = jsonPath.getString("store.book.category[1]");

        assertThat(category1, equalTo("reference"));
        assertThat(category2, equalTo("fiction"));
    }

    @Test
    public void pretty_printing_works() {
        String json = """
                {
                    "data":
                        [
                            {
                                 "uid": 10,
                                 "name": "abc"
                            }
                       ]
                }""";
        final JsonPath jsonPath = new JsonPath(json);

        final String string = jsonPath.prettyPrint();
        assertThat(string, equalTo("""
                {
                    "data": [
                        {
                            "uid": 10,
                            "name": "abc"
                        }
                    ]
                }"""));
    }

    @Test
    public void parses_json_document_with_attribute_name_equal_to_properties() {
        final String jsonWithPropertyAttribute = """
                [
                    {
                        "properties": "test"
                    }
                ]
                """;
        final String value = new JsonPath(jsonWithPropertyAttribute).getString("[0].properties");
        assertThat(value, equalTo("test"));
    }

    @Test
    public void parses_json_document_with_attribute_name_equal_to_size() {
        String JSON_PATH_WITH_SIZE = """
                { "map" :
                    {
                        "size" : 12.3,
                        "1": 15.0
                    }
                }
                """;
        final float anInt = new JsonPath(JSON_PATH_WITH_SIZE).getFloat("map.size");
        assertThat(anInt, is(12.3f));
    }

    @Test
    public void can_find_if_a_key_exists_in_json_object() {
        String json = """
                {
                "status": "success",
                "fund_code":"00200",
                "fund_name":"My Fund Name",
                "models": [
                  {
                    "model_id": 639506,
                    "model_name": "New Validated Model",
                    "model_type": null,
                    "portfolios": null,
                    "created_date": 1390978800000,
                    "display_create_date": "01/29/2014",
                    "updated_date": 1392274800000,
                    "display_update_date": "02/13/2014",
                    "number_of_clients": 1,
                    "risk": "N/A",
                    "time_frame": "N/A"
                  },
                  {
                    "model_id": 639507,
                    "model_name": "My Validated Model",
                    "model_type": null,
                    "portfolios": null,
                    "created_date": 1390978800000,
                    "display_create_date": "01/29/2014",
                    "updated_date": 1392274800000,
                    "display_update_date": "02/13/2014",
                    "number_of_clients": 1,
                    "risk": "N/A",
                    "time_frame": "N/A"
                  }
                ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getBoolean("any { it.key == 'fund_code' }"), is(true));
        assertThat(jsonPath.getBoolean("models.any { it.containsKey('number_of_clients') }"), is(true));
    }

    @Test
    public void can_parse_json_attributes_starting_with_a_number() {
        String json = """
                {
                   "6269f15a0bb9b1b7d86ae718e84cddcd" : {
                            "attr1":"val1",
                            "attr2":"val2",
                            "attrx":"valx"
                   }
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getString("6269f15a0bb9b1b7d86ae718e84cddcd.attr1"), equalTo("val1"));
    }

    @Test
    public void automatically_escapes_json_attributes_whose_name_equals_properties() {
        String json = """
                {
                   "features":[
                      {
                         "type":"Feature",
                         "geometry":{
                            "type":"GeometryCollection",
                            "geometries":[
                               {
                                  "type":"Point",
                                  "coordinates":[
                                     19.883992823270653,
                                     50.02026203045478
                                  ]
                               }
                            ]
                         },
                         "properties":{
                            "gridId":6
                         }
                      },
                      {
                         "type":"Feature",
                         "geometry":{
                            "type":"GeometryCollection",
                            "geometries":[
                               {
                                  "type":"Point",
                                  "coordinates":[
                                     19.901266347582094,
                                     50.07074684071764
                                  ]
                               }
                            ]
                         },
                         "properties":{
                            "gridId":7
                         }
                      }
                   ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getList("features.properties.gridId", Integer.class), hasItems(7));
    }

    @Test
    public void can_manually_escape_class_property() {
        String json = """
                {
                  "semester": "Fall 2015",
                  "groups": [
                    {
                      "siteUrl": "http://cphbusinessjb.cloudapp.net/CA2/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-ebski.rhcloud.com/CA2New/",
                      "authors": "Ebbe, Kasper, Christoffer",
                      "class": "A klassen",
                      "group": "Gruppe: Johns Llama Herders A/S"
                    },
                    {
                      "siteUrl": "http://ca2-chrislind.rhcloud.com/CA2Final/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-pernille.rhcloud.com/NYCA2/",
                      "authors": "Marta, Jeanette, Pernille",
                      "class": "DAT A",
                      "group": "Group: MJP"
                    },
                    {
                      "siteUrl": "https://ca2-afn.rhcloud.com:8443/company.jsp",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp",
                      "authors": "Mikkel, Steffen, B Andersen",
                      "class": "A Class Computer Science",
                      "group": "1"
                    }
                  ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getList("groups.getAt('class')", String.class), hasItems("A klassen"));
    }

    @Test
    public void automatically_escapes_class_property() {
        String json = """
                {
                  "semester": "Fall 2015",
                  "groups": [
                    {
                      "siteUrl": "http://cphbusinessjb.cloudapp.net/CA2/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-ebski.rhcloud.com/CA2New/",
                      "authors": "Ebbe, Kasper, Christoffer",
                      "class": "A klassen",
                      "group": "Gruppe: Johns Llama Herders A/S"
                    },
                    {
                      "siteUrl": "http://ca2-chrislind.rhcloud.com/CA2Final/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-pernille.rhcloud.com/NYCA2/",
                      "authors": "Marta, Jeanette, Pernille",
                      "class": "DAT A",
                      "group": "Group: MJP"
                    },
                    {
                      "siteUrl": "https://ca2-afn.rhcloud.com:8443/company.jsp",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp",
                      "authors": "Mikkel, Steffen, B Andersen",
                      "class": "A Class Computer Science",
                      "group": "1"
                    }
                  ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getList("groups.class", String.class), hasItems("A klassen"));
    }


    @Test
    public void unicode_json_values_are_pretty_printed_without_unicode_escaping() {
        final String prettyJson = JsonPath.with("{\"some\":\"ŘÍŠŽŤČÝŮŇÚĚĎÁÉÓ\"}").prettyPrint();
        assertThat(prettyJson, equalTo("{\n    \"some\": \"ŘÍŠŽŤČÝŮŇÚĚĎÁÉÓ\"\n}"));
    }

    @Test
    public void need_to_escape_lists_with_hyphen_and_brackets() {
        String json = """
                {
                    "some-list[0]" : [ "one", "two" ]
                 }
                """;
        JsonPath jsonPath = JsonPath.from(json);
        assertThat(jsonPath.getString("'some-list[0]'[0]"), equalTo("one"));
    }

    @Test
    public void doesnt_need_to_escape_lists_with_hyphen_without_brackets() {
        String json = """
                { "some-list" : [ "one", "two" ] }
                """;
        JsonPath jsonPath = JsonPath.from(json);
        assertThat(jsonPath.getString("some-list[0]"), equalTo("one"));
    }

    @Test
    public void does_not_fail_on_absent_lists() {
        String json = """
                {
                    "root" : { }
                }
                """;
        JsonPath jsonPath = JsonPath.from(json);
        assertThat(jsonPath.getString("root.items[0]"), is(nullValue()));
    }
}
