package com.ddpm.project.monkeyislandclientmock2;

public class MockCommunication {

    private Communication communication;
    private Terminal terminal;

    public MockCommunication() {
        this.communication = new Communication();
        this.terminal = new Terminal();
        terminal.setVisible(true);
        terminal.addObservateur(this.communication);
    }

    public static void main(String args[]){
        System.out.println("Bonjour client 2");
        new MockCommunication();
    }
}
