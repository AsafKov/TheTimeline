package com.asafk.thetimeline.Model;
import com.google.firebase.database.Exclude;

public class User {

    private String id;
    private long birthday;
    private String country;
    private int gender, orientation, faith;

    public User() { }

    public User(String id, long birthday, String country, Gender gender, Orientation orientation, Faith faith) {
        this.id = id;
        this.birthday = birthday;
        this.country = country;
        this.gender = gender.value;
        this.orientation = orientation.value;
        this.faith = faith.value;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getFaith() {
        return faith;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public void setGender(Gender gender){
        this.gender = gender.value;
    }

    @Exclude
    public void setOrientation(Orientation orientation){
        this.orientation = orientation.value;
    }

    @Exclude
    public void setFaith(Faith faith){
        this.faith = faith.value;
    }

    public enum Gender{
        MALE(11),
        FEMALE(12),
        NONBINARY(13);

        public final int value;
        public static Gender fromInt(int value) {
            for (Gender gender : Gender.values()) {
                if (gender.value == value) {
                    return gender;
                }
            }
            return null;
        }
        public static Gender fromString(String value){
            for (Gender gender : Gender.values()) {
                if (gender.name().equalsIgnoreCase((value))) {
                    return gender;
                }
            }
            return null;
        }
        Gender(int value){
            this.value = value;
        }
    }

    public enum Orientation{
        HETEROSEXUAL(201),
        HOMOSEXUAL(202),
        BISEXUAL(203),
        ASEXUAL(204);

        public final int value;
        public static Orientation fromInt(int value) {
            for (Orientation orientation : Orientation.values()) {
                if (orientation.value == value) {
                    return orientation;
                }
            }
            return null;
        }
        public static Orientation fromString(String value){
            for (Orientation orientation : Orientation.values()) {
                if (orientation.name().equalsIgnoreCase((value))) {
                    return orientation;
                }
            }
            return null;
        }
        Orientation(int value){
            this.value = value;
        }
    }

    public enum Faith{
        AGNOSTIC(301),
        RELIGIOUS(302),
        ATHEIST(303);

        public final int value;
        public static Faith fromInt(int value) {
            for (Faith faith : Faith.values()) {
                if (faith.value == value) {
                    return faith;
                }
            }
            return null;
        }
        public static Faith fromString(String value){
            for (Faith faith : Faith.values()) {
                if (faith.name().equalsIgnoreCase((value))) {
                    return faith;
                }
            }
            return null;
        }
        Faith(int value){
            this.value = value;
        }
    }


}
