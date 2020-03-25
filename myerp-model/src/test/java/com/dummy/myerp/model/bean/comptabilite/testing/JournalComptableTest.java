package com.dummy.myerp.model.bean.comptabilite.testing;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JournalComptableTest {

    private List<JournalComptable> journalComptableList;

    /*
     * Création d'une liste de journauList comptables journauxComptable
     */

    @Before
    public void initJournalComptable() {
        journalComptableList = new ArrayList<JournalComptable>();
        for (int i = 1; i < 3; i++ ) {
            Date date= new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            JournalComptable journalComptable = Mockito.mock(JournalComptable.class);
            Mockito.when(journalComptable.getCode()).thenReturn("A"+i);
            Mockito.when(journalComptable.getLibelle()).thenReturn("Libelle"+i);
            journalComptableList.add(journalComptable);}
    }

    /*
     * Test si le compte est présent dans la liste
     */
    @Test
    public void getByCode_whenJournalComptableExist() {
        assertThat(JournalComptable.getByCode(journalComptableList, "A1").getLibelle()).isEqualTo("Libelle1");
        assertThat(JournalComptable.getByCode(journalComptableList, "A2").getLibelle()).isEqualTo("Libelle2");

    }

    /*
     * Test si le compte n'est pas dans la liste
     */
    @Test
    public void getByCode_whenJournalComptableNotExist() {
        assertThat(JournalComptable.getByCode(journalComptableList, "A3")).isEqualTo(null);
    }

    /*
     * Après chaque test effacement des données
     */

    @After
    public void undefJournalComptable() {

        journalComptableList.clear();
    }
}
