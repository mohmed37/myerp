package com.dummy.myerp.model.bean.comptabilite.testing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Size;

import com.dummy.myerp.model.validation.constraint.MontantComptable;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * Bean représentant une Ligne d'écriture comptable.
 *
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LigneEcritureComptable {

    // ==================== Attributs ====================


    Integer id;

    /** The Libelle. */
    @Size(max = 200)
     String libelle;

    /** The Debit. */
    @MontantComptable
   BigDecimal debit;

    /** The Credit. */
    @MontantComptable
    BigDecimal credit;

    /** Ecriture Comptable */

    EcritureComptable ecritureComptable;

    /** Compte Comptable */
    CompteComptable compteComptable;


    /**
     * Instantiates a new Ligne ecriture comptable.
     *
     * @param pCompteComptable the Compte Comptable
     * @param pLibelle the libelle
     * @param pDebit the debit
     * @param pCredit the credit
     */
    public LigneEcritureComptable(Integer pId, CompteComptable pCompteComptable, String pLibelle,
                                  BigDecimal pDebit, BigDecimal pCredit) {
        id = pId;
        compteComptable = pCompteComptable;
        libelle = pLibelle;
        debit = pDebit;
        credit = pCredit;
    }

    private LigneEcritureComptable(Builder builder) {
        this.id=builder.id;
        this.libelle=builder.libelle;
        this.credit=builder.credit;
        this.debit=builder.credit;
        this.compteComptable=builder.compteComptable;
        this.ecritureComptable=builder.ecritureComptable;


    }
    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link EcritureComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link LigneEcritureComptable}
     * @param pId le id de l'écriture du {@link LigneEcritureComptable} à chercher
     * @return {@link LigneEcritureComptable} ou {@code null}
     */
    static LigneEcritureComptable getById(List<? extends LigneEcritureComptable> pList, Integer pId) {
        LigneEcritureComptable vRetour = null;
        for (LigneEcritureComptable vBean : pList) {
            if (isLigneEcritureComptableExist( vBean,  pId )) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

    static boolean isLigneEcritureComptableExist(LigneEcritureComptable vBean, Integer pId){

        return (vBean != null && Objects.equals(vBean.getEcritureComptable().getId(), pId) );
    }

    public static class Builder{
        Integer id;
        String libelle;
        BigDecimal debit;
        BigDecimal credit;
        EcritureComptable ecritureComptable;
        CompteComptable compteComptable;

        public LigneEcritureComptable.Builder ecritureComptable(EcritureComptable ecritureComptable){
            this.ecritureComptable= ecritureComptable;
            return this;
        }

        public LigneEcritureComptable.Builder compteComptable(CompteComptable compteComptable){
            this.compteComptable=compteComptable;
            return this;
        }

        public LigneEcritureComptable.Builder id(Integer id){
            this.id= id;
            return this;
        }
        public LigneEcritureComptable.Builder libelle(String libelle){
            this.libelle=libelle;
            return this;
        }
        public LigneEcritureComptable.Builder debit(BigDecimal debit){
            this.debit=debit;
            return this;
        }
        public LigneEcritureComptable.Builder credit(BigDecimal credit){
            this.credit=credit;
            return this;
        }

        public LigneEcritureComptable build(){
            return new LigneEcritureComptable(this);
        }

    }



}
