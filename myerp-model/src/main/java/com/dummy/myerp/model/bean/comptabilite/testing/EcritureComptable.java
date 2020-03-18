package com.dummy.myerp.model.bean.comptabilite.testing;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Data
@NoArgsConstructor
@ToString
public class EcritureComptable{

    // ==================== Attributs ====================
    /** The Id. */
    Integer id;

    /** Journal comptable */
    @NotNull
    JournalComptable journalComptable;

    /** The Reference. */
    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}/[0-9]{5}")
    String reference;

    /** The Date. */
    @NotNull
    Date date;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 200)
    private String libelle;




    /** La liste des lignes d'écriture comptable. */
    @Valid
    @Size(min = 2)
    private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();



    /**
     * Calcul et renvoie le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     */

    public BigDecimal getTotalDebit() {
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
    public BigDecimal getTotalCredit() {
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

    public boolean isAmountNotNull(BigDecimal amount){
        return  (amount != null);
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






}
