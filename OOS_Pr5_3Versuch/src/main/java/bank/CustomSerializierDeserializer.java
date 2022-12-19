package bank;

import bank.exceptions.AttributeException;
import bank.exceptions.IncomingInterestException;
import bank.exceptions.OutgoingInterestException;
import com.google.gson.*;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
//import org.jetbrains.annotations.NotNull;

import java.lang.*;
import java.lang.reflect.Type;

/**
 * Die Klasse ist eine modifizierte Version der JsonSerializer/Deserializer Klasse
 */
public class CustomSerializierDeserializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    /**
     * Die Methode gibt die Struktur des serialisierendes Objekt vor
     * @param transaction Objekte von der Klasse Transaction
     * @return gibt das zu serialisierende Objekt (vom payment oder transfer) zurück
     */
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObjectType = new JsonObject();

        jsonObject.addProperty("date", transaction.getDate());
        jsonObject.addProperty("amount", transaction.getAmount());
        jsonObject.addProperty("description", transaction.getDescription());
        if(transaction instanceof Transfer transfer){
            jsonObject.addProperty("sender", transfer.getSender());
            jsonObject.addProperty("recipient", transfer.getRecipient());
        } else if(transaction instanceof Payment payment){
            jsonObject.addProperty("incomingInterest", payment.getIncomingInterest());
            jsonObject.addProperty("outgoingInterest", payment.getOutgoingInterest());
        }
        jsonObjectType.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        jsonObjectType.add("INSTANCE", jsonObject);

        return jsonObjectType;
    }

    /**
     * Die Methode liest/deserialisiert .json Datei
     * @param jsonElement das einzulesende .json Objekt
     * @return payment das eingelesene Payment Objekt zurückgeben
     * @return incomingTransfer das eingelesene IncomingTransfer Objekt zurückgeben
     * @return outgoingTransfer das eingelesene OutgoingTransfer Objekt zurückgeben
     * @throws JsonParseException
     */
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObjectType = jsonElement.getAsJsonObject();
        if(jsonObjectType.get("CLASSNAME").getAsString().equals("Payment")){
            Payment payment;
            try {
                payment = new Payment(jsonObjectType.get("date").getAsString(),
                        jsonObjectType.get("amount").getAsDouble(),
                        jsonObjectType.get("description").getAsString(),
                        jsonObjectType.get("incomingInterest").getAsDouble(),
                        jsonObjectType.get("outgoingInterest").getAsDouble()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return payment;
        } else if(jsonObjectType.get("CLASSNAME").getAsString().equals("OutgoingTransfer")){
            {
                OutgoingTransfer outgoingTransfer;
                try {
                    outgoingTransfer = new OutgoingTransfer(
                            jsonObjectType.get("date").getAsString(),
                            jsonObjectType.get("amount").getAsDouble(),
                            jsonObjectType.get("description").getAsString(),
                            jsonObjectType.get("sender").getAsString(),
                            jsonObjectType.get("recipient").getAsString()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return outgoingTransfer;
            }
        } else if (jsonObjectType.get("CLASSNAME").getAsString().equals("IncomingTransfer")){
            IncomingTransfer incomingTransfer;
            try {
                incomingTransfer = new IncomingTransfer(
                        jsonObjectType.get("date").getAsString(),
                        jsonObjectType.get("amount").getAsDouble(),
                        jsonObjectType.get("description").getAsString(),
                        jsonObjectType.get("sender").getAsString(),
                        jsonObjectType.get("recipient").getAsString()
                );
            } catch (Exception e){
                throw new RuntimeException(e);
            }
            return incomingTransfer;
        }
        return null;
    }
}

