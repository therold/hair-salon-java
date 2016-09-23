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
  public void client_instantiatesWithName_Test() {
    Client testClient = new Client("Test");
    assertEquals("Test", testClient.getName());
  }

  @Test
  public void client_instantiaNametesWithoutId_0() {
    Client testClient = new Client("Test");
    assertEquals(0, testClient.getId());
  }

  @Test
  public void client_instantiatesWithoutStylistId_0() {
    Client testClient = new Client("Test");
    assertEquals(0, testClient.getStylistId());
  }

  @Test
  public void setName_setsTheName_newName() {
    Client testClient = new Client("Test");
    testClient.setName("newName");
    assertEquals("newName", testClient.getName());
  }

  @Test
  public void setStylistId_setsTheStylistId_1() {
    Client testClient = new Client("Test");
    testClient.setStylistId(1);
    assertEquals(1, testClient.getStylistId());
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
  public void save_savesName_test() {
    Client testClient = new Client("Test");
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("Test", savedClient.getName());
  }

  @Test
  public void save_savesStylistId_1() {
    Client testClient = new Client("Test");
    testClient.setStylistId(1);
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(1, savedClient.getStylistId());
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
  public void findByName_returnsClientWithSameName_secondClient() {
    Client testClient = new Client("Test");
    testClient.save();
    Client secondClient = new Client("Second");
    secondClient.save();
    assertEquals(secondClient, Client.findByName("Second"));
  }

  @Test
  public void findByName_returnsNothingForUnknownName_null() {
    Client testClient = new Client("Test");
    testClient.save();
    assertEquals(null, Client.findByName("NotATest"));
  }

  @Test
  public void update_preservesOriginalId_true() {
    Client testClient = new Client("Test");
    testClient.save();
    testClient.setName("newName");
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
    assertEquals("Test", savedClient.getName());
  }

  @Test
  public void update_savesNewName_newName() {
    Client testClient = new Client("Test");
    testClient.save();
    testClient.setName("newName");
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals("newName", savedClient.getName());
  }

  @Test
  public void update_preservesOriginalStylistId_1() {
    Client testClient = new Client("Test");
    testClient.setStylistId(1);
    testClient.save();
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(1, savedClient.getStylistId());
  }

  @Test
  public void update_savesNewStylistId_2() {
    Client testClient = new Client("Test");
    testClient.setStylistId(1);
    testClient.save();
    testClient.setStylistId(2);
    testClient.update();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(2, savedClient.getStylistId());
  }

  @Test
  public void delete_removesClientFromDatabase_null() {
    Client testClient = new Client("Test");
    testClient.save();
    testClient.delete();
    assertEquals(null, Client.find(testClient.getId()));
  }

  @Test
  public void equals_returnsTrueIfNamesAndIdsAndStylistIdAreEqual_true() {
    Client testClient = new Client("Test");
    testClient.setStylistId(1);
    testClient.save();
    Client firstClient = Client.find(testClient.getId());
    Client secondClient = Client.find(testClient.getId());
    assertEquals(true, firstClient.equals(secondClient));
  }

}
