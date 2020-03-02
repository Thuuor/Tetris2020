/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.closebutton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author 10229590
 */
public class CloseButton extends JButton implements ActionListener {

    public CloseButton(){
        super();
        addActionListener(this);
    }
    
    public CloseButton(String title){
        super.setText(title);
        addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
       System.exit(0);
    }
    
}
