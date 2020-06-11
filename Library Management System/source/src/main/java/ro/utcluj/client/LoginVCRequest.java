package ro.utcluj.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.RequestMessage;
import ro.utcluj.intermediar.actions.Transform;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginVCRequest {
    private ServerConnection serverConnection;

    public List<Account> getAccountCriteria(String username){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(username);
        RequestMessage lr = new RequestMessage("LOGIN_PASS", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Account>>() {}.getType();
        List<Account> accountCriteria = new Gson().fromJson(response, listOfMyClassObject);

        return accountCriteria;
    }
}
