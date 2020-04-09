package com.dummy.myerp.model.bean.comptabilite.testing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CompteComptableTest {

    private List<CompteComptable> comptesComptable;
    private CompteComptable compteComptable;


    /*
     * Création d'une liste de compte comptable comptesComptable
     */

    @Before
    public void initCompteComptable() {
        comptesComptable = new ArrayList<CompteComptable>();
        for (int i = 1; i < 3; i++ ) {
        this.compteComptable= Mockito.mock(CompteComptable.class);

        Mockito.when(compteComptable.getNumero()).thenReturn(400+i);
        Mockito.when(compteComptable.getLibelle()).thenReturn("Fournisseurs n° "+i);
        comptesComptable.add(compteComptable);}
    }


    /*
     * Test si le numéro du  compte est présent dans la liste
     */
    @Test
    public void getByNumero_whenCompteComptableExist(){
        assertThat(CompteComptable.getByNumero(comptesComptable,401).getNumero()).isEqualTo(401);
        assertThat(CompteComptable.getByNumero(comptesComptable,402).getNumero()).isEqualTo(402);
    }

    /*
     * Test si le numéro du  compte n'est pas présent dans la liste
     */
    @Test
    public void getByNumero_whenCompteComptableNotExist(){
        assertThat(CompteComptable.getByNumero(comptesComptable,402)).isNotNull();
        assertThat(CompteComptable.getByNumero(comptesComptable,404)).isNull();
    }

    /*
     * Test si le libelle du  compte est présent dans la liste
     */
    @Test
    public void getByLibelle_whenCompteComptableExist(){
        assertThat(CompteComptable.getByLibelle(comptesComptable, "Fournisseurs n° 1").getLibelle()).isEqualTo("Fournisseurs n° 1");
        assertThat(CompteComptable.getByLibelle(comptesComptable, "Fournisseurs n° 2").getLibelle()).isEqualTo("Fournisseurs n° 2");
    }

    /*
     * Test si le libelle du  compte n'est  pas présent dans la liste
     */
    @Test
    public void  getByLibelle_whenCompteComptableNotExist(){
        assertThat(CompteComptable.getByLibelle(comptesComptable,"Fournisseurs n° 2")).isNotNull();
        assertThat(CompteComptable.getByLibelle(comptesComptable,"Fournisseurs n° 4")).isNull();
    }



    @After
    public void undefCompteComptable() {

        comptesComptable.clear();
    }


}
