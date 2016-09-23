import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist testStylist = new Stylist("Test");
    assertEquals(true, testStylist instanceof Stylist);
  }

  @Test
  public void stylist_instantiatesWithName_Test() {
    Stylist testStylist = new Stylist("Test");
    assertEquals("Test", testStylist.getName());
  }

  @Test
  public void stylist_instantiatesWithoutId_0() {
    Stylist testStylist = new Stylist("Test");
    assertEquals(0, testStylist.getId());
  }

  @Test
  public void setName_setsTheName_newName() {
    Stylist testStylist = new Stylist("Test");
    testStylist.setName("newName");
    assertEquals("newName", testStylist.getName());
  }

  @Test
  public void getId_getsTheId_int() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    assertEquals(true, testStylist.getId() > 0);
  }

  @Test
  public void save_savesToDatabase_true() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(testStylist));
  }

  @Test
  public void save_setsTheId_true() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(testStylist.getId(), savedStylist.getId());
    assertEquals(true, savedStylist.getId() > 0);
  }

  @Test
  public void save_savesName_test() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals("Test", savedStylist.getName());
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    Stylist firstStylist = new Stylist("First");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(firstStylist));
    assertEquals(true, Stylist.all().get(1).equals(secondStylist));
  }

  @Test
  public void find_returnsStylistWithSameId_secondStylist() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }

  @Test
  public void find_returnsNothingForUnknownId_null() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    assertEquals(null, Stylist.find(-1));
  }

  @Test
  public void findByName_returnsStylistWithSameName_secondStylist() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    Stylist secondStylist = new Stylist("Second");
    secondStylist.save();
    assertEquals(secondStylist, Stylist.findByName("Second"));
  }

  @Test
  public void findByName_returnsNothingForUnknownName_null() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    assertEquals(null, Stylist.findByName("NotATest"));
  }

  @Test
  public void search_returnsStylistWithSimilarNameCaseInsensitve_listOfStylists() {
    Stylist firstStylist = new Stylist("Bob");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Tom");
    secondStylist.save();
    Stylist thirdStylist = new Stylist("Tommy");
    thirdStylist.save();
    assertEquals(false, Stylist.search("tom").contains(firstStylist));
    assertEquals(true, Stylist.search("tom").contains(secondStylist));
    assertEquals(true, Stylist.search("tom").contains(thirdStylist));
  }

  @Test
  public void search_returnsNothingForUnknownStylistId_0() {
    Stylist firstStylist = new Stylist("Bob");
    firstStylist.save();
    Stylist secondStylist = new Stylist("George");
    secondStylist.save();
    assertEquals(0, Stylist.search("tom").size());
  }

  @Test
  public void update_savesNewName_newName() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testStylist.setName("newName");
    testStylist.update();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals("newName", savedStylist.getName());
  }

  @Test
  public void update_preservesOriginalName_Test() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testStylist.update();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals("Test", savedStylist.getName());
  }

  @Test
  public void update_preservesOriginalId_true() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testStylist.setName("newName");
    testStylist.update();
    Stylist savedStylist = Stylist.find(testStylist.getId());
    assertEquals(testStylist.getId(), savedStylist.getId());
  }

  @Test
  public void delete_removesStylistFromDatabase_null() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    testStylist.delete();
    assertEquals(null, Stylist.find(testStylist.getId()));
  }

  @Test
  public void equals_returnsTrueIfNamesAndIdsAreEqual_true() {
    Stylist testStylist = new Stylist("Test");
    testStylist.save();
    Stylist firstStylist = Stylist.find(testStylist.getId());
    Stylist secondStylist = Stylist.find(testStylist.getId());
    assertEquals(true, firstStylist.equals(secondStylist));
  }

}
