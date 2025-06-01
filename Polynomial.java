import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial{
    double [] coefficients;
    int [] exponents;

    public Polynomial(){
        coefficients = new double[0];
        exponents = new int[0];
    }
    public Polynomial(double [] coefficients, int [] exponents){
        int len = coefficients.length;
        this.coefficients = new double[len];
        this.exponents = new int[len];
        for(int i = 0; i<len; i++){
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }
    public Polynomial(File file){
        try {
            Scanner input = new Scanner(file);
            String poly = input.nextLine();
            input.close();

            if(poly.charAt(0)!='-'){
                poly = '+'+poly;
            }

            int num_of_terms = 0;
            for(int i = 0; i<poly.length(); i++){
                if(poly.charAt(i)=='+'||poly.charAt(i)=='-'){
                    num_of_terms++;
                }
            }

            coefficients = new double[num_of_terms];
            exponents = new int[num_of_terms];

            int index = 0;
            int i = 0;
            while( i < poly.length()){
                char sign = poly.charAt(i);
                int j = i+1;
                while(j<poly.length() && poly.charAt(j) != '+' && poly.charAt(j) != '-'){
                    j++;
                }
                String current_term = poly.substring(i+1, j);
                double coefficient;
                int exponent;
                int index_of_variable = current_term.indexOf("x");
                if(index_of_variable == -1){
                    coefficient = Double.parseDouble(current_term);
                    exponent= 0;
                }
                else if(index_of_variable == 0){
                    coefficient = 1;
                    exponent = Integer.parseInt(current_term.substring(index_of_variable+1));
                }
                else{
                    coefficient = Double.parseDouble(current_term.substring(0, index_of_variable));
                    exponent = Integer.parseInt(current_term.substring(index_of_variable+1));
                }
                if(sign == '-'){
                    coefficient = -coefficient;
                }

                i = j;
                coefficients[index] = coefficient;
                exponents[index] = exponent;
                index++;
            }


        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    public void saveToFile(String name){
        try {
            PrintStream output = new PrintStream(name);
            for(int i = 0; i < coefficients.length; i++){
                double coefficient = coefficients[i];
                int exponent = exponents[i];

                if(coefficients[i] != 0){

                    if(i == 0){
                        output.print(coefficient);
                    }
                    else{
                        if(coefficient>=0){
                            output.print("+");
                        }
                        output.print(coefficient);
                    }
                    if(exponent != 0){
                        output.print("x" + exponent);
                    }
                }
            }
            output.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }


    public Polynomial add(Polynomial added) {
        int len1 = this.coefficients.length;
        int len2 = added.coefficients.length;

        double[] tempCoeffs = new double[len1 + len2];
        int[] tempExps = new int[len1 + len2];
        int count = 0;

        for (int i = 0; i < len1; i++) {
            tempCoeffs[count] = this.coefficients[i];
            tempExps[count] = this.exponents[i];
            count++;
        }

        for (int i = 0; i < len2; i++) {
            int exp = added.exponents[i];
            double coeff = added.coefficients[i];
            boolean matched = false;

            for (int j = 0; j < count; j++) {
                if (tempExps[j] == exp) {
                    tempCoeffs[j] += coeff;
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                tempCoeffs[count] = coeff;
                tempExps[count] = exp;
                count++;
            }
    }

    double[] finalCoeffs = new double[count];
    int[] finalExps = new int[count];
    for (int i = 0; i < count; i++) {
        finalCoeffs[i] = tempCoeffs[i];
        finalExps[i] = tempExps[i];
    }

    return new Polynomial(finalCoeffs, finalExps);
}

    public double evaluate(double x){
        int len = this.coefficients.length;
        double ans = 0;
        for(int i = 0; i<len; i++){
            ans += this.coefficients[i]*Math.pow(x, this.exponents[i]);
        }
        return ans;
    }
    public boolean hasRoot(double x){
        if(evaluate(x)==0){
            return true;
        }
        else{
            return false;
        }
    }

    public Polynomial multiply(Polynomial poly){
        int len1 = this.coefficients.length;
        int len2 = poly.coefficients.length;

        double[] result_coefficients = new double [len1*len2];
        int[] result_exponents = new int [len1*len2];
        int[] different_exponents = new int [len1*len2];
        int current = 0;

        double[] simplified_coefficients;
        int[] simplified_exponents;

        for(int i = 0; i < len1; i++){
            for(int j = 0; j<len2; j++){
                result_coefficients[current] = this.coefficients[i]*poly.coefficients[j];
                result_exponents[current] = this.exponents[i]+poly.exponents[j];
                current++;
            }
        }

        current = 0;

        boolean found = false;
        for(int i = 0; i<len1*len2; i++){
            found = false;
            for(int j =0; j<len1*len2; j++){
                if(result_exponents[i] == different_exponents[j]){
                    found = true;
                }
            }
            if(!found){
                different_exponents[current] = result_exponents[i];
                current++;
            }
        }
        simplified_coefficients = new double [current];
        simplified_exponents = new int [current];

        for(int i = 0; i<current; i++){
            simplified_exponents[i] = different_exponents[i];
            simplified_coefficients[i] = 0;
        }

        for(int i = 0; i<len1*len2; i++){
            for(int j=0; j<current; j++){
                if(result_exponents[i] == simplified_exponents[j]){
                    simplified_coefficients[j] += result_coefficients[i];
                }
            }
        }
        Polynomial product = new Polynomial(simplified_coefficients, simplified_exponents);
        return product; 

    }
}