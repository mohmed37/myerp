package com.dummy.myerp.model.bean.comptabilite.testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LigneEcritureComptableTest {
   private List<LigneEcritureComptable> ligneEcritureComptableList;
   private JournalComptable journalComptable;
   private EcritureComptable ecritureComptable;
   private CompteComptable compteComptable;
    /*
     *  d'une de ligne Ecriture Comptable
     */
    @Before
    public void initLigneEcritureComptable() {

        ligneEcritureComptableList = new ArrayList<LigneEcritureComptable>();
        // JournalComptable journalComptable
        journalComptable=new JournalComptable.Builder().code("AC").libelle("Achat").build();
        // EcritureComptable ecritureComptable
        ecritureComptable =new EcritureComptable.Builder()
                .id(1).journalComptable(journalComptable).reference("AC-2020/00001")
                .date(new Date()).libelle("Cartouches d'imprimante").build();
        // CompteComptable compteComptable
        compteComptable=new CompteComptable.Builder().numero(401).libelle("Fournisseurs").build();

        //  LigneEcritureComptable ligneEcritureComptable
        LigneEcritureComptable ligneEcritureComptable = Mockito.mock(LigneEcritureComptable.class);
        Mockito.when(ligneEcritureComptable.getId()).thenReturn(1);
        Mockito.when(ligneEcritureComptable.getCompteComptable()).thenReturn(compteComptable);
        Mockito.when(ligneEcritureComptable.getLibelle()).thenReturn("Cartouches d'imprimante");
        Mockito.when(ligneEcritureComptable.getEcritureComptable()).thenReturn(ecritureComptable);

        ligneEcritureComptableList.add(ligneEcritureComptable);}

    /*
     * Test si la ligneEcriture Comptable est pas dans la liste avec ID
     */

    @Test
    public void getByLigneId() {
        // Assert
        assertThat(LigneEcritureComptable.getById(ligneEcritureComptableList, 1).getLibelle())
                .isEqualTo("Cartouches d'imprimante");

        assertThat(LigneEcritureComptable.getById(ligneEcritureComptableList, 1).getId())
                .isEqualTo(1);

        assertThat(LigneEcritureComptable.getById(ligneEcritureComptableList, 2)).isEqualTo(null);

    }

    @Test
    public void isLigneEcritureComptableExistAndCompteComptable() {

        assertThat(LigneEcritureComptable.getById(ligneEcritureComptableList, 1).getCompteComptable().getNumero())
                .isEqualTo(401);
    }

    @Test
    public void isLigneEcritureComptableExist() {

        LigneEcritureComptable ligneEcritureComptable =new LigneEcritureComptable.Builder().
                libelle("Cartouche d'imprimante").id(1).debit(BigDecimal.valueOf(43.95)).credit(null)
                .compteComptable(compteComptable).
                        ecritureComptable(ecritureComptable).build();

        assertThat(LigneEcritureComptable.isLigneEcritureComptableExist(ligneEcritureComptable, 1)).isEqualTo(true);
    }


    /*
     * Après chaque test effacement des données
     */
    @After
    public void undefligneEcritureComptable() {

        ligneEcritureComptableList.clear();
    }
}
