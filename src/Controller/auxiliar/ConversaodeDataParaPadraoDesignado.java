package Controller.auxiliar;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
    
    public int idade(Date dataAniversairio){
        return this.periodoEntreDataAtual(dataAniversairio).getYears()*(-1);
    }
    
    public boolean aniversarianteDoDia(Date dataAniversario){
        Period aniversario = this.periodoEntreDataAtual(dataAniversario);
        return aniversario.getDays()==0&&aniversario.getMonths()==0;
    }
    
    public boolean anviversarianteMes(Date dataMenor){
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    String format = formato.format(dataMenor);
    LocalDate dataHoje = LocalDate.now();
    LocalDate dataAniversario = LocalDate.parse(format);
    return dataHoje.getMonthValue()==dataAniversario.getMonthValue();
    }
    
    private Period periodoEntreDataAtual(Date dataMenor){
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    String format = formato.format(dataMenor);
    LocalDate dataHoje = LocalDate.now();
    LocalDate dataAniversario = LocalDate.parse(format);
    Period between = Period.between(dataHoje, dataAniversario);
    return between;
    }
    
    
    public Date conversaoLocalforDate(LocalDate data){
        Date dataRetorno = Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return dataRetorno;
    }
    
    public LocalDate conversaoLocalforDate(Date data){
        LocalDate dataRetorno = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dataRetorno;
    }
}
