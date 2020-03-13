package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LigneEcritureComptableTest {


    @Test
    public void getByIdEcritureAndLigneId() {

        List<LigneEcritureComptable> ligneEcritureComptableList = new ArrayList<LigneEcritureComptable>();
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();


        JournalComptable journal = new JournalComptable("AC", "Achat");
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(-1);
        ecritureComptable.setJournalComptable(journal);
        ecritureComptable.setReference("AC-2019/00001");
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Cartouches d'imprimante");

        // EcritureComptable ecritureComptable;
        ligneEcritureComptable.setId(1);
        ligneEcritureComptable.setCompteComptable(new CompteComptable(401, "Fournisseurs"));
        ligneEcritureComptable.setLibelle("Cartouches d'imprimante");
        ligneEcritureComptable.setDebit(new BigDecimal("43.95"));
        ligneEcritureComptable.setCredit(null);
        ligneEcritureComptable.setEcritureComptable(ecritureComptable);
        ligneEcritureComptableList.add(ligneEcritureComptable);

        Assert.assertNotNull(LigneEcritureComptable.getById(ligneEcritureComptableList, -1));
        Assert.assertEquals(ligneEcritureComptable.getId(), LigneEcritureComptable.getById(ligneEcritureComptableList, -1).getId());
        Assert.assertNull(LigneEcritureComptable.getById(ligneEcritureComptableList, -10));
    }

    @Test
    public void isLigneEcritureComptableExist() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(-1);
        ecritureComptable.setJournalComptable(new JournalComptable("AC", "Achat"));
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Cartouches d'imprimante");

        ecritureComptable.setReference("AC-2019/00001");

        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
                1, new CompteComptable(606, "Achats non stockés de matières et fournitures"),
                "Cartouches d'imprimante",
                new BigDecimal("43.95"),null);

        ligneEcritureComptable.setEcritureComptable(ecritureComptable);

        Assert.assertTrue(LigneEcritureComptable.isLigneEcritureComptableExist(ligneEcritureComptable, -1));
        Assert.assertFalse(LigneEcritureComptable.isLigneEcritureComptableExist(ligneEcritureComptable, -10));
        Assert.assertFalse(LigneEcritureComptable.isLigneEcritureComptableExist(null, -1));
        Assert.assertFalse(LigneEcritureComptable.isLigneEcritureComptableExist(null, -10));

    }
}
