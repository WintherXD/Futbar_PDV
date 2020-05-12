/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Builders.Client;
import View.CustomSwing.CustomLabel;
import FutbarPDV.Listener;
import FutbarPDV.Util;
import View.CustomSwing.CustomTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author davi
 */
public class Clients { 
    
    private final Listener listener;
    private final Util util;
    
    public final JFrame frame;
    private final JPanel panel;
    
    public DefaultTableModel listClients;
    public JTable listClientsTable;
    public JTextField searchField;
    
    public List<Client> clients = new ArrayList<>();
    
    /*
        Mode: 0 -> Menu principal
              1 -> Marcar
    */
    public int mode;
    
    public Clients(Listener listener, int mode) {
        frame = new JFrame();
        panel = new JPanel();
        util = new Util();
        this.listener = listener;
        this.mode = mode;
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension((int) (screenSize.getWidth()*0.3), (int) (screenSize.getHeight()*0.6)));
        addComponents();
    }
    
    private void addComponents() {
        JLabel info = new CustomLabel("Nome/Código:", 24, true);
        searchField = new CustomTextField(40);
        
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5, 15, 5, 15);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 1;
        cons.weighty = 0.2;
        panel.add(info, cons);
        cons.gridx = 0;
        cons.gridy = 1;
        cons.weightx = 1;
        cons.weighty = 0.2;
        panel.add(searchField, cons);
        cons.gridx = 0;
        cons.gridy = 2;
        cons.weightx = 1;
        cons.weighty = 0.6;
        panel.add(listItemsPanel(), cons);

        searchField.addKeyListener(listener);
    }
    
    private JPanel listItemsPanel() {
        GridBagConstraints cons = new GridBagConstraints();
        Font fontHeader = new Font("Tahoma", Font.BOLD, 28);
        JPanel listPanel = new JPanel(new GridBagLayout());

        cons.fill = GridBagConstraints.BOTH;
        cons.weightx = 1;
        cons.weighty = 1;
        
        listClients = new DefaultTableModel();
        listClients.addColumn("Nome:");
        listClients.addColumn("Deve:");
        util.getClients().forEach((client) -> {
            clients.add(client);
            
            //double balance = client.getBalance() - util.getClientTotalMark(client);
            listClients.addRow(new Object[]{client.getName(), "R$ " + String.format("%.2f", util.getClientTotalMark(client))});
        });
        
        listClientsTable = new JTable(listClients);
        listClientsTable.setFont(fontHeader);
        listClientsTable.setRowHeight(60);
        listClientsTable.setDefaultEditor(Object.class, null);
        listClientsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        listClientsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        listClientsTable.addMouseListener(listener);
        
        JScrollPane jscroll = new JScrollPane(listClientsTable);
        listPanel.add(jscroll, cons);

        return listPanel;
    }
    
    
    private JPanel addButton(String text, int textSize, String color) {
        JPanel btn = new JPanel(new GridBagLayout());
        JLabel btnName = new CustomLabel(text, textSize, true);

        btn.setBackground(Color.decode(color));
        btn.add(btnName);
        
        return btn;
    }
    
    private void prepareFrame() {
        frame.setUndecorated(true);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void clearClientList() {
        for(int i = listClients.getRowCount() - 1; i >= 0; i--) {
            listClients.removeRow(i);
        }
        clients.clear();
    }
    
    public void updateClients(List<Client> newclients) {
        clearClientList();
        for(Client client : newclients) {
            clients.add(client);
            listClients.addRow(new Object[]{client.getName(), "R$ " + String.format("%.2f", client.getBalance())});
        }
    }
    
    public Client getClient(int id) {
        return clients.get(id);
    }
}
