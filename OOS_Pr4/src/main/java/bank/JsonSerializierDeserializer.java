package bank;

import bank.exceptions.AttributeException;
import bank.exceptions.IncomingInterestException;
import bank.exceptions.OutgoingInterestException;
import com.google.gson.*;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

import java.lang.*;
import java.lang.reflect.Type;

public class JsonSerializierDeserializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.get("CLASSNAME").getAsString().equals("Payment")) {
            Payment payment;
            try {
                payment = new Payment(
                        jsonObject.get("date").getAsString(),
                        jsonObject.get("amount").getAsDouble(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("incomingInterest").getAsDouble(),
                        jsonObject.get("outgoingInterest").getAsDouble()
                );
            } catch (AttributeException e) {
                throw new RuntimeException(e);
            } catch (IncomingInterestException e) {
                throw new RuntimeException(e);
            } catch (OutgoingInterestException e) {
                throw new RuntimeException(e);
            }
            return payment;
        }
        else if(jsonObject.get("CLASSNAME").getAsString().equals("IncomingTransfer"))
        {
            IncomingTransfer incomingTransfer;
            try {
                 incomingTransfer = new IncomingTransfer(
                        jsonObject.get("date").getAsString(),
                        jsonObject.get("amount").getAsDouble(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("sender").getAsString(),
                        jsonObject.get("recipient").getAsString()
                );
            } catch (AttributeException e) {
                throw new RuntimeException(e);
            }
            return incomingTransfer;
        }
        else if(jsonObject.get("CLASSNAME").getAsString().equals("OutgoingTransfer"))
        {
            OutgoingTransfer outgoingTransfer;
            try {
                outgoingTransfer = new OutgoingTransfer(
                        jsonObject.get("date").getAsString(),
                        jsonObject.get("amount").getAsDouble(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("sender").getAsString(),
                        jsonObject.get("recipient").getAsString()
                );
            } catch (AttributeException e) {
                throw new RuntimeException(e);
            }
            return outgoingTransfer;
        }
        return null;
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonTrans = new JsonObject();
        if(transaction instanceof Transfer transfer)
        {
            jsonTrans.addProperty("sender", transfer.getSender());
            jsonTrans.addProperty("recipient", transfer.getRecipient());
        }
        else if(transaction instanceof Payment payment)
        {
            jsonTrans.addProperty("incomingInterest", payment.getIncomingInterest());
            jsonTrans.addProperty("outgoingInterest", payment.getOutgoingInterest());
        }
        jsonTrans.addProperty("date", transaction.getDate());
        jsonTrans.addProperty("amount", transaction.getAmount());
        jsonTrans.addProperty("description", transaction.getDescription());
        jsonObject.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        jsonObject.add("INSTANCE", jsonTrans);

        return jsonObject;
    }
}
