package Controller.auxiliar;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversaodeDataParaPadraoDesignado {
    //Conversão de data para o formato dd/MM/YYYY
    public Date parseDate(String data){
        try{
            if(data==null||data.equals("")){
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(data);
        } catch (ParseException ex) {
            gerarLog(ex);
            return null;
        } 
    }

    public String parseDate(Date data){
        if(data == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.format(data);
    }
    //Conversao de data para o formato do banco
    public java.sql.Date getSqlDate(Date data) {
        if(data == null){
            return null;
        }
        return new java.sql.Date(data.getTime());
    }
    
    //Conversão de dia e hora para dd/HH/yyyy HH:mm:ss
    public Date parseDateAndTime(Timestamp data){
        try{
            if(data==null){
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setLenient(false);
            return sdf.parse(sdf.format(data));
        } catch (ParseException ex) {
            gerarLog(ex);
            return null;
        }    
    }
    
        public String parseDateAndTime(Date data){
        if(data==null||data.equals("")){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(data);
    }
    
    //Conversão de Data e Hora para o formato do Banco
    public java.sql.Timestamp getSqlDateAndTime(Date data) {
        if(data == null){
            return null;
        }
        return new java.sql.Timestamp(data.getTime());
    }
    
    //Somente Números
    public String Datadesformatada(Date data){
        String data_desformatada = parseDate(data);
        data_desformatada = data_desformatada.replace("/", "");
        return data_desformatada; 
    }
    
    //Conversão de hora para o formato HH:mm
    public Time parseHour(String hora){
      try{
          if(hora.equals("")){
              return null;
          }
          DateFormat formato = new SimpleDateFormat("HH:mm");
          return new java.sql.Time(formato.parse(hora).getTime());
      } catch (ParseException ex) {
            gerarLog(ex);
            return null;
        }    
    }
    
    public String parseHour (Time hora){
      if(hora == null){
          return null;
      }
      int tamanhoHora = hora.toString().length()-3;
      String formato = hora.toString().substring(0, tamanhoHora);
      
      return formato;
    }
    
    
    public int idade(Date dataAniversairio){
        return this.periodoEntreDataAtual(dataAniversairio).getYears()*(-1);
    }
    
    public boolean aniversarianteDoDia(Date dataAniversario){
        Period aniversario = periodoEntreDataAtual(dataAniversario);
        return aniversario.getDays()==0&&aniversario.getMonths()==0;
    }
    
    public boolean anviversarianteMes(Date dataMenor){
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    String format = formato.format(dataMenor);
    LocalDate dataHoje = LocalDate.now();
    LocalDate dataAniversario = LocalDate.parse(format);
    return dataHoje.getMonthValue()==dataAniversario.getMonthValue();
    }
    
    public Period periodoEntreDataAtual(Date dataMenor){
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
        if(data==null){return null;}
        LocalDate dataRetorno = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dataRetorno;
    }
    
    public Long dataEHoraCodificada(){
        LocalDateTime dataAtual = LocalDateTime.now();
    Date data = Date.from(dataAtual.atZone(ZoneId.systemDefault()).toInstant());
    
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String dataFormatada = format.format(data);
    dataFormatada = dataFormatada.replaceAll("/", "");
    dataFormatada = dataFormatada.replaceAll(":", "");
    dataFormatada = dataFormatada.replaceAll(" ", "");
    return Long.valueOf(dataFormatada);
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
   
}
