/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutbarPDV;

import Builders.Client;
import Builders.Categorie;
import Builders.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author davi
 */
public class Util {
        
    private List<Product> products;
    private List<Categorie> categories;
    private Connection con;
            
    public Util() {
        createTables();
    }
    
    private Connection getConnection() {
        try {
            
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.103:3306/futbar", "futbar", "futbar");
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
	
    public final void createTables() {
        try {
            PreparedStatement st = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Categorias` (`ID` INT AUTO_INCREMENT PRIMARY KEY, `nome` VARCHAR(10))");
            st.execute();
            st.close();
            PreparedStatement st2 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Produtos` (`ID` INT AUTO_INCREMENT PRIMARY KEY, `nome` VARCHAR(30), `descricao` VARCHAR(10), `preco` DOUBLE(10, 2), `icone` VARCHAR(30), `categoria` INT, `cozinha` TINYINT(1) NOT NULL DEFAULT '0')");
            st2.execute();
            st2.close();
            PreparedStatement st3 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Clientes` (`ID` INT AUTO_INCREMENT PRIMARY KEY, `nome` VARCHAR(30), `codigo` INT, `saldo` DOUBLE(10, 2) NOT NULL DEFAULT '0')");
            st3.execute();
            st3.close();
            PreparedStatement st4 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Cozinha` (`ID` INT AUTO_INCREMENT PRIMARY KEY, `idCozinha` TINYINT(1), `produto` VARCHAR(30), `data` DATETIME DEFAULT CURRENT_TIMESTAMP, `status` TINYINT(1) NOT NULL DEFAULT '0', `mesa` INT NOT NULL DEFAULT '0', `senha` INT NOT NULL DEFAULT '0')");
            st4.execute();
            st4.close();
            PreparedStatement st5 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Log_Vendas` (`ID` INT AUTO_INCREMENT PRIMARY KEY, `data` DATETIME DEFAULT CURRENT_TIMESTAMP, `produto` VARCHAR(30), `preco` DOUBLE(10,2), `Pagamento` VARCHAR(30), `cliente` VARCHAR(30), `pago` TINYINT(1) NOT NULL DEFAULT '0')");
            st5.execute();
            st5.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Categorie> getCategories() {
        if(categories == null) {
            categories = new ArrayList<>();
            try {
                PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Categorias`");
                ResultSet rs = st.executeQuery();

                while(rs.next()) {
                    categories.add(new Categorie(rs.getString("nome"), rs.getInt("ID")));
                }
                st.close();
                rs.close();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if(categories.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma categoria encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                return null;
            }
        }
        return categories;
    }
    
    public Categorie getCategorie(int id) {
        for(Categorie categorie : getCategories()) {
            if(categorie.getId() == id) {
                return categorie;
            }
        }
        return null;
    }
    
    public List<Product> getProducts() {
        if(products == null) {
            products = new ArrayList<>();
            try {
                PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Produtos`");
                ResultSet rs = st.executeQuery();

                while(rs.next()) {
                    products.add(new Product(rs.getString("nome"), rs.getString("descricao"), rs.getDouble("preco"), rs.getString("icone"), rs.getInt("categoria"), rs.getInt("ID"), rs.getInt("Cozinha")));            
                }
                st.close();
                rs.close();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if(products.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                return null;
            }
        }
        return products;
    }
    
    public Product getProduct(int id) {
        for(Product prod : getProducts()) {
            if(prod.getProdId() == id) {
                return prod;
            }
        }
        return null;
    }
    
    public Product getProduct(String name) {
        for(Product prod : getProducts()) {
            if(prod.getName().equalsIgnoreCase(name)) {
                return prod;
            }
        }
        return null;
    }
    
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Clientes`");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                clients.add(new Client(rs.getInt("ID"), rs.getString("nome"), rs.getInt("codigo"), rs.getDouble("saldo")));            
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return clients;
    }
    
    public List<Client> getClients(String name) {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Clientes` WHERE `nome` LIKE ?");
            st.setString(1, "%" + name + "%");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                clients.add(new Client(rs.getInt("ID"), rs.getString("nome"), rs.getInt("codigo"), rs.getDouble("saldo")));            
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return clients;
    }
    
    public List<Client> getClients(int code) {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Clientes` WHERE `codigo` LIKE ?");
            st.setString(1, "%" + code + "%");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                clients.add(new Client(rs.getInt("ID"), rs.getString("nome"), rs.getInt("codigo"), rs.getDouble("saldo")));            
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return clients;
    }
    
    public Client getClient(int id) {
        for(Client client : getClients()) {
            if(client.getId() == id) {
                return client;
            }
        }
        return null;
    }
    
    public void setClientBalance(Client client, double newbalance) {
        try {
            PreparedStatement st = getConnection().prepareStatement("UPDATE `Clientes` SET `saldo` = ? WHERE `ID` = ?");
            st.setDouble(1, newbalance);
            st.setInt(2, client.getId());
            st.execute();
            st.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public double getClientTotalMark(Client client) {
        double total = 0;
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Log_Vendas` WHERE `cliente` = ? AND `pago` = 0");
            st.setString(1, client.getName());
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                total += rs.getDouble("preco");
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return total;
    }
    
    public Map<Integer, Product> getClientProductsMark(Client client) {
        Map<Integer, Product> products = new LinkedHashMap<>();
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Log_Vendas` WHERE `cliente` = ? AND `pago` = 0 ORDER BY `data` DESC");
            st.setString(1, client.getName());
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int regId = rs.getInt("ID");
                String prodName = rs.getString("produto");
                double prodPrice = rs.getDouble("preco");
                String prodDate = rs.getString("data");
                
                Product prod = new Product(prodName, prodPrice, prodDate, regId, client);
                products.put(regId, prod);
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return products;
    }
    
    public void addItemRegistro(Product prod, String pagamento, String cliente, int pago) {
        try {
            PreparedStatement st = getConnection().prepareStatement("INSERT INTO `Log_Vendas` (`produto`, `preco`, `cliente`, `pagamento`, `pago`) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, prod.getName());
            st.setDouble(2, prod.getPrice());
            st.setString(3, pagamento);
            st.setString(4, cliente);
            st.setInt(5, pago);
            st.execute();
            st.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setItemPaid(int regId, String payment) {
        try {
           PreparedStatement st = getConnection().prepareStatement("UPDATE `Log_Vendas` SET `pago` = 1, `pagamento` = ? WHERE `ID` = ?");
           st.setString(1, payment);
           st.setInt(2, regId);
           st.execute();
           st.close();
           con.close();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    public void addItemCozinha(int idCozinha, String produto, int mesa, int senha) {
        try {
            PreparedStatement st = getConnection().prepareStatement("INSERT INTO `Cozinha` (`idCozinha`, `produto`, `mesa`, `senha`) VALUES (?, ?, ?, ?)");
            st.setInt(1, idCozinha);
            st.setString(2, produto);
            st.setInt(3, mesa);
            st.setInt(4, senha);
            st.execute();
            st.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean cozinhaContainsSenha(int senha) {
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM `Cozinha` WHERE `senha` = ?");
            st.setInt(1, senha);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()) {
                Date dateMysql = new Date(rs.getDate("data").getTime());
                Calendar dataSenha = Calendar.getInstance();
                dataSenha.setTime(dateMysql);
                
                Calendar dataAtual = Calendar.getInstance();

                int dias = (dataAtual.get(Calendar.DAY_OF_MONTH)) - (dataSenha.get(Calendar.DAY_OF_MONTH));
                
                if(dias > 0) {
                    st.close();
                    rs.close();
                    con.close();
                    return true;
                }
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "MySQL Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
