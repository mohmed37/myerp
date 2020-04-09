package com.dummy.myerp.model.bean.comptabilite.testing;


import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Bean représentant un Compte Comptable
 *
 *
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
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

    private CompteComptable(Builder builder) {
        this.numero=builder.numero;
        this.libelle=builder.libelle;
    }



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
    static CompteComptable getByLibelle(List<? extends CompteComptable> pList, String libelle) {
        CompteComptable vRetour = null;
        for (CompteComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getLibelle(), libelle)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

    public static class Builder{
        private Integer numero;
        private String libelle;

        public CompteComptable.Builder numero(Integer numero){
            this.numero= numero;
            return this;
        }
        public CompteComptable.Builder libelle(String libelle){
            this.libelle=libelle;
            return this;
        }
        public CompteComptable build(){
            return new CompteComptable(this);
        }


    }

}
