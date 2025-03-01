package TQS.lab3.ex2;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;
    private String maker;
    private String model;
    private String segment;
    private String motorType;
    
    public Car() {}

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model, String segment, String motorType) {
        this.maker = maker;
        this.model = model;
        this.segment = segment;
        this.motorType = motorType;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getMotorType() {
        return motorType;
    }

    public void setMotorType(String motorType) {
        this.motorType = motorType;
    }
}
