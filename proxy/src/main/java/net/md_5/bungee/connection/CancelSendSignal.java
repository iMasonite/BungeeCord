
package net.md_5.bungee.connection;

@SuppressWarnings("serial")
public class CancelSendSignal extends Error {
	
	@Override
	public Throwable initCause(Throwable cause) {
		return this;
	}
	
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
