package com.fimet.cfg.transaction;

import java.util.ArrayList;
import java.util.List;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.TransactionType;
import com.fimet.persistence.sqlite.dao.TransactionTypeDAO;

public class TransactionAnalyzerManager implements ITransactionAnalyzerManager {
	private TransactionTypeDAO dao = TransactionTypeDAO.getInstance();
	private List<TransactionAnalyzer> analyzers;
	public TransactionAnalyzerManager() {}
	public TransactionAnalyzerManager initialize(Integer idDialect) {
		List<com.fimet.core.entity.sqlite.TransactionType> entities = dao.findRootsByIdDialect(idDialect);
		analyzers = new ArrayList<>();
		if (entities != null && !entities.isEmpty()) {
			for (com.fimet.core.entity.sqlite.TransactionType entity : entities) {
				analyzers.add(build(entity, idDialect));
			}
		}
		return this;
	}
	private TransactionAnalyzer build(com.fimet.core.entity.sqlite.TransactionType entity, Integer idDialect) {
		TransactionAnalyzer analyzer = new TransactionAnalyzer(entity);
		List<TransactionType> children = dao.findChildrenByIdDialect(entity.getId(), idDialect);
		if (children != null && !children.isEmpty()) {
			for (TransactionType child : children) {
				analyzer.addChild(build(child, idDialect));
			}
		}
		return analyzer;
	}
	public ITransactionAnalyzer analyze(Message message) {
		ITransactionAnalyzer match;
		for (TransactionAnalyzer analyzer : analyzers) {
			match = analyzer.analyze(message);
			if (match != null) {
				return match;
			}
		}
		return null;
	}
}
