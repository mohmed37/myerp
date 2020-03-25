package com.dummy.myerp.model.bean.comptabilite.testing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
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
    private EcritureComptable ecritureComptable;
    private List<EcritureComptable> ecritureComptableList;
    @Before
    public void initEcritureComptable(){

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

        ecritureComptable = new EcritureComptable();

        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fourniture",
                606,"Prix fourniture","80.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fourniture",
                4456,"TVA","20.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fourniture",
                401,"Facture C110002",null, "100.00"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2,"Founisseur 1",
                401,"Fournisseur","100.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "Fournisseur 1",
                512, "Banque",null, "100.00"));

        ecritureComptableList = new ArrayList<EcritureComptable>();

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
    }

    /*
     * Test si ecriture comptable est équilibré
     *
     */
    @Test
    public void isEquilibree() {
        // Assert
        assertThat(ecritureComptable.isEquilibree()).isEqualTo(true);
    }

    /*
     * Test si ecriture comptable n'est pas équilibré
     */
    @Test
    public void isNotEquilibree() {
        ecritureComptable.setLibelle("Non équilibrée");

        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fourniture",
                606,"Prix fourniture","80.0", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "Fourniture",
                401,"Facture C110002",null, "100.00"));

        assertThat(ecritureComptable.isEquilibree()).isEqualTo(false);

    }
    /*
     * Test si la somme du débit est correct
     */

    @Test
    public void getTotalDebit() {
        // Assert
        Assert.assertEquals(new BigDecimal( "200.00"),ecritureComptable.getTotalDebit() );
        Assert.assertNotEquals(new BigDecimal( "200"),ecritureComptable.getTotalDebit());

    }

    /*
     * Test si la somme du crédit est correct
     */
    @Test
    public void getTotalCredit(){

        // Assert
        Assert.assertEquals(new BigDecimal( "200.00"),ecritureComptable.getTotalCredit());
        Assert.assertNotEquals(new BigDecimal( "200"),ecritureComptable.getTotalCredit());
    }

    /*
     * Test si ecriture comptable est présent dans la liste avec ID
     *
     */
    @Test
    public void getById_whenEcritureComptableExist() {
        // Assert
        assertThat(EcritureComptable.isEcritureComptableExist(ecritureComptableList.get(0), 1));

    }

    @Test
    public void getById_whenEcritureComptable() {

        // Assert
        assertThat(EcritureComptable.getById(ecritureComptableList, 1).getId());
        assertThat(EcritureComptable.getById(ecritureComptableList, 1).getReference()).isEqualTo("VE-2020/00001");
        assertThat(EcritureComptable.getById(ecritureComptableList, 1).getLibelle()).isEqualTo("Fournisseur");
        assertThat(EcritureComptable.getById(ecritureComptableList,1).getJournalComptable().
                getCode()).isEqualTo("VE");
    }


    /*
     * Test si ecriture comptable n'est  pas présent dans la liste avec ID
     *
     */
    @Test
    public void getById_whenEcritureComptableNotExist() {
        // Assert
        assertThat(EcritureComptable.getById(ecritureComptableList, 2)).isEqualTo(null);

    }




    /*
     * Après chaque test effacement des données
     */
    @After
    public void undefCompteComptable() {
        compteComptableList.clear();
        journalComptableList.clear();
    }


}
