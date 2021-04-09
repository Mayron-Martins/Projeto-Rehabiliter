/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

/**
 *
 * @author Mayro
 */
public class Matriculas {
    private int codBanco;
    private int codTurma;
    private int codAluno;
    private int anoMatricula;
    private String matricula;

    public Matriculas(int codBanco, int codTurma, int codAluno, int anoMatricula, String matricula) {
        this.codBanco = codBanco;
        this.codTurma = codTurma;
        this.codAluno = codAluno;
        this.anoMatricula = anoMatricula;
        this.matricula = matricula;
    }
    
    public Matriculas(int codTurma, int anoMatricula, String matricula) {
        this.codTurma = codTurma;
        this.anoMatricula = anoMatricula;
        this.matricula = matricula;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public int getCodTurma() {
        return codTurma;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public int getAnoMatricula() {
        return anoMatricula;
    }

    public String getMatricula() {
        return matricula;
    }
    
    
}
