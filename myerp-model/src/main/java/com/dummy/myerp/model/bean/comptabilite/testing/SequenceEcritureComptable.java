package com.dummy.myerp.model.bean.comptabilite.testing;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor


public class SequenceEcritureComptable{

    // ==================== Attributs ====================
    /** Journal comptable */
    @NotNull
    JournalComptable journalComptable;


    /** L'année */
    @NotNull
    Integer annee;

    @NotNull
    /** La dernière valeur utilisée */
    Integer derniereValeur;





    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link SequenceEcritureComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link SequenceEcritureComptable}
     * @param pCode le code du {@link SequenceEcritureComptable} à chercher
     * @param  pYear l'année du {@link SequenceEcritureComptable} à chercher
     * @return {@link SequenceEcritureComptable} ou {@code null}
     */
    public static SequenceEcritureComptable getByCodeAndYear(List<? extends SequenceEcritureComptable> pList, String pCode, Integer pYear) {
        SequenceEcritureComptable vRetour = null;
        for (SequenceEcritureComptable vBean : pList) {
            if (isSequenceEcritureComptableExist( vBean,  pCode, pYear )) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }


    public static boolean isSequenceEcritureComptableExist(SequenceEcritureComptable vBean, String pCode, Integer pYear){
        return (vBean != null && Objects.equals(vBean.getJournalComptable().getCode(), pCode) && Objects.equals(vBean.getAnnee(), pYear));
    }



}
