package model;

import java.util.Objects;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        if (roomNumber == null || price == null || roomType == null) {
            throw new IllegalArgumentException("Cannot receive null input");
        }
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    public String getRoomNumber()  {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return price;
    }

    public boolean isFree() {
        return price == 0;
    }
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + '\n' +
                "Price: " + price + '\n' +
                "Room Type: " + roomType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Room)) {
            return false;
        }
        Room otherRoom = (Room) obj;
        return roomNumber.equals(otherRoom.roomNumber)
                && Objects.equals(price, otherRoom.price)
                && roomType == otherRoom.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, roomType);
    }
}
