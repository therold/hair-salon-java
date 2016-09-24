import java.util.List;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void client_instantiatesCorrectly_true() {
    Client testClient = new Client("Test");
    assertEquals(true, testClient instanceof Client);
  }

  @Test
  public void client_instantiatesWithUserame_Test() {
    Client testClient = new Client("Test");
    assertEquals("Test", testClient.getUsername());
  }

  @Test
  public void client_instantiaNametesWithoutId_0() {
    Client testClient = new Client("Test");
    assertEquals(0, testClient.getId());
  }

  @Test
  public void client_instantiatesWithoutStylistId_null() {
    Client testClient = new Client("Test");
    Integer expected = null;
    assertEquals(expected, testClient.getStylistId());
  }

  @Test
  public void setUsername_setsTheName_newUsername() {
    Client testClient = new Client("Test");
    testClient.setUsername("newName");
    assertEquals("newName", testClient.getUsername());
  }

  @Test
  public void setStylistId_setsTheStylistId_1() {
    Client testClient = new Client("Test");
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testClient.setStylistId(testStylist.getId());
    assertEquals((Integer)testStylist.getId(), testClient.getStylistId());
  }

  @Test
  public void getId_getsTheId_int() {
    Client testClient = new Client("Test");
    testClient.save();
    assertEquals(true, testClient.getId() > 0);
  }

  @Test
  public void save_savesToDatabase_true() {
    Client testClient = new Client("Test");
    testClient.save();
    assertEquals(true, Client.all().get(0).equals(testClient));
  }

  @Test
  public void save_savesUsername_test() {
    Client testClient = new Client("Test");
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("Test", savedClient.getUsername());
  }

  @Test
  public void save_savesStylistId_1() {
    Client testClient = new Client("Test");
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testClient.setStylistId(testStylist.getId());
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals((Integer)testStylist.getId(), savedClient.getStylistId());
  }

  @Test
  public void save_setsTheId_true() {
    Client testClient = new Client("Test");
    testClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(testClient.getId(), savedClient.getId());
    assertEquals(true, savedClient.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("First");
    firstClient.save();
    Client secondClient = new Client("Second");
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client testClient = new Client("Test");
    testClient.save();
    Client secondClient = new Client("Second");
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  @Test
  public void find_returnsNothingForUnknownId_null() {
    Client testClient = new Client("Test");
    testClient.save();
    assertEquals(null, Client.find(-1));
  }

  @Test
  public void findByUsername_returnsClientWithSameName_secondClient() {
    Client testClient = new Client("Test");
    testClient.save();
    Client secondClient = new Client("Second");
    secondClient.save();
    assertEquals(secondClient, Client.findByUsername("Second"));
  }

  @Test
  public void findByUsername_returnsNothingForUnknownName_null() {
    Client testClient = new Client("Test");
    testClient.save();
    assertEquals(null, Client.findByUsername("NotATest"));
  }

  @Test
  public void withStylistId_returnsClientsWithSameStylistId_listOfClients() {
    Stylist firstStylist = new Stylist("Test");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    Client firstClient = new Client("Test");
    firstClient.setStylistId(firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Second");
    secondClient.setStylistId(secondStylist.getId());
    secondClient.save();
    Client thirdClient = new Client("Third");
    thirdClient.setStylistId(secondStylist.getId());
    thirdClient.save();
    assertEquals(false, Client.withStylistId(secondStylist.getId()).contains(firstClient));
    assertEquals(true, Client.withStylistId(secondStylist.getId()).contains(secondClient));
    assertEquals(true, Client.withStylistId(secondStylist.getId()).contains(thirdClient));
  }

  @Test
  public void withStylistId_returnsNothingForUnknownStylistId_0() {
    Client firstClient = new Client("Test");
    Stylist firstStylist = new Stylist("Test");
    firstStylist.save();
    firstClient.setStylistId(firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Second");
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    secondClient.setStylistId(secondStylist.getId());
    secondClient.save();
    assertEquals(0, Client.withStylistId(-1).size());
  }

  @Test
  public void search_returnsClientWithSimilarNameCaseInsensitve_listOfClients() {
    Client firstClient = new Client("Bob");
    firstClient.save();
    Client secondClient = new Client("Tom");
    secondClient.save();
    Client thirdClient = new Client("Tommy");
    thirdClient.save();
    assertEquals(false, Client.search("tom").contains(firstClient));
    assertEquals(true, Client.search("tom").contains(secondClient));
    assertEquals(true, Client.search("tom").contains(thirdClient));
  }

  @Test
  public void search_returnsNothingForUnknownStylistId_0() {
    Client firstClient = new Client("Bob");
    firstClient.save();
    Client secondClient = new Client("George");
    secondClient.save();
    assertEquals(0, Client.search("tom").size());
  }

  @Test
  public void update_preservesOriginalId_true() {
    Client testClient = new Client("Test");
    testClient.save();
    testClient.setUsername("newName");
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(testClient.getId(), savedClient.getId());
  }

  @Test
  public void update_preservesOriginalName_Test() {
    Client testClient = new Client("Test");
    testClient.save();
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("Test", savedClient.getUsername());
  }

  @Test
  public void update_savesNewName_newName() {
    Client testClient = new Client("Test");
    testClient.save();
    testClient.setUsername("newName");
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("newName", savedClient.getUsername());
  }

  @Test
  public void update_preservesOriginalStylistId_1() {
    Client testClient = new Client("Test");
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
    Client testClient = new Client("Test");
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
    Client testClient = new Client("Test");
    testClient.save();
    testClient.delete();
    assertEquals(null, Client.find(testClient.getId()));
  }

  @Test
  public void equals_returnsTrueIfNamesAndIdsAreEqual_true() {
    Client testClient = new Client("Test");
    testClient.save();
    Client firstClient = Client.find(testClient.getId());
    Client secondClient = Client.find(testClient.getId());
    assertEquals(true, firstClient.equals(secondClient));
  }

}
