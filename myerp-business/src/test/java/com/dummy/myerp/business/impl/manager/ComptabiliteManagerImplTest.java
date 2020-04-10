package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.*;

import com.dummy.myerp.business.impl.BusinessProxyImpl;
import com.dummy.myerp.model.bean.comptabilite.testing.*;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import com.dummy.myerp.testbusiness.business.SpringRegistry;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;


public class ComptabiliteManagerImplTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private List<CompteComptable> compteComptableList = new ArrayList<>();
    private List<JournalComptable> journalComptableList= new ArrayList<>();
    private JournalComptable journalComptable;




    private LigneEcritureComptable createLigne(Integer pLigneId, String pLigneEcritureLibelle,
                                               Integer pCompteComptableNumero, String pCompteComptableLibelle,
                                               String pDebit, String pCredit) {

        CompteComptable pCompteComptable =  ObjectUtils.defaultIfNull(
                CompteComptable.getByNumero( compteComptableList, pCompteComptableNumero ),
                new CompteComptable( pCompteComptableNumero,pCompteComptableLibelle ) );

        BigDecimal vDebit = pDebit == null ?  null : new BigDecimal( pDebit );
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal( pCredit );


        LigneEcritureComptable vRetour = new LigneEcritureComptable(pLigneId, pCompteComptable, pLigneEcritureLibelle,
                vDebit,vCredit );
        return vRetour;
    }
    @Before
    public void init(){

        ComptabiliteManagerImpl.setTransactionManager( BusinessTestCase.getTransactionManager() );
        ComptabiliteManagerImpl.setDaoProxy( BusinessTestCase.getDaoProxy() );
        ComptabiliteManagerImpl.setBusinessProxy( BusinessTestCase.getBusinessProxy() );


        compteComptableList.add(new CompteComptable(401,"Fournisseurs" ) );
        compteComptableList.add(new CompteComptable(411,"Clients" ) );
        compteComptableList.add(new CompteComptable(4456,
                "Taxes sur le chiffre d'affaires déductibles" ) );
        compteComptableList.add(new CompteComptable(4457,
                "Taxes sur le chiffre d'affaires collectées par l'entreprise" ) );
        compteComptableList.add(new CompteComptable(512,"Banque" ) );
        compteComptableList.add(new CompteComptable(606,
                "Achats non stockés de matières et fournitures" ) );
        compteComptableList.add(new CompteComptable(706,"Prestations de services" ) );

        journalComptableList.add(new JournalComptable("AC","Achat") );
        journalComptableList.add(new JournalComptable("VE","Vente") );
        journalComptableList.add(new JournalComptable("BQ","Banque") );
        journalComptableList.add(new JournalComptable("OD","Opérations Diverses") );


        journalComptable = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode( journalComptableList, "AC" ),
                new JournalComptable( "AL","Achat Libre" ) );
    }

    @Test(expected = UnsatisfiedLinkError.class )
    public void testInit()throws UnsatisfiedLinkError{

        Assert.assertEquals(BusinessProxyImpl.getInstance(SpringRegistry.getDaoProxy(),
                BusinessTestCase.getTransactionManager() ), BusinessProxyImpl.getInstance() );
        Assert.assertNotEquals(BusinessProxyImpl.getInstance(null,BusinessTestCase.getTransactionManager() ),
                BusinessProxyImpl.getInstance() );
    }
    @Test
    public void getListCompteComptable() {
        List<CompteComptable> listCompteComptable = manager.getListCompteComptable();
        assertNotNull( listCompteComptable);
    }

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> listJournalComptable = manager.getListJournalComptable();
        assertNotNull(listJournalComptable);
    }

    @Test
    public void getListEcritureComptable()  {
        List<EcritureComptable> listEcritureComptable = manager.getListEcritureComptable();
        assertNotNull(listEcritureComptable);
    }



    @Test
    public void setReference(){

        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalComptable,
                2020,1);

        Assert.assertEquals("AC-2020/00001",manager.setReference( sequenceEcritureComptable ) );

        Assert.assertNotEquals("AC-2020/0001",manager.setReference( sequenceEcritureComptable ) );

        Assert.assertNotEquals("AL-2020/0001",manager.setReference( sequenceEcritureComptable ) );

        Assert.assertNotEquals("AC-2019/00001",manager.setReference( sequenceEcritureComptable ) );

    }


    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable ecritureComptable= new EcritureComptable();
        manager.checkEcritureComptableUnit(ecritureComptable);
    }


    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG1()throws FunctionalException{

        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournalComptable( journalComptable);
        ecritureComptable.setReference("BQ-2020/00001");
        ecritureComptable.setDate( new Date() );
        ecritureComptable.setLibelle("Nombre écriture valide : au moins 1  ligne de débit et 1 ligne de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));

        manager.checkEcritureComptable(ecritureComptable );

        ecritureComptable.setReference("AC-2020/00001");
        manager.checkEcritureComptable(ecritureComptable );
    }



    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {

        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournalComptable( journalComptable );
        ecritureComptable.setReference("AC-2019/00001");
        ecritureComptable.setDate( new Date());
        ecritureComptable.setLibelle("équilibrée");
        ecritureComptable.setLibelle("Nombre écriture valide : au moins 1  ligne de débit et 1 ligne de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));
        manager.checkEcritureComptable(ecritureComptable );
        ecritureComptable.setLibelle("Non équilibrée");
        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture valide : au moins 1  ligne de débit et 1 ligne de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "62.74"));
        manager.checkEcritureComptable( ecritureComptable );
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {

        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournalComptable( journalComptable );
        ecritureComptable.setReference("BQ-2020/00001");
        ecritureComptable.setDate( new Date() );
        ecritureComptable.setLibelle("Nombre écriture valide : au moins 1  ligne de débit et 1 ligne de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));
        manager.checkEcritureComptable( ecritureComptable);

        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture non valide : 2 lignes de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", null, "52.74"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));
        manager.checkEcritureComptable( ecritureComptable );
    }
    @Test
    public void isNumberValidEcritureComptable(){
        EcritureComptable ecritureComptable = new EcritureComptable();

        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture valide : au moins 1  ligne de débit et 1 ligne de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));
        Assert.assertTrue(manager.isNumberValidEcritureComptable( ecritureComptable ));

        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture non valide : 1 seule ligne de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Facture F11001",
                512,"Banque",null, "52.74"));
        Assert.assertFalse(manager.isNumberValidEcritureComptable( ecritureComptable ) );

        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture non valide : 1 seule ligne de débit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        Assert.assertFalse(manager.isNumberValidEcritureComptable( ecritureComptable ) );

        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture non valide : 2 lignes de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Facture F11001",
                512,"Banque",null, "52.74"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));
        Assert.assertFalse(manager.isNumberValidEcritureComptable( ecritureComptable ) );

        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Nombre écriture non valide : 2 lignes de crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2,"Facture F11001",
                401,"Fournisseur", "52.74", null));
        Assert.assertFalse(manager.isNumberValidEcritureComptable( ecritureComptable ) );
    }

    @Test
    public void isAmountExist(){
        Assert.assertTrue(manager.isAmountExist( new BigDecimal("1") ));
        Assert.assertTrue(manager.isAmountExist( new BigDecimal("-1") ));
        Assert.assertTrue(manager.isAmountExist( new BigDecimal("1.1") ));
        Assert.assertTrue(manager.isAmountExist( new BigDecimal("-1.1") ));

        Assert.assertFalse(manager.isAmountExist( BigDecimal.ZERO) );
        Assert.assertFalse(manager.isAmountExist( new BigDecimal("0")) );
        Assert.assertFalse(manager.isAmountExist( new BigDecimal("0.00")) );
        Assert.assertFalse(manager.isAmountExist( null ) );
    }
    @Test
    public void isReferenceValid(){
        Date date = new Date();
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournalComptable( new JournalComptable("AC","Achat") );

        ecritureComptable.setReference("AC-2020/00001");
        ecritureComptable.setDate( date );
        Assert.assertTrue( manager.isReferenceValid( ecritureComptable ) );

        ecritureComptable.setReference("AL-2020/00001");
        Assert.assertFalse( manager.isReferenceValid( ecritureComptable ) );

        ecritureComptable.setReference("AL-2020/00001");
        Assert.assertFalse( manager.isReferenceValid( ecritureComptable ) );

        ecritureComptable.setReference("AC-2019/00001");
        Assert.assertFalse( manager.isReferenceValid( ecritureComptable ) );

        ecritureComptable.setReference("AC-2020/0000");
        Assert.assertFalse( manager.isReferenceValid( ecritureComptable ) );


    }
    @Test(expected = FunctionalException.class )
    public void checkEcritureComptableUnitConstaint()throws FunctionalException{

        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournalComptable( journalComptable );
        ecritureComptable.setReference("AC-2020/00001");
        ecritureComptable.setDate( new Date() );
        ecritureComptable.setLibelle("équilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Facture F11001",
                512,"Banque",null, "52.74"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Facture F11001",
                512,"Banque",null, "52.74"));
        manager.checkEcritureComptable( ecritureComptable );
        ecritureComptable.getListLigneEcriture().clear();
        manager.checkEcritureComptable(ecritureComptable );
    }





}
