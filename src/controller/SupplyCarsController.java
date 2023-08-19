package controller;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.RentTransaction;
import model.SupplyCar;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class SupplyCarsController implements Initializable {


    public DatePicker txtSupplyDate;
    public TextField txtSupplyId;
    public ComboBox comboCarId;
    public ComboBox comboSupplierId;
    public TextField txtPrice;
    public TableView<SupplyCar> tableSupplyCars;
    public TableColumn<SupplyCar, Integer> colSupplyId;
    public TableColumn<SupplyCar, Integer> colCarId;
    public TableColumn<SupplyCar, Integer> colSupplierId;
    public TableColumn<SupplyCar, Date> colSupplyDate;
    public TableColumn<SupplyCar, Double> colPrice;
    public TextField filterField;
    ObservableList<SupplyCar> listM;
    ObservableList<SupplyCar> dataList;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @FXML
    void addSupply(ActionEvent event) {
        conn = Database.ConnectDb();
        String sql = "insert into supply_car (rc_CarId,s_id,supply_date,price)values(?,?,?,? )";

        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.valueOf(comboCarId.getValue().toString()));
            pst.setInt(2, Integer.valueOf(comboSupplierId.getValue().toString()));
            pst.setString(3, txtSupplyDate.getValue().toString());
            pst.setDouble(4, Double.parseDouble(txtPrice.getText()));
            pst.execute();

            JOptionPane.showMessageDialog(null, "Supply Add success");
            updateTable();
            searchTransaction();
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


    @FXML
    void deleteSupply(ActionEvent event) {

        conn = Database.ConnectDb();
        String sql = "delete from supply_car where supply_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.valueOf(txtSupplyId.getText()));
            pst.execute();

            JOptionPane.showMessageDialog(null, "Supply Deleted");
            updateTable();
            searchTransaction();
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void editSupply(ActionEvent event) {
        try {
            conn = Database.ConnectDb();

            String sql = "update supply_car set rc_CarId= ?,s_id= ?,supply_date= ?,price= ? where supply_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.valueOf(comboCarId.getValue().toString()));
            pst.setInt(2, Integer.valueOf(comboSupplierId.getValue().toString()));
            pst.setString(3, String.valueOf(txtSupplyDate.getValue()));
            pst.setDouble(4, Double.parseDouble(txtPrice.getText()));
            pst.setInt(5, Integer.valueOf(txtSupplyId.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supply Updated");
            updateTable();
            searchTransaction();
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    public void getSelected(MouseEvent event) {

        index = tableSupplyCars.getSelectionModel().getSelectedIndex();
        if (index <= -1) {

            return;
        }
        txtSupplyId.setText(colSupplyId.getCellData(index).toString());
        comboCarId.setValue(colCarId.getCellData(index).toString());
        comboSupplierId.setValue(colSupplierId.getCellData(index).toString());
        txtSupplyDate.setValue(LocalDate.parse(colSupplyDate.getCellData(index).toString()));
        txtPrice.setText(colPrice.getCellData(index).toString());
    }


    public void updateTable() {

        colSupplyId.setCellValueFactory(new PropertyValueFactory<SupplyCar, Integer>("supplyId"));
        colCarId.setCellValueFactory(new PropertyValueFactory<SupplyCar, Integer>("carId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<SupplyCar, Integer>("supplierId"));
        colSupplyDate.setCellValueFactory(new PropertyValueFactory<SupplyCar, Date>("supplyDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<SupplyCar, Double>("price"));
        listM = Database.getSupplyCars();
        tableSupplyCars.setItems(listM);
    }

    public void clear() {
        txtSupplyId.setText("");
        txtPrice.setText("");
        txtSupplyDate.setValue(null);
        comboSupplierId.setValue("Customer Id");
        comboCarId.setValue("Car Id");
    }


    public void searchTransaction() {
        colSupplyId.setCellValueFactory(new PropertyValueFactory<SupplyCar, Integer>("supplyId"));
        colCarId.setCellValueFactory(new PropertyValueFactory<SupplyCar, Integer>("carId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<SupplyCar, Integer>("supplierId"));
        colSupplyDate.setCellValueFactory(new PropertyValueFactory<SupplyCar, Date>("supplyDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<SupplyCar, Double>("price"));

        dataList = Database.getSupplyCars();


        tableSupplyCars.setItems(dataList);
        FilteredList<SupplyCar> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(supplyCar -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(supplyCar.getCarId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(supplyCar.getSupplierId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(supplyCar.getSupplyId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (supplyCar.getSupplyDate().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(supplyCar.getPrice()).indexOf(lowerCaseFilter) != -1)
                    return true;

                else
                    return false; // Does not match.
            });
        });
        SortedList<SupplyCar> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableSupplyCars.comparatorProperty());
        tableSupplyCars.setItems(sortedData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> carIdList = Database.getCarList();
        comboCarId.setItems(carIdList);
        ObservableList<String> supplierIdList = Database.getSupplierList();
        comboSupplierId.setItems(supplierIdList);

        updateTable();
        searchTransaction();
    }


}
