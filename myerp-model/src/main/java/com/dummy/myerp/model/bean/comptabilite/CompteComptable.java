package com.dummy.myerp.model.bean.comptabilite;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Bean représentant un Compte Comptable
 *
 *
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompteComptable {
    // ==================== Attributs ====================
    /** The Numero. */

    @NotNull
    private Integer numero;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    @Valid
    private final List<LigneEcritureComptable> ligneEcritureComptableList = new ArrayList<>();



    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link CompteComptable} de numéro {@code pNumero} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link CompteComptable}
     * @param pNumero le numero du {@link CompteComptable} à chercher
     * @return {@link CompteComptable} ou {@code null}
     */
    public static CompteComptable getByNumero(List<? extends CompteComptable> pList, Integer pNumero) {
        CompteComptable vRetour = null;
        for (CompteComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getNumero(), pNumero)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
   static boolean isCompteComptableExist(CompteComptable vBean, Integer pNumero){

        return (vBean != null && Objects.equals(vBean.getNumero(), pNumero) );
    }
}
