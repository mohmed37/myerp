package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.SpringRegistry;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.testing.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;



public class ComptabiliteDaoImplTest {

    private ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();

    private List<CompteComptable> compteComptableList = new ArrayList<>();
    private List<JournalComptable> journalComptableList= new ArrayList<>();
    private SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();


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

    @Before
    public void init() {
        SpringRegistry.init();
        compteComptableList = dao.getListCompteComptable();
        journalComptableList = dao.getListJournalComptable();
    }
    @After
    public void initData(){
        dao.initData();
    }

    @Test
    public void isDataSourceConfig(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://127.0.0.1:9032/db_myerp");
        ds.setUsername("usr_myerp");
        ds.setPassword("myerp");

        Map<DataSourcesEnum, DataSource> vMapDataSource = new HashMap<>(DataSourcesEnum.values().length);
        vMapDataSource.put(DataSourcesEnum.MYERP,ds );

        Assert.assertFalse( AbstractDbConsumer.isDataSourceConfig(ds,vMapDataSource,DataSourcesEnum.MYERP) );
        Assert.assertTrue( AbstractDbConsumer.isDataSourceConfig(ds,vMapDataSource,null) );
        Assert.assertTrue( AbstractDbConsumer.isDataSourceConfig(null,vMapDataSource,DataSourcesEnum.MYERP) );
        Assert.assertTrue( AbstractDbConsumer.isDataSourceConfig(null,vMapDataSource,null) );

    }

    @Test(expected = UnsatisfiedLinkError.class )
    public void isDataSourceIsNull()throws UnsatisfiedLinkError{
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://127.0.0.1:9032/db_myerp");
        ds.setUsername("usr_myerp");
        ds.setPassword("myerp");

        Map<DataSourcesEnum, DataSource> vMapDataSource = new HashMap<>(DataSourcesEnum.values().length);
        vMapDataSource.put(DataSourcesEnum.MYERP,ds );
        AbstractDbConsumer.isDataSourceIsNull(ds,DataSourcesEnum.MYERP) ;
        AbstractDbConsumer.isDataSourceIsNull(null,DataSourcesEnum.MYERP);
    }



    @Test
    public void crudSequenceEcritureComptable(){
        JournalComptable journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode( journalComptableList, "VE" ),
                new JournalComptable( "VE","Vente" ) );
         sequenceEcritureComptable = new SequenceEcritureComptable(journal,2020,
                1);
        dao.insertSequenceEcritureComptable( sequenceEcritureComptable );

        sequenceEcritureComptable = dao.getSequenceEcritureComptable("VE",2020);
        assertNotNull( sequenceEcritureComptable );


        sequenceEcritureComptable.setDerniereValeur( 2 );
        dao.updateSequenceEcritureComptable( sequenceEcritureComptable );;


        sequenceEcritureComptable = dao.getSequenceEcritureComptable("VE",2020);
        assertTrue( sequenceEcritureComptable.getDerniereValeur().equals( 2 ) );

        dao.deleteSequenceEcritureComptable( sequenceEcritureComptable );


        sequenceEcritureComptable = dao.getSequenceEcritureComptable("VE",2020);
        assertNull( sequenceEcritureComptable );

    }




    @Test
    public void getListCompteComptable() {
        List<CompteComptable> listCompteComptable = dao.getListCompteComptable();
        assertThat(listCompteComptable.isEmpty()).isFalse();
    }

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> listJournalComptable = dao.getListJournalComptable();
        assertThat(listJournalComptable.isEmpty()).isFalse();}

    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> listEcritureComptable = dao.getListEcritureComptable();
        assertThat(listEcritureComptable.isEmpty()).isFalse();
    }



    @Test
    public void getEcritureComptable() {
        EcritureComptable ecritureComptable = dao.getEcritureComptable( -1);
        assertNotNull( ecritureComptable);
        ecritureComptable =dao.getEcritureComptable( 0);
        assertNull(ecritureComptable);

    }

    @Test(expected = NotFoundException.class )
    public void getEcritureComptableByRef() throws NotFoundException {
        EcritureComptable ecritureComptable = dao.getEcritureComptableByRef("AC-2016/00001");
        assertNotNull( ecritureComptable);
        dao.getEcritureComptableByRef("AC-2016/00000");

    }



    @Test(expected = NotFoundException.class )
    public void crudEcritureComptable() throws NotFoundException {
        Date date = new Date();
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournalComptable( new JournalComptable("BQ","Banque") );
        ecritureComptable.setDate(date );
        ecritureComptable.setReference("BQ-2020/00001");
        ecritureComptable.setLibelle("Paiement Facture F11001");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F110001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F110001",
                512,"Banque",null, "52.74"));


        dao.insertEcritureComptable(ecritureComptable );

        EcritureComptable getEcritureComptableByRef = dao.getEcritureComptableByRef(ecritureComptable.getReference());

        assertNotNull( getEcritureComptableByRef);

        getEcritureComptableByRef.setReference("QB-2020/00002");

        dao.updateEcritureComptable( getEcritureComptableByRef );

        getEcritureComptableByRef = dao.getEcritureComptableByRef(getEcritureComptableByRef.getReference() );

        assertNotNull( getEcritureComptableByRef);

        dao.deleteEcritureComptable( getEcritureComptableByRef.getId() );

        dao.getEcritureComptableByRef(getEcritureComptableByRef.getReference() );

    }




}
