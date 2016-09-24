import java.util.List;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void client_instantiatesCorrectly_true() {
    Client testClient = new Client("Test", "Bob", "deTester");
    assertEquals(true, testClient instanceof Client);
  }

  @Test
  public void client_instantiatesWithUserName_Test() {
    Client testClient = new Client("Test", "Bob", "deTester");
    assertEquals("Test", testClient.getUserName());
  }

  @Test
  public void client_instantiatesWithFirstName_Test() {
    Client testClient = new Client("Test", "Bob", "deTester");
    assertEquals("Bob", testClient.getFirstName());
  }

  @Test
  public void client_instantiatesWithLastName_Test() {
    Client testClient = new Client("Test", "Bob", "deTester");
    assertEquals("deTester", testClient.getLastName());
  }

  @Test
  public void client_instantiaNametesWithoutId_0() {
    Client testClient = new Client("Test", "Bob", "deTester");
    assertEquals(0, testClient.getId());
  }

  @Test
  public void client_instantiatesWithoutStylistId_null() {
    Client testClient = new Client("Test", "Bob", "deTester");
    Integer expected = null;
    assertEquals(expected, testClient.getStylistId());
  }

  @Test
  public void setUserName_setsTheName_newUserName() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.setUserName("newName");
    assertEquals("newName", testClient.getUserName());
  }

  @Test
  public void setStylistId_setsTheStylistId_1() {
    Client testClient = new Client("Test", "Bob", "deTester");
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testClient.setStylistId(testStylist.getId());
    assertEquals((Integer)testStylist.getId(), testClient.getStylistId());
  }

  @Test
  public void setFirstName_setsTheFirstName_Tom() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.setFirstName("Tom");
    assertEquals("Tom", testClient.getFirstName());
  }

  @Test
  public void setLastName_setsTheLastName_laTester() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.setLastName("laTester");
    assertEquals("laTester", testClient.getLastName());
  }

  @Test
  public void getId_getsTheId_int() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    assertEquals(true, testClient.getId() > 0);
  }

  @Test
  public void save_savesToDatabase_true() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    assertEquals(true, Client.all().get(0).equals(testClient));
  }

  @Test
  public void save_savesUserName_test() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("Test", savedClient.getUserName());
  }

  @Test
  public void save_savesStylistId_1() {
    Client testClient = new Client("Test", "Bob", "deTester");
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testClient.setStylistId(testStylist.getId());
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals((Integer)testStylist.getId(), savedClient.getStylistId());
  }

  @Test
  public void save_setsTheId_true() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(testClient.getId(), savedClient.getId());
    assertEquals(true, savedClient.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("First", "Bob", "deTester");
    firstClient.save();
    Client secondClient = new Client("Second", "Tom", "laTest");
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    Client secondClient = new Client("Second", "Tom", "laTest");
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  @Test
  public void find_returnsNothingForUnknownId_null() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    assertEquals(null, Client.find(-1));
  }

  @Test
  public void findByUserName_returnsClientWithSameName_secondClient() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    Client secondClient = new Client("Second", "Tom", "laTest");
    secondClient.save();
    assertEquals(secondClient, Client.findByUserName("Second"));
  }

  @Test
  public void findByUserName_returnsNothingForUnknownName_null() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    assertEquals(null, Client.findByUserName("NotATest"));
  }

  @Test
  public void withStylistId_returnsClientsWithSameStylistId_listOfClients() {
    Stylist firstStylist = new Stylist("Test");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    Client firstClient = new Client("Test", "Bob", "deTester");
    firstClient.setStylistId(firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Second", "Tom", "laTest");
    secondClient.setStylistId(secondStylist.getId());
    secondClient.save();
    Client thirdClient = new Client("Third", "John", "Test");
    thirdClient.setStylistId(secondStylist.getId());
    thirdClient.save();
    assertEquals(false, Client.withStylistId(secondStylist.getId()).contains(firstClient));
    assertEquals(true, Client.withStylistId(secondStylist.getId()).contains(secondClient));
    assertEquals(true, Client.withStylistId(secondStylist.getId()).contains(thirdClient));
  }

  @Test
  public void withStylistId_returnsNothingForUnknownStylistId_0() {
    Client firstClient = new Client("Test", "Bob", "deTester");
    Stylist firstStylist = new Stylist("Test");
    firstStylist.save();
    firstClient.setStylistId(firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Second", "Tom", "laTest");
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    secondClient.setStylistId(secondStylist.getId());
    secondClient.save();
    assertEquals(0, Client.withStylistId(-1).size());
  }

  @Test
  public void search_returnsClientWithSimilarNameCaseInsensitve_listOfClients() {
    Client firstClient = new Client("First", "Bob", "deTester");
    firstClient.save();
    Client secondClient = new Client("Second", "Tom", "laTest");
    secondClient.save();
    Client thirdClient = new Client("Third", "Tommy", "Test");
    thirdClient.save();
    assertEquals(false, Client.search("tom").contains(firstClient));
    assertEquals(true, Client.search("tom").contains(secondClient));
    assertEquals(true, Client.search("tom").contains(thirdClient));
  }

  @Test
  public void search_returnsNothingForUnknownStylistId_0() {
    Client firstClient = new Client("First", "Bob", "deTester");
    firstClient.save();
    Client secondClient = new Client("Second", "John", "Test");
    secondClient.save();
    assertEquals(0, Client.search("tom").size());
  }

  @Test
  public void update_preservesOriginalId_true() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    testClient.setUserName("newName");
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(testClient.getId(), savedClient.getId());
  }

  @Test
  public void update_preservesOriginalName_Test() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("Test", savedClient.getUserName());
  }

  @Test
  public void update_savesNewName_newName() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    testClient.setUserName("newName");
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("newName", savedClient.getUserName());
  }

  @Test
  public void update_preservesOriginalStylistId_1() {
    Client testClient = new Client("Test", "Bob", "deTester");
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testClient.setStylistId(testStylist.getId());
    testClient.save();
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals((Integer)testStylist.getId(), savedClient.getStylistId());
  }

  @Test
  public void update_savesNewStylistId_2() {
    Client testClient = new Client("Test", "Bob", "deTester");
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testClient.setStylistId(testStylist.getId());
    testClient.save();
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    testClient.setStylistId(secondStylist.getId());
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals((Integer)secondStylist.getId(), savedClient.getStylistId());
  }

  @Test
  public void delete_removesClientFromDatabase_null() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    testClient.delete();
    assertEquals(null, Client.find(testClient.getId()));
  }

  @Test
  public void equals_returnsTrueIfNamesAndIdsAreEqual_true() {
    Client testClient = new Client("Test", "Bob", "deTester");
    testClient.save();
    Client firstClient = Client.find(testClient.getId());
    Client secondClient = Client.find(testClient.getId());
    assertEquals(true, firstClient.equals(secondClient));
  }

}
