package restassured.paths;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import restassured.support.Colors;

public class XPaths {

    public static void queryPath(String query, String xml) {
        try {
            System.out.print(Colors.Purple + query + " => ");
            System.out.println(Colors.Green + XmlPath.from(xml).get(query));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void queryPath(String query, XmlPath xmlPath) {
        try {
            System.out.print(Colors.Purple + query + " => ");
            System.out.println(Colors.Green + xmlPath.get(query));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String books = """
                <response version-api="2.0">
                        <value>
                            <books>
                                <book available="20" id="1">
                                    <title>Don Quixote</title>
                                    <author id="1">Miguel de Cervantes</author>
                                </book>
                                <book available="14" id="2">
                                    <title>Catcher in the Rye</title>
                                   <author id="2">JD Salinger</author>
                               </book>
                               <book available="13" id="3">
                                   <title>Alice in Wonderland</title>
                                   <author id="3">Lewis Carroll</author>
                               </book>
                               <book available="5" id="4">
                                   <title>Don Quixote</title>
                                   <author id="4">Miguel de Cervantes</author>
                               </book>
                           </books>
                       </value>
                    </response>
                                """;
        queryPath("$", books);
        queryPath("response", books);
        queryPath("response.value.books.book[0].author", books);
        queryPath("response.value.books.book[0]", books);
        queryPath("response.value.books.book[0]['@id']", books);
        // Example shows a simple use of *, which only iterates over the direct children of the node.
        // Look for any node with a tag name equal to 'book' having an id with a value of '2' directly under the 'books' node.
        queryPath("""
                response.value.books.'*'.find { node ->
                    node.name() == 'book' && node.@id == '2'
                }
                """, books);
        // ** is the same as looking for something everywhere in the tree from this point down.
        queryPath("""
                response.'**'.find { book ->
                     book.author.text() == 'Lewis Carroll'
                 }.@id
                        """, books);
        queryPath("""
                response.value.books.book.findAll { book ->
                          book.@id.toInteger() > 2
                      }*.title.size()
                                """, books);
        queryPath("""
                response.value.books.book.findAll { book ->
                          book.@id.toInteger() > 2
                      }*.title
                                """, books);
    }
}
