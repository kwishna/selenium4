package rough;

public class JsonSlurperTest {
    public static void main(String[] args) {
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

                
    }
}
