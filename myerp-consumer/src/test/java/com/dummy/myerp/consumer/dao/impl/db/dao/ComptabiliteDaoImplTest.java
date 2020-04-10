package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.SpringRegistry;
import com.dummy.myerp.model.bean.comptabilite.testing.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;



public class ComptabiliteDaoImplTest {

    private ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();

    private List<CompteComptable> compteComptableList = new ArrayList<>();
    private List<JournalComptable> journalComptableList= new ArrayList<>();
    private SequenceEcritureComptable sequenceEcritureComptable;
    public EcritureComptable ecritureComptable;



    private LigneEcritureComptable createLigne(Integer pLigneId, String pLigneEcritureLibelle,
                                               Integer pCompteComptableNumero, String pCompteComptableLibelle,
                                               String pDebit, String pCredit) {

        CompteComptable pCompteComptable =  ObjectUtils.defaultIfNull(
                CompteComptable.getByNumero( compteComptableList, pCompteComptableNumero ),
                new CompteComptable( pCompteComptableNumero,pCompteComptableLibelle ) );

        BigDecimal vDebit = pDebit == null ?  null : new BigDecimal( pDebit );
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal( pCredit );
        LigneEcritureComptable vRetour = new LigneEcritureComptable(pLigneId, pCompteComptable,
                pLigneEcritureLibelle,vDebit,vCredit );
        return vRetour;
    }

    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        assertNotNull(SpringRegistry.getDaoProxy());

    }



    @Before
    public void init() {
        SpringRegistry.init();
        compteComptableList = dao.getListCompteComptable();
        journalComptableList = dao.getListJournalComptable();

        JournalComptable journalComptable=new JournalComptable.Builder().code("VE").libelle("Vente").build();

        this.sequenceEcritureComptable=new SequenceEcritureComptable.Builder().JournalComptable(journalComptable)
                .annee(2020).derniereValeur(1).build();
        this.ecritureComptable=new EcritureComptable.Builder().id(-1).journalComptable(journalComptable).
                reference("VE-2020/00001").date(new Date()).libelle("Cartouches d'imprimante").build();
        }

    @After
    public void initData(){
        dao.initData();
    }



    @Test
    public void crudSequenceEcritureComptable(){

        //Test de la méthode d'insertion
        Assertions.assertThat(dao.getSequenceEcritureComptable("VE",2020)).isNull();
        dao.insertSequenceEcritureComptable( sequenceEcritureComptable );
        Assertions.assertThat(dao.getSequenceEcritureComptable("VE",2020)).isNotNull();

        //Test la methode modification
        Assertions.assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(1);
        sequenceEcritureComptable.setDerniereValeur(2);

        dao.updateSequenceEcritureComptable( sequenceEcritureComptable );
        Assertions.assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(2);

        //Test de la méthode de suppression

        dao.deleteSequenceEcritureComptable( sequenceEcritureComptable );
        Assertions.assertThat(dao.getSequenceEcritureComptable("VE",2020)).isNull();

     }


    @Test
    public void getListCompteComptable() {
        List<CompteComptable> listCompteComptable = dao.getListCompteComptable();
        assertThat(listCompteComptable.isEmpty()).isFalse();

    }

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> listJournalComptable = dao.getListJournalComptable();
        assertThat(listJournalComptable.isEmpty()).isFalse();



    }

    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> listEcritureComptable = dao.getListEcritureComptable();
        assertThat(listEcritureComptable.isEmpty()).isFalse();
    }



    @Test
    public void getEcritureComptable() throws NotFoundException {

        dao.insertEcritureComptable(ecritureComptable);
        Assertions.assertThat(dao.getEcritureComptable(2)).isNull();

    }

    @Test(expected = NotFoundException.class )
    public void getEcritureComptableByRef() throws NotFoundException {

        Assertions.assertThat(dao.getEcritureComptableByRef("VE-2020/00001")).isNotNull();
        Assertions.assertThat(dao.getEcritureComptable(-1)).isNotNull();
        Assertions.assertThat(dao.getEcritureComptableByRef("AC-2016/00000")).isNull();


    }



    @Test(expected = NotFoundException.class )
    public void crudEcritureComptable() throws NotFoundException {

        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F110001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F110001",
                512,"Banque",null, "52.74"));


        dao.insertEcritureComptable(ecritureComptable );

        EcritureComptable getEcritureComptableByRef = dao.getEcritureComptableByRef(ecritureComptable.getReference());

        assertNotNull( getEcritureComptableByRef);

        getEcritureComptableByRef.setReference("VE-2020/00001");

        dao.updateEcritureComptable( getEcritureComptableByRef );

        getEcritureComptableByRef = dao.getEcritureComptableByRef(getEcritureComptableByRef.getReference() );

        assertNotNull( getEcritureComptableByRef);

        dao.deleteEcritureComptable( getEcritureComptableByRef.getId() );

        dao.getEcritureComptableByRef(getEcritureComptableByRef.getReference() );

    }




}
