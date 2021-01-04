package cn.xjh.softwarereliability.domain;

public class GO {
    double sig ;
    double xl ;
    double xr ;
    double xm ;
    double d ;
    double a ;
    double b ;
    double data[]= new double[1000];
    int data_count = 1000;
    double set_sig() {
        double setsig = 1.0;
        return setsig;
    }

    void set_data(GO self ) {

    }
    double coculate_d(GO self) {   //求误差D
        double tempD = 0;
        for (double d:self.data) {
            tempD+=d;
        }
        return (tempD / self.data[data_count - 1] * self.data_count);
    }

    GO go() {                      //步骤一
        GO self = new GO();  //实例化类
        self.sig = 0.0;
        self.xl = 0.0;
        self.xr = 0.0;
        self.xm = 0.0;
        self.d = 0.0;
        self.a = 0.0;
        self. b = 0.0;
        self.sig = set_sig();          //给sigma赋值

        d = coculate_d(self);           //计算D
        if(d > 0 && d < 0.5) {          //如果0<d<0.5,则转步骤2，否则转步骤5
            xl = (1 - 2 * d) / 2;
            xr = 1 / d;
            func_2(self);
        }
        return self;                    //return a,b
    }

    void func_2(GO self) {         //步骤二：计算xm=（xl+xr）/2
        self.xm = (self.xl + self.xr) / 2;
        if(Math.abs(self.xl - self.xr) <= self.sig) {
            func_4(self);               //如果|xr-xl|<sig,跳转到步骤4
        }
        else
            func_3(self);               //否则跳转到步骤三
    }

    void func_3(GO self) {         //步骤三：计算f
        double f = (1 - self.d * self.xm) * Math.exp(self.xm) + (self.d - 1) * self.xm - 1;
        if(f > self.sig) {              //如果f > sig,则xl = xm
            self.xl = self.xm;
            func_2(self);
        }
        else {
            if(f < -self.sig) {         //如果f<-sig,则xr = xm
                self.xr = self.xm;
                func_2(self);           //跳转到步骤二
            }
        }
    }

    void func_4(GO self) {         //步骤四：求a和b
        self.b = self.xm / self.data[self.data_count - 1];
        self.a = self.data_count / (1 - Math.exp(-self.b * self.data[self.data_count - 1]));
    }
}
