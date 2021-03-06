package edu.fau.whatsup.Helpers;

import edu.fau.whatsup.Entities.Location.Address;

import java.util.ArrayList;

public class Validate {



    //region Address Validation
    public static Result Address(Address a){
        ArrayList<Result> results = new ArrayList<Result>();
        results.add(StreetLine1(a.StreetLine1));
        results.add(StreetLine2(a.StreetLine2));
        results.add(City(a.City));
        results.add(State(a.State));
        results.add(PostalCode(a.PostalCode));

        for(Result r : results){
            if(!r.valid) return r;
        }

        Result retVal = new Result();
        retVal.valid = true;
        return retVal;
    }

    public static Result StreetLine1(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if (val.equals("")) {
            retVal.valid = false;
            retVal.message = "No address entered";
        }

        return retVal;
    }

    public static Result StreetLine2(String val){
        Result retVal = new Result();
        retVal.valid = true;

        // can be blank

        return retVal;
    }

    public static Result City(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if (val.equals("")) {
            retVal.valid = false;
            retVal.message = "No city entered";
        }

        return retVal;
    }

    public static Result State(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if (val.equals("")) {
            retVal.valid = false;
            retVal.message = "No state entered";
        }

        return retVal;
    }

    public static Result PostalCode(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if (val.equals("")) {
            retVal.valid = false;
            retVal.message = "No postal code entered";
        }
        if (!val.matches("\\d{5}")) {
            retVal.valid = false;
            retVal.message = "Invalid postal code";
        }

        return retVal;
    }
    //endregion

    //region Event Specific Validation
    public static Result EventTitle(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if (val.isEmpty()) {
            retVal.valid = false;
            retVal.message = "No event title entered";
        }

        else if (val.length() > 30){
            retVal.valid = false;
            retVal.message = "Event titles can only be up to 30 characters!";
        }

        return retVal;
    }

    public static Result EventDescription(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if (val.equals("")) {
            retVal.valid = false;
            retVal.message = "No description entered";
        }

        return retVal;
    }
    //endregion

    public static Result Website(String val){
        Result retVal = new Result();
        retVal.valid = true;

        // I guess this can be blank

        return retVal;
    }

    public static Result Phone(String val){
        Result retVal = new Result();

        if (val.matches("\\d{10}")) retVal.valid = true;

        //validating phone number with -, . or spaces
        else if(val.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) retVal.valid = true;

        //validating phone number with extension length from 3 to 5
        else if(val.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) retVal.valid = true;

        //validating phone number where area code is in braces ()
        else if(val.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) retVal.valid = true;

        else{
            retVal.valid = false;
            retVal.message = "Please enter a valid phone number!";
        }
        return retVal;
    }

    public static Result FirstName(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if(val.length() == 0){
            retVal.valid &= false;
            retVal.message = "You must enter a first name";
        }

        if(val.length() > 15){
            retVal.valid &= false;
            retVal.message = "First names cannot be longer than 15 characters.";
        }


        return retVal;
    }

    public static Result LastName(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if(val.length() == 0){
            retVal.valid &= false;
            retVal.message = "You must enter a last name";
        }

        if(val.length() > 20){
            retVal.valid &= false;
            retVal.message = "Last names cannot be longer than 20 characters.";
        }

        return retVal;
    }

    public static Result Username(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if(!val.matches(".*@.*\\..*")){
            retVal.valid &= false;
            retVal.message = "Invalid email address entered!";
        }

        return retVal;
    }

    public static Result Password(String val){
        Result retVal = new Result();
        retVal.valid = true;

        if(val.length() == 0){
            retVal.valid &= false;
            retVal.message = "You must enter a password!";
        }

        if(val.length() > 20){
            retVal.valid &= false;
            retVal.message = "Passwords can be a maximum of 20 characters";
        }

        return retVal;
    }

    public static Result Age(int age){
        Result retVal = new Result();
        retVal.valid = true;

        if(age > 122){
            retVal.valid &= false;
            retVal.message = "There's no way you're that old! =)";
        }

        if(age <= 0){
            retVal.valid &= false;
            retVal.message = "Please enter your age";
        }

        return retVal;
    }


    public static class Result{
        public String message;
        public boolean valid;
    }

}
