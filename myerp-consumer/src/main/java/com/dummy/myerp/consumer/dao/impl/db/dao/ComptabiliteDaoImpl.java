package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.util.List;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.model.bean.comptabilite.testing.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constantes ====================

    private String journalCode="journal_code";
    private String reference="reference";
    private String ecritureId="ecriture_id";
    private String libelle="libelle";
    private String annee="annee";



    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
    }


    // ==================== Méthodes ====================
// ==================== Reinitialisation de la base de donnée ====================
    private String sqlInitData;

    public void setSQLinitData(String pSQLinitData) {
        sqlInitData = pSQLinitData;
    }

    public void initData(){
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vJdbcTemplate.update(sqlInitData, vSqlParams);
    }


    /** SQLgetListCompteComptable */
    private static String sqlGetListCompteComptable=null;
    public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        sqlGetListCompteComptable = pSQLgetListCompteComptable;
    }
    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        return  vJdbcTemplate.query(sqlGetListCompteComptable, vRM);
            }


    /** SQLgetListJournalComptable */
    private static String sqlGetListJournalComptable;
    public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        sqlGetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        return vJdbcTemplate.query(sqlGetListJournalComptable, vRM);
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String sqlGetListEcritureComptable;
    public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        sqlGetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        return vJdbcTemplate.query(sqlGetListEcritureComptable, vRM);
    }


    /** SQLgetEcritureComptable */
    private static String sqlGetEcritureComptable;
    public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        sqlGetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            return null;

        }
        return vBean;
    }



    /** SQLgetEcritureComptableByRef */
    private static String sqlGetEcritureComptableByRef;
    public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        sqlGetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(reference, pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLloadListLigneEcriture */
    private static String sqlLoadListLigneEcriture;
    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        sqlLoadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(sqlLoadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }




    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String sqlInsertEcritureComptable;
    public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        sqlInsertEcritureComptable = pSQLinsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pEcritureComptable.getJournalComptable().getCode());
        vSqlParams.addValue(reference, pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(libelle, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(sqlInsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                                                           Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** SQLinsertListLigneEcritureComptable */
    private static String sqlInsertListLigneEcritureComptable;
    public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        sqlInsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }
    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue("ligne_id", vLigneId);
            vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue(libelle, vLigne.getLibelle());
            vSqlParams.addValue("debit", vLigne.getDebit());

            vSqlParams.addValue("credit", vLigne.getCredit());

            vJdbcTemplate.update(sqlInsertListLigneEcritureComptable, vSqlParams);
        }
        }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String sqlUpdateEcritureComptable;
    public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        sqlUpdateEcritureComptable = pSQLupdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pEcritureComptable.getId());
        vSqlParams.addValue(journalCode, pEcritureComptable.getJournalComptable().getCode());
        vSqlParams.addValue(reference, pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(libelle, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(sqlUpdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String sqlDeleteEcritureComptable;
    public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        sqlDeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        vJdbcTemplate.update(sqlDeleteEcritureComptable, vSqlParams);
    }


    /** SQLdeleteListLigneEcritureComptable */
    private static String sqlDeleteListLigneEcritureComptable;
    public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        sqlDeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureId);
        vJdbcTemplate.update(sqlDeleteListLigneEcritureComptable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - GET ====================

    private static String sqlGetSequenceEcritureComptable;
    public void setSQLgetSequenceEcritureComptable(String pSQLgetSequenceEcritureComptable) {
        sqlGetSequenceEcritureComptable = pSQLgetSequenceEcritureComptable;
    }

    public SequenceEcritureComptable getSequenceEcritureComptable(String pJournalCode, Integer pAnnee){
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pJournalCode);
        vSqlParams.addValue(annee, pAnnee);

        SequenceEcritureComptableRM vRM = new SequenceEcritureComptableRM();
        SequenceEcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetSequenceEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            return null;
        }
        return vBean;
    }

    // ==================== SequenceEcritureComptable - INSERT ====================
    private static String sqlInsertSequenceEcritureComptable;

    public void setSQLinsertSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        sqlInsertSequenceEcritureComptable = pSQLinsertSequenceEcritureComptable;
    }

    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable ){

        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode,sequenceEcritureComptable.getJournalComptable().getCode() );
        vSqlParams.addValue(annee,sequenceEcritureComptable.getAnnee() );
        vSqlParams.addValue("derniere_valeur",sequenceEcritureComptable.getDerniereValeur() );
        vJdbcTemplate.update(sqlInsertSequenceEcritureComptable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - UPDATE ====================
    private static String sqlUpdateSequenceEcritureComtpable;
    public void setSQLupdateSequenceEcritureComtpable(String pSQLupdateSequenceEcritureComtpable) {
        sqlUpdateSequenceEcritureComtpable = pSQLupdateSequenceEcritureComtpable;
    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable){
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode,sequenceEcritureComptable.getJournalComptable().getCode() );
        vSqlParams.addValue(annee,sequenceEcritureComptable.getAnnee() );
        vSqlParams.addValue("derniere_valeur",sequenceEcritureComptable.getDerniereValeur() );
        vJdbcTemplate.update(sqlUpdateSequenceEcritureComtpable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - DELETE ====================
    private static String sqlDeleteSequenceEcritureComptable;

    public void setSQLdeleteSequenceEcritureComptable(String pSQLdeleteSequenceEcritureComptable) {
        sqlDeleteSequenceEcritureComptable = pSQLdeleteSequenceEcritureComptable;
    }

    @Override
    public void deleteSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, sequenceEcritureComptable.getJournalComptable().getCode() );
        vSqlParams.addValue(annee, sequenceEcritureComptable.getAnnee());
        vJdbcTemplate.update(sqlDeleteSequenceEcritureComptable, vSqlParams);
    }


}
