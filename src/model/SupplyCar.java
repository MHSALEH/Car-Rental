package model;

import java.util.Date;

public class SupplyCar {
    private int supplyId;
    private int carId;
    private int supplierId;
    private Date supplyDate;
    private double price;

    public SupplyCar(int supplyId, int carId, int supplierId, Date supplyDate, double price) {
        this.supplyId = supplyId;
        this.carId = carId;
        this.supplierId = supplierId;
        this.supplyDate = supplyDate;
        this.price = price;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
