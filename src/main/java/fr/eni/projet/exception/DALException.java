package fr.eni.projet.exception;

public class DALException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DALException() {
	}

	public DALException(String message) {
	}

	@Override
	public String toString() {
		return "***DALException***\n" + super.getMessage();
	}
}
