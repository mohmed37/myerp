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
@Getter
@Setter
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

    public SequenceEcritureComptable(Builder builder) {
        this.journalComptable=builder.journalComptable;
        this.annee=builder.annee;
        this.derniereValeur=builder.derniereValeur;
    }


    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link SequenceEcritureComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link SequenceEcritureComptable}
     * @param pCode le code du {@link SequenceEcritureComptable} à chercher
     * @param  pYear l'année du {@link SequenceEcritureComptable} à chercher
     * @return {@link SequenceEcritureComptable} ou {@code null}
     */
    static SequenceEcritureComptable getByCodeAndYear(List<? extends SequenceEcritureComptable> pList, String pCode, Integer pYear) {
        SequenceEcritureComptable vRetour = null;
        for (SequenceEcritureComptable vBean : pList) {
            if (isSequenceEcritureComptableExist( vBean,  pCode, pYear )) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }


    static boolean isSequenceEcritureComptableExist(SequenceEcritureComptable vBean, String pCode, Integer pYear){
        return (vBean != null && Objects.equals(vBean.getJournalComptable().getCode(), pCode) && Objects.equals(vBean.getAnnee(), pYear));
    }

    public static class Builder{

        private JournalComptable journalComptable;
        private Integer annee;
        private  Integer derniereValeur;

        public SequenceEcritureComptable.Builder JournalComptable(JournalComptable journalComptable){
            this.journalComptable= journalComptable;
            return this;
        }
        public SequenceEcritureComptable.Builder annee(Integer annee){
            this.annee=annee;
            return this;
        }
        public SequenceEcritureComptable.Builder derniereValeur(Integer derniereValeur){
            this.derniereValeur=derniereValeur;
            return this;
        }

        public SequenceEcritureComptable build(){
            return new SequenceEcritureComptable(this);
        }


    }


}
