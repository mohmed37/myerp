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
    private static  String SQLinitData;

    public void setSQLinitData(String pSQLinitData) {
        SQLinitData = pSQLinitData;
    }

    public void initData(){
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vJdbcTemplate.update(SQLinitData, vSqlParams);
    }


    /** SQLgetListCompteComptable */
    private static String SqlGetListCompteComptable;
    public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        SqlGetListCompteComptable = pSQLgetListCompteComptable;
    }
    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        return  vJdbcTemplate.query(SqlGetListCompteComptable, vRM);
            }


    /** SQLgetListJournalComptable */
    private static String SqlGetListJournalComptable;
    public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        SqlGetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        return vJdbcTemplate.query(SqlGetListJournalComptable, vRM);
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String SqlGetListEcritureComptable;
    public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        SqlGetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        return vJdbcTemplate.query(SqlGetListEcritureComptable, vRM);
    }


    /** SQLgetEcritureComptable */
    private static String SqlGetEcritureComptable;
    public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        SqlGetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SqlGetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            return null;

        }
        return vBean;
    }



    /** SQLgetEcritureComptableByRef */
    private static String SqlGetEcritureComptableByRef;
    public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        SqlGetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(reference, pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SqlGetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLloadListLigneEcriture */
    private static String SqlLoadListLigneEcriture;
    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        SqlLoadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(SqlLoadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }




    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String SqlInsertEcritureComptable;
    public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        SqlInsertEcritureComptable = pSQLinsertEcritureComptable;
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

        vJdbcTemplate.update(SqlInsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                                                           Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** SQLinsertListLigneEcritureComptable */
    private static String SqlInsertListLigneEcritureComptable;
    public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        SqlInsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
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

            vJdbcTemplate.update(SqlInsertListLigneEcritureComptable, vSqlParams);
        }
        }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String SqlUpdateEcritureComptable;
    public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        SqlUpdateEcritureComptable = pSQLupdateEcritureComptable;
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

        vJdbcTemplate.update(SqlUpdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String SqlDeleteEcritureComptable;
    public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        SqlDeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        vJdbcTemplate.update(SqlDeleteEcritureComptable, vSqlParams);
    }


    /** SQLdeleteListLigneEcritureComptable */
    private static String SqlDeleteListLigneEcritureComptable;
    public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        SqlDeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureId);
        vJdbcTemplate.update(SqlDeleteListLigneEcritureComptable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - GET ====================

    private static String SqlGetSequenceEcritureComptable;
    public void setSQLgetSequenceEcritureComptable(String pSQLgetSequenceEcritureComptable) {
        SqlGetSequenceEcritureComptable = pSQLgetSequenceEcritureComptable;
    }

    public SequenceEcritureComptable getSequenceEcritureComptable(String pJournalCode, Integer pAnnee){
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pJournalCode);
        vSqlParams.addValue(annee, pAnnee);

        SequenceEcritureComptableRM vRM = new SequenceEcritureComptableRM();
        SequenceEcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SqlGetSequenceEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            return null;
        }
        return vBean;
    }

    // ==================== SequenceEcritureComptable - INSERT ====================
    private static String SqlInsertSequenceEcritureComptable;

    public void setSQLinsertSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        SqlInsertSequenceEcritureComptable = pSQLinsertSequenceEcritureComptable;
    }

    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable ){

        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode,sequenceEcritureComptable.getJournalComptable().getCode() );
        vSqlParams.addValue(annee,sequenceEcritureComptable.getAnnee() );
        vSqlParams.addValue("derniere_valeur",sequenceEcritureComptable.getDerniereValeur() );
        vJdbcTemplate.update(SqlInsertSequenceEcritureComptable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - UPDATE ====================
    private static String SqlUpdateSequenceEcritureComtpable;
    public void setSQLupdateSequenceEcritureComtpable(String pSQLupdateSequenceEcritureComtpable) {
        SqlUpdateSequenceEcritureComtpable = pSQLupdateSequenceEcritureComtpable;
    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable){
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode,sequenceEcritureComptable.getJournalComptable().getCode() );
        vSqlParams.addValue(annee,sequenceEcritureComptable.getAnnee() );
        vSqlParams.addValue("derniere_valeur",sequenceEcritureComptable.getDerniereValeur() );
        vJdbcTemplate.update(SqlUpdateSequenceEcritureComtpable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable - DELETE ====================
    private static String SqlDeleteSequenceEcritureComptable;

    public void setSQLdeleteSequenceEcritureComptable(String pSQLdeleteSequenceEcritureComptable) {
        SqlDeleteSequenceEcritureComptable = pSQLdeleteSequenceEcritureComptable;
    }

    @Override
    public void deleteSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, sequenceEcritureComptable.getJournalComptable().getCode() );
        vSqlParams.addValue(annee, sequenceEcritureComptable.getAnnee());
        vJdbcTemplate.update(SqlDeleteSequenceEcritureComptable, vSqlParams);
    }


}
