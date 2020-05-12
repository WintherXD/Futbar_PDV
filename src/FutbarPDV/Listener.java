/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutbarPDV;

import Builders.Client;
import Builders.Product;
import View.Checkout.Card;
import View.Checkout.Checkout;
import View.Checkout.Mark;
import View.Checkout.Money;
import View.ClientInfo;
import View.InsertValue;
import View.Clients;
import View.PDV;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author davi
 */
public class Listener implements MouseListener, AdjustmentListener, KeyListener {
    
    private final PDV pdv;
    private final Util util;
    
    //Menu principal
    private InsertValue insertValue;
    private Clients clients;
    private ClientInfo clientInfo;

    //Pagamento
    private Checkout checkout;
    private Money money;
    private Card card;
    private Mark mark;
    
    public Listener(PDV pdv, Util util) {
        this.pdv = pdv;
        this.util = util;
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Object click = me.getSource();
        
        //items
        pdv.itemsButtons.keySet().forEach((id) -> {
            if (pdv.itemsButtons.get(id) == click) {
                Product prod = util.getProduct(id);
                pdv.addToCart(prod);
            }
        });
        
        //categories
        pdv.categoriesButtons.keySet().forEach((id) -> {
            if (pdv.categoriesButtons.get(id) == click) {
                pdv.selectedCategoryID = id;
                pdv.reload();
            }
        });
        
        //listItems
        if(click == pdv.listItemsTable) {
            JTable table = pdv.listItemsTable;
            if (table.getSelectedRow() != -1) {
                pdv.removeFromCart(table.getSelectedRow());
            }
        }
        
        //menuButtons
        if(click == pdv.menuButtons.get(0)) {
            insertValue = new InsertValue(this, 0);
            return;
        }
        
        if(click == pdv.menuButtons.get(1)) {
            pdv.clearCart();
            return;
        }
        
        if(click == pdv.menuButtons.get(2)) {
            clients = new Clients(this, 0);
            return;
        }
        
        if(click == pdv.menuButtons.get(3)) {
            return;
        }
                
        if(click == pdv.menuButtons.get(4)) {
            if(pdv.totalValue > 0) {
                boolean marcar = true;
                for(Product prod : pdv.cartProducts) {
                    if(prod.getRegId() != 0) {
                        marcar = false;
                        break;
                    }
                }
                checkout = new Checkout(this, pdv.totalValue, marcar);
            }
            return;
        }
        
        //insertValue
        if(insertValue != null) {
            Map<Integer, JPanel> buttons = insertValue.buttons;
            JTextField valueField = insertValue.value;
            String valueText = valueField.getText();

            for(int i=0; i<10; i++) {
                if(click == buttons.get(i)) {
                    valueField.setText(valueText + i);
                    return;
                }
            }
            
            if(click == buttons.get(10)) {
                valueField.setText(valueText + ",");
                return;
            }
            
            if(click == buttons.get(11)) {
                if(valueField.getText().charAt(valueText.length()-1) != ' ') {
                    valueField.setText(valueText.substring(0, valueText.length()-1));
                }
                return;
            }
            
            if(click == buttons.get(12)) {
                if(valueField.getText().charAt(valueText.length()-1) != ' ') {
                    if(insertValue.mode == 0) {
                        try {
                            double value = Double.parseDouble(valueField.getText().substring(3).replaceAll(",", "."));
                            if(value>0) {
                                Product prod = new Product("Valor", value);
                                pdv.addToCart(prod);
                                insertValue.frame.dispose();
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Valor inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                            valueField.setText("R$ ");
                        }
                    } else if(insertValue.mode == 1) {
                        try {
                            double valuePaid = Double.parseDouble(valueField.getText().substring(3).replaceAll(",", "."));
                            if(valuePaid>=pdv.totalValue) {
                                money = new Money(this, pdv.totalValue, valuePaid);
                                insertValue.frame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "O valor inserido é menor do que o total da compra", "Erro", JOptionPane.ERROR_MESSAGE);
                                valueField.setText("R$ ");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Valor inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                            valueField.setText("R$ ");
                        }
                    }
                } else {
                    insertValue.frame.dispose();
                }
            }
        }
        
        //clients
        if(clients != null) {
            if(click == clients.listClientsTable) {
                JTable table = clients.listClientsTable;
                if (table.getSelectedRow() != -1) {
                    Client client = clients.getClient(table.getSelectedRow());
                    if(clients.mode == 0) {
                        clientInfo = new ClientInfo(this, pdv, client);
                    } else if(clients.mode == 1) {
                        mark = new Mark(this, pdv.totalValue, client);
                    }
                    clients.frame.dispose();
                }
            }
        }
        
        //clientInfo
        if(clientInfo != null) {
            if(click == clientInfo.listItemsTable) {
                JTable table = clientInfo.listItemsTable;
                int row = table.getSelectedRow();
                if (row != -1) {
                    List<Product> listProducts = new ArrayList<>();
                    for(Map.Entry<Integer, Product> entry : clientInfo.markProducts.entrySet()) {
                        listProducts.add(entry.getValue());
                    }
                    pdv.addToCart(listProducts.get(row));
                    clientInfo.markProducts.remove(listProducts.get(row).getRegId());
                    clientInfo.listItems.removeRow(row);
                }
                return;
            }
            if(click == clientInfo.buttons.get(0)) {
                for(Map.Entry<Integer, Product> entry : clientInfo.markProducts.entrySet()) {
                    pdv.addToCart(entry.getValue());
                }
                clientInfo.clearProductList();
                return;
            }
        }
        
        //checkout
        if(checkout != null) {
            if(click == checkout.buttons.get(0)) {
                insertValue = new InsertValue(this, 1);
                checkout.frame.dispose();
                return;
            }

            if(click == checkout.buttons.get(1)) {
                card = new Card(this, pdv.totalValue);
                checkout.frame.dispose();
                return;
            }

            if(click == checkout.buttons.get(2)) {
                clients = new Clients(this, 1);
                checkout.frame.dispose();
                return;
                
            }
        }
        
        //money
        if(money != null) {
            if(click == money.buttons.get(0)) {
                Finalize finalize = new Finalize(pdv, util, money.valuePaid);
                money.frame.dispose();
                return;
            }
        }
        
        //card
        if(card != null) {
            if(click == card.buttons.get(0)) {
                Finalize finalize = new Finalize(pdv, util, 0);
                card.frame.dispose();
                return;
            }
        }
        
        //mark
        if(mark != null) {
            if(click == mark.buttons.get(0)) {
                Finalize finalize = new Finalize(pdv, util, mark.getClient());
                mark.frame.dispose();
            }
        }
    }
    
    @Override
    public void adjustmentValueChanged(AdjustmentEvent ae) {
        ae.getAdjustable().setValue(ae.getAdjustable().getMaximum());
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        if(clients != null) {
            if(ke.getSource() == clients.searchField) {
                clients.clearClientList();
                List<Client> searchClients = new ArrayList<>();

                try {
                    int code = Integer.parseInt(clients.searchField.getText());
                    searchClients = util.getClients(code);
                } catch (NumberFormatException nfe) {
                    String name = clients.searchField.getText();
                    searchClients = util.getClients(name);
                }

                for(Client client : searchClients) {
                    clients.clients.add(client);

                    double balance = client.getBalance() - util.getClientTotalMark(client);
                    clients.listClients.addRow(new Object[]{client.getName(), "R$ " + String.format("%.2f", balance)});
                }
            }
        }
        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(insertValue != null) {
                insertValue.frame.dispose();
            }
            if(clients != null) {
                clients.frame.dispose();
            }
            if(clientInfo != null) {
                clientInfo.frame.dispose();
            }
            if(checkout != null) {
                checkout.frame.dispose();
            }
            if(money != null) {
                money.frame.dispose();
            }
            if(card != null) {
                card.frame.dispose();
            }
            if(mark != null) {
                mark.frame.dispose();
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae) {}
    
    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}
    
    @Override
    public void mouseEntered(MouseEvent me) {}
    
    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}
}