package com.dummy.myerp.model.bean.comptabilite.testing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class EcritureComptableTest {
    private List<CompteComptable> compteComptableList = new ArrayList<CompteComptable>();
    private List<JournalComptable >journalComptableList= new ArrayList<JournalComptable>();

    private LigneEcritureComptable createLigne(Integer pLigneId, String pLigneEcritureLibelle,
                                               Integer pCompteComptableNumero,String pCompteComptableLibelle,
                                               String pDebit, String pCredit) {

        CompteComptable pCompteComptable =  ObjectUtils.defaultIfNull(
                CompteComptable.getByNumero( compteComptableList, pCompteComptableNumero ),
                new CompteComptable( pCompteComptableNumero,pCompteComptableLibelle ) );

        BigDecimal vDebit = pDebit == null ?  null : new BigDecimal( pDebit );
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal( pCredit );


        LigneEcritureComptable vRetour = new LigneEcritureComptable(pLigneId, pCompteComptable, pLigneEcritureLibelle,vDebit,vCredit );
        return vRetour;
    }

    @Before
    public void init(){

        compteComptableList.add(new CompteComptable(401,"Fournisseurs" ) );
        compteComptableList.add(new CompteComptable(411,"Clients" ) );
        compteComptableList.add(new CompteComptable(4456,"Taxes sur le chiffre d'affaires déductibles"
        ) );
        compteComptableList.add(new CompteComptable(4457,"Taxes sur le chiffre d'affaires collectées" +
                " par l'entreprise" ) );
        compteComptableList.add(new CompteComptable(512,"Banque" ) );
        compteComptableList.add(new CompteComptable(606,"Achats non stockés de matières et fournitures"
        ) );
        compteComptableList.add(new CompteComptable(706,"Prestations de services" ) );

        journalComptableList.add(new JournalComptable("AC","Achat") );
        journalComptableList.add(new JournalComptable("VE","Vente") );
        journalComptableList.add(new JournalComptable("BQ","Banque") );
        journalComptableList.add(new JournalComptable("OD","Opérations Diverses") );


    }

    @Test
    public void isEquilibree() {
        // Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();

        // Act
        ecritureComptable.setLibelle("Equilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Founisseur 1",
                401,"Fournisseur","52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fournisseur 1",
                512, "Banque",null, "52.74"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                606,"Prix fourniture","43.95", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                4456,"TVA","8.79", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                401,"Facture C110002",null, "52.74"));

        // Assert
        Assert.assertTrue(ecritureComptable.toString(), ecritureComptable.isEquilibree());

        // Act
        ecritureComptable.getListLigneEcriture().clear();
        ecritureComptable.setLibelle("Non équilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1,"Founisseur 1",
                401,"Fournisseur","52.74", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fournisseur 1",
                512, "Banque",null, "52.74"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                606,"Prix fourniture","43.95", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                401,"Facture C110002",null, "52.74"));

        // Assert
        Assert.assertFalse(ecritureComptable.toString(), ecritureComptable.isEquilibree());
    }

    @Test
    public void getTotalDebit() {
        // Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();

        // Act
        ecritureComptable.setLibelle("Total Débit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                606,"Prix fourniture","80", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                4456,"TVA","20", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                401,"Facture C110002",null, "100"));

        // Assert
        Assert.assertEquals(new BigDecimal( "100.00"),ecritureComptable.getTotalDebit() );

        Assert.assertNotEquals(new BigDecimal( "100"),ecritureComptable.getTotalDebit());
    }

    @Test
    public void getTotalCredit(){
        // Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();

         // Act
        ecritureComptable.setLibelle("Total Crédit");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                606,"Prix fourniture","80", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                4456,"TVA","20", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fourniture",
                401,"Facture C110002",null, "100"));

        // Assert
        Assert.assertEquals(new BigDecimal( "100.00"),ecritureComptable.getTotalDebit() );

        Assert.assertNotEquals(new BigDecimal( "100"),ecritureComptable.getTotalCredit());
    }

    @Test
    public void isAmountNotNull(){
        // Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        // Assert
        Assert.assertTrue( ecritureComptable.isAmountNotNull( new BigDecimal( "100.00") ));
        Assert.assertTrue(ecritureComptable.isAmountNotNull(new BigDecimal("100")));
        Assert.assertFalse( ecritureComptable.isAmountNotNull( null ) );

    }

    @Test
    public void getById() {
        // Arrange
        List<EcritureComptable> ecritureComptableList = new ArrayList<EcritureComptable>();

        // Act
        JournalComptable journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode(journalComptableList, "VE"),
                new JournalComptable("VE", "Vente"));

        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setJournalComptable(journal);
        ecritureComptable.setReference("VE-2020/00001");
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Fournisseur");
        ecritureComptableList.add(ecritureComptable);

        // Assert
        Assert.assertNotNull(EcritureComptable.getById(ecritureComptableList, 1));
        Assert.assertNull(EcritureComptable.getById(ecritureComptableList, 2));
    }

    }
