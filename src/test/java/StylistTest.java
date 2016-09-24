import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    assertEquals(true, testStylist instanceof Stylist);
  }

  @Test
  public void stylist_instantiatesWithUserName_Test() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    assertEquals("Test", testStylist.getUserName());
  }

  @Test
  public void stylist_instantiatesWithFirstName_Bob() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    assertEquals("Bob", testStylist.getFirstName());
  }

  @Test
  public void stylist_instantiatesWithLastName_deTester() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    assertEquals("deTester", testStylist.getLastName());
  }

  @Test
  public void stylist_instantiatesWithSpecialty_Hair() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    assertEquals("Hair", testStylist.getSpecialty());
  }

  @Test
  public void stylist_instantiatesWithoutId_0() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    assertEquals(0, testStylist.getId());
  }

  @Test
  public void setUserName_setsTheName_newName() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.setUserName("newName");
    assertEquals("newName", testStylist.getUserName());
  }

  @Test
  public void getId_getsTheId_int() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    assertEquals(true, testStylist.getId() > 0);
  }

  @Test
  public void setFirstName_setsTheFirstName_Tom() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.setFirstName("Tom");
    assertEquals("Tom", testStylist.getFirstName());
  }

  @Test
  public void setLastName_setsTheLastName_laTester() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.setLastName("laTester");
    assertEquals("laTester", testStylist.getLastName());
  }

  @Test
  public void setSpecialty_setsTheSpecialty_Nails() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.setSpecialty("Nails");
    assertEquals("Nails", testStylist.getSpecialty());
  }

  @Test
  public void save_savesToDatabase_true() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(testStylist));
  }

  @Test
  public void save_setsTheId_true() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(testStylist.getId(), savedStylist.getId());
    assertEquals(true, savedStylist.getId() > 0);
  }

  @Test
  public void save_savesName_test() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals("Test", savedStylist.getUserName());
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    Stylist firstStylist = new Stylist("First", "Bob", "deTester", "Hair");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Second", "Tom", "deTest", "Hair");
    secondStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(firstStylist));
    assertEquals(true, Stylist.all().get(1).equals(secondStylist));
  }

  @Test
  public void find_returnsStylistWithSameId_secondStylist() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    Stylist secondStylist = new Stylist("Second", "Tom", "laTest", "Hair");
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }

  @Test
  public void find_returnsNothingForUnknownId_null() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    assertEquals(null, Stylist.find(-1));
  }

  @Test
  public void findByUserName_returnsStylistWithSameName_secondStylist() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    Stylist secondStylist = new Stylist("Second", "Tom", "laTest", "Hair");
    secondStylist.save();
    assertEquals(secondStylist, Stylist.findByUserName("Second"));
  }

  @Test
  public void findByUserName_returnsNothingForUnknownName_null() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    assertEquals(null, Stylist.findByUserName("NotATest"));
  }

  @Test
  public void search_returnsStylistWithSimilarNameCaseInsensitve_listOfStylists() {
    Stylist firstStylist = new Stylist("First", "Bob", "deTester", "Hair");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Second", "Tom", "laTester", "Hair");
    secondStylist.save();
    Stylist thirdStylist = new Stylist("Third", "Tommy", "Test", "Hair");
    thirdStylist.save();
    assertEquals(false, Stylist.search("tom").contains(firstStylist));
    assertEquals(true, Stylist.search("tom").contains(secondStylist));
    assertEquals(true, Stylist.search("tom").contains(thirdStylist));
  }

  @Test
  public void search_returnsNothingForUnknownStylistId_0() {
    Stylist firstStylist = new Stylist("First", "Bob", "deTester", "Hair");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Second", "John", "Test", "Hair");
    secondStylist.save();
    assertEquals(0, Stylist.search("tom").size());
  }

  @Test
  public void update_savesNewName_newName() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    testStylist.setUserName("newName");
    testStylist.update();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals("newName", savedStylist.getUserName());
  }

  @Test
  public void update_preservesOriginalName_Test() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    testStylist.update();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals("Test", savedStylist.getUserName());
  }

  @Test
  public void update_preservesOriginalId_true() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    testStylist.setUserName("newName");
    testStylist.update();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals(testStylist.getId(), savedStylist.getId());
  }

  @Test
  public void delete_removesStylistFromDatabase_null() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    testStylist.delete();
    assertEquals(null, Stylist.find(testStylist.getId()));
  }

  @Test
  public void equals_returnsTrueIfNamesAndIdsAreEqual_true() {
    Stylist testStylist = new Stylist("Test", "Bob", "deTester", "Hair");
    testStylist.save();
    Stylist firstStylist = Stylist.find(testStylist.getId());
    Stylist secondStylist = Stylist.find(testStylist.getId());
    assertEquals(true, firstStylist.equals(secondStylist));
  }

}
