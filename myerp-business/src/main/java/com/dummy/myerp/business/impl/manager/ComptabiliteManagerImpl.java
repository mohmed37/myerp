package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import com.dummy.myerp.model.bean.comptabilite.testing.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Comptabilite manager implementation.
 *
 *
 */
@Getter
@NoArgsConstructor
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================
    private EcritureComptable ecritureComptable = new EcritureComptable();



    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    @Override
    public synchronized void addReference() {

        Date date = ecritureComptable.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        Integer annee = calendar.get(Calendar.YEAR );

        SequenceEcritureComptable pSeq = getSequenceEcritureComptable( ecritureComptable.getJournalComptable().getCode(),annee);

        if ( pSeq != null){
            pSeq.setDerniereValeur( pSeq.getDerniereValeur() + 1 );
            updateSequenceEcritureComptable( pSeq );
        }else{
            pSeq = new SequenceEcritureComptable(ecritureComptable.getJournalComptable() ,annee,1);
            insertSequenceEcritureComptable( pSeq );
        }

        ecritureComptable.setReference( setReference( pSeq ) );
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */

    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {

        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        this.checkEcritureComptableContext (pEcritureComptable );

        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        this.checkConstraintValid (pEcritureComptable ) ;

        // ===== RG_Compta_2
        this.isEquilibree( pEcritureComptable );

        // ===== RG_Compta_3
        if(!this.isNumberValidEcritureComptable( pEcritureComptable) ){
            throw new FunctionalException("L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }

        // ===== RG_Compta_5
        if( !this.isReferenceValid( pEcritureComptable ) ) {
            throw new FunctionalException("La référence de l'écriture comptable n'est pas conforme.");
        }
    }


    /**
     *
     * @param pEcritureComptable
     * @throws FunctionalException
     */
    private void checkConstraintValid( EcritureComptable pEcritureComptable ) throws FunctionalException {
        Set< ConstraintViolation< EcritureComptable > > vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty() ) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }
    }

    /**
     * RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
     * @param pEcritureComptable
     * @return
     * @throws FunctionalException
     */
    private void isEquilibree( EcritureComptable pEcritureComptable) throws FunctionalException {
        if  (!pEcritureComptable.isEquilibree() ) throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
    }

    /**
     * RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
     *
     * @param pEcritureComptable
     * @return
     */
    public boolean isNumberValidEcritureComptable( EcritureComptable pEcritureComptable ) {

        if( pEcritureComptable.getListLigneEcriture().size() < 2 ) return false;

        int vNbrCredit = 0;
        int vNbrDebit = 0;

        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture() ) {

            if (isAmountExist( vLigneEcritureComptable.getCredit() ) )
                vNbrCredit++;

            if (isAmountExist( vLigneEcritureComptable.getDebit() ))
                vNbrDebit++;
        }

        return ( vNbrCredit > 0 && vNbrDebit > 0 );

    }

    /**
     *
     *
     * @param amount
     * @return
     */
    public boolean isAmountExist(BigDecimal amount){
        return ( BigDecimal.ZERO.compareTo( ObjectUtils.defaultIfNull(amount, BigDecimal.ZERO )  ) != 0 );
    }

    /**
     * RG_Compta_5 : Format et contenu de la référence doit être conforme
     * @param ecritureComptable
     * @return
     */
    public boolean isReferenceValid( EcritureComptable ecritureComptable){
        Date date = ecritureComptable.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        boolean bYear = String.valueOf( calendar.get(Calendar.YEAR ) ).equals(ecritureComptable.getReference().substring(3,7) );

        String pattern="[A-Z]{2}-[0-9]{4}/[0-9]{5}";
        boolean bRegEx = Pattern.matches(pattern, ecritureComptable.getReference() ) ;

        boolean bCodeJournal = ecritureComptable.getJournalComptable().getCode().equals( ecritureComptable.getReference().substring(0,2) );



        return  bYear && bRegEx && bCodeJournal ;

    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable( pEcritureComptable );
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    @Override
    public  SequenceEcritureComptable getSequenceEcritureComptable(String pJournalCode, Integer pAnnee){
        return getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable( pJournalCode,pAnnee );
    }

    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {
      getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(sequenceEcritureComptable);
    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {
        getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable( sequenceEcritureComptable);
    }

    /**
     * @param sequenceEcritureComptable
     * @return
     */
    public String setReference(SequenceEcritureComptable sequenceEcritureComptable){
        String reference = sequenceEcritureComptable.getJournalComptable().getCode() + "-";
        reference += sequenceEcritureComptable.getAnnee()  + "/";
        reference +=String.format("%05d", sequenceEcritureComptable.getDerniereValeur() );
        return  reference;
    }



}
