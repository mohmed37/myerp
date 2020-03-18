package com.dummy.myerp.model.bean.comptabilite.testing;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class CompteComptableTest {



    @Test
    public void getByNumero(){
        // Arrange
        List<CompteComptable> compteComptableList = new  ArrayList<CompteComptable>();
        // Act
        compteComptableList.add(  new CompteComptable(401,"Fournisseur") );
        // Assert
        Assert.assertNotNull( CompteComptable.getByNumero(compteComptableList,401));
        Assert.assertNull( CompteComptable.getByNumero(compteComptableList,402));

    }
    @Test
    public void getByLibelle(){
        // Arrange
        List<CompteComptable> compteComptableList = new  ArrayList<CompteComptable>();
        // Act
        compteComptableList.add(  new CompteComptable(401,"Fournisseur") );
        // Assert
        Assert.assertNotNull( CompteComptable.getByLibelle(compteComptableList,"Fournisseur"));
        Assert.assertNull( CompteComptable.getByLibelle(compteComptableList,null));

    }
    @Test
    public void isCompteComptableExist(){
        // Arrange
        CompteComptable compteComptable = new CompteComptable();
        // Act
        compteComptable.setNumero( 401);
        compteComptable.setLibelle("Fournisseur");
        // Assert
        Assert.assertTrue( CompteComptable.isCompteComptableExist( compteComptable,401));
        Assert.assertFalse( CompteComptable.isCompteComptableExist( compteComptable,402));
        Assert.assertFalse( CompteComptable.isCompteComptableExist( null,401));
    }
}
