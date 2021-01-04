package cn.xjh.softwarereliability.domain;

import static java.lang.Math.abs;
/*JM模型假设与数据要求
程序固有错误数N为固定值；
各个错误是相互独立的，各次失效时间间隔也相互独立，每个错误导致系统失效发生的可能性大致相同；
测试中检测到的错误都被排除，每次排错只排除一个错误且排错时间不计，不引入新的错误
程序的失效率在每个失效时间间隔是常数，正比于程序中剩余的错误数

* */
public class JM {
    private int []data;							//失效时间间隔
    private double ex = 0.1;					//针对自变量x给定的误差精度范围
    private double ey = 0.1;					//针对自变量x给定的误差精度范围
    private double resN;						//系统错误总数点估计值
    private double resΦ;						//系统故障率点估计值
    private double p;							//中间计算值

    public JM(int[] data) {
        this.data = data;
    }

    public JM(int []data, double ex, double ey) {
        this.data = data;
        this.ex = ex;
        this.ey = ey;
    }

    public double getEx() {
        return ex;
    }

    public void setEx(double ex) {
        this.ex = ex;
    }

    public double getEy() {
        return ey;
    }

    public void setEy(double ey) {
        this.ey = ey;
    }

    public double getResN() {
        return resN;
    }

    public void setResN(double resN) {
        this.resN = resN;
    }

    public double getResΦ() {
        return resΦ;
    }

    public void setResΦ(double resΦ) {
        this.resΦ = resΦ;
    }

    private double F( double x) { 			//E{1/(N-i+1)} - n/(N-p) 求精度
        int n = data.length - 1;
        double tmp = 0;
        for (int i = 1; i <= n; i++) {
            tmp += 1 / (double) (x - i + 1);
        }
        tmp -= n / (x - p);
        return tmp;
    }

    private double fifth(double root) {//步骤5
        int n = data.length - 1;
        double N = root;								//得到N的点估计值
        double tmp = 0;
        for (int i = 1; i <= n; i++) {
            tmp += (i - 1) * (data[i] - data[i - 1]);
        }
        double res = n / (N * data[n] - tmp);
        resN = N;
        resΦ = res;									//得到fai的点估计值
        return res;
    }


    private double third(double l, double r) {			//步骤3
        double root = (l + r) / 2;
        if (abs(r - l) < ex) {							//若l,r只差绝对值在精度范围内，转步骤五
            return fifth(root);
        } else {
            //步骤4
            if (F( root) >= -ey && F( root) <= ey) {	//F(root) < |ey|则转步骤五
                return fifth(root);
            } else if (F( root) > ey) {
                l = root;
            } else {									//F(root) < -ey
                r = root;
            }
            return third( l, r);
        }
    }

    public void first() { 								//步骤1
        calculatorP();
        int n = data.length - 1;
        if (p > (n - 1) / 2) {							//若p < (n-1)/2则结束计算
            second(n - 1, n);
        }
    }

    private double second(double l, double r) {			//步骤2
        while (F(r) > ey) {								//若精度值大于最大值则重复该步骤，l和r逐渐增大
            l = r;
            r++;
        }
        if (F(r) <= -ey) {
            return third(l, r);							//精度小于最小值则转步骤三
        } else {										//精度在误差范围内则转到步骤五
            return fifth(r);
        }
    }

    private void calculatorP() {  		//计算p = E{(i-1)*(t[i] - t[i-1])}/t[n]
        double tmp = 0;
        int len = data.length - 1;
        for (int i = 1; i <= len; i++) {
            tmp += (i - 1) * (data[i] - data[i - 1]);
        }
        p = tmp / data[len];
    }
}
