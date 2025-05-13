public class Polynomial{
    double [] coefficients;

    public Polynomial(){
        coefficients = new double[0];
    }
    public Polynomial(double [] coefficients){
        int len = coefficients.length;
        this.coefficients = new double[len];
        for(int i = 0; i<len; i++){
            this.coefficients[i] = coefficients[i];
        }
    }
    public Polynomial add(Polynomial added){
        int len1 = this.coefficients.length;
        int len2 = added.coefficients.length;
        double[] result;
        if(len1>len2){
            result = new double[len1];
            for(int i = 0; i<len2; i++){
                result[i] = this.coefficients[i]+added.coefficients[i];
            }
            for(int i = len2; i<len1; i++){
                result[i]=this.coefficients[i];
            }
            
        }else{
            result = new double[len2];
            for(int i = 0; i<len1; i++){
                result[i] = this.coefficients[i]+added.coefficients[i];
            }
            for(int i = len1; i<len2; i++){
                result[i]=added.coefficients[i];
            }

        }
        Polynomial sum = new Polynomial(result);


        return sum;
    }
    public double evaluate(double x){
        int len = this.coefficients.length;
        double ans = 0;
        for(int i = 0; i<len; i++){
            ans += this.coefficients[i]*Math.pow(x, i);
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
}