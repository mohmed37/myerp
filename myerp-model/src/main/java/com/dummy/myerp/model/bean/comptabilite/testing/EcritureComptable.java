package com.dummy.myerp.model.bean.comptabilite.testing;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;



/**
 * Bean représentant une Écriture Comptable
 *
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EcritureComptable{

    // ==================== Attributs ====================
    /** The Id. */
    Integer id;

    /** Journal comptable */
    @NotNull
    JournalComptable journalComptable;

    //Correction sur la regex  en premier instance  il prenait des chiffres au lieu des lettres
    /** The Reference. */
    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}/[0-9]{5}")
    String reference;

    /** The Date. */
    @NotNull
    Date date;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 200)
    String libelle;




    /** La liste des lignes d'écriture comptable. */
    @Valid
    @Size(min = 2)
    private List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();

    private EcritureComptable(Builder builder) {
       this.id=builder.id;
       this.date=builder.date;
       this.libelle=builder.libelle;
       this.reference=builder.reference;
       this.journalComptable=builder.journalComptable;
       this.listLigneEcriture=builder.listLigneEcriture;


    }


    /**
     * Calcul et renvoie le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     */

    BigDecimal getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        return vRetour.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcul et renvoie le total des montants au crédit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au crédit
     */
    //Modification sur le paramètre utilisé car utilisation du débit au lieu du crédit
    BigDecimal getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;

        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getCredit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            }
        }
        return vRetour.setScale(2,RoundingMode.HALF_UP);
    }

    /**
     * Renvoie si l'écriture est équilibrée (TotalDebit = TotalCrédit)
     * @return boolean
     */
    public boolean isEquilibree() {
        return this.getTotalDebit().equals(getTotalCredit());
    }


      // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link EcritureComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link EcritureComptable}
     * @param pId le id de l'écriture du {@link EcritureComptable} à chercher
     * @return {@link EcritureComptable} ou {@code null}
     */
    public static EcritureComptable getById(List<? extends EcritureComptable> pList, Integer pId) {
        EcritureComptable vRetour = null;
        for (EcritureComptable vBean : pList) {
            if (isEcritureComptableExist( vBean,  pId )) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

    public static boolean isEcritureComptableExist(EcritureComptable vBean, Integer pId ){

        return (vBean != null && Objects.equals(vBean.getId(), pId));

    }

    public static class Builder{
        private Integer id;
        private String reference;
        private Date date;
        private String libelle;
        private   JournalComptable journalComptable;
        private List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();

        public EcritureComptable.Builder journalComptable(JournalComptable journalComptable){
            this.journalComptable=journalComptable;
            return this;
        }
        public EcritureComptable.Builder listLigneEcriture(List<LigneEcritureComptable>ligneEcritureComptable){
            this.listLigneEcriture=ligneEcritureComptable;
            return this;
        }

        public EcritureComptable.Builder id(Integer id){
            this.id= id;
            return this;
        }
        public EcritureComptable.Builder libelle(String libelle){
            this.libelle=libelle;
            return this;
        }

        public EcritureComptable.Builder reference(String reference){
            this.reference=reference;
            return this;
        }

        public EcritureComptable.Builder date(Date date){
            this.date=date;
            return this;
        }


        public EcritureComptable build(){
            return new EcritureComptable(this);
        }


    }





}
