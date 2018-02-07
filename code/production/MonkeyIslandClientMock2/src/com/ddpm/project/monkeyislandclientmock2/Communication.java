package com.ddpm.project.monkeyislandclientmock2;

import java.io.IOException;

public class Communication extends Thread implements Observateur{

	/**
	 * The postmanClient who send the messages for the client
	 */
	public PostmanClient postmanClient;

	private ReadThread readThread;

	/**
	 * Builder of the communication
	 */
	public Communication()
	{
		//The postmanClient
		this.postmanClient =new PostmanClient(this);
		this.readThread = new ReadThread();
	}

	public void launchReadThread(){
		this.readThread.start();
	}

	@Override
	public void sendText(String text) {
		this.postmanClient.writeMessage(text);
	}

	/**
	 * Process which ask to the postmanClient to read a message
	 * @return the received message
     */
	public String readComMessage() throws IOException{
		if (this.getPostmanClient().getStateSocket() == PostmanClient.CONNECTED){
			return postmanClient.readMessage();
		}
		return null;
	}

	/**
	 * Process called to close the socket
	 */
	public void closeSocket() {
		if (this.postmanClient != null){
			this.postmanClient.closeSocket();
		}
	}

	/**
	 * Getter of the postmanClient
	 * @return the postmanClient
     */
	public PostmanClient getPostmanClient(){
		return this.postmanClient;
	}

	/**
	 * Reading thread class called to read a message
	 */
	private class ReadThread extends Thread {

		/**
		 * The received message from the socket
		 */
		String receivedMessage;

		/**
		 * Called to read a message and send this message to the binder man's letter box
		 * @throws IOException
		 * @throws NullPointerException
		 */
		private void manageReading() throws IOException, NullPointerException{
			// loop which read the buffer and block the task when nothing is received
			while ((receivedMessage = readComMessage()) == null) ;

			LogUtils.d("MESSAGE RECU > " + receivedMessage);

			// Send message to the dispatcher to be interpreted
		}

		/**
		 * Process called when a "start" of the thread occur
		 */
		@Override
		public void run() {

			try {
				// infinite loop. If a network problem occur : the infinite loop die
				while (true) {
					// manage the message read
					manageReading();
				}
			} catch (NullPointerException npe) {
				npe.printStackTrace();
				LogUtils.d("Pas de socket...");
			} catch (IOException ioe){
				ioe.printStackTrace();
				LogUtils.d("Socket fermée...");
			}
		}
	}
}