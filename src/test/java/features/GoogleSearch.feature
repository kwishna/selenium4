@smoke
Feature: As a test-enginner
  I should be able to validate
  Google search functionality

  Background:
    Given Selenium4 Webdriver "chrome" browser is started

  @smoke
  Scenario Outline: Valid Google Search
    When I open "google" home page
    Then I verify doc string parameter
        """
            Some Title, Eh?
            ===============
            Here is the first paragraph of my blog post. Lorem ipsum dolor sit amet,
            consectetur adipiscing elit.
        """
    Then I should be navigated to "google" search page
    When I enter a search "<keyword>" in search field
    Then I verify following data table:
      | firstName   | lastName | birthDate  |
      | Annie M. G. | Schmidt  | 1911-03-20 |
      | Roald       | Dahl     | 1916-09-13 |
      | Astrid      | Lindgren | 1907-11-14 |

    Examples:
      | keyword |
      | Apple   |
      | Mango   |

#    java type: List<List<String>>
#    [
#       [ "firstName", "lastName", "birthDate" ],
#       [ "Annie M.G", "Schmidt", "1911-03-20" ],
#       [ "Roald", "Dahl", "1916-09-13" ],
#       [ "Astrid", "Lindgren", "1907-11-14" ]
#    ]

#    java type: List<Map<String, String>>
#    [
#       { "firstName": "Annie M.G", "lastName": "Schmidt",  "birthDate": "1911-03-20" },
#       { "firstName": "Roald",     "lastName": "Dahl",     "birthDate": "1916-09-13" },
#       { "firstName": "Astrid",    "lastName": "Lindgren", "birthDate": "1907-11-14" }
#    ]

#   https://github.com/cucumber/cucumber-jvm/tree/main/datatable
#   https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-java#data-tables
#    registry.defineDataTableType(DataTableType.cell(Airport.class));
#    registry.defineDataTableType(DataTableType.entry(Geolocation.class));

#    registry.defineDataTableType(
#    new DataTableType(
#        "airport",
#        Airport.class,
#        new TableCellTransformer<Airport>() {
#            @Override
#            public Airport transform(String cell) {
#                return new Airport(cell);
#            }
#        }
#    )
#  );
#
#    registry.defineDataTableType(
#    new DataTableType(
#        Geolocation.class,
#        new TableEntryTransformer<Geolocation>() {
#            @Override
#            public Geolocation transform(Map<String, String> entry) {
#                return new Geolocation(
#                    parseDouble(entry.get("lat")),
#                    parseDouble(entry.get("lon"))
#                );
#            }
#        }
#    )
# );

#  public class StepDefinitions {
#
#    @DataTableType(replaceWithEmptyString = "[blank]")
#    public Author authorEntryTransformer(Map<String, String> entry) {
#        return new Author(
#            entry.get("firstName"),
#            entry.get("lastName"),
#            entry.get("birthDate"));
#    }
#
#    @Given("a list of authors in a table")
#    public void aListOfAuthorsInATable(List<Author> authors) {
#
#    }
#  }


