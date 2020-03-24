package com.dummy.myerp.model.bean.comptabilite.testing;


import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class JournalComptableTest {


    private SequenceEcritureComptable createSequence(JournalComptable journalComptable,Integer year,Integer lastValue ){
        return new SequenceEcritureComptable( journalComptable,year,lastValue );
    }

    @Test
    public void getByCode() {
        // Arrange
        List<JournalComptable> journalComptableList =new ArrayList<JournalComptable>();
        // Act
        Date date= new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("AC");
        journalComptable.setLibelle("Achat");
        journalComptable.setSequenceEcritureComptable(this.createSequence(journalComptable,
                calendar.get(Calendar.YEAR ),1));
        journalComptableList.add(  journalComptable );
        // Assert
        Assert.assertNotNull(JournalComptable.getByCode( journalComptableList,"AC" ) );
        Assert.assertEquals(journalComptable.getLibelle(),JournalComptable.getByCode( journalComptableList,
                "AC" ).getLibelle() );
        Assert.assertNull(JournalComptable.getByCode( journalComptableList,"AB" ) );
    }

    @Test
    public void isJournalComptableExist() {
        // Arrange
        JournalComptable journalComptable = new JournalComptable() ;

        // Act
        journalComptable.setCode("AC");
        journalComptable.setLibelle("Achat");

        // Assert
        Assert.assertTrue( JournalComptable.isJournalComptableExist( journalComptable,"AC") );
        Assert.assertFalse( JournalComptable.isJournalComptableExist( journalComptable,"AB" ) );
        Assert.assertFalse( JournalComptable.isJournalComptableExist( null,"AC") );
    }
}
