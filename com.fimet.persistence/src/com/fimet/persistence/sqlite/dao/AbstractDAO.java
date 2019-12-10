package com.fimet.persistence.sqlite.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.utils.ThreadUtils;
import com.fimet.persistence.Activator;
import com.fimet.persistence.sqlite.PersistenceException;
import com.fimet.persistence.sqlite.SQLiteManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 * @param <E> Entity class
 * @param <T> primary key class
 */
public class AbstractDAO<E, T> {

	private Dao<E,T> dao;
	protected Integer sequence;
	private ConnectionSource connection;
	public AbstractDAO(ConnectionSource connection) {
		this.connection = connection;
	}
	public AbstractDAO() {
		this.connection = SQLiteManager.getInstance().getConnection();
	}	
	@SuppressWarnings("unchecked")
	protected Dao<E,T> getDAO(){
		if (dao == null) {
			Class<E> classE = (Class<E>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			try {
				dao = DaoManager.createDao(connection, classE);
			} catch (SQLException e) {
				Activator.getInstance().error("Cannot create dao for class: " + classE.getName(), e);
			}
		}
		return dao;
	}
	public boolean exists(Integer id) {
		try {
			String nameTable = getClass().getSimpleName().substring(0,getClass().getSimpleName().length()-3);
			Integer result = getDAO().queryRaw("select id from "+nameTable+" where id = "+id, new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : 1;
			       }
			}).getFirstResult();
			return result != null;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public Integer getNextSequenceId() {
		if (sequence != null) {
			return sequence++;
		}
		try {
			String nameTable = getClass().getSimpleName().substring(0,getClass().getSimpleName().length()-3);
			Integer result = getDAO().queryRaw("select max(id)+1 from "+nameTable, new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : 1;
			       }
			}).getFirstResult();
			sequence = result == null ? 0 : result;
			return sequence++;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public Integer getPrevSequenceId() {
		if (sequence != null) {
			return sequence--;
		}
		try {
			String nameTable = getClass().getSimpleName().substring(0,getClass().getSimpleName().length()-3);
			Integer result = getDAO().queryRaw("select max(id) from "+nameTable, new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : 1;
			       }
			}).getFirstResult();
			return sequence = result == null ? 0 : result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public E findLast() {
		try {
			QueryBuilder<E, T> q = AbstractDAO.this.getDAO().queryBuilder();
			return q.orderBy("id", false).queryForFirst();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public E findLast(String idName) {
		try {
			QueryBuilder<E, T> q = AbstractDAO.this.getDAO().queryBuilder();
			return q.orderBy(idName, false).queryForFirst();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public static interface OnSave<E> {
		public void onSave(E entity);
	}
	public static interface OnDelete<E> {
		public void onDelete(E entity);
	}
	public static interface OnQuery<E> {
		public void onQuery(List<E> entities);
	}
	public static interface OnFindEntity<E> {
		public void onFindEntity(E entity);
	}
	public static interface OnResult<O> {
		public void onResult(O object);
	}
	public void insert(E entity) {
		insert(entity,null);
	}
	public void insert(E entity, OnSave<E> listener) {
		//ThreadUtils.runAcync(()->{
			try {
				AbstractDAO.this.getDAO().create(entity);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onSave(entity);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		//});
	}
	public E insertSync(E entity) {
		try {
			AbstractDAO.this.getDAO().create(entity);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return entity;
	}
	public void delete(E entity) {
		delete(entity, null);
	}
	public void delete(E entity , OnDelete<E> listener) {
		ThreadUtils.runAcync(()->{
			try {
				AbstractDAO.this.getDAO().delete(entity);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onDelete(entity);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});
	}
	public E deleteSync(E entity) {
		try {
			AbstractDAO.this.getDAO().delete(entity);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return entity;
	}
	public void update(E entity) {
		update(entity, null);
	}
	public void update(E entity , OnSave<E> listener) {
		ThreadUtils.runAcync(()->{
			try {
				AbstractDAO.this.getDAO().update(entity);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onSave(entity);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});
	}
	public E updateSync(E entity) {
		try {
			AbstractDAO.this.getDAO().update(entity);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return entity;
	}
	public E insertOrUpdate(E entity) {
		try {
			AbstractDAO.this.getDAO().createOrUpdate(entity);
			return entity;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public void insertOrUpdate(E entity , OnSave<E> listener) {
		ThreadUtils.runAcync(()->{
			try {
				AbstractDAO.this.getDAO().createOrUpdate(entity);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onSave(entity);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});
	}
	public void query(PreparedQuery<E> pq,OnQuery<E> listener) {
		ThreadUtils.runAcync(()->{
			try {
				List<E> results = AbstractDAO.this.getDAO().query(pq);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onQuery(results);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});		
	}
	public List<E> querySync(PreparedQuery<E> pq,OnQuery<E> listener) {
		try {
			return AbstractDAO.this.getDAO().query(pq);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	/*public void findById(T id, OnFindEntity<E> listener) {
		ThreadUtils.runAcync(()->{
			try {
				E entity = AbstractDAO.this.getDAO().queryForId(id);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onFindEntity(entity);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});		
	}*/
	public E findById(T id) {
		try {
			return AbstractDAO.this.getDAO().queryForId(id);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public Long countOfSync(PreparedQuery<E> pq) {
		try {
			return getDAO().countOf(pq);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}	
	public void count(OnResult<Long> listener) {
		ThreadUtils.runAcync(()->{
			try {
				long count = getDAO().countOf();
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onResult(count);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});	
	}
	public long count() {
		try {
			return getDAO().countOf();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public void findAll(OnQuery<E> listener, String orderBy, boolean asc) {
		ThreadUtils.runAcync(()->{
			try {
				QueryBuilder<E, T> qb = getDAO().queryBuilder();
				if (orderBy != null) {
					qb.orderBy(orderBy, asc);
				}
				PreparedQuery<E> preparedQuery = qb.prepare();
				List<E> result = getDAO().query(preparedQuery);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onQuery(result);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}	
		});		
	}
	public List<E> findAll(String orderBy, boolean asc) {
		try {
			QueryBuilder<E, T> qb = getDAO().queryBuilder();
			if (orderBy != null) {
				qb.orderBy(orderBy, asc);
			}
			PreparedQuery<E> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public CloseableIterator<E> iteratorAll() {
		try {
			return getDAO().queryBuilder().iterator();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public List<E> findAll() {
		try {
			QueryBuilder<E, T> qb = getDAO().queryBuilder();
			PreparedQuery<E> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
	public void findBy(OnQuery<E> listener, String field, Object value) {
		ThreadUtils.runAcync(()->{
			try {
				QueryBuilder<E, T> qb = getDAO().queryBuilder();
				qb.setWhere(qb.where().eq(field, value));
				PreparedQuery<E> preparedQuery = qb.prepare();
				List<E> result = getDAO().query(preparedQuery);
				if (listener != null) {
					ThreadUtils.runOnMainThread(()->{
						listener.onQuery(result);
					});
				}
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		});		
	}
	public List<E> findBy(String field, Object value) {
		try {
			QueryBuilder<E, T> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq(field, value));
			PreparedQuery<E> preparedQuery = qb.prepare();
			return getDAO().query(preparedQuery);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}	
	}
}
