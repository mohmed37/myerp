package com.dummy.myerp.model.bean.comptabilite.testing;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Bean représentant un Journal Comptable
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor


public class JournalComptable {

    // ==================== Attributs ====================
    /** code */

    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    /** libelle */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;

    public JournalComptable(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

     SequenceEcritureComptable sequenceEcritureComptable;

    private JournalComptable(Builder builder) {
        this.code=builder.code;
        this.libelle=builder.libelle;

    }


    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link JournalComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link JournalComptable}
     * @param pCode le code du {@link JournalComptable} à chercher
     * @return {@link JournalComptable} ou {@code null}
     */
    public static JournalComptable getByCode(List<? extends JournalComptable> pList, String pCode) {
        JournalComptable vRetour = null;
        for (JournalComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getCode(), pCode)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
    public static class Builder{
        private String code;
        private String libelle;

       public Builder code(String code){
           this.code= code;
           return this;
       }
        public Builder libelle(String libelle){
           this.libelle=libelle;
           return this;
        }
        public JournalComptable build(){
           return new JournalComptable(this);
        }


    }


}
