package it.amedeo.utils;

import java.sql.SQLException;

public class FindSQLExceptionWithVendorErrorCode {
	public static String SqlDuplicateKey(Throwable t) {
		  Throwable rootCause = t;
		  String ret = "error";
		  while (rootCause != null) {
		    if (rootCause instanceof SQLException) {
		        SQLException sqlException = (SQLException) rootCause;
		        if (sqlException.getErrorCode() != 0)  {
					if (sqlException.getErrorCode() == 1062) {
						if (sqlException.getSQLState().equals("23000")) {
							return "dupkey";
						}  
					} 
		        }
		    }
		    rootCause = t.getCause();
		  }
		  return ret;
	}
}
