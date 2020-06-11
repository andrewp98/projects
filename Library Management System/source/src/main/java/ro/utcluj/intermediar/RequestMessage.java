package ro.utcluj.intermediar;

import java.util.List;

//clasa asta o sa faca requesturile
public class RequestMessage {

    private String requestName;
    private String modelName;
    private List<String> args;

    public RequestMessage(){
        this.requestName = null;
        this.modelName = null;
        this.args = null;
    }


    public RequestMessage(String requestName, String modelName, List<String> args){
        this.requestName = requestName;
        this.modelName = modelName;
        this.args = args;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
