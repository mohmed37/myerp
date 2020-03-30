package com.dummy.myerp.model.bean.comptabilite.testing;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SequenceEcritureComptableTest {


    private List<SequenceEcritureComptable> sequenceEcritureComptableList;
    private  SequenceEcritureComptable sequenceEcritureComptable=new SequenceEcritureComptable();
    private List<JournalComptable > journalComptableList= new ArrayList<JournalComptable>();
    @Before
    public void init() {
        sequenceEcritureComptableList = new ArrayList<SequenceEcritureComptable>();

        JournalComptable journalComptable = new JournalComptable.Builder().code("VE").libelle("Vente").build();

        SequenceEcritureComptable sequenceEcritureComptable=Mockito.mock(SequenceEcritureComptable.class);
        Mockito.when(sequenceEcritureComptable.getJournalComptable()).thenReturn(journalComptable);
        Mockito.when(sequenceEcritureComptable.getAnnee()).thenReturn(2019);
        Mockito.when(sequenceEcritureComptable.getDerniereValeur()).thenReturn(1);
        sequenceEcritureComptableList.add(sequenceEcritureComptable);

    }



    @Test
    public void getByCodeAndYear() {

        // Assert

        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VE",2019).
                getAnnee()).isEqualTo(2019);
        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VE",2019).
                getJournalComptable().getCode()).isEqualTo("VE");
        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VE",2019).
                getDerniereValeur()).isEqualTo(1);

        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VE",2019).
                getAnnee()).isEqualTo(2019);
        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VE",2019).
                getDerniereValeur()).isEqualTo(1);
    }

    @Test
    public void getByCodenotYear(){
        // Assert
        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VE",2020))
                .isEqualTo(null);
        assertThat(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"VA",2019))
                .isEqualTo(null);
    }
    @Test
    public void isSequenceEcritureComptableExist(){


        JournalComptable journalComptable=new JournalComptable.Builder().code("AC").libelle("Achat").build();


        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable.Builder()
                .JournalComptable(journalComptable).annee(2016).derniereValeur(1).build();

        // Assert
        assertThat(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable,
                "AC",2016));

    }



    /*
     * Après chaque test effacement des données
     */
    @After
    public void undefSequenceEcriture() {

        sequenceEcritureComptableList.clear();
    }

}
