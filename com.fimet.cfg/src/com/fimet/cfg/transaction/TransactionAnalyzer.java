package com.fimet.cfg.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fimet.core.ISO8583.parser.Message;

public class TransactionAnalyzer implements ITransactionAnalyzer {
	public static byte MASK_ID = 0x7F;//01111111
	public static byte MASK_PRESENCE = -0x80;//10000000
	
	private String mti;
	private String transactionType;
	private byte[] bitmapRules;
	private String name;
	private String[] primaryKey;
	private Map<String, Character> tokens;
	//private ParserFormula rule;
	private List<ITransactionAnalyzer> children;
	public TransactionAnalyzer(com.fimet.core.entity.sqlite.TransactionType entity) {
		super();
		if (entity == null) {
			throw new NullPointerException(this+" transaction entity is null");
		}
		/*if (entity.getBitmap() == null) {
			throw new NullPointerException(this+" transaction bitmap is null");
		}*/
		/*if (entity.getTokens() == null) {
			throw new NullPointerException(this+" transaction tokens is null");
		}*/
		if (entity.getMti() == null) {
			throw new NullPointerException(this+" transaction mti is null");
		}
		/*if (entity.getTransactionType() == null) {
			throw new NullPointerException(this+" transaction type is null");
		}*/
		/*if (entity.getRule() == null) {
			throw new NullPointerException(this+" rule is null");
		}*/
		bitmapRules = entity.getBitmap();
		name = entity.getName();
		tokens = entity.getTokens();
		mti = entity.getMti();
		transactionType = entity.getTransactionType();
		primaryKey = entity.getPrimaryKey();
//		rule = entity.getRule() != null ? new ParserFormula().parse(entity.getRule()) : null;
	}
	public boolean matchesBitmap(Message message) {
		if (bitmapRules == null) {
			return true;
		} else {
			BimapIterator bi = new BimapIterator(message.getBitmap());
			int id = 0;
			int idRule = 0;
			for (byte rule : bitmapRules) {
				if (isMandatory(rule)) {// if is mandatory must be in the message
					if (!bi.hasNext()) {
						return false;
					}
					id = bi.next();
					idRule = getId(rule);
					if (id < idRule) {
						while (bi.hasNext() && id < idRule) id = bi.next();
					}
					if (id != idRule) {
						// Is mandatory and not exist in message
						return false;
					}
				} else if (isOptional(rule)) {
					// Do nothing
				}
			}
			return true;
		}
	}
	public boolean matchesTokens(Message message) {
		if (tokens == null) {
			return true;
		} else {
			if (message.hasField("63")) {
				if (message.hasChildren("63")) {
					for (Map.Entry<String, Character> e : tokens.entrySet()) {
						if (e.getValue() == 'M' && !message.hasField("63."+e.getKey())) {
							return false;
						}
					}
				}
				return true;
			}
			return false;
		}
	}
	public boolean matchesRule(Message message) {
		/*if ( rule == null) {
			return true;
		} else {
			rule.setMsg(message);
			Expression exp = rule.eval();
			if (exp instanceof TypeBoolean) {
				return ((TypeBoolean)exp).getValue();
			} else {// The expression must be resolve as a boolean type
				return false;
			}
		}*/
		return false;
	}
	public boolean matchesType(Message message) {
		return transactionType == null ? true : transactionType != null && message.hasField(3) && transactionType.equals(message.getValue("3.1")); 
	}
	public boolean matches(Message message) {
		return 	(mti == null || mti.equals(message.getMti())) && 
				matchesBitmap(message) && 
				matchesType(message) && 
				matchesTokens(message) &&
				matchesRule(message);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Transaction Analyzer "+name;
	}
	private int getId(byte b) {
		return (b & MASK_ID)+1;
	}
	private boolean isMandatory(byte b) {
		return b > (byte)0;
	}
	private boolean isOptional(byte b) {
		return b < (byte)0;
	}
	private class BimapIterator {
		int[] bitmap;
		int index;
		int length;
		BimapIterator(int[] bitmap){
			this.bitmap = bitmap;
			length = bitmap.length;
			index = 0;
		}
		boolean hasNext() {
			return index < length;
		}
		int next() {
			return bitmap[index++];
		}
	}
	public boolean hasChildren() {
		return children != null;
	}
	public void addChild(TransactionAnalyzer child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}
	public List<ITransactionAnalyzer> getChildren(){
		return children;
	}
	@Override
	public ITransactionAnalyzer analyze(Message message) {
		if (matches(message)) {
			if (children != null) {
				ITransactionAnalyzer match;
				for (ITransactionAnalyzer child : children) {
					match = child.analyze(message);
					if (match != null) {
						return match;
					}
				}
				return this;
			} else {
				return this;
			}
		} else {
			return null;
		}
	}
	@Override
	public String[] getPrimaryKey() {
		return primaryKey;
	}
}
