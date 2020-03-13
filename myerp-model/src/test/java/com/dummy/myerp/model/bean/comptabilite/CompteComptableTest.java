package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {



    @Test
    public void getByNumero(){
        List<CompteComptable> compteComptableList = new  ArrayList<CompteComptable>();
        compteComptableList.add(  new CompteComptable(401,"Fournisseur") );
        Assert.assertNotNull( CompteComptable.getByNumero(compteComptableList,401));
        Assert.assertNull( CompteComptable.getByNumero(compteComptableList,402));
    }

    @Test
    public void isCompteComptableExist(){
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero( 401);
        compteComptable.setLibelle("Fournisseur");
        Assert.assertTrue( CompteComptable.isCompteComptableExist( compteComptable,401));
        Assert.assertFalse( CompteComptable.isCompteComptableExist( compteComptable,402));
        Assert.assertFalse( CompteComptable.isCompteComptableExist( null,401));
    }
}
