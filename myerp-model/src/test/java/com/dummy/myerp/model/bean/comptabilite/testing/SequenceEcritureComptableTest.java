package com.dummy.myerp.model.bean.comptabilite.testing;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SequenceEcritureComptableTest {

    private List<JournalComptable> journalComptableList = new ArrayList<JournalComptable>();

    @Before
    public void init() {
        journalComptableList.add(new JournalComptable("AC", "Achat"));
        journalComptableList.add(new JournalComptable("VE", "Vente"));
        journalComptableList.add(new JournalComptable("BQ", "Banque"));
        journalComptableList.add(new JournalComptable("OD", "Opérations Diverses"));
    }

    @Test
    public void getByCodeAndYear() {

        // Arrange
        List<SequenceEcritureComptable> sequenceEcritureComptableList = new ArrayList<SequenceEcritureComptable>();
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();

        // Act
        JournalComptable journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode(journalComptableList, "VE"),
                new JournalComptable("VE", "Vente"));

        sequenceEcritureComptable.setJournalComptable(journal);
        sequenceEcritureComptable.setAnnee(2019);
        sequenceEcritureComptable.setDerniereValeur(1);
        sequenceEcritureComptableList.add(sequenceEcritureComptable);

        // Assert
        Assert.assertNotNull(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList, "VE",
                2019));
        Assert.assertEquals(sequenceEcritureComptable.getDerniereValeur(), SequenceEcritureComptable.getByCodeAndYear
                (sequenceEcritureComptableList, "VE", 2019).getDerniereValeur());
        Assert.assertNull(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList, "AC",
                2020));

    }

    @Test
    public void isSequenceEcritureComptableExist() {

        // Arrange
        JournalComptable journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode(journalComptableList, "VE"),
                new JournalComptable("VE", "Vente"));

        // Act
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journal, 2019,
                1);


        // Assert
        Assert.assertTrue(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable
                , "VE", 2019));
        Assert.assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable,
                "AC", 2019));
        Assert.assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable,
                "VE", 2020));
        Assert.assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(null, "VE",
                2019));
        Assert.assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(null, "VE",
                2020));

    }

}
