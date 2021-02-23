package name.ank.lab4;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class BibDatabaseTest {

    private BibDatabase openDatabase(String s) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(s))) {
            return new BibDatabase(reader);
        }
    }

    private BibDatabase openDatabase(@SuppressWarnings("SameParameterValue") String s, BibConfig cfg) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(s))) {
            return new BibDatabase(reader, cfg);
        }
    }


    @Test
    public void getFirstEntry() throws IOException {
        BibDatabase database = openDatabase("/references.bib");
        BibEntry first = database.getEntry(0);
        Assert.assertEquals(Types.ARTICLE, first.getType());
        Assert.assertEquals("The semantic web", first.getField(Keys.TITLE));
        Assert.assertNull("Field 'chapter' does not exist", first.getField(Keys.CHAPTER));
    }

    @Test
    public void normalModeDoesNotThrowException() throws IOException {
        BibDatabase database = openDatabase("/mixed.bib");
        BibConfig cfg = database.getCfg();
        cfg.strict = false;

        BibEntry first = database.getEntry(0);
        for (int i = 0; i < cfg.maxValid + 1; i++) {
            @SuppressWarnings({"unused", "RedundantSuppression"})
            BibEntry unused = database.getEntry(0);
            assertNotNull("Should not throw any exception @" + i, first.getType());
        }
    }

    @Test
    public void canReadAllItemsFromMixed() throws IOException {
        BibDatabase database = openDatabase("/mixed.bib");

        for (int i = 0; i < database.size(); i++) {
            BibEntry entry = database.getEntry(i);
            assertNotNull(entry.getType());
        }
    }

    @SuppressWarnings("RedundantSuppression")
    @Test
    public void strictModeThrowsException() throws IOException {
        BibDatabase database = openDatabase("/mixed.bib");
        BibConfig cfg = database.getCfg();
        cfg.strict = true;

        BibEntry first = database.getEntry(0);
        for (int i = 0; i < cfg.maxValid - 1; i++) {
            @SuppressWarnings("unused")
            BibEntry unused = database.getEntry(0);
            assertNotNull("Should not throw any exception @" + i, first.getType());
        }

        @SuppressWarnings("unused")
        BibEntry latestUnused = database.getEntry(0);
        assertThrows("Should throw an exception @" + (cfg.maxValid), IllegalStateException.class, first::getType);
    }

    @Test
    public void shuffleFlag() throws IOException {
        BibConfig nonShuffleCfg = new BibConfig();
        nonShuffleCfg.shuffle = false;
        BibDatabase normalDatabase = openDatabase("/mixed.bib", nonShuffleCfg);
        BibDatabase shuffledDatabase = openDatabase("/mixed.bib");

        for (int i = 0; i < normalDatabase.size(); i++) {
            assertNotEquals("Should be shuffled", normalDatabase.getEntry(i), shuffledDatabase.getEntry(i));
        }
    }
}
