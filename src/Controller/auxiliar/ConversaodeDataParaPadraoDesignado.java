package Controller.auxiliar;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    
    public Time parseHour(String hora) throws ParseException{
      if(hora.equals("")){
          return null;
      }
      DateFormat formato = new SimpleDateFormat("HH:mm");
      return new java.sql.Time(formato.parse(hora).getTime());
    }
    
    public String parseHour (Time hora){
      /*if(hora == null){
          return "";
      }*/
      int tamanhoHora = hora.toString().length()-3;
      String formato = hora.toString().substring(0, tamanhoHora);
      
        return formato;
    }
    
    public int obterAnoAtual (){
        int anoAtual;
        Calendar calendario = GregorianCalendar.getInstance();
        anoAtual = calendario.get(Calendar.YEAR);
        return anoAtual;
    }
    
    public int verificarMaiorIdade(Date dataAniversario){
        long idadeAtual;
        Calendar diaAtual = Calendar.getInstance();
        Calendar diaAniversario = new GregorianCalendar();
        diaAniversario.setTime(dataAniversario);
        
        if(diaAtual.get(Calendar.DAY_OF_YEAR)==diaAniversario.get(Calendar.DAY_OF_YEAR)
                && diaAtual.get(Calendar.MONTH)==diaAniversario.get(Calendar.MONTH)){
            idadeAtual = diaAtual.get(Calendar.YEAR)-diaAniversario.get(Calendar.YEAR);
            return (int) idadeAtual;
        }
        
        long quantDiasDaDataAtual =  diaAtual.getTimeInMillis();
        long quantDiasAniversario =  dataAniversario.getTime();
        
        idadeAtual = (quantDiasDaDataAtual-quantDiasAniversario)/(1000*3600*24*365);
        
        return (int) idadeAtual;
    }
    
}
