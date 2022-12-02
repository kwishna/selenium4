package restassured.paths;

import io.restassured.path.json.JsonPath;
import restassured.support.Colors;

public class JPaths {
    public static void queryPath(String query, String json) {
        try {
            System.out.print(Colors.Purple + query + " => ");
            System.out.println(Colors.Green + JsonPath.from(json).get(query));
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            e.printStackTrace();
        }
    }

    public static void queryPath(String query, JsonPath path) {
        try {
            System.out.print(Colors.Purple + query + " => ");
            System.out.println(Colors.Green + path.get(query));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        String books = """
                  {
                  "response": {
                    "value": {
                      "books": {
                        "book": [
                          {
                            "title": "Don Quixote",
                            "author": "Miguel de Cervantes"
                          },
                          {
                            "title": "Catcher in the Rye",
                            "author": "JD Salinger"
                          },
                          {
                            "title": "Alice in Wonderland",
                            "author": "Lewis Carroll"
                          },
                          {
                            "title": "Don Quixote",
                            "author": "Miguel de Cervantes"
                          }
                        ]
                      }
                    }
                  }
                }
                """;
        String user = """
                {
                    "firstName": "John",
                    "lastName": "doe",
                    "age": 26,
                    "address": {
                        "streetAddress": "naist street",
                        "city": "Nara",
                        "postalCode": "630-0192"
                    },
                    "phoneNumbers": [
                        {
                            "type": "iPhone",
                            "number": "0123-4567-8888"
                        },
                        {
                            "type": "home",
                            "number": "0123-4567-8910"
                        }
                    ]
                }
                """;
        String store = """
                {
                   "store": {
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
                                 "price": 12.99
                             },
                             {
                                 "category": "fiction",
                                 "author": "Herman Melville",
                                 "title": "Moby Dick",
                                 "isbn": "0-553-21311-3",
                                 "price": 9.99
                             },
                             {
                                 "category": "fiction",
                                 "author": "J. R. R. Tolkien",
                                 "title": "The Lord of the Rings",
                                 "isbn": "0-395-19395-8",
                                 "price": 22.99
                             },
                             {
                                 "category": "horror",
                                 "author": "Krishna Singh",
                                 "title": "Snakes In The Ganga",
                                 "isbn": "0-235-14536-6",
                                 "price": 33
                             }
                       ],
                       "bicycle": {
                             "color": "red",
                             "price": 19.95
                       }
                     },
                     "city":"Bangalore"
                  }
                   """;
        String persons = """
                [
                    {
                        "id": 1,
                        "name": "John Smith",
                        "description": null,
                        "shortDescription": "No recorded interests.",
                        "alias": "JS",
                        "preferences": [
                            {
                                "id": 1,
                                "description": "likes candy and papaya",
                                "image": "papaya.jpg",
                                "name": "Papaya",
                                "dependentPreferences": [
                                    {
                                        "id": 1,
                                        "description": "Fruit must be ripe",
                                        "image": "ripe-papaya.jpg",
                                        "name": "pap"
                                    }
                                ]
                            }
                         ]
                    },
                    {
                        "id": 2,
                        "name": "Jane Smith",
                        "description": null,
                        "shortDescription": "No recorded interests.",
                        "alias": "JS",
                        "preferences": [
                            {
                                "id": 1,
                                "description": "likes candy and papaya",
                                "image": "papaya.jpg",
                                "name": "Papaya",
                                "dependentPreferences": [
                                    {
                                        "id": 1,
                                        "description": "Candy must be Skittles",
                                        "image": "Skittles.jpg",
                                        "name": "skt"
                                    }
                                ]
                            }
                         ]
                    }
                ]
                """;
        String football = """
                {
                  "count": 20,
                  "teams": [
                    {
                      "id": 322,
                      "name": "Hull City FC",
                      "shortName": "Hull",
                      "squadMarketValue": null,
                      "crestUrl": "http://upload.wikimedia.org/wikipedia/de/a/a9/Hull_City_AFC.svg"
                    },
                    {
                      "id": 338,
                      "name": "Leicester City FC",
                      "shortName": "Foxes",
                      "squadMarketValue": null,
                      "crestUrl": "http://upload.wikimedia.org/wikipedia/en/6/63/Leicester02.png"
                    },
                    {
                      "id": 340,
                      "name": "Southampton FC",
                      "shortName": "Southampton",
                      "squadMarketValue": null,
                      "crestUrl": "http://upload.wikimedia.org/wikipedia/de/c/c9/FC_Southampton.svg"
                    }
                  ]
                }
                """;
        String players = """
                {
                  "count": 24,
                  "players": [
                    {
                      "id": 439,
                      "name": "David de Gea",
                      "position": "Keeper",
                      "jerseyNumber": 1,
                      "dateOfBirth": "1990-11-07",
                      "nationality": "Spain",
                      "contractUntil": "2019-06-30",
                      "marketValue": null
                    },
                    {
                      "id": 440,
                      "name": "Sergio Romero",
                      "position": "Keeper",
                      "jerseyNumber": 20,
                      "dateOfBirth": "1987-02-22",
                      "nationality": "Argentina",
                      "contractUntil": "2018-06-30",
                      "marketValue": null
                    },
                    {
                      "id": 441,
                      "name": "Eric Bailly",
                      "position": "Centre-Back",
                      "jerseyNumber": 3,
                      "dateOfBirth": "1994-04-12",
                      "nationality": "Cote d'Ivoire",
                      "contractUntil": "2020-06-30",
                      "marketValue": null
                    }
                  ]
                }
                """;
//        queryPath("$", books);
//        queryPath("response", books);
//        queryPath("response.value.books.book[0].author", books);
//        queryPath("response.value.books.book[0]", books);
//        queryPath("phoneNumbers[1].number", user);

//        queryPath("find { it.name == 'John Smith' }.preferences.find { it.image == 'papaya.jpg' }.dependentPreferences.description", persons);
//        queryPath("response.value.books.book.find { it.author == 'Lewis Carroll' }.title", books);
//        queryPath("teams.name[-1]", football);
//        queryPath("teams.name[0,1]", football);

//        queryPath("players.findAll { it.jerseyNumber > 1 }.name", players);
//        queryPath("players.max { it.jerseyNumber }.name", players);
//        queryPath("players.min { it.jerseyNumber }.name", players);
//        queryPath("players.collect { it.jerseyNumber }.sum()", players);
//        queryPath("players.findAll { it.position == 'Keeper' }.find { it.nationality == 'Argentina' }.name", players);


//        queryPath("store.book[*].title", store); // Doesn't Work
//        queryPath("store.book[*]", store); // Doesn't Work

//        queryPath("store.bicycle.color", store);
//        queryPath("store.book[1].title", store);
//        queryPath("store.book[-1].title", store);
//        queryPath("store.book.findAll { it.category == 'reference' }", store);
//        queryPath("store.book.findAll { it.category in ['reference','fiction'] }", store);
//        queryPath("store.book.findAll { it.category=='reference'}.size()", store);
//        queryPath("store.book.findAll { it.price < 12.0 }", store);
//        queryPath("store.book.findAll { it.category != 'reference' }", store);
//        queryPath("store.book.findAll { it.category == 'reference' || it.category == 'fiction' }", store);
//        queryPath("store.book.findAll { it.category == 'reference' && it.category ==  'fiction' }", store);
//        queryPath("store.book.findAll { it.category == 'reference' && it.author ==  'Herman Melville' }", store);
//        queryPath("store.book.findAll { it.category =~/ref.*/ || it.category=~/icti.*/ }", store);

//        queryPath("store.bicycle.price.toInteger();", store);
//        queryPath("store.bicycle.price.any();", store);
//        queryPath("store.book.get(1);", store);
//        queryPath("store.book.every { it.category == 'reference' };", store);
//        queryPath("store.book.any { it.category == 'reference' };", store);
//        queryPath("store.book.any { it.category.contains('reference') };", store);
//        queryPath("store.book.author.get(0).toUpperCase();", store);

//        queryPath("store.book[1..3]", store);
//        queryPath("store.book[1,3]", store);
//        queryPath("store.book[-1,-2,-3].asReversed()", store);
//        queryPath("store.book.price.average()", store);
        queryPath("store.book.price", store);
//        queryPath("store.book.price*.plus(3)", store);
//        queryPath("store.book.price*.div(3)", store);
//        queryPath("store.book.price*.minus(15)", store);
//        queryPath("store.book.price*.multiply(3)", store);
//        queryPath("store.book.price*.minus(15)*.abs()", store);
//        queryPath("store.book.price*.toInteger()", store);
//        queryPath("store.book.price.findAll { Math.toIntExact(it.toLong()) % 2 == 0 }", store);
//        queryPath("store.book.price.grep { Math.toIntExact(it.toLong()) % 2 == 0 }", store);
//        queryPath("store.book.category", store);
//        queryPath("store.book.category.unique()", store);
//        queryPath("store.book.unique()", store);
//        queryPath("store.book.toUnique { it.category == 'horror' }", store);
//        queryPath("store.book.price.sort()", store);
//        queryPath("store.book.price.sort( { (a, b) -> (a == b) ? 0 : (a < b) ? 1 : -1 } )", store);
//        queryPath("store.book.price.max()", store);
//        queryPath("store.book.price.min()", store);
//        queryPath("store.book.collect { it.author }", store);
//        queryPath("store.book.price.join('-')", store);

//        queryPath("store.book[0].minus(['category':'reference']);", store);
//        queryPath("store.book.removeAll { it.key == 'reference' }", store);
//        queryPath("store.book.retainAll { it -> it.price % 2 == 0 }", store);
//        queryPath("store.book*.findAll { it.key == 'price' }", store);
//        queryPath("store.book*.findAll { it.key == 'author' }*.author", store);
//        queryPath("store.book.collect { entry -> entry.category }", store);

//        queryPath("store.book*.each { println(it) }", store);
//        queryPath("store.book.each { println(it) }", store);

//        queryPath("store.book.each { it.price > 30 }", store);


    }

    /*
    assert nums.min() == 1
            assert nums.max() == 3
            assert nums.sum() == 6
            assert nums.indices == [0, 1, 2]
            assert nums.swap(0, 2) == [3, 2, 1] as int[]
     */

    // https://docs.groovy-lang.org/latest/html/api/org/codehaus/groovy/runtime/DefaultGroovyMethods.htm
}