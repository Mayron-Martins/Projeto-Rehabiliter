package Controller.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversaodeDataParaPadraoDesignado {
    public Date parseDate(String data) throws ParseException {
        if(data==null||data.equals("")){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(data);
    }

    public String parseDate(Date data) throws ParseException {
        if(data == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.format(data);
    }

    public java.sql.Date getSqlDate(Date data) {
        if(data == null){
            return null;
        }
        return new java.sql.Date(data.getTime());
    }
    
    public String Datadesformatada(Date data) throws ParseException{
        String data_desformatada = parseDate(data);
        data_desformatada = data_desformatada.replace("/", "");
        return data_desformatada; 
    }
    
}
